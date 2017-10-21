package ru.vk_hack.museumguide.presentation.ar

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.google.ar.core.*
import ru.vk_hack.museumguide.R
import java.io.IOException
import java.util.*
import java.util.concurrent.ArrayBlockingQueue
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created by Vitaly Nikonorov on 21.10.17.
 * email@nikonorov.net
 */
class ARActivity: AppCompatActivity(), GLSurfaceView.Renderer {
    private val TAG = ARActivity::class.java!!.getSimpleName()
    // Rendering. The Renderers are created here, and initialized when the GL surface is created.
    private var mSurfaceView: GLSurfaceView? = null

    private var mDefaultConfig: Config? = null
    private var mSession: Session? = null
    private val mBackgroundRenderer = BackgroundRenderer()
    private var mGestureDetector: GestureDetector? = null
    private var mLoadingMessageSnackbar: Snackbar? = null

    private val mVirtualObject = ObjectRenderer()
    private val mVirtualObjectShadow = ObjectRenderer()
    private val mPlaneRenderer = PlaneRenderer()
    private val mPointCloud = PointCloudRenderer()

    // Temporary matrix allocated here to reduce number of allocations for each frame.
    private val mAnchorMatrix = FloatArray(16)

    // Tap handling and UI.
    private val mQueuedSingleTaps = ArrayBlockingQueue<MotionEvent>(16)
    private val mTouches = ArrayList<PlaneAttachment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ar)
        mSurfaceView = findViewById(R.id.surfaceview)

        mSession = Session(/*context=*/this)

        // Create default config, check is supported, create session from that config.
        mDefaultConfig = Config.createDefaultConfig()
        if (mSession != null && !mSession!!.isSupported(mDefaultConfig)) {
            Toast.makeText(this, "This device does not support AR", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        // Set up tap listener.
        mGestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                onSingleTap(e)
                return true
            }

            override fun onDown(e: MotionEvent): Boolean {
                return true
            }
        })

        mSurfaceView?.setOnTouchListener(View.OnTouchListener { v, event -> mGestureDetector!!.onTouchEvent(event) })

        // Set up renderer.
        mSurfaceView?.setPreserveEGLContextOnPause(true)
        mSurfaceView?.setEGLContextClientVersion(2)
        mSurfaceView?.setEGLConfigChooser(8, 8, 8, 8, 16, 0) // Alpha used for plane blending.
        mSurfaceView?.setRenderer(this)
        mSurfaceView?.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY)
    }

    override fun onResume() {
        super.onResume()
        // ARCore requires camera permissions to operate. If we did not yet obtain runtime
        // permission on Android M and above, now is a good time to ask the user for it.
        if (CameraPermissionHelper.hasCameraPermission(this)) {
            showLoadingMessage()
            // Note that order matters - see the note in onPause(), the reverse applies here.
            mSession?.resume(mDefaultConfig)
            mSurfaceView?.onResume()
        } else {
            CameraPermissionHelper.requestCameraPermission(this)
        }
    }

    public override fun onPause() {
        super.onPause()
        // Note that the order matters - GLSurfaceView is paused first so that it does not try
        // to query the session. If Session is paused before GLSurfaceView, GLSurfaceView may
        // still call mSession.update() and get a SessionPausedException.
        mSurfaceView?.onPause()
        mSession?.pause()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, results: IntArray) {
        if (!CameraPermissionHelper.hasCameraPermission(this)) {
            Toast.makeText(this,
                    "Camera permission is needed to run this application", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            // Standard Android full-screen functionality.
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

    private fun onSingleTap(e: MotionEvent) {
        // Queue tap if there is space. Tap is lost if queue is full.
        mQueuedSingleTaps.offer(e)
    }

    override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
        GLES20.glClearColor(0.1f, 0.1f, 0.1f, 1.0f)

        // Create the texture and pass it to ARCore session to be filled during update().
        mBackgroundRenderer.createOnGlThread(/*context=*/this)
        mSession?.setCameraTextureName(mBackgroundRenderer.textureId)

        // Prepare the other rendering objects.
        try {
            mVirtualObject.createOnGlThread(/*context=*/this, "flower_decor.obj", "flower_decor.png")
            mVirtualObject.setMaterialProperties(0.0f, 3.5f, 1.0f, 6.0f)

            mVirtualObjectShadow.setBlendMode(ObjectRenderer.BlendMode.Shadow)
            mVirtualObjectShadow.setMaterialProperties(1.0f, 0.0f, 0.0f, 1.0f)
        } catch (e: IOException) {
            Log.e(TAG, "Failed to read obj file")
        }

        try {
            mPlaneRenderer.createOnGlThread(/*context=*/this, "trigrid.png")
        } catch (e: IOException) {
            Log.e(TAG, "Failed to read plane texture")
        }

        mPointCloud.createOnGlThread(/*context=*/this)
    }

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
        // Notify ARCore session that the view size changed so that the perspective matrix and
        // the video background can be properly adjusted.
        mSession?.setDisplayGeometry(width.toFloat(), height.toFloat())
    }

    override fun onDrawFrame(gl: GL10) {
        // Clear screen to notify driver it should not load any pixels from previous frame.
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)

        try {
            // Obtain the current frame from ARSession. When the configuration is set to
            // UpdateMode.BLOCKING (it is by default), this will throttle the rendering to the
            // camera framerate.
            if (mSession == null) return
            val frame = mSession!!.update()

            // Handle taps. Handling only one tap per frame, as taps are usually low frequency
            // compared to frame rate.
            val tap = mQueuedSingleTaps.poll()
            if (tap != null && frame.trackingState == Frame.TrackingState.TRACKING) {
                for (hit in frame.hitTest(tap)) {
                    // Check if any plane was hit, and if it was hit inside the plane polygon.
                    if (hit is PlaneHitResult && hit.isHitInPolygon && mSession != null) {
                        // Cap the number of objects created. This avoids overloading both the
                        // rendering system and ARCore.
                        if (mTouches.size >= 16) {
                            mSession!!.removeAnchors(Arrays.asList(mTouches[0].anchor))
                            mTouches.removeAt(0)
                        }
                        // Adding an Anchor tells ARCore that it should track this position in
                        // space. This anchor will be used in PlaneAttachment to place the 3d model
                        // in the correct position relative both to the world and to the plane.
                        mTouches.add(PlaneAttachment(
                                hit.plane,
                                mSession!!.addAnchor(hit.getHitPose())))

                        // Hits are sorted by depth. Consider only closest hit on a plane.
                        break
                    }
                }
            }

            // Draw background.
            mBackgroundRenderer.draw(frame)

            // If not tracking, don't draw 3d objects.
            if (frame.trackingState == Frame.TrackingState.NOT_TRACKING) {
                return
            }

            // Get projection matrix.
            val projmtx = FloatArray(16)
            mSession?.getProjectionMatrix(projmtx, 0, 0.1f, 100.0f)

            // Get camera matrix and draw.
            val viewmtx = FloatArray(16)
            frame.getViewMatrix(viewmtx, 0)

            // Compute lighting from average intensity of the image.
            val lightIntensity = frame.lightEstimate.pixelIntensity

            // Visualize tracked points.
            mPointCloud.update(frame.pointCloud)
            mPointCloud.draw(frame.pointCloudPose, viewmtx, projmtx)

            // Check if we detected at least one plane. If so, hide the loading message.
            if (mLoadingMessageSnackbar != null && mSession != null) {
                for (plane in mSession!!.getAllPlanes()) {
                    if (plane.type == com.google.ar.core.Plane.Type.HORIZONTAL_UPWARD_FACING && plane.trackingState == Plane.TrackingState.TRACKING) {
                        hideLoadingMessage()
                        break
                    }
                }
            }

            // Visualize planes.
            mPlaneRenderer.drawPlanes(mSession!!.getAllPlanes(), frame.pose, projmtx)

            // Visualize anchors created by touch.
            val scaleFactor = 1.0f
            for (planeAttachment in mTouches) {
                if (!planeAttachment.isTracking) {
                    continue
                }
                // Get the current combined pose of an Anchor and Plane in world space. The Anchor
                // and Plane poses are updated during calls to session.update() as ARCore refines
                // its estimate of the world.
                planeAttachment.pose.toMatrix(mAnchorMatrix, 0)

                // Update and draw the model and its shadow.
                mVirtualObject.updateModelMatrix(mAnchorMatrix, scaleFactor)
                mVirtualObjectShadow.updateModelMatrix(mAnchorMatrix, scaleFactor)
                mVirtualObject.draw(viewmtx, projmtx, lightIntensity)
                mVirtualObjectShadow.draw(viewmtx, projmtx, lightIntensity)
            }

        } catch (t: Throwable) {
            // Avoid crashing the application due to unhandled exceptions.
            Log.e(TAG, "Exception on the OpenGL thread", t)
        }
    }

    private fun showLoadingMessage() {
        runOnUiThread {
            mLoadingMessageSnackbar = Snackbar.make(
                    this@ARActivity.findViewById(android.R.id.content),
                    "Searching for surfaces...", Snackbar.LENGTH_INDEFINITE)
            mLoadingMessageSnackbar?.getView()?.setBackgroundColor(-0x40cdcdce)
            mLoadingMessageSnackbar?.show()
        }
    }

    private fun hideLoadingMessage() {
        runOnUiThread {
            mLoadingMessageSnackbar?.dismiss()
            mLoadingMessageSnackbar = null
        }
    }

}

package ru.vk_hack.museumguide.presentation.feed

import android.Manifest
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.vk_hack.museumguide.BuildConfig
import ru.vk_hack.museumguide.R
import ru.vk_hack.museumguide.data.Network
import ru.vk_hack.museumguide.databinding.ActivityFeedBinding
import ru.vk_hack.museumguide.utils.ImageUtils
import java.io.File


class FeedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFeedBinding

    private lateinit var feedRecycler: RecyclerView

    private val adapter = FeedAdapter()
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_feed)
        binding.recognizeBtn.setOnClickListener {
            recognizeBase()
        }
        feedRecycler = binding.feedList
        feedRecycler.layoutManager = LinearLayoutManager(this)
        feedRecycler.adapter = adapter

        val hasWriteExtStorePermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (hasWriteExtStorePermission != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        REQUEST_CODE_WRITE)
            }
            return
        }
//        disposables.add(Network
//                .getPaintings()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({it -> Log.i("TAG", it.toString())},
//                        { error -> error.printStackTrace()}
//        ))
        return
    }

    private lateinit var file : File
    var progress : ProgressDialog? = null
    private fun recognizeFile() {
        file = File(filesDir, "my_images/" + System.currentTimeMillis().toString())
        val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, file)
        startActivityForResult(cameraIntent, CAMERA_REQUEST_FILE)
    }

    private fun recognizeBase() {
        file = File(filesDir, System.currentTimeMillis().toString())
        val uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID, file)
        val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        progress = ProgressDialog(this)
        progress?.setMessage("Обработка изображения...")
        progress?.show()
        startActivityForResult(cameraIntent, CAMERA_REQUEST_BASE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            CAMERA_REQUEST_BASE -> {
                val disposable = Network.uploadPhoto(file.absolutePath)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe({ it ->
                                            Log.d("res", it.toString())
                                            progress?.dismiss()
                                        }, { error ->
                                            error.printStackTrace()
                                            progress?.dismiss()
                                        })
                disposables.add(disposable)
            }
            CAMERA_REQUEST_FILE -> {
                if (data == null) return
                file //
                val disposable = Network.uploadPhoto(file.absolutePath)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe({ it ->
                                            System.out.print(it.toString())
                                        }, { error ->
                                            error.printStackTrace()
                                        })
                disposables.add(disposable)
            }
        }
    }

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }

    companion object {
        private const val CAMERA_REQUEST_BASE = 1
        private const val CAMERA_REQUEST_FILE = 3
        private const val REQUEST_CODE_WRITE = 2
    }
}

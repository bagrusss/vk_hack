package ru.vk_hack.museumguide.presentation.ar

import com.google.ar.core.Anchor
import com.google.ar.core.Plane
import com.google.ar.core.Pose

/**
 * Created by Vitaly Nikonorov on 21.10.17.
 * email@nikonorov.net
 */

class PlaneAttachment(private val mPlane: Plane, val anchor: Anchor) {

    // Allocate temporary storage to avoid multiple allocations per frame.
    private val mPoseTranslation = FloatArray(3)
    private val mPoseRotation = FloatArray(4)

    /*true if*/ val isTracking: Boolean
        get() =
            mPlane.trackingState == Plane.TrackingState.TRACKING && anchor.trackingState == Anchor.TrackingState.TRACKING

    val pose: Pose
        get() {
            val pose = anchor.pose
            pose.getTranslation(mPoseTranslation, 0)
            pose.getRotationQuaternion(mPoseRotation, 0)
            mPoseTranslation[1] = mPlane.centerPose.ty()
            return Pose(mPoseTranslation, mPoseRotation)
        }
}

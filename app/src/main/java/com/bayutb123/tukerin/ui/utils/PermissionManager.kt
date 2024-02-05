package com.bayutb123.tukerin.ui.utils

import android.Manifest
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher

object PermissionManager {
    fun getGalleryPermissions() : Array<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            arrayOf(
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED,
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(
                Manifest.permission.READ_MEDIA_IMAGES,
            )
        } else {
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    fun requestPermission(permissions: Array<String>, launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>) {
        launcher.launch(permissions)
    }
}
package com.bayutb123.tukerin.core.utils

import android.Manifest
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher

object PermissionManager {
    fun getPermissions(type: Type) : Array<String> {
        when(type) {
            Type.GALLERY -> {
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
            Type.LOCATION -> {
                return arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
            }
        }
    }

    fun requestPermission(permissions: Array<String>, launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>) {
        launcher.launch(permissions)
    }

    enum class Type {
        GALLERY,
        LOCATION
    }
}
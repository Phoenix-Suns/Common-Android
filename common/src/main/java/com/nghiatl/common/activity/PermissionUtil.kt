package com.nghiatl.common.activity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.util.*

var REQUEST_ALL_PERMISSION = 1
var listPermissions = arrayOf(
    Manifest.permission.CAMERA,
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE
)

object PermissionUtil {

    /**
     * Hiện yêu cầu Nhiều Permission cùng 1 lúc
     * @param activity
     * @param permissions danh sách Permission như trên
     * @return Permissions not granted
     */
    fun requestAllPermission(
        activity: Activity,
        permissions: Array<String>,
        requestCode: Int
    ): Array<String> {

        // --- Request Permission ---
        val permissionsToRequest = getPermissionNotGranted(activity, permissions)
        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(activity, permissionsToRequest, requestCode)
        }
        return permissionsToRequest
    }

    fun requestAllPermission(
        fragment: Fragment,
        permissions: Array<String>,
        requestCode: Int
    ): Array<String> {

        // --- Request Permission ---
        val permissionsToRequest = getPermissionNotGranted(fragment.requireContext(), permissions)
        if (permissionsToRequest.isNotEmpty()) {
            fragment.requestPermissions(permissionsToRequest, requestCode)
        }
        return permissionsToRequest
    }

    fun requestAllPermission(
        activity: AppCompatActivity,
        permissions: Array<String>,
        requestCode: Int
    ): Array<String> {

        // --- Request Permission ---
        val permissionsToRequest = getPermissionNotGranted(activity, permissions)
        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(activity, permissionsToRequest, requestCode)

        }
        return permissionsToRequest
    }

    /**
     * Check list Permission granted
     * @param context
     * @param permissions
     * @return
     */
    fun getPermissionNotGranted(context: Context, permissions: Array<String>): Array<String> {

        val permissionsToRequest = ArrayList<String>()
        for (i in permissions.indices) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    permissions[i]
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsToRequest.add(permissions[i])
            }
        }
        return permissionsToRequest.toTypedArray()
    }

    fun hasAllPermissionsGranted(grantResults: IntArray): Boolean {
        for (grantResult in grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false
            }
        }
        return true
    }
}
package com.example.democommon.ui.common_library.samples

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.democommon.R
import com.nghiatl.common.activity.PermissionUtil
import kotlinx.android.synthetic.main.fragment_check_permissions.*

var listPermissions = arrayOf(
    Manifest.permission.INTERNET,
    Manifest.permission.READ_EXTERNAL_STORAGE,
)

const val REQUEST_CODE_PERMISSION = 1001

class CheckPermissionsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_check_permissions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Check
        buttonCheckPermission.setOnClickListener {
            val permissionNotGranted = PermissionUtil.getPermissionNotGranted(
                requireContext(),
                listPermissions
            )
            textViewStatus.text =
                if (permissionNotGranted.isNotEmpty())
                    "NOT Allow"
                else
                    "Allowed"
        }

        // Request
        buttonRequestPermission.setOnClickListener {
            textViewStatus.text = ""

            val permissionNotGranted = PermissionUtil.getPermissionNotGranted(
                requireContext(),
                listPermissions
            )
            if (permissionNotGranted.isNotEmpty()) {
                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected. In this UI,
                // include a "cancel" or "no thanks" button that allows the user to
                // continue using your app without granting the permission.
                PermissionUtil.requestAllPermission(this, listPermissions, REQUEST_CODE_PERMISSION)
            } else {
                doNext()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (PermissionUtil.hasAllPermissionsGranted(grantResults)) {
            textViewStatus.text = "Allowed (onRequestPermissionsResult)"
            doNext()
        } else {
            textViewStatus.text = "NOT Allowed (onRequestPermissionsResult)"
            // Explain to the user that the feature is unavailable because
            // the features requires a permission that the user has denied.
            // At the same time, respect the user's decision. Don't link to
            // system settings in an effort to convince the user to change
            // their decision.
        }
    }

    private fun doNext() {

    }
}
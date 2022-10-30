package com.example.democommon.ui.common_library.samples

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.democommon.R
import com.nghiatl.common.activity.PermissionUtil
import kotlinx.android.synthetic.main.fragment_check_permissions.*

var listPermissions = arrayOf(
    Manifest.permission.INTERNET,
    Manifest.permission.READ_EXTERNAL_STORAGE,
)

class CheckPermissionsFragment : Fragment() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->

        // Handle Permission granted/rejected
        permissions.entries.forEach {
            val permissionName = it.key
            val isGranted = it.value
            if (isGranted) {

                // Permission is granted
                textViewStatus.text = textViewStatus.text.toString() + "\n Allowed $permissionName"
                doNext()
            } else {

                // Permission is denied
                textViewStatus.text = textViewStatus.text.toString() + "\n NOT Allowed $permissionName"

                // Explain to the user that the feature is unavailable because
                // the features requires a permission that the user has denied.
                // At the same time, respect the user's decision. Don't link to
                // system settings in an effort to convince the user to change
                // their decision.
            }
        }

        /*if (PermissionUtil.hasAllPermissionsGranted(grantResults)) {
            textViewStatus.text = "Allowed (onRequestPermissionsResult)"
            doNext()
        } else {
            textViewStatus.text = "NOT Allowed (onRequestPermissionsResult)"
        }*/
    }

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
                //PermissionUtil.requestAllPermission(this, listPermissions, REQUEST_CODE_PERMISSION)
                requestPermissionLauncher.launch(listPermissions)

                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected. In this UI,
                // include a "cancel" or "no thanks" button that allows the user to
                // continue using your app without granting the permission.
            } else {
                doNext()
            }
        }
    }

    private fun doNext() {
        Toast.makeText(requireContext(), "working after request permission", Toast.LENGTH_SHORT)
            .show()
    }
}
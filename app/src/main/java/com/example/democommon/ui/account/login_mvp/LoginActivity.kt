package com.example.democommon.ui.account.login_mvp

import android.content.Intent
import android.view.View
import android.widget.Toast
import com.example.democommon.R
import com.example.democommon.extension.text
import com.example.democommon.extension.validate
import com.example.democommon.models.response.LoginRespond
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.nghiatl.common.dialog.DialogUtil
import com.nghiatl.common.dialog.LoadingDialogView
import com.nghiatl.common.extension.isEmailValid
import com.vn.onewayradio.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*
import timber.log.Timber


private const val REQUEST_GOOGLE_SIGN_IN = 9001

class LoginActivity : BaseActivity<LoginPresenter>(), LoginView {

    private var fbCallbackManager: CallbackManager? = null
    private lateinit var googleSignInClient: GoogleSignInClient
    
    override fun layoutId(): Int = R.layout.activity_login

    override fun initPresenter() {
        presenter = LoginPresenter(this)
    }

    override fun initView() {
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.google_sign_request_id))
            .requestServerAuthCode(getString(R.string.google_sign_request_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

    }

    override fun start() {

    }

    override fun onLoginSuccess(data: LoginRespond?) {
        Timber.d("server token:%s", data?.token)
        Toast.makeText(this, "Login Success, Token: " + data?.token, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        DialogUtil.showLoadingDialog(this, true)
    }

    override fun dismissLoading() {

    }

    override fun showError(errorMsg: String, errorCode: Int) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show()
    }

/*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
*/

    fun loginClick(view: View) {
        // TODO: abc
        // get data

        // validate

        // send server

        // receive data


        // đăng kí di view model
        // base viewmodel, activiy, fragment
        if (!validateInput()) {
            return
        }

        presenter?.login(editText_username.text, editText_password.text)
    }

    fun loginFacebookClick(view: View) {
        fbCallbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().logOut()
        LoginManager.getInstance().logInWithReadPermissions(this, listOf("public_profile", "email"))
        LoginManager.getInstance().registerCallback(fbCallbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    Timber.d("Facebook token: " + loginResult.accessToken.token)
                    presenter?.loginFacebook(loginResult.accessToken.token)
                }

                override fun onCancel() {
                    Timber.d("Facebook onCancel.")
                }

                override fun onError(error: FacebookException) {
                    Timber.d("Facebook onError: " + error.message)
                }
            })
    }

    fun loginGoogleClick(view: View) {
        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener(this) {}
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, REQUEST_GOOGLE_SIGN_IN)
    }

    private fun validateInput(): Boolean {
        if (!editText_username.validate(getString(R.string.error_not_format_email)) { it.isEmailValid() }) {
            return false
        }

        if (!editText_password.validate(getString(R.string.error_not_empty)) { it.isNotEmpty() }) {
            return false
        }

        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //facebook callback
        fbCallbackManager?.onActivityResult(requestCode, resultCode, data)

        //google callback
        if (requestCode == REQUEST_GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, send token to server
                val account = task.getResult(ApiException::class.java)
                account?.idToken?.let {
                    presenter?.loginGoogle(it)
                }
            } catch (e: Exception) {
                // Google Sign In failed, update UI appropriately
                Timber.d("Google sign in failed: ${e.message}")
            }
        }
    }
}

package self.tranluunghia.demomvi.core.basemvi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import self.tranluunghia.demomvi.R
import self.tranluunghia.demomvi.core.helper.DialogUtils
import self.tranluunghia.demomvi.core.helper.extention.observe

//abstract class BaseMVIFragment<VIEW_MODEL: BaseMVIViewModel<*, STATE>, DATA_BINDING: ViewBinding, STATE: BaseMVIContract.BaseState>: Fragment() {
abstract class BaseMVIFragment<VIEW_MODEL : BaseMVIViewModel<*, *>, DATA_BINDING : ViewBinding> :
    Fragment() {
    protected lateinit var binding: DATA_BINDING
    protected abstract val viewModel: VIEW_MODEL
    abstract fun layout(): Int
    abstract fun viewModelClass(): Class<VIEW_MODEL>

    private val loadingDialog by lazy { DialogUtils.createLoadingDlg(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = DataBindingUtil.inflate(inflater, layout(), container, false)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        setEvents()

        subscribeUI()
    }

    open fun setUpViews() {}

    open fun setEvents() {}

    open fun subscribeUI() {
        // Loading
        observe(viewModel.loadingEvent) { isLoading ->
            if (isLoading == true) {
                loadingDialog.show()
            } else {
                loadingDialog.hide()
            }
        }

        // Handle some error
        observe(viewModel.errorMessageEvent) { errorMessage ->
            DialogUtils.createAlertDlg(
                requireContext(),
                title = getString(R.string.error),
                message = errorMessage,
                cancellable = true,
                onClose = null,
            ).show()
        }
        observe(viewModel.noInternetConnectionEvent) {
            DialogUtils.createAlertDlg(
                requireContext(),
                title = getString(R.string.error),
                message = getString(R.string.no_internet_connection),
                cancellable = true,
                onClose = null,
            ).show()
        }
        observe(viewModel.connectTimeoutEvent) {
            DialogUtils.createAlertDlg(
                requireContext(),
                title = getString(R.string.error),
                message = getString(R.string.unknown_error),
                cancellable = true,
                onClose = null,
            ).show()
        }
        observe(viewModel.tokenExpiredEvent) {
            DialogUtils.createAlertDlg(
                requireContext(),
                title = getString(R.string.error),
                message = getString(R.string.you_need_login_again),
                cancellable = true,
                onClose = null,
            ).show()
        }
    }

}

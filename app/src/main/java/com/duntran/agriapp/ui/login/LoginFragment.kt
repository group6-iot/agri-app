package com.duntran.agriapp.ui.login


import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.duntran.agriapp.R
import com.duntran.agriapp.data.api.response.DataResponse
import com.duntran.agriapp.data.api.response.LoadingStatus
import com.duntran.agriapp.databinding.FragmentLoginBinding
import com.duntran.agriapp.ui.base.BaseScreenWithViewModelFragment
import com.duntran.agriapp.utils.Constants
import com.duntran.agriapp.utils.SharedPreferenceUtils
import com.duntran.agriapp.utils.ToastUtils


class LoginFragment : BaseScreenWithViewModelFragment<FragmentLoginBinding>() {
    private lateinit var mViewModel: LoginViewModel

    override fun getLayout(): Int {
        return R.layout.fragment_login
    }

    override fun initView() {
        binding.viewModel = mViewModel
        binding.btnLogin.setOnClickListener {
            val username = binding.edtUsername.text.toString()
            val password = binding.edtPassword.text.toString()
            mViewModel.login(username, password)
        }

//        binding.btnChange.setOnClickListener {
//            mViewModel.change("on")
//        }
    }

    override fun initViewModel() {
        val factory = LoginViewModel.Factory(requireActivity().application)
        mViewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

        mViewModel.token.observe(this) {
            Log.e("----", "initViewModel: ${it?.loadingStatus}", )
            it?.let {
                if (it.loadingStatus == LoadingStatus.Success) {
                    val body = (it as DataResponse.DataSuccess).body
                    SharedPreferenceUtils.getInstance(requireContext()).putStringValue(Constants.TOKEN_KEY, body.api_key)
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                } else if (it.loadingStatus == LoadingStatus.Error) {
                    ToastUtils.getInstance(requireContext()).showToast("Login fail!")
                }
            }

        }
    }

}
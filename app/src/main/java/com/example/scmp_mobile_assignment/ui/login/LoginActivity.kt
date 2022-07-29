package com.example.scmp_mobile_assignment.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.scmp_mobile_assignment.data.remote.LoginRepository
import com.example.scmp_mobile_assignment.databinding.ActivityLoginBinding
import com.example.scmp_mobile_assignment.ui.staff.StaffListActivity
import com.example.scmp_mobile_assignment.utils.*

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        viewModel =  ViewModelProvider(this,
            LoginViewModel.LoginViewModelFactory(LoginRepository())
        ).get(LoginViewModel::class.java)
        setContentView(binding.root)
        initView()
        initData()
    }

    private fun initView() {
        binding.etEmail.doAfterTextChanged {
            viewModel.loginDataChanged(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
        }
        binding.etPassword.doAfterTextChanged {
            viewModel.loginDataChanged(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
        }
        binding.btLogin.setOnClickListener {
            viewModel.login(binding.etEmail.text.toString(), binding.etPassword.text.toString())
        }
    }

    private fun initData() {
        viewModel.loginInfoState.observe(this) { loginState ->
            binding.btLogin.isEnabled = loginState.isValid

            loginState.emailError?.apply {
                binding.etEmail.error = getString(this)
            }
            loginState.passwordError?.apply {
                binding.etPassword.error = getString(this)
            }
        }
        viewModel.loading.observe(this) {
            if (it) binding.loading.show() else binding.loading.hide()
        }

        viewModel.loginResult.observe(this) {
            if (it.error.isNullOrEmpty()) {
                StaffListActivity.start(this,it.token?:"")
                leaveFromLeft()
                showToast("Login Success")
            } else {
                showToast(it.error)
            }
        }
    }
}
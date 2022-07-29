package com.example.scmp_mobile_assignment.ui.login

import androidx.lifecycle.*
import com.example.scmp_mobile_assignment.R
import com.example.scmp_mobile_assignment.data.model.raw.LoginResponseData
import com.example.scmp_mobile_assignment.data.remote.LoginRepository
import com.example.scmp_mobile_assignment.data.remote.NetworkUtils
import com.example.scmp_mobile_assignment.utils.RegexUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepository
) : ViewModel() {
    private val _loginInfoState = MutableLiveData<LoginInfoState>()
    val loginInfoState: LiveData<LoginInfoState> = _loginInfoState

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _loginResult = MutableLiveData<LoginResponseData>()
    val loginResult: LiveData<LoginResponseData> = _loginResult

    fun login(email: String, password: String) {
        if (_loading.value == true) return
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            loginRepository.login(email, password).catch { e ->
                e.printStackTrace()
                _loading.postValue(false)
                _loginResult.postValue(LoginResponseData(null, e.message))
            }.collect {
                _loading.postValue(false)
                _loginResult.postValue(it)
            }
        }
    }


    fun loginDataChanged(email: String, password: String) {
        _loginInfoState.value = LoginInfoState().apply {
            if (email.isNotEmpty() && !isEmailValid(email)) {
                emailError = R.string.invalid_email
            }
            if (password.isNotEmpty() && !isPasswordValid(password)) {
                passwordError = R.string.invalid_password
            }
            isValid = isPasswordValid(password) && isEmailValid(email)
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return email.matches(Regex(RegexUtils.EMAIL_REGEX))
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.matches(Regex(RegexUtils.PASSWORD_REGEX))
    }

    class LoginViewModelFactory(private val repository:LoginRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LoginViewModel(repository) as T
        }
    }
}
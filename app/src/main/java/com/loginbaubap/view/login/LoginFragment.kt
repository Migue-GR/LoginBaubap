package com.loginbaubap.view.login

import android.util.Patterns
import com.loginbaubap.R
import com.loginbaubap.databinding.FragmentLoginBinding
import com.loginbaubap.model.enums.ErrorCode.ERROR_INVALID_CREDENTIALS
import com.loginbaubap.utils.AppSession.showGlobalError
import com.loginbaubap.utils.BaseFragmentBinding
import com.loginbaubap.utils.LoginBaubapException
import com.loginbaubap.utils.ext.observeWith
import com.loginbaubap.utils.ext.showToast
import com.loginbaubap.utils.setOnSingleClickListener
import com.loginbaubap.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragmentBinding<FragmentLoginBinding>() {
    override fun bind() = FragmentLoginBinding.inflate(layoutInflater)

    private val viewModel: LoginViewModel by viewModel()

    override fun onViewCreated() {
        binding.btnLogin.setOnSingleClickListener {
            validateFieldsToLogin()
        }
    }

    private fun validateFieldsToLogin() {
        val email = binding.inputEmail.editText?.text.toString().trim()
        val password = binding.inputPassword.editText?.text.toString().trim()
        when {
            email.isEmpty() -> {
                binding.inputEmail.error = getString(R.string.email_is_mandatory)
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.inputEmail.error = getString(R.string.wrong_email_format)
            }
            password.isEmpty() -> {
                binding.inputPassword.error = getString(R.string.mandatory_password)
            }
            else -> login(email, password)
        }
    }

    private fun login(
        email: String,
        password: String
    ) = viewModel.login(email, password).observeWith(viewLifecycleOwner, { user ->
        clearFields()
        showToast("Bienvenido ${user?.email}")
    }, ::showError)

    private fun showError(error: LoginBaubapException) {
        showGlobalError(error)
        if (error.errorCode == ERROR_INVALID_CREDENTIALS) {
            clearFields()
        }
    }

    private fun clearFields() {
        binding.inputEmail.editText?.text?.clear()
        binding.inputPassword.editText?.text?.clear()
        binding.inputEmail.isErrorEnabled = false
        binding.inputPassword.isErrorEnabled = false
    }
}
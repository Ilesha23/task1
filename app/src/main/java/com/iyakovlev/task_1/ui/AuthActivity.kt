package com.iyakovlev.task_1.ui

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.iyakovlev.task_1.common.Constants.APP_PREFERENCES
import com.iyakovlev.task_1.common.Constants.EMAIL
import com.iyakovlev.task_1.common.Constants.IS_LOGINED
import com.iyakovlev.task_1.common.Constants.NAME
import com.iyakovlev.task_1.common.Constants.SURNAME
import com.iyakovlev.task_1.databinding.AuthLayoutBinding
import com.iyakovlev.task_1.utils.extensions.capitalizeFirstChar

class AuthActivity : AppCompatActivity() {

    // TODO: base activity

    private lateinit var binding: AuthLayoutBinding
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        autoLogin()

        binding = AuthLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //todo setListeners()
        setupActionListeners()
    }

    private fun setupActionListeners() {
        binding.btnRegister.setOnClickListener{ onRegisterClick() }
    }

    // TODO: with binding
    private fun onRegisterClick() {
        if (isInputValid(binding.emailText, binding.passwordText)) {    //todo maybe send strings (not EditText)
            preferences.edit()      //todo maybe?? create interface
                .putString(EMAIL, binding.emailText.text.toString())
                .apply()

            parseEmailToPrefs()

            if (binding.chkRemember.isChecked) {
                preferences.edit()
                    .putBoolean(IS_LOGINED, true)
                    .apply()
            }

            val intent = Intent(this, MainActivity::class.java) // TODO: Don't repeat yourself
            // TODO: overridePendingTransition() and animation on xml
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())

        }
    }

    private fun autoLogin() {
        preferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        if (preferences.getBoolean(IS_LOGINED, false)) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    //todo validate when focus changed?
    //todo add view of hidden pattern
    private fun isInputValid (emailText: EditText, passwordText: EditText) :Boolean {   //todo with binding and no send arguments
        var isValid = true
        val email = emailText.text.toString()
        val pass = passwordText.text.toString()

        if (!isEmailValid(email)) {
            emailText.error = "Email is invalid"        //todo to strings!
            isValid = false
        }
        if (!isPassValid(pass)) {
            passwordText.error = "min 8 length, a-z, A-Z, 0-9 required" //todo to strings! with arguments!
            isValid = false
        }

        return isValid
    }

    private fun isEmailValid(email: String): Boolean {  //todo crash without dot!
        if (!email.matches(Regex("""[\w-\.]+@([\w-]+\.)+[\w-]{2,4}"""))) {  //todo extract logic to separate object, regex to constants!, use regex of Google
            return false
        }
        return true
    }

    private fun isPassValid(pass: String): Boolean {
        if (!pass.matches(Regex("""^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}${'$'}"""))) {  //todo extract logic to separate object, length to const
            return false
        }
        return true
    }

    private fun parseEmailToPrefs() {       //todo to main layout
        val leftPart = binding.emailText.text.toString().split("@")[0]  //todo extract logic to separate object, @ to const, nun null safe!
        var name = "Name"               //todo resources
        var surname = "Surname"         //todo resources
        if (leftPart.contains(".")) {       //todo to const
            name = leftPart.split(".")[0].capitalizeFirstChar()
            surname = leftPart.split(".")[1].capitalizeFirstChar()
        }
        preferences.edit()
            .putString(NAME, name)
            .putString(SURNAME, surname)
            .apply()
    }
}
package com.aah.sftelehealthworker.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.util.regex.Matcher
import java.util.regex.Pattern

object NumberValidation {


    open fun checkMobileNumber(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (s!!.length < 10) {
                    editText.setError("")
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.equals("")) {
                    return
                }
                if (s.length == 10) {
                    if (!isValid(s)) {
                        editText.setError("Invalid Number")
                    }
                } else if (s!!.length > 10) {
                    editText.getText().delete(s!!.length - 1, s!!.length);
                }

            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    open fun isValid(s: CharSequence?): Boolean {

        val PHONE_PATTERN = "^(13|14|15|16|17|18|19)\\d{8}$"

        val pattern: Pattern = Pattern.compile(PHONE_PATTERN)
        val matcher: Matcher = pattern.matcher(s)
        return matcher.matches()
    }
}
package com.example.fieldbuzz.util

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.textfield.TextInputLayout

object UtilityMethods {

     fun validateTextField(textInputLayout: TextInputLayout): Boolean {
        return if (textInputLayout.editText?.text.isNullOrEmpty()) {
            textInputLayout.error = "This Field is Required"
            textInputLayout.editText?.requestFocus()
            false
        } else {
            textInputLayout.editText?.error = null
            textInputLayout.error = null
            true
        }
    }

    fun validateMinMax(textInputLayout: TextInputLayout, min: Int, max: Int): Boolean{
        if (textInputLayout.editText?.text.toString().toInt() < min || textInputLayout.editText?.text.toString().toInt() > max){
            textInputLayout.error = "Range(for salary: 15000-60000) (for grad year: 2015-2020)"
            textInputLayout.editText?.requestFocus()
            return false
        } else{
            textInputLayout.editText?.error = null
            textInputLayout.error = null
            return true
        }
    }

    fun extractText(textInputLayout: TextInputLayout): String{
        if (!textInputLayout.editText?.text.toString().isNullOrEmpty()){
            return textInputLayout.editText?.text.toString()
        }else{
            return ""
        }
    }

    fun extractDouble(textInputLayout: TextInputLayout): Double{
        if (!textInputLayout.editText?.text.toString().isNullOrEmpty()){
            return textInputLayout.editText?.text.toString().toDouble()
        }else{
            return 0.0
        }
    }

    fun extractInt(textInputLayout: TextInputLayout): Int{
        if (!textInputLayout.editText?.text.toString().isNullOrEmpty()){
            return textInputLayout.editText?.text.toString().toInt()
        }else{
            return 0
        }
    }

    fun hideKeyboard(activity: Activity) {
        try {
            val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            var view = activity.currentFocus
            if (view == null) {
                view = View(activity)
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
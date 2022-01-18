package com.task.noteapp.ui

object Validator {
    fun validateInput(titleText: String, contentText: String): Boolean {
        return titleText.isNotEmpty() && contentText.isNotEmpty()
    }
}
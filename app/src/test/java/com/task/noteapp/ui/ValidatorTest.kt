package com.task.noteapp.ui

import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ValidatorTest : TestCase() {

    @Test
    fun whenInputIsValid() {
        val title = "Adding Note title"
        val content = "Note Content"
        val result = Validator.validateInput(title, content)
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun whenInputIsInvalid() {
        val title = ""
        val content = "Note Content"
        val result = Validator.validateInput(title, content)
        assertThat(result).isEqualTo(false)
    }
}
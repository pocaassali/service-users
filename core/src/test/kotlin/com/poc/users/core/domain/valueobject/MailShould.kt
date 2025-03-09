package com.poc.users.core.domain.valueobject

import com.poc.users.core.domain.exception.UserCreationException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import kotlin.test.Test

class MailShould {

    @Test
    fun `should create a valid Mail object when provided with a valid email`() {
        val validEmail = "test@example.com"
        val mail = Mail(validEmail)

        assertThat(mail.value).isEqualTo(validEmail)
    }

    @Test
    fun `should allow creation of Mail object with invalid email string - no validation enforced`() {
        val invalidEmail = "invalid-email"

        assertThrows(UserCreationException::class.java) {
            Mail(invalidEmail)
        }
    }

    @Test
    fun `should create Mail object with empty string value`() {
        val emptyEmail = ""

        assertThrows(UserCreationException::class.java) {
            Mail(emptyEmail)
        }
    }

    @Test
    fun `should handle special characters in the email`() {
        val specialCharEmail = "user_tag@sub-example.com"
        val mail = Mail(specialCharEmail)

        assertThat(mail.value).isEqualTo(specialCharEmail)
    }
}
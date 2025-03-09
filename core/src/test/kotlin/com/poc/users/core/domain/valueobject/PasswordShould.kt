package com.poc.users.core.domain.valueobject

import com.poc.users.core.domain.exception.UserCreationException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import kotlin.test.Test

class PasswordShould {

    @Test
    fun `valid password should not throw exception`() {
        val validPassword = "StrongP@ssw0rd"
        val encryptedValue = "encrypted_$validPassword"
        Password(value = validPassword, encryptedValue = encryptedValue)
    }

    @Test
    fun `password shorter than minimum length should throw exception`() {
        val shortPassword = "Short1!"
        val exception = assertThrows(UserCreationException::class.java) {
            Password(value = shortPassword, encryptedValue = "encrypted")
        }

        assertThat(exception.message).isEqualTo(Password.ERROR_MIN_LENGTH)
    }

    @Test
    fun `password without uppercase letter should throw exception`() {
        val noUpperCasePassword = "lowercase1!"

        val exception = assertThrows(UserCreationException::class.java) {
            Password(value = noUpperCasePassword, encryptedValue = "encrypted")
        }

        assertThat(exception.message).isEqualTo(Password.ERROR_UPPERCASE)
    }

    @Test
    fun `password without lowercase letter should throw exception`() {
        val noLowerCasePassword = "UPPERCASE1!"

        val exception = assertThrows(UserCreationException::class.java) {
            Password(value = noLowerCasePassword, encryptedValue = "encrypted")
        }

        assertThat(exception.message).isEqualTo(Password.ERROR_LOWERCASE)
    }

    @Test
    fun `password without digit should throw exception`() {
        val noDigitPassword = "NoDigits!"

        val exception = assertThrows(UserCreationException::class.java) {
            Password(value = noDigitPassword, encryptedValue = "encrypted")
        }

        assertThat(exception.message).isEqualTo(Password.ERROR_DIGIT)
    }

    @Test
    fun `password without special character should throw exception`() {
        val noSpecialCharPassword = "NoSpecial1"

        val exception = assertThrows(UserCreationException::class.java) {
            Password(value = noSpecialCharPassword, encryptedValue = "encrypted")
        }

        assertThat(exception.message).isEqualTo(Password.ERROR_SPECIAL_CHARACTER)
    }

    @Test
    fun `default password should not throw exception`() {
        Password.DEFAULT
    }
}
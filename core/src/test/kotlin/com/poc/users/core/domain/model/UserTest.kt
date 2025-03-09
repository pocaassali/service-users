package com.poc.users.core.domain.model

import com.poc.users.core.domain.valueobject.Mail
import com.poc.users.core.domain.valueobject.Password
import com.poc.users.core.domain.valueobject.UserRole
import com.poc.users.core.factories.anAdmin
import com.poc.users.core.factories.anUser
import com.poc.users.core.factories.anUserUpdateHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.util.*

class UserTest {
    @Test
    fun `updateWith should update mail when new mail is provided`() {
        val originalUser = anUser()

        val updatedMail = Mail("updated@test.com")
        val updateHelper = anUserUpdateHelper(mail = updatedMail.value)

        val updatedUser = originalUser.updateWith(updateHelper)

        assertThat(updatedUser).isEqualTo(originalUser.copy(mail = updatedMail))
    }

    @Test
    fun `updateWith should retain mail when no new mail is provided`() {
        val originalUser = anUser()

        val updateHelper = anUserUpdateHelper()

        val updatedUser = originalUser.updateWith(updateHelper)

        assertThat(updatedUser).isEqualTo(originalUser)
    }

    @Test
    fun `updateWith should update password when new password is provided`() {
        val originalUser = anUser()

        val updatedPassword = Password("NewPassword1!", "newEncryptedPassword")
        val updateHelper = anUserUpdateHelper(password = updatedPassword.encryptedValue)

        val updatedUser = originalUser.updateWith(updateHelper)

        assertThat(updatedUser)
            .isEqualTo(originalUser.copy(password = Password.DEFAULT.copy(encryptedValue = updatedPassword.encryptedValue)))
    }

    @Test
    fun `updateWith should retain password when no new password is provided`() {
        val originalUser = anUser()

        val updateHelper = anUserUpdateHelper()

        val updatedUser = originalUser.updateWith(updateHelper)

        assertThat(updatedUser).isEqualTo(originalUser)
    }

    @Test
    fun `updateWith should update role when new role is provided`() {
        val originalUser = anUser()

        val updatedRole = UserRole.ADMIN
        val updateHelper = anUserUpdateHelper(role = updatedRole.name)

        val updatedUser = originalUser.updateWith(updateHelper)

        assertThat(updatedUser).isEqualTo(originalUser.copy(role = updatedRole))
    }

    @Test
    fun `updateWith should retain role when no new role is provided`() {
        val originalUser = anUser()

        val updateHelper = UserUpdateHelper(
            mail = Optional.empty(),
            password = Optional.empty(),
            role = Optional.empty()
        )

        val updatedUser = originalUser.updateWith(updateHelper)

        assertThat(updatedUser).isEqualTo(originalUser)
    }
}
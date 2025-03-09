package com.poc.users.core.domain.model

import com.poc.users.core.domain.valueobject.Mail
import com.poc.users.core.domain.valueobject.Password
import com.poc.users.core.domain.valueobject.UserRole
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.util.*

class UserTest {

    /**
     * This test ensures that `updateWith` updates the `mail` field when a new mail is provided via `UserUpdateHelper`.
     */
    @Test
    fun `updateWith should update mail when new mail is provided`() {
        val originalUser = User(
            identifier = UUID.randomUUID(),
            mail = Mail("original@test.com"),
            password = Password("Password1!", "encryptedPassword1"),
            role = UserRole.USER
        )

        val updatedMail = Mail("updated@test.com")
        val updateHelper = UserUpdateHelper(
            mail = Optional.of(updatedMail),
            password = Optional.empty(),
            role = Optional.empty()
        )

        val updatedUser = originalUser.updateWith(updateHelper)

        assertEquals(updatedMail, updatedUser.mail)
        assertEquals(originalUser.password, updatedUser.password)
        assertEquals(originalUser.role, updatedUser.role)
    }

    /**
     * This test ensures that `updateWith` retains the existing `mail` when no new mail is provided via `UserUpdateHelper`.
     */
    @Test
    fun `updateWith should retain mail when no new mail is provided`() {
        val originalUser = User(
            identifier = UUID.randomUUID(),
            mail = Mail("original@test.com"),
            password = Password("Password1!", "encryptedPassword1"),
            role = UserRole.USER
        )

        val updateHelper = UserUpdateHelper(
            mail = Optional.empty(),
            password = Optional.empty(),
            role = Optional.empty()
        )

        val updatedUser = originalUser.updateWith(updateHelper)

        assertEquals(originalUser.mail, updatedUser.mail)
    }

    /**
     * This test ensures that `updateWith` updates the `password` field when a new password is provided via `UserUpdateHelper`.
     */
    @Test
    fun `updateWith should update password when new password is provided`() {
        val originalUser = User(
            identifier = UUID.randomUUID(),
            mail = Mail("original@test.com"),
            password = Password("Password1!", "encryptedPassword1"),
            role = UserRole.USER
        )

        val updatedPassword = Password("NewPassword1!", "newEncryptedPassword")
        val updateHelper = UserUpdateHelper(
            mail = Optional.empty(),
            password = Optional.of(updatedPassword),
            role = Optional.empty()
        )

        val updatedUser = originalUser.updateWith(updateHelper)

        assertEquals(updatedPassword, updatedUser.password)
        assertEquals(originalUser.mail, updatedUser.mail)
        assertEquals(originalUser.role, updatedUser.role)
    }

    /**
     * This test ensures that `updateWith` retains the existing `password` when no new password is provided via `UserUpdateHelper`.
     */
    @Test
    fun `updateWith should retain password when no new password is provided`() {
        val originalUser = User(
            identifier = UUID.randomUUID(),
            mail = Mail("original@test.com"),
            password = Password("Password1!", "encryptedPassword1"),
            role = UserRole.USER
        )

        val updateHelper = UserUpdateHelper(
            mail = Optional.empty(),
            password = Optional.empty(),
            role = Optional.empty()
        )

        val updatedUser = originalUser.updateWith(updateHelper)

        assertEquals(originalUser.password, updatedUser.password)
    }

    /**
     * This test ensures that `updateWith` updates the `role` field when a new role is provided via `UserUpdateHelper`.
     */
    @Test
    fun `updateWith should update role when new role is provided`() {
        val originalUser = User(
            identifier = UUID.randomUUID(),
            mail = Mail("original@test.com"),
            password = Password("Password1!", "encryptedPassword1"),
            role = UserRole.USER
        )

        val updatedRole = UserRole.ADMIN
        val updateHelper = UserUpdateHelper(
            mail = Optional.empty(),
            password = Optional.empty(),
            role = Optional.of(updatedRole)
        )

        val updatedUser = originalUser.updateWith(updateHelper)

        assertEquals(updatedRole, updatedUser.role)
        assertEquals(originalUser.mail, updatedUser.mail)
        assertEquals(originalUser.password, updatedUser.password)
    }

    /**
     * This test ensures that `updateWith` retains the existing `role` when no new role is provided via `UserUpdateHelper`.
     */
    @Test
    fun `updateWith should retain role when no new role is provided`() {
        val originalUser = User(
            identifier = UUID.randomUUID(),
            mail = Mail("original@test.com"),
            password = Password("Password1!", "encryptedPassword1"),
            role = UserRole.USER
        )

        val updateHelper = UserUpdateHelper(
            mail = Optional.empty(),
            password = Optional.empty(),
            role = Optional.empty()
        )

        val updatedUser = originalUser.updateWith(updateHelper)

        assertEquals(originalUser.role, updatedUser.role)
    }
}
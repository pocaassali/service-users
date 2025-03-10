package com.poc.users.factories

import com.poc.users.infra.api.UserCreationRequest
import com.poc.users.infra.api.UserEditionRequest
import com.poc.users.infra.api.UserLoginRequest

fun anUserCreationRequest(
    mail: String = "user@mail.com",
    password: String = "P@ssw0rd",
    role: String = "USER",
) = UserCreationRequest(
    mail = mail,
    password = password,
    role = role,
)

fun anUserEditionRequest(
    mail: String? = null,
    password: String? = null,
    role: String? = null,
) = UserEditionRequest(
    mail = mail,
    password = password,
    role = role,
)

fun anUserLoginRequest(
    mail: String = "user@mail.com",
) = UserLoginRequest(
    mail = mail
)
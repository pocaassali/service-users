package com.poc.users

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest(classes = [ServiceUsersApplication::class])
@ExtendWith(SpringExtension::class)
class ApiTests

package com.poc.users.infra.api

import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(private val userAdapter: UserAdapter) {

    @PostMapping
    fun createUser(@RequestBody request: UserCreationRequest): ResponseEntity<UserView>{
        return ResponseEntity.ok(userAdapter.create(request))
    }

    @GetMapping
    fun getUsers(): ResponseEntity<List<UserView>> {
        return ResponseEntity.ok(userAdapter.getAllUsers())
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: String): ResponseEntity<UserView?>{
        return ResponseEntity.ok(userAdapter.getUserById(id))
    }

    @PutMapping("/{id}")
    @PreAuthorize("@securityGuard.isSelfOrAdmin(#id)")
    fun updateUser(@PathVariable id: String, @RequestBody request: UserEditionRequest): ResponseEntity<UserView?>{
        return ResponseEntity.ok(userAdapter.update(id, request))
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@securityGuard.hasAdminRole()")
    fun deleteUser(@PathVariable id: String): ResponseEntity<String>{
        userAdapter.delete(id)
        return ResponseEntity.ok("User with id: $id has been deleted")
    }
}
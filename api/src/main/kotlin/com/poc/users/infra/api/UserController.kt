package com.poc.users.infra.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(private val userAdapter: UserAdapter) {

    @GetMapping
    fun getUsers(): ResponseEntity<List<UserView>> {
        return ResponseEntity.ok(userAdapter.getAllUsers())
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: String): ResponseEntity<UserView?>{
        return ResponseEntity.ok(userAdapter.getUserById(id))
    }

    //TODO : move this controller as register AuthController
    @PostMapping
    fun createUser(@ModelAttribute request: UserCreationRequest): ResponseEntity<UserView>{
        println(request)
        //TODO : Probably should return template register-confirmation
        return ResponseEntity.ok(userAdapter.create(request))
    }

    /*@PutMapping("/{id}")
    @PreAuthorize("@authServerSecurityGuard.isSelfOrAdmin(#id)")
    fun updateUser(@PathVariable id: String, @RequestBody request: UserEditionRequest): ResponseEntity<UserView?>{
        return ResponseEntity.ok(userAdapter.update(id, request))
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@authServerSecurityGuard.hasAdminRole()")
    fun deleteUser(@PathVariable id: String): ResponseEntity<String>{
        userAdapter.delete(id)
        return ResponseEntity.ok("User with id: $id has been deleted")
    }*/
}
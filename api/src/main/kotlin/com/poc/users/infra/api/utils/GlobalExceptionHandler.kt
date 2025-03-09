package com.poc.users.infra.api.utils

import com.poc.users.core.domain.exception.UserCreationException
import com.poc.users.core.domain.exception.UserNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authorization.AuthorizationDeniedException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException


@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(UserCreationException::class)
    fun handleUserAlreadyExistsException(ex: UserCreationException, request: WebRequest): ResponseEntity<Any> {
        val errorResponse = ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.message!!)
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(ex: UserNotFoundException, request: WebRequest): ResponseEntity<Any> {
        val errorResponse = ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.message!!)
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<Any> {
        val errors = mutableListOf<String>()
        val bindingResult: BindingResult = ex.bindingResult

        for (fieldError: FieldError in bindingResult.fieldErrors) {
            errors.add("${fieldError.field}: ${fieldError.defaultMessage}")
        }

        val errorResponse = ErrorResponse(HttpStatus.BAD_REQUEST.value(), errors.joinToString(", "))
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(AuthorizationDeniedException::class)
    fun handleAccessDeniedException(ex: AuthorizationDeniedException): ResponseEntity<ApiResponse> {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(mapOf("message" to (ex.message ?: "Access Denied")))
    }

    @ExceptionHandler(Exception::class)
    fun handleGlobalException(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        val errorResponse = ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.message ?: "Internal Server Error")
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    data class ErrorResponse(val status: Int, val message: String)
}

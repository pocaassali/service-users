package com.poc.users.infra.api

import com.poc.users.core.domain.exception.UserCreationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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

    @ExceptionHandler(Exception::class)
    fun handleGlobalException(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        val errorResponse = ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.message ?: "Internal Server Error")
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    data class ErrorResponse(val status: Int, val message: String)
}

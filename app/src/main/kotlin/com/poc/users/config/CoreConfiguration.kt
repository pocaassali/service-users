package com.poc.users.config

import com.poc.users.core.ddd.ApplicationService
import com.poc.users.core.ddd.DomainService
import com.poc.users.core.ddd.Usecase
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.ComponentScan.Filter
import org.springframework.context.annotation.FilterType.ANNOTATION

@Configuration
@ComponentScan(
    basePackages = ["com.poc.users"],
    includeFilters = [Filter(
        type = ANNOTATION,
        value = [DomainService::class, ApplicationService::class, Usecase::class]
    )]
)
class CoreConfiguration
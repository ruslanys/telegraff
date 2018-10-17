package me.ruslanys.telegraff.autoconfigure

import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication
import org.springframework.context.annotation.Configuration


/**
 * Configuration for Telegraff when used in a non-web context.
 */
@Configuration
@ConditionalOnNotWebApplication
class TelegraffNonWebConfiguration {
    // TODO()
}
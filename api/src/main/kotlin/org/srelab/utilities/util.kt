package org.srelab.utilities

import io.opentelemetry.api.trace.Span
import io.opentelemetry.api.trace.Tracer
import io.opentelemetry.context.Scope

inline fun <T> withSpan(
        tracer: Tracer,
        spanName: String,
        crossinline block: (Span) -> T
): T {
    val span: Span = tracer.spanBuilder(spanName).startSpan()
    return try {
        val scope: Scope = span.makeCurrent()
        scope.use {
            block(span)
        }
    } finally {
        span.end()
    }
}
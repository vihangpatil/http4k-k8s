package dev.vihang.http4k

import org.http4k.cloudnative.Http4kK8sServer
import org.http4k.cloudnative.asK8sServer
import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.KtorCIO

val app: HttpHandler = routes(
    "/ping" bind Method.GET to {
        Response(OK)
            .header("Content-Type", "text/plain")
            .body("pong")
    }
)

fun main() {
    val server = app.asK8sServer(
        serverConfig = ::KtorCIO,
        port = 8080,
        healthPort = 8081,
    )
    server.start()
    println("Server started listening at port: ${server.port()}; health port: ${server.healthPort()}")
    server.block()
}
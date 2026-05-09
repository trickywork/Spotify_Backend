package com.laioffer.spotify

import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

private val json = Json {
    prettyPrint = true
    ignoreUnknownKeys = true
}

@Serializable
data class HealthResponse(
    val status: String,
    val service: String,
    val mode: String,
)

@Serializable
data class FeedResponse(
    val title: String,
    val sections: List<FeedSection>,
)

@Serializable
data class FeedSection(
    val id: String,
    val title: String,
    val playlistIds: List<Long>,
)

@Serializable
data class Playlist(
    val id: Long,
    val name: String,
    val description: String,
    val cover: String,
    val songs: List<Song>,
)

@Serializable
data class Song(
    val id: Long,
    val name: String,
    val artist: String,
    val lyric: String,
    val src: String,
    val length: Int,
)

fun main() {
    val port = System.getenv("PORT")?.toIntOrNull() ?: 8080
    embeddedServer(Netty, port = port, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json(json)
    }
    install(CORS) {
        allowMethod(HttpMethod.Get)
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
        anyHost()
    }

    val playlists = loadPlaylists()
    val feed = loadFeed()

    routing {
        get("/") {
            call.respondText("Spotify backend is running. Try /feed, /playlists, or /playlist/1.")
        }

        get("/api/health") {
            call.respond(HealthResponse(status = "ok", service = "spotify-backend", mode = "demo"))
        }

        get("/feed") {
            call.respond(feed)
        }

        get("/playlists") {
            call.respond(playlists)
        }

        get("/playlist/{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
            val playlist = playlists.firstOrNull { it.id == id }
            if (playlist == null) {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to "Playlist not found"))
                return@get
            }
            call.respond(playlist)
        }

        get("/songs/{file}") {
            val file = call.parameters["file"] ?: "unknown"
            call.respond(
                HttpStatusCode.NotImplemented,
                mapOf(
                    "error" to "Demo audio files are not bundled.",
                    "file" to file,
                    "reason" to "The cloud demo uses simulated playback so the repo does not ship copyrighted songs.",
                ),
            )
        }
    }
}

private fun loadFeed(): FeedResponse {
    return json.decodeFromString(resourceText("feed.json"))
}

private fun loadPlaylists(): List<Playlist> {
    return json.decodeFromString(resourceText("playlists.json"))
}

private fun resourceText(path: String): String {
    return Thread.currentThread().contextClassLoader.getResource(path)?.readText()
        ?: error("Missing resource: $path")
}

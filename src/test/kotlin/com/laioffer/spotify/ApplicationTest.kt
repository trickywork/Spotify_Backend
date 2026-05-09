package com.laioffer.spotify

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ApplicationTest {
    @Test
    fun healthEndpointReturnsOk() = testApplication {
        application {
            module()
        }

        val response = client.get("/api/health")

        assertEquals(HttpStatusCode.OK, response.status)
        assertTrue(response.body<String>().contains("spotify-backend"))
    }

    @Test
    fun playlistEndpointReturnsKnownPlaylist() = testApplication {
        application {
            module()
        }

        val response = client.get("/playlist/1")

        assertEquals(HttpStatusCode.OK, response.status)
        assertTrue(response.body<String>().contains("Morning Kotlin"))
    }
}

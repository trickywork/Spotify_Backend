# Spotify Backend

Ktor backend for the Spotify-style portfolio project. It exposes playlist, feed, and song metadata APIs that are consumed by the separate static frontend repo.

## Live Service

- Cloud Run service: `spotify-api`
- Cloud Run URL: `https://spotify-api-gb7rmueyna-uc.a.run.app`
- Frontend service: `https://spotify-gb7rmueyna-uc.a.run.app`
- Portfolio URL: `https://spotify.junliu.dev`
- Google Cloud project: `caramel-vim-441513-e1`
- Region: `us-central1`

## Tech Stack

- Kotlin
- Ktor 3
- Netty engine
- Kotlin serialization
- Gradle Kotlin DSL
- JDK 21
- Static JSON resources for portfolio data
- Docker, Google Cloud Build, Google Cloud Run
- Postman collection for API testing

## Project Structure

```text
Spotify_Backend/
  src/main/kotlin/dev/junliu/spotify/
    Application.kt
  src/main/resources/
    feed.json
    playlists.json
  docs/
    api.md
    configuration.md
  postman/
    Spotify_Backend.postman_collection.json
  build.gradle.kts
  Dockerfile
  cloudbuild.yaml
```

## Features

- Home feed sections.
- Playlist list.
- Individual playlist detail.
- Song route shape preserved for frontend compatibility.
- CORS enabled for a separately hosted frontend.
- No database required for the portfolio version.

The demo does not ship copyrighted audio files. Playback in the frontend is simulated around the metadata.

## Local Development

Run the API:

```bash
cd /Users/junliu/git_repo/Spotify_Backend
PORT=8083 ./gradlew run
```

Expected local URL:

```text
http://localhost:8083
```

Smoke checks:

```bash
curl http://localhost:8083/api/health
curl http://localhost:8083/feed
curl http://localhost:8083/playlists
curl http://localhost:8083/playlist/1
```

Expected result:

- `/api/health` returns a healthy response.
- `/feed` returns sections for the home page.
- `/playlists` returns playlist cards and song metadata.
- `/playlist/1` returns one playlist object.

## API Endpoints

| Method | Path | Description |
| --- | --- | --- |
| `GET` | `/` | Basic service response. |
| `GET` | `/api/health` | Health check. |
| `GET` | `/feed` | Home feed sections. |
| `GET` | `/playlists` | All playlists. |
| `GET` | `/playlist/{id}` | One playlist. |
| `GET` | `/songs/{file}` | Compatibility route for song file URLs. |

## Postman

Import:

```text
postman/Spotify_Backend.postman_collection.json
```

Suggested variables:

```text
baseUrl=http://localhost:8083
```

For Cloud Run:

```text
baseUrl=https://spotify-api-gb7rmueyna-uc.a.run.app
```

## Tests And Build

```bash
./gradlew test
./gradlew buildFatJar
```

Build a local image:

```bash
docker build -t spotify-api:local .
```

## Cloud Deployment

Manual deployment:

```bash
gcloud builds submit \
  --config cloudbuild.yaml \
  --project caramel-vim-441513-e1
```

Cloud Run cost controls:

- `min-instances=0`
- `max-instances=2`
- no database
- no persistent storage
- JSON resources are bundled into the container

## Frontend Pairing

Frontend repo:

```text
/Users/junliu/git_repo/Spotify_Frontend
https://github.com/trickywork/Spotify_Frontend
```

The frontend should point to this backend with:

```env
API_BASE_URL=https://spotify-api-gb7rmueyna-uc.a.run.app
```

## Expected Portfolio Behavior

The deployed frontend should load playlist/feed data from this API, show playlist details, allow track selection, and support simulated player controls. The backend should remain stateless and cheap to run.

## Additional Notes

More details:

- `docs/api.md`
- `docs/configuration.md`

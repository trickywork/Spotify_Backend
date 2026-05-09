# Spotify Backend

Ktor backend for the Spotify portfolio project. It exposes a compact music API and is prepared for low-cost Cloud Run deployment.

## Features

- `GET /feed` returns home feed sections.
- `GET /playlists` returns playlist and song metadata.
- `GET /playlist/{id}` returns one playlist.
- `GET /songs/{file}` keeps the static song URL shape from the course, but the portfolio demo does not ship copyrighted audio.
- CORS is enabled so a separate frontend repo can call the API.

## Local Run

Requirements:

- JDK 21 or newer
- No global Gradle install required; use the included wrapper.

```bash
./gradlew run
```

Then open:

```bash
curl http://localhost:8080/api/health
curl http://localhost:8080/feed
curl http://localhost:8080/playlists
curl http://localhost:8080/playlist/1
```

## Tests

```bash
./gradlew test
```

## API Testing

Import `postman/Spotify_Backend.postman_collection.json` into Postman. Update the `baseUrl` variable to the Cloud Run URL when testing the deployed service.

## Configuration Notes

Non-code setup is documented in `docs/configuration.md`, including JSON data resources, lack of database, Cloud Run service settings, and pending Cloud Build trigger setup.

## Cloud Run Deployment

The repo includes `Dockerfile` and `cloudbuild.yaml`.

Current Cloud Run URL:

```text
https://spotify-api-gb7rmueyna-uc.a.run.app
```

Manual deploy:

```bash
gcloud builds submit --config cloudbuild.yaml --project caramel-vim-441513-e1
```

The Cloud Build trigger should deploy on pushes to `main` after the GitHub repository is connected.

Cost controls:

- `--min-instances=0` so the service can scale to zero.
- `--max-instances=2` to avoid surprise scaling.
- No Cloud SQL, VM, Elasticsearch, or paid storage dependency for the demo data.

## Project Structure

```text
src/main/kotlin/dev/junliu/spotify/Application.kt
src/main/resources/feed.json
src/main/resources/playlists.json
postman/Spotify_Backend.postman_collection.json
docs/api.md
cloudbuild.yaml
Dockerfile
```

## Course Alignment

The course coding pad builds a Kotlin Ktor service with playlist JSON and static song routes. This implementation keeps the backend contract portfolio-friendly and cloud-friendly while avoiding bundled copyrighted audio assets.

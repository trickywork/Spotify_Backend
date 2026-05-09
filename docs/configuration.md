# Spotify Backend Configuration

This file records the non-code setup needed to run, test, and redeploy the Spotify backend.

## Runtime Shape

The backend is a Kotlin Ktor service. It serves JSON resources from the repo and exposes stable portfolio API routes.

There is no database in the current implementation.

Data files:

```text
src/main/resources/feed.json
src/main/resources/playlists.json
```

The `/songs/{file}` route is kept for API compatibility, but the portfolio demo does not bundle copyrighted audio.

## Local Environment

Use `.env.example` as a note for runtime settings:

```env
PORT=8080
```

The app reads `PORT`; if it is absent, it uses `8080`.

## Local Startup

```bash
cd Spotify_Backend
./gradlew run
```

Health check:

```bash
curl http://localhost:8080/api/health
```

## API Testing

Postman collection:

```text
Spotify Backend - Portfolio API Smoke Tests
```

Variables:

```text
baseUrl=http://localhost:8080
```

Useful checks:

```bash
curl http://localhost:8080/feed
curl http://localhost:8080/playlists
curl http://localhost:8080/playlist/1
```

## Cloud Resources

Google Cloud project:

```text
caramel-vim-441513-e1
```

Region:

```text
us-central1
```

Cloud Run service:

```text
spotify-api
```

Cloud Run URL:

```text
https://spotify-api-gb7rmueyna-uc.a.run.app
```

Cloud Build trigger:

```text
spotify-backend-main-deploy
```

Status:

```text
Pending until the Cloud Build GitHub App has access to trickywork/Spotify_Backend.
```

## Cost Notes

- No Cloud SQL, Elasticsearch, or bucket is required.
- Cloud Run is configured for `min-instances=0`.
- Data changes require editing the JSON resources and redeploying.

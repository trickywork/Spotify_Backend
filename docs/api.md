# Spotify API

Base URL:

- Local: `http://localhost:8080`
- Cloud Run: `https://spotify-api-gb7rmueyna-uc.a.run.app`

## Endpoints

| Method | Path | Description |
| --- | --- | --- |
| GET | `/` | Simple service status text. |
| GET | `/api/health` | Health check for Cloud Run and smoke tests. |
| GET | `/feed` | Home feed sections with playlist ids. |
| GET | `/playlists` | All playlists and songs. |
| GET | `/playlist/{id}` | One playlist by id. |
| GET | `/songs/{file}` | Course-compatible song URL shape. The portfolio demo does not bundle copyrighted audio files. |

## Data Model

`Playlist` contains `id`, `name`, `description`, `cover`, and `songs`.

`Song` contains `id`, `name`, `artist`, `lyric`, `src`, and `length` in seconds.

## Course Notes

The LaiOffer coding pad uses a Ktor backend with `/feed`, `/playlists`, `/playlist/{id}`, and static song resources. This repo keeps the same API surface so an Android or web frontend can call it directly.

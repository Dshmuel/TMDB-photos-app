# Android TMDB with Compose

An app illustrating several technologies like Jetpack Compose UI, Endless scrolling with Paging, Hilt, REST-API communication with network

## Features
The have a Tabs row with genres retrieved from server and a view-pager of movies for these genres.
The genre list is retrieved from the app start and are static from then.
The movie list is lazely loaded as it's page open and remains in cache.
The movies lists are paged - more pages of data loaded from network as user scrolls down the list.

### API key needed to make it work
The app is using TMDB API (https://developer.themoviedb.org/)
To access this API you will need to obtain a free developer API key. That should be put in local.properties file:
```
TMDB_API_KEY=<your TMDB access key>
```

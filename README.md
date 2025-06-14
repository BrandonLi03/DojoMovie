# DojoMovie

DojoMovie is an Android application for browsing, viewing, and tracking movies. It allows users to explore a curated list of movies, view details, and keep a transaction history of their interactions with the films.

## Features

- **Browse Movies:** View a list of available films with images and prices.
- **Movie Details:** Tap on a movie for detailed information.
- **Transaction History:** Track your movie "purchases" or interactions in a dedicated history screen.
- **Database Storage:** Uses a local SQLite database to store film data and user transactions.
- **API Integration:** Fetches and stores movie data from a remote JSON API endpoint.
- **Modern Android Stack:** Built with Kotlin, Room, RecyclerView, ViewBinding, and uses libraries such as Volley and Glide.

## Getting Started

### Prerequisites

- Android Studio (Flamingo or newer recommended)
- Android SDK 34+
- Kotlin

### Setup

1. **Clone the repository:**
   ```bash
   git clone https://github.com/BrandonLi03/DojoMovie.git
   ```

2. **Open in Android Studio:**
   - File > Open > Select the cloned directory

3. **Build the project:**
   - Sync Gradle and allow dependencies to download.

4. **Run the app:**
   - Connect an Android device or use an emulator.
   - Click Run.

### Configuration

- The app fetches movie data from:  
  `https://api.npoint.io/66cce8acb8f366d2a508`

## Project Structure

- `app/src/main/java/com/example/project/`
  - `FilmRepository.kt`: Handles fetching and storing film data.
  - `database/DatabaseHelper.kt`: Manages SQLite database creation and queries.
  - `fragments/HomeFragment.kt`: Displays the movie list.
  - `fragments/HistoryFragment.kt`: Shows transaction history.
  - `adapter/`: RecyclerView adapters for movies and transactions.

## Dependencies

- [Glide](https://github.com/bumptech/glide)
- [Volley](https://developer.android.com/training/volley)
- [Room](https://developer.android.com/jetpack/androidx/releases/room)
- [Gson](https://github.com/google/gson)
- AndroidX Libraries
- Google Material Components

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Author

- BrandonLi03
- Calvin-77
- KeenanAP

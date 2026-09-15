# MovieHub 🎬

MovieHub is a production-ready Android application for discovering popular and trending movies. It demonstrates modern Android development practices using a 100% Kotlin, Jetpack Compose, and Coroutines/Flow based tech stack.

The architecture strictly follows the **MVVM (Model-View-ViewModel)** pattern combined with a Repository pattern for data management, ensuring a clear separation of concerns, scalability, and testability.

## ✨ Features

* **Home Screen:** Infinite scrolling grid of popular movies with seamless pagination.
* **Live Search:** Reactive search screen featuring Coroutine Flow operators (`debounce`, `distinctUntilChanged`) to optimize network calls.
* **Movie Details:** Deep dive into movie details, including high-resolution backdrop banners, overviews, dynamic genre chips, and cast carousels.
* **Type-Safe Navigation:** Utilizes Navigation Compose 2.8.0+ with Kotlin Serialization for strictly typed route arguments.
* **Robust Image Loading:** Image caching and asynchronous loading handled by Coil.
* **Resilient Networking:** Custom `Resource<T>` wrapper to gracefully handle `Success`, `Error`, and `Loading` UI states.

## 🛠 Tech Stack & Libraries

* **UI:** [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3)
* **Architecture:** MVVM (ViewModel, StateFlow)
* **Dependency Injection:** [Dagger Hilt](https://dagger.dev/hilt/)
* **Navigation:** [Navigation Compose](https://developer.android.com/jetpack/compose/navigation) (Type-safe)
* **Networking:** [Retrofit 2](https://square.github.io/retrofit/) & OkHttp3 Logging Interceptor
* **Serialization:** [kotlinx.serialization](https://kotlinlang.org/docs/serialization.html)
* **Asynchronous Programming:** [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) & [Flow](https://kotlinlang.org/docs/flow.html)
* **Image Loading:** [Coil Compose](https://coil-kt.github.io/coil/compose/)

## 🏗 Architecture & Folder Structure

The app is structured by features within a layered architecture framework:

```text
com.acuminx.moviehub
├── core            # Core utilities, wrappers (e.g., Resource wrapper)
├── data            # Data layer: DTOs, API interfaces, Repository implementations
├── di              # Dependency Injection modules (Hilt)
├── domain          # Domain layer: Interfaces for Repositories 
└── presentation    # UI layer: Compose screens, ViewModels, and Navigation components

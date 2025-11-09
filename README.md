# Truecaller Task - Android Assignment

Android app that fetches and analyzes web content from Truecaller blog.

## Features

- Fetches web content from Truecaller blog
- Finds the 15th character
- Extracts every 15th character
- Counts word occurrences (sorted by frequency)
- Modern Material Design 3 UI
- Comprehensive error handling

## Tech Stack

- **Kotlin** with Jetpack Compose
- **MVVM + Clean Architecture**
- **Retrofit** with custom CallAdapter
- **Hilt** for dependency injection
- **Coroutines + Flow** for async operations

## Architecture

The app follows Clean Architecture with three distinct layers:

**Presentation Layer**: Jetpack Compose UI, ViewModels, and state management using UDF pattern

**Domain Layer**: Business logic with use cases (FetchWebContent, Find15thCharacter, FindEvery15thCharacter, CountWordOccurrences), domain models, and repository interfaces

**Data Layer**: Retrofit API implementation, repository implementations, and custom CallAdapter for centralized error handling

## Project Structure

The project is organized into clear packages following Clean Architecture:

- **data**: Contains the network layer with Retrofit API interfaces, custom CallAdapter for error handling, and repository implementations
- **domain**: Houses the business logic with use cases, domain models (WebContent, TaskResult), and repository interfaces
- **presentation**: Includes Jetpack Compose UI components, ViewModels, UI state management, and Material3 theme
- **di**: Hilt dependency injection modules (NetworkModule, AppModule)
- **navigation**: Compose navigation graph setup

## Getting Started

### Requirements
- Android Studio Hedgehog or newer
- Minimum SDK: 24
- Target SDK: 34

### Run the App
1. Clone the repository: `git clone https://github.com/YOUR_USERNAME/truecaller-task.git`
2. Open in Android Studio
3. Sync Gradle files
4. Run on device or emulator

## Key Implementation Highlights

- **Parallel processing**: Three tasks run simultaneously using Kotlin coroutines for optimal performance
- **Custom CallAdapter**: Centralized error handling that automatically wraps all network responses in NetworkResult
- **Type-safe navigation**: Compose Navigation with type-safe routes
- **UDF Pattern**: Unidirectional data flow where events flow up and state flows down



*Created for Truecaller Android Engineer assignment*

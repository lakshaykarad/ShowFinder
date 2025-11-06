# ShowFinder – Movie & TV Show App

## Project Overview
**ShowFinder** is a modern Android application built using **Kotlin**, **Jetpack Compose**, and the **MVVM** architecture.  
The app integrates **Watchmode** and **OMDb APIs** to provide detailed information on movies and TV shows. Users can search for content, view details, and explore rich media content with a dynamic and responsive UI.  

The project implements **Hilt** for dependency injection, **Retrofit/OkHttp** for networking, and **Jetpack Navigation** for smooth app navigation. Error handling and state management ensure a robust user experience.  

---

## Key Features
- 🎬 **Movie & TV Show Search**: Users can search for movies or TV shows by keyword.  
- 📝 **Detailed Information**: Fetch complete details for any item using Watchmode ID.  
- 🖼️ **Images Integration**: OMDb API used to retrieve posters and images for movies and shows.  
- ⚡ **State Management**: Loading, success, and error states with proper error handling.  
- 🏗️ **Architecture**: Clean MVVM structure using ViewModel, Repository, and Hilt DI.  
- 🎨 **UI/UX**: Built entirely with Jetpack Compose for modern and dynamic interfaces.  
- 🔀 **Navigation**: Seamless navigation using NavHost, NavController, and route-based architecture.  

---

## Tested Inputs
1. Search for a specific movie (e.g., “Inception”)  
2. Search for a TV show (e.g., “Breaking Bad”)  
3. Search with invalid/random text  
4. Search with an empty query  
5. Fetch detailed information for a movie/TV show using Watchmode ID  

---

## Architecture & Tools
- **Architecture**: MVVM (Model-View-ViewModel)  
- **Dependency Injection**: Hilt  
- **Networking**: Retrofit + OkHttp  
- **UI Framework**: Jetpack Compose  
- **Navigation**: NavHost, NavController, route-based navigation  
- **APIs**: Watchmode API (data), OMDb API (images)  
- **Error Handling**: Loading, success, error states  

---

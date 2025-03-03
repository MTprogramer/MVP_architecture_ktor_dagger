# MVP Architecture App

This is a simple Android app built with **Jetpack Compose**, **Kotlin**, **Dagger Hilt**, and **Ktor** to fetch and display posts from a REST API (JSONPlaceholder). The app follows the **MVP (Model-View-Presenter)** architecture to keep the code **organized, maintainable, and testable**.

---

## 📌 What is MVP in Simple Words?
MVP is a way to structure your app’s code into three main parts: **Model, View, and Presenter**. Think of it like a team where each member has a specific job:

- **Model** → The "data keeper". It handles getting data (like posts from an API) and knows nothing about how it’s shown on the screen.
- **View** → The "display board". It shows the data to the user (like a list of posts) and tells the Presenter when the user does something (like clicking a post).
- **Presenter** → The "middleman". It talks to the Model to get data and tells the View what to show. It decides what happens when the user interacts with the app.

### 🛠️ How They Work Together
1. The **View** says, "Hey Presenter, the user clicked something!"
2. The **Presenter** says, "Okay, I’ll get data from the Model and tell the View what to show."
3. The **Model** says, "Here’s the data you asked for!"

This separation makes it easier to change one part (like the UI) without breaking others.

---

## 🔧 What Each Part Does in This App

### **Model (M) - Data Management**
**Role:** Manages data—fetches posts from the API and provides them to the Presenter.
- **Post.kt** → Defines the `Post` data class.
- **PostRepository.kt** → Interface defining methods to get posts (`getPosts`, `getPostById`).
- **PostRepositoryImpl.kt** → Implements `PostRepository`, fetches data from API via `PostApi`.
- **PostApi.kt** → Interface for API calls (`getPosts`, `getPostById`).
- **PostApiImpl.kt** → Implements `PostApi` using Ktor’s `HttpClient`.
- **ApiResult.kt** → Sealed class for API results (Success, Error, Loading).

### **View (V) - UI Layer**
**Role:** Shows the UI (screens) and listens to user actions, passing them to the Presenter.
- **MainActivity.kt** → Entry point, sets up navigation with `AppNavigation`.
- **Screens.kt** → Contains:
    - `HomeScreen` → Button to navigate to `PostsScreen`.
    - `PostsScreen` → Displays a list of posts, handles clicks for navigation.
    - `PostDetailScreen` → Shows a post’s details.

### **Presenter (P) - Business Logic**
**Role:** Acts as the brain—gets data from Model, updates View, and handles user actions.
- **PostContract.kt** → Defines `View` and `Presenter` methods.
- **PostPresenter.kt** → Fetches a list of posts, updates the `View`, and handles navigation.
- **PostDetailContract.kt** → Defines contract for the detail screen.
- **PostDetailPresenter.kt** → Fetches a single post, updates the `View`.

---

## 🛠 Dependency Injection (DI)

### **Hilt Setup**
- **AppModule.kt** → Hilt module providing `HttpClient` (Ktor), `PostApi`, and `PostRepository`.
- **MvpApplication.kt** → Custom `Application` class with `@HiltAndroidApp` to enable Hilt.

---

## 📂 Project Structure
```
app/
│── di/
│   ├── AppModule.kt        # Provides dependencies via Hilt
│
│── model/
│   ├── api/
│   │   ├── PostApi.kt      # Defines API methods
│   │   ├── PostApiImpl.kt  # Implements API calls with Ktor
│   │   ├── ApiResult.kt    # Sealed class for API responses
│   ├── data_model/
│   │   ├── Post.kt         # Data model for a Post
│   ├── repo/
│       ├── PostRepository.kt     # Interface defining data operations
│       ├── PostRepositoryImpl.kt # Implementation fetching data from API
│
│── presentation/
│   ├── post/
│   │   ├── PostContract.kt       # Contract for PostPresenter
│   │   ├── PostPresenter.kt      # Handles post fetching and View updates
│   │   ├── PostDetailContract.kt # Contract for PostDetailPresenter
│   │   ├── PostDetailPresenter.kt # Fetches single post details
│
│── view/
│   ├── MainActivity.kt  # Entry point and navigation setup
│   ├── Screens.kt       # Defines UI screens with Compose
│
└── MvpApplication.kt   # Custom Application class with Hilt
```

---

## 🚀 How It Fulfills MVP Requirements

| Layer  | Responsibility | Implementation |
|--------|--------------|----------------|
| Model  | Fetches & manages data | `PostRepositoryImpl` uses `PostApiImpl` to get posts from API |
| View   | Displays data & handles user actions | `PostsScreen` and `PostDetailScreen` update UI based on Presenter commands |
| Presenter | Controls logic & updates UI | `PostPresenter` and `PostDetailPresenter` fetch data, update View, and handle navigation |

---

## 🎯 App Features
✅ **Home Screen** → Button to navigate to `PostsScreen`.<br>
✅ **Posts Screen** → Displays posts from API. Click a post to see details.<br>
✅ **Post Detail Screen** → Shows post’s title, ID, user ID, and body.<br>
✅ **Fetch Data** → Uses Ktor to get posts from API.<br>
✅ **Display Data** → Uses Jetpack Compose to show posts and details.<br>
✅ **Navigation** → Moves between screens with `NavController`.<br>
✅ **Dependency Injection** → Hilt provides `PostRepository` and dependencies.<br>
✅ **MVP Structure** → Separates Model, View, and Presenter for clean architecture.<br>




## Difference Between MVP and MVC (Simple Points)

| Feature | MVP (Model-View-Presenter) | MVC (Model-View-Controller) |
|---------|---------------------------|-----------------------------|
| **Communication** | View and Model interact through Presenter | View interacts directly with Model |
| **Responsibility** | Presenter handles UI logic separately | Controller handles both UI and business logic |
| **Testability** | Easier to test as View and Model are separate | Harder to test due to direct View-Model connection |
| **Complexity** | More structured, good for large apps | Simpler, better for small apps |
| **Dependency** | View is dependent on Presenter | View is dependent on Controller |

---


🚀 **Happy Coding!** 😊


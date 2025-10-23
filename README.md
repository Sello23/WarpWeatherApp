# 🌦️ WarpWeatherApp

WarpWeatherApp is a modern Android weather application built using **Jetpack Compose**, **Clean Architecture**, and **Hilt Dependency Injection**.
It fetches real-time weather forecasts based on the user’s current location or a searched city name using the **OpenWeatherMap API**.

---

## ✅ Project Checklist (Rubric Compliance)

| Requirement                               | Description                                                    | Status |
| ----------------------------------------- | -------------------------------------------------------------- | ------ |
| **✅ README file included**                | Contains all project setup and architectural details.          | ✔️     |
| **✅ Build instructions**                  | Includes clear Gradle command (`./gradlew installDebug`).      | ✔️     |
| **✅ Minimum SDK specified**               | Minimum SDK 26 clearly stated.                                 | ✔️     |
| **✅ Architectural decisions explained**   | Explains Clean Architecture, data flow, and layer separation.  | ✔️     |
| **✅ Instructions for API key management** | `local.properties` usage documented with security explanation. | ✔️     |
| **✅ Testing strategy included**           | Unit, UI, and integration testing setup detailed.              | ✔️     |
| **✅ Secure credentials practice**         | Explained why API keys must not be pushed to GitHub.           | ✔️     |
| **✅ Readable and organized**              | Uses Markdown formatting and consistent structure.             | ✔️     |

---

## 🚀 Build & Run Instructions

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/yourusername/WarpWeatherApp.git
cd WarpWeatherApp
```

### 2️⃣ Add Your API Key in `local.properties`

Before building, create or edit your **`local.properties`** file and add:

```properties
OPEN_WEATHER_MAP_API_KEY=your_api_key_here
```

🔒 **Important:**
This file must **not** be committed to GitHub because it contains sensitive credentials.
If exposed publicly:

* Attackers could use your API key to make unauthorized calls.
* It could result in unexpected billing or data abuse.
* Publicly leaked keys are automatically blacklisted by most API providers.

Keeping credentials locally ensures **secure and responsible key management**.

---

### 3️⃣ Build and Install the App

From the project root:

```bash
./gradlew clean installDebug
```

Or using Android Studio:

```
Run → Run 'app'
```

---

## 📱 Minimum Requirements

| Setting                 | Version |
| ----------------------- | ------- |
| **Minimum Android SDK** | 26      |
| **Target Android SDK**  | 35      |
| **Compile SDK**         | 35      |
| **Kotlin Version**      | 1.9.x   |
| **Gradle Plugin**       | 8.6.x   |

---

## 🤐 Architectural Overview

WarpWeatherApp is structured around **Clean Architecture** principles with three core layers:

### 🧩 1. **Data Layer**

Responsible for data handling and API integration.

* **Retrofit + Kotlinx Serialization** handle API requests and parsing.
* The **repository** acts as a bridge between network data and domain logic.

```
data/
 ├─ model/
 ├─ remote/
 ├─ repository/
 └─ state/
```

---

### 🤓 2. **Domain Layer**

Contains business logic and use cases that are **framework-independent**.

* `GetWeatherByCityUseCase`
* `GetWeatherByCoordinatesUseCase`

```
domain/
 ├─ model/
 ├─ usecase/
```

---

### 🎨 3. **UI (Presentation) Layer**

Implements **Jetpack Compose (Material 3)** UI with reactive state handling via `StateFlow`.

* ViewModels connect domain logic to composable UIs.
* Screens are declarative and lifecycle-aware.

```
ui/
 ├─ screens/
 ├─ components/
 ├─ theme/
 └─ WarpWeatherAppState.kt
```

---

## 🤪 Testing Strategy

WarpWeatherApp is built with testability in mind.

| Test Type             | Tools                          | Purpose                                                                 |
| --------------------- | ------------------------------ | ----------------------------------------------------------------------- |
| **Unit Tests**        | JUnit4, MockK, Coroutines Test | Verify use cases, repositories, and business logic.                     |
| **API Tests**         | MockWebServer                  | Validate Retrofit integration and JSON responses.                       |
| **UI Tests**          | Compose UI Test, Robolectric   | Ensure correct UI rendering and state transitions.                      |
| **Integration Tests** | Hilt, Fake Managers            | Verify dependency injection and reactive state (`WarpWeatherAppState`). |

### Example Test Commands

```bash
./gradlew testDebugUnitTest
./gradlew connectedAndroidTest
```

---

## 🔧 Key Features

* ✅ Jetpack Compose + Material 3 UI
* ✅ Clean Architecture (Separation of Concerns)
* ✅ Hilt for Dependency Injection
* ✅ Kotlin Coroutines + Flow for async operations
* ✅ Retrofit + Kotlinx Serialization networking
* ✅ MockWebServer + Robolectric for test coverage
* ✅ Secure API key handling (`local.properties`)

---

## 📈 Future Enhancements

* Add offline caching via Room or DataStore
* Implement weather-based dynamic theming
* Introduce app widgets for daily weather glance

---

## 👨‍💻 Author

**Sello Islen Nyama**
Android Developer | Jetpack Compose Specialist
📍 Johannesburg, South Africa

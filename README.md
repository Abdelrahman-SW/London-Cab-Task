<h1 align="center"> <img src="https://github.com/devicons/devicon/blob/master/icons/jetpackcompose/jetpackcompose-original.svg" alt="jetpack compose" width="40" height="40"/> 
  📱 MyTasks – Android Multi-Module App
  <img src="https://github.com/devicons/devicon/blob/master/icons/jetpackcompose/jetpackcompose-original.svg" alt="jetpack compose" width="40" height="40"/>
</h1> 
<h3 align="center" > --- An Android App In Kotlin & Jetpack Compose ---</h3>

<p align="center"> 
<img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/android/android-original-wordmark.svg" alt="android" width="40" height="40"/>   
<img src="https://www.vectorlogo.zone/logos/kotlinlang/kotlinlang-icon.svg" alt="kotlin" width="40" height="40"/>  
<img src="https://github.com/devicons/devicon/blob/master/icons/jetpackcompose/jetpackcompose-original.svg" alt="jetpack compose" width="40" height="40"/> 
</p>


<h2 align="left">About :</h2>

An Android application showcasing best practices in Kotlin, Clean Architecture, and Jetpack.
This project is designed as a example that demonstrates architecture, modularization, and advanced Android development skills.

<h2 align="left">🚀 Features :</h2>


📝 Task management with offline-first support

🔐 Secure authentication with token storage

📊 Dynamic Analytics feature module (on-demand install)

🎨 Built with Jetpack Compose for UI

<h2 align="left">🏗️ Architecture & Tech Stack</h2>

Language: Kotlin (with coroutines, sealed classes, extension functions)

Architecture: Clean Architecture + MVI

DI: Koin

Multi-module: 

core – shared utilities, networking, persistence

auth – authentication flow

tasks – task management feature

analytics – dynamic feature module

Networking: Ktor Client + OkHttp engine + JSON serialization

Persistence: Room + EncryptedSharedPreferences for secure token storage

UI: Jetpack Compose, Material3

📦 Android SDK & Jetpack

ViewModel, LiveData, Room, Navigation

Runtime permissions (notifications simulation)

Compose UI for tasks & auth screens

🌐 API Integration & Security

Ktor Client for REST API (mocked backend)

JWT-based authentication simulation

Secure storage of tokens with EncryptedSharedPreferences

💾 Data & Offline Support

Room database with schema migrations

Offline-first approach (local cache + sync simulation)

🔒 Authentication & Security

EncryptedSharedPreferences for token storage

HTTPS by default, SSL pinning simulation

Obfuscation (ProGuard/R8)

🧪 Testing

Unit Tests: JUnit + Mock + google truth

UI Tests: Compose UI Testing

Snapshot Testing: Paparazzi


⚡ Performance

Memory leak detection with LeakCanary

Optimized coroutines for async work

🔄 Background Tasks

WorkManager for background sync (daily Notification every 2 hour , syncing data every 30 min)

📈 Scalability & Modularization

Dynamic feature module: analytics

Feature flags & mocked remote config

Clearly separated core, auth, tasks, analytics

🔑 Version Control

Feature branch workflow

Semantic versioning & release tags

Sample changelog included

📝 Documentation & Code Quality

KDoc comments in code

Architecture overview diagram in docs/

Static analysis: ktlint, detekt

Clean Git commit history

<h2 align="left">📂 Project Structure</h2>

app/              → Main app (entry point, navigation host)
core/             → Shared utilities (networking, persistence, UI base)
auth/             → Authentication module
tasks/            → Task management feature
analytics/        → Dynamic feature module

<h2 align="left">▶️ Getting Started</h2>

Clone the repo:

git clone https://github.com/Abdelrahman-SW?tab=repositories


Open in Android Studio (latest stable).

Select build variant (freeDebug, paidDebug).

Run on emulator or device.

<h1 align="center">App Screenshots : </h1>

<br></br>
<p align="center">
  <img src="https://github.com/user-attachments/assets/4947fab2-7f73-4016-b5fb-9e33d40d07d9" width="360" height="720">
</p>
<br></br>

<br></br>
<p align="center">
  <img src="https://github.com/user-attachments/assets/92ed7872-9379-4794-86ce-737b7f624d90" width="360" height="720">
</p>
<br></br>

<br></br>
<p align="center">
  <img src="https://github.com/user-attachments/assets/209f8cca-3adf-4689-91e4-b228a336aa91" width="360" height="720">
</p>
<br></br>

<br></br>
<p align="center">
  <img src="https://github.com/user-attachments/assets/6145f4f8-f1a7-40f6-af76-fca4587506db" width="360" height="720">
</p>
<br></br>

<br></br>
<p align="center">
  <img src="https://github.com/user-attachments/assets/a3ab9373-c2d1-4cd6-8522-dcae7ddda747" width="360" height="720">
</p>
<br></br>



<h2 align="left">Conclusion</h2>
This project demonstrates clean architecture, modularization, and modern Android development practices.  
It includes First Offline App , Dynamic Modeul , product flavors, secure handling of secrets, testing strategies  
The goal is to keep the codebase scalable, maintainable, and production-ready while supporting future growth.

<br></br>
-- This App Was Developed At Aug 2025 📅
<br></br>


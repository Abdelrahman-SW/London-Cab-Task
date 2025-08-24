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

<h2 align="left">🏗️ Architecture</h2>

The code Is Splitted By Features so each Feature in the code has it`s own Module 
then inside each feature Module the code is packaged by 3 different layers : presentation,domain,data and the di for dependency Injection , so what is the benefit of that
and why this considered as a clean arch code lets talk about this in next section.


<h2>Splitting Android Code into Presentation, Domain, and Data Layers: Benefits and Best Practices</h2>

<p>In Android development, organizing your code into distinct layers—Presentation, Domain, and Data—is a powerful architectural strategy. This separation of concerns improves code maintainability, testability, and scalability. Let's explore each layer and the benefits of this approach.</p>

<h3>1. Presentation Layer</h3>
<p><strong>Description:</strong><br>
The Presentation layer is responsible for displaying data to the user and handling user interactions. It typically includes Activities, Fragments, Views, and ViewModels in MVVM architecture.</p>

<p><strong>Responsibilities:</strong></p>
<ul>
  <li>Managing UI components and rendering data.</li>
  <li>Handling user inputs and updating the UI accordingly.</li>
  <li>Communicating with the ViewModel to fetch or send data.</li>
</ul>

<h3>2. Domain Layer</h3>
<p><strong>Description:</strong><br>
The Domain layer contains the business logic of the application. It is independent of any other layers, making it easy to test and reuse.</p>

<p><strong>Responsibilities:</strong></p>
<ul>
  <li>Encapsulating the core business logic.</li>
  <li>Defining use cases or interactors that represent specific business actions.</li>
  <li>Providing a clear API for the Presentation layer.</li>
</ul>

<h3>3. Data Layer</h3>
<p><strong>Description:</strong><br>
The Data layer is responsible for managing data sources. It includes repositories, data sources (remote and local), and data models. This layer abstracts the data operations from the rest of the application.</p>

<p><strong>Responsibilities:</strong></p>
<ul>
  <li>Handling data operations, such as network requests, database queries, and caching.</li>
  <li>Providing a unified data interface to the Domain layer.</li>
  <li>Managing data models and mappings.</li>
</ul>

<h3>Benefits of Layered Architecture</h3>
<p><strong>Separation of Concerns:</strong></p>
<ul>
  <li>Each layer has a distinct responsibility, making the code easier to understand, maintain, and extend.</li>
</ul>

<p><strong>Testability:</strong></p>
<ul>
  <li>Isolated layers allow for easier unit testing. For example, the Domain layer can be tested without dependencies on the Presentation or Data layers.</li>
</ul>

<p><strong>Reusability:</strong></p>
<ul>
  <li>Business logic encapsulated in the Domain layer can be reused across different parts of the application or even in different applications.</li>
</ul>

<p><strong>Maintainability:</strong></p>
<ul>
  <li>Changes in one layer (e.g., switching from a REST API to GraphQL in the Data layer) have minimal impact on other layers.</li>
</ul>

<p><strong>Scalability:</strong></p>
<ul>
  <li>The clear separation allows for easier scaling of the codebase. Different teams can work on different layers simultaneously without causing conflicts.</li>
</ul>

<br></br>

<h2 align="left">🏗️ Tech Stack</h2>

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
  <img src="https://github.com/user-attachments/assets/6b1ec5c2-68ea-4116-bf46-39eaf2d3a65b" width="360" height="720">
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


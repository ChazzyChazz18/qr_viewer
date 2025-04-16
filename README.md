# QR Viewer App

Welcome to the **QR Viewer**! 🚀 This application combines the power of native Android features and Flutter to deliver a seamless user experience. With QR Viewer, users can:

- Log in securely using **biometric authentication**.
- Scan QR codes using an advanced camera interface.
- View a list of scanned QR codes, including the timestamp of when they were scanned.

---

## Features

### Android (Native)
- **Biometric Authentication:** Secure login via fingerprint or facial recognition using the `BiometricPrompt API`.
- **QR Code Scanning:** A robust scanning mechanism built with `CameraX` for fast and accurate QR code detection.
- **Room Database:** Efficient local storage of scanned QR data with timestamps.
- **MVVM Architecture:** A clean and structured approach to organizing your code using **ViewModel** for state management.
- **Jetpack Compose:** Modern declarative UI framework for Android.
- **Flow:** Streamlined state management for asynchronous tasks.

### Flutter
- **Bloc Architecture:** Highly testable, reactive state management for a clean and scalable codebase.
- **Cross-Platform Compatibility:** Simplifying user interactions across devices while using Flutter widgets for seamless UI.

### Interaction Between Flutter and Native Android
- **Pigeon**: Facilitates the smooth communication between Flutter and native Android code by generating structured messaging channels.

---

## Technology Stack

### Android
- **Language:** Kotlin
- **Frameworks/Tools:** CameraX, BiometricPrompt API, Flow, Room Database, Jetpack Compose, MVVM, ViewModel

### Flutter
- **Language:** Dart
- **Architecture:** Bloc

---

## Getting Started

### Prerequisites
1. Install the latest version of [Android Studio](https://developer.android.com/studio) and [Flutter](https://flutter.dev/).
2. Ensure you have an Android device/emulator with biometric capabilities.

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/ChazzyChazz18/qr-viewer.git
   cd qr-viewer-app

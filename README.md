# Android TV Video Browser

A lightweight Android TV application built using Kotlin, RecyclerView, and ExoPlayer.  
Users can browse a list of videos, navigate using the TV D-pad, and play videos with custom playback controls.

---
// Demo video

![Screen_recording_20251115_120030-ezgif com-video-to-gif-converter](https://github.com/user-attachments/assets/42fdb552-4374-4f07-950e-836d920e65a6)



## 📥 Setup Instructions (How to Run the App)

1. Download or clone this repository.
2. Open the project in **Android Studio (Flamingo or later)**.
3. Allow Gradle to sync automatically.
4. Set up an **Android TV emulator** (Google TV or AOSP TV, API 29+).
5. Press **Run** to install the app.
6. Navigate using:
   - **D-Pad Up/Down/Left/Right**
   - **OK/Center** to open a video or toggle play/pause
   - **Back** to exit playback

---

## 📦 Dependencies Used

- **ExoPlayer** – video playback
- **Coil** – image loading for thumbnails
- **RecyclerView** – TV-optimized video grid
- **ConstraintLayout** – UI layout management
- **Material Components** – UI styling
- **SharedPreferences** – store last played video memory
- **AndroidX Core / AppCompat** – platform support libraries

---

## 📱 Device / Emulator Used for Testing

- **Android TV Emulator (1080p, API 29)**
- **Google TV Emulator (API 33)**
- **NVIDIA Shield TV (Android 11)**
- **Xiaomi MiBox (Android 9)**

---

## 🧱 Architecture Choices

- **Kotlin** as the main programming language
- **Single-Activity architecture** for simplicity and performance
- **RecyclerView + GridLayoutManager** for smooth D-pad navigation
- **Custom focus animations** (scale/elevation) for TV compliance
- **Custom ExoPlayer controls**:
  - Play/Pause
  - Seek Forward / Rewind
  - SeekBar with live progress
  - Auto-hide after 5 seconds
- **SharedPreferences** used for “Last Played” video memory
- **Handlers** used for inactivity auto-play and auto-hide logic
- **Back navigation** supports:
  - Android 13+ (`OnBackInvokedDispatcher`)
  - Pre-13 devices (`OnBackPressedDispatcher`)
  - Old TV remotes (`KEYCODE_BACK`, `KEYCODE_ESCAPE`)

---

## ⚠️ Known Issues / Limitations

- Thumbnails require an active internet connection.
- SeekBar behavior may vary on certain low-cost Android TV boxes.
- Auto-play is canceled immediately if the user interacts within 5 seconds.
- Subtitle support is not included (can be added with ExoPlayer).
- Leanback library is not used; UI is custom-built with RecyclerView.

---


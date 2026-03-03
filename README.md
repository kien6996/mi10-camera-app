# MI10 Camera App

## Build Instructions for APK

Follow these steps to build the APK for the MI10 Camera App:

1. **Clone the repository**:
   ```bash
   git clone https://github.com/kien6996/mi10-camera-app.git
   cd mi10-camera-app
   ```

2. **Install dependencies**:
   Make sure you have the required SDKs and build tools. You can find the necessary dependencies in the `build.gradle` file.
   Follow the instructions provided in the Android documentation to install:
   - Android Studio
   - Android SDK
   - Build Tools

3. **Open the project in Android Studio**:
   - Open Android Studio.
   - Select `Open an existing Android Studio project`.
   - Navigate to the cloned repository and open it.

4. **Sync the project**:
   When prompted, sync the project with Gradle files to ensure all dependencies are properly configured.

5. **Build the APK**:
   - Select `Build` from the top menu.
   - Click on `Build Bundle(s) / APK(s)`.
   - Select `Build APK(s)`.

6. **Locate the APK**:
   Once the build is complete, you can find the APK in the `app/build/outputs/apk/debug` directory.

7. **Installation**:
   You can install the APK on your device by running:
   ```bash
   adb install path/to/your/app.apk
   ```

8. **Run the application**:
   After installation, you can run the MI10 Camera App on your device.

### Additional Notes
- Ensure you have connected your device to your computer and that USB debugging is enabled.
- Refer to Android documentation for more details on configuration issues, if any.

Happy coding!
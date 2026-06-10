# Keep Retrofit models
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.example.workoutapp.network.model.** { *; }
-dontwarn okhttp3.**
-dontwarn retrofit2.**

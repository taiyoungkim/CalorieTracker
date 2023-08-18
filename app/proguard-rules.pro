# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# protobuf
-keepclassmembers class * extends com.google.protobuf.GeneratedMessageLite* {
   <fields>;
}

#retofit
# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions
#jsoup
-keep public class org.jsoup.**{
    public *;
}

#okhttp3
-dontwarn okhttp3.**
-dontwarn okio.**
-dontnote okhttp3.**

-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.SerializationKt
-keep,includedescriptorclasses class com.tydev.tracker.data.**$$serializer { *; }
-keepclassmembers class com.tydev.tracker.data.* {
    *** Companion;
}
-keepclasseswithmembers class com.tydev.tracker.data.* {
     kotlinx.serialization.KSerializer serializer(...);
}

-keepclasseswithmembers class com.tydev.tracker.data.* { *; }
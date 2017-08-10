# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\user\Documents\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
-renamesourcefileattribute SourceFile

-keep public class * implements com.levarech.weatcher.domain.** { public *; }

-keepclassmembers class * {
    @javax.inject.Inject public <init>(...);
}

-keepparameternames

-keepattributes Exceptions,InnerClasses,Signature,Deprecated,*Annotation*,EnclosingMethod

-keeppackagenames com.levarech.weatcher.data.**

# Retrolambda
-dontwarn java.lang.invoke.*

# To keep all json data model class
-keepclassmembernames class com.levarech.weatcher.data.**.network.response.* { <fields>; }

# To keep all classes and it's members, that used for Realm entity model
-keepnames class * extends io.realm.RealmObject { <fields>; }

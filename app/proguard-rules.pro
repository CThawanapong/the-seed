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

-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose

#Default
#keep all public and protected methods that could be used by java reflection
-keepclassmembernames class * { public protected <methods>; }
-keepclasseswithmembernames class * { native <methods>; }
-keepclasseswithmembernames class * { public <init>(android.content.Context, android.util.AttributeSet); }
-keepclasseswithmembernames class * { public <init>(android.content.Context, android.util.AttributeSet, int); }
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable { public static final android.os.Parcelable$Creator *; }

# Serialize
-keepattributes Signature,Exceptions,*Annotation*,SourceFile,LineNumberTable,EnclosingMethod
-keep public class * extends java.lang.Exception
-keep class sun.misc.Unsafe {*;}
-dontnote sun.misc.Unsafe
-keepclassmembers class * extends androidx.viewbinding.ViewBinding {
    public static *** bind(android.view.View);
}

#Gson
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Moshi
-keepclasseswithmembers class * {
    @com.squareup.moshi.* <methods>;
}
# Enum field names are used by the integrated EnumJsonAdapter.
# Annotate enums with @JsonClass(generateAdapter = false) to use them with Moshi.
-keepclassmembers @com.squareup.moshi.JsonClass class * extends java.lang.Enum {
    <fields>;
}

# The name of @JsonClass types is used to look up the generated adapter.
-keepnames @com.squareup.moshi.JsonClass class *

# Retain generated JsonAdapters if annotated type is retained.
-keep class **JsonAdapter {
    <init>(...);
    <fields>;
}

#Square Config
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
-dontwarn com.squareup.okhttp3.internal.huc.**
-dontnote com.squareup.**
-dontwarn okhttp3.**
-dontnote okhttp3.**
-dontwarn com.okio.**
-dontwarn okio.**
-dontnote okio.**
-dontwarn retrofit2.**
-keep,includedescriptorclasses class retrofit2.** { *; }
-dontwarn javax.annotation.**
-dontwarn org.codehaus.mojo.animal_sniffer.*
-dontwarn okhttp3.internal.platform.ConscryptPlatform

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# Jackson
-keep @com.fasterxml.jackson.annotation.JsonIgnoreProperties class * { *; }
-keep class com.fasterxml.** { *; }
-keep class org.codehaus.** { *; }
-keepnames class com.fasterxml.jackson.** { *; }
-keepclassmembers public final enum com.fasterxml.jackson.annotation.JsonAutoDetect$Visibility {
    public static final com.fasterxml.jackson.annotation.JsonAutoDetect$Visibility *;
}

#Glide
-keep class com.bumptech.glide.GeneratedAppGlideModuleImpl { *; }
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule { *; }
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-dontnote com.bumptech.glide.**

# Picasso
-dontwarn com.squareup.picasso.**

# Google Stuff
-keep class com.google.android.gms.phenotype.Phenotype { *; }
-dontwarn com.google.firebase.**
-dontwarn com.google.errorprone.annotations.*
-dontnote com.google.**
-dontwarn com.android.installreferrer.api.**
-keep class com.google.android.gms.ads.identifier.** { *; }
-keepclassmembers class * extends com.google.crypto.tink.shaded.protobuf.GeneratedMessageLite {
  <fields>;
}

# Filter out warnings that refer to legacy Code.
-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

#Volley
-dontnote com.android.volley.**

#Line Indicator
-dontwarn com.viewpagerindicator.LinePageIndicator
-dontnote com.viewpagerindicator.**

#Fast Scroller
-keep class com.simplecityapps.** {*;}

#Firebase
-keep class com.firebase.** { *; }
-keep class org.apache.** { *; }
-dontnote org.apache.**
-keepnames class com.fasterxml.jackson.** { *; }
-keepnames class javax.servlet.** { *; }
-keepnames class org.ietf.jgss.** { *; }
-dontwarn org.w3c.dom.**
-dontwarn org.joda.time.**
-dontwarn org.shaded.apache.**
-dontwarn org.ietf.jgss.**

#Chart
-keep class com.github.mikephil.charting.** { *; }
-keep class com.akexorcist.roundcornerprogressbar.** { *; }
-dontwarn com.akexorcist.roundcornerprogressbar.TextRoundCornerProgressBar

#Jackson & JWTS
-keepnames class com.fasterxml.jackson.databind.** { *; }
-dontwarn com.fasterxml.jackson.databind.**
-keepattributes InnerClasses

-keep class org.bouncycastle.** { *; }
-keepnames class org.bouncycastle.* { *; }
-dontwarn org.bouncycastle.**

-keep class io.jsonwebtoken.** { *; }
-keepnames class io.jsonwebtoken.* { *; }
-keepnames interface io.jsonwebtoken.* { *; }

-dontwarn javax.xml.bind.DatatypeConverter
-dontwarn io.jsonwebtoken.impl.Base64Codec

-keepnames class com.fasterxml.jackson.** { *; }
-keepnames interface com.fasterxml.jackson.** { *; }

#Line
-keep class com.linecorp.linesdk.** { *; }
-dontwarn com.linecorp.linesdk.**
-dontnote com.linecorp.**

# Kotlin
-keepclassmembers class **$WhenMappings {
    <fields>;
}
-dontwarn org.jetbrains.annotations.**
-dontnote kotlin.**
-dontnote kotlinx.**
-dontwarn kotlin.internal.annotations.**
-dontwarn kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor
-dontwarn kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor
-dontwarn kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptorWithTypeParameters
-dontwarn kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor
-dontwarn kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertyDescriptorImpl
-dontwarn kotlin.reflect.jvm.internal.impl.load.java.JavaClassFinder
-dontwarn kotlin.reflect.jvm.internal.impl.resolve.OverridingUtil
-dontwarn kotlin.reflect.jvm.internal.impl.types.DescriptorSubstitutor
-dontwarn kotlin.reflect.jvm.internal.impl.types.DescriptorSubstitutor
-dontwarn kotlin.reflect.jvm.internal.impl.types.TypeConstructor
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}

# RxJava
-keep class rx.schedulers.Schedulers { public static <methods>; }
-keep class rx.schedulers.ImmediateScheduler { public <methods>; }
-keep class rx.schedulers.TestScheduler { public <methods>; }
-keep class rx.schedulers.Schedulers { public static ** test(); }
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    long producerNode;
    long consumerNode;
}
-dontnote io.reactivex.**
-dontnote org.reactivestreams.**
-dontnote rx.schedulers.**
-dontnote rx.internal.**
-dontnote durdinapps.rxfirebase2.**

# Cloudflare
-dontwarn com.cloudflare.**
-dontnote com.cloudflare.**

# Facebook
-keep class com.facebook.applinks.** { *; }
-keepclassmembers class com.facebook.applinks.** { *; }
-keep class com.facebook.FacebookSdk { *; }
-dontnote com.facebook.**
-dontnote bolts.**

# Twitter
-dontnote com.twitter.**

# Intro Stepper
-dontnote com.stepstone.stepper.**

# Material EditText & Dialog
-dontnote com.rengwuxian.materialedittext.**
-dontnote com.afollestad.materialdialogs.**

# Ink Page Indicator
-dontnote com.pixelcan.inkpageindicator.**

# None Old Android
-dontnote com.nineoldandroids.animation.**

# Range Bar
-dontnote com.appyvet.rangebar.**

# Photo View
-dontnote com.github.chrisbanes.photoview.**

# Observable Scrollview
-dontnote com.github.ksoichiro.android.observablescrollview.**

# Wheel View
-dontnote com.lantouzi.wheelview.**

# Smart Tab Layout
-dontnote com.ogaclejapan.smarttablayout.**

# Advanced Recyclerview
-dontnote jp.wasabeef.recyclerview.**

# Secure Preference
-dontnote com.tozny.crypto.**

# Fast scroll recyclerview
-dontwarn com.simplecityapps.recyclerview_fastscroll.**

# UCrop
-dontwarn com.yalantis.ucrop**
-keep class com.yalantis.ucrop** { *; }
-keep interface com.yalantis.ucrop** { *; }

# UX Cam
-keep class com.uxcam.** { *; }
-dontwarn com.uxcam.**

# Mixpanel
-dontwarn com.mixpanel.**

# Branch
-dontwarn io.branch.**

# Twilio
-keep class tvi.webrtc.** { *; }
-keep class com.twilio.video.** { *; }
-keepattributes InnerClasses

# New Relic
-keep class com.newrelic.** { *; }
-dontwarn com.newrelic.**

# Test Dependencies
-dontwarn android.test.**
-dontwarn org.junit.**
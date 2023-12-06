import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
	alias(libs.plugins.androidApplication)
	alias(libs.plugins.kotlinAndroid)
	alias(libs.plugins.ksp)
	alias(libs.plugins.hilt)
}

android {
	namespace = "com.dimovsoft.shutterfly"
	compileSdk = 34

	defaultConfig {
		applicationId = "com.dimovsoft.shutterfly"
		minSdk = 29
		targetSdk = 34
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		vectorDrawables {
			useSupportLibrary = true
		}
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
		}
		getByName("debug") {
			val key = gradleLocalProperties(rootDir, providers).getProperty("TMDB_API_KEY")
			buildConfigField("String", "TMDB_API_KEY", key)
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}
	kotlinOptions {
		jvmTarget = "1.8"
	}
	buildFeatures {
		compose = true
		buildConfig = true
	}
	composeOptions {
		kotlinCompilerExtensionVersion = "1.5.1"
	}
	packaging {
		resources {
			excludes += "/META-INF/{AL2.0,LGPL2.1}"
		}
	}
}

dependencies {
	implementation(libs.androidx.paging.compose)
	ksp(libs.hilt.android.compiler)

	implementation(libs.core.ktx)
	implementation(libs.lifecycle.runtime.ktx)
	implementation(libs.activity.compose)
	implementation(platform(libs.compose.bom))
	implementation(libs.ui)
	implementation(libs.ui.graphics)
	implementation(libs.ui.tooling.preview)
	implementation(libs.material3)
	implementation(libs.compose.foundation)
	implementation(libs.window)

	implementation(libs.hilt.android)

	implementation(libs.glide)

	implementation(libs.gson)
	implementation(libs.okhttp3.logging.interceptor)
	implementation(libs.retrofit2.converter.gson)
	implementation(libs.retrofit2)

	testImplementation(libs.junit)
	androidTestImplementation(libs.androidx.test.ext.junit)
	androidTestImplementation(libs.espresso.core)
	androidTestImplementation(platform(libs.compose.bom))
	androidTestImplementation(libs.ui.test.junit4)
	debugImplementation(libs.ui.tooling)
	debugImplementation(libs.ui.test.manifest)
}
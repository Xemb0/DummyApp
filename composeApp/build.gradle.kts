import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)

    alias(libs.plugins.jetbrains.kotlin.serialization)


    //Room
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
//    alias(libs.plugins.jetbrains.kotlin.serialization)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            export("io.github.mirzemehdi:kmpnotifier:1.6.0")
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    room {
        schemaDirectory("$projectDir/schemas")
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)


            //Koin DI
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)


            //Ktor
            implementation(libs.ktor.client.okhttp)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            //Navigation
            implementation(libs.navigation.compose)
            implementation(libs.kotlinx.serialization.json)

            //material icon extended
            implementation(compose.materialIconsExtended)

            //Koin DI
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            api(libs.koin.core)

            //Room
//            implementation(libs.kotlinx.serialization.json)
            implementation(libs.androidx.room.runtime)
            implementation(libs.sqlite.bundled)

            //Ktor
            implementation(libs.bundles.ktor)

            //Datetime
            implementation(libs.kotlinx.datetime)

            //datastore
            api(libs.datastore.pref)
            api(libs.datastore)
            implementation(libs.atomicfu)

            // Kermit logger
            implementation(libs.kermit)

            //Firestore DB
            implementation(libs.firebase.firestore)


            //notification
            api("io.github.mirzemehdi:kmpnotifier:1.6.0")

            //Coil
            implementation("io.coil-kt.coil3:coil-compose:3.3.0")
//            implementation("io.coil-kt.coil3:coil-network-okhttp:3.3.0")



            implementation("com.darkrockstudios:mpfilepicker:3.1.0")
            implementation("io.github.ismoy:imagepickerkmp:1.0.26")

        }

        nativeMain.dependencies {
            implementation(libs.ktor.client.darwin)


        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.app.harigaji"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.app.harigaji"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    //Room KSP
    add("kspAndroid", libs.androidx.room.compiler)
    add("kspIosSimulatorArm64", libs.androidx.room.compiler)
//    add("kspIosX64", libs.androidx.room.compiler)
    add("kspIosArm64", libs.androidx.room.compiler)


    debugImplementation(compose.uiTooling)
}

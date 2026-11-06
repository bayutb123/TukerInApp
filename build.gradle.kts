// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "9.5.1" apply false
    id("com.android.library") version "9.5.1" apply false
    // AGP 9 compiles Kotlin itself, so the Kotlin Android plugin is no longer
    // applied. Compose needs the Kotlin compose compiler plugin, whose
    // version must match the Kotlin version AGP uses.
    id("org.jetbrains.kotlin.plugin.compose") version "2.4.21" apply false
    id("com.google.dagger.hilt.android") version "2.60.1" apply false
    id("com.google.devtools.ksp") version "2.3.13" apply false
}
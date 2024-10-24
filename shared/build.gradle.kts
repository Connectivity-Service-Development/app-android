import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask
import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.openapi)
}

val openapiOutputDir = layout.buildDirectory.dir("generated/openapi")

android {
    namespace = "cz.eman.skoda.csd.shared"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    sourceSets {
        getByName("main").kotlin.srcDir(openapiOutputDir.map { it.dir("src/main") })
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

val modulePackageName = "${android.namespace}.data"
openApiGenerate {
    generatorName.set("kotlin")
    library.set("jvm-retrofit2")
    configOptions.putAll(
        mapOf(
            "serializationLibrary" to "kotlinx_serialization",
            "useCoroutines" to "true",
        ),
    )

    inputSpec.set(projectDir.resolve("src/main/specs/openapi.json").path)
    outputDir.set(openapiOutputDir.map { it.asFile.path })

    val packageName = "$modulePackageName.api"
    apiPackage.set("$packageName.service")
    modelPackage.set("$packageName.model")
    modelNameSuffix.set("Dto")

    generateApiTests.set(false)
    generateApiDocumentation.set(false)
    generateModelTests.set(false)
    generateModelDocumentation.set(false)
}

tasks.withType<GenerateTask> {
    inputs.property(
        "ignores",
        """
        /*
        gradle/
        """.trimIndent(),
    )
    doFirst("Generate Generator ignore file") {
        File(outputDir.get(), ".openapi-generator-ignore").writeText(inputs.properties["ignores"] as String)
    }
}

tasks.withType<KotlinCompilationTask<*>> {
    dependsOn(tasks.openApiGenerate)
}

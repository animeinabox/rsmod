plugins {
    id("base-conventions")
    id("integration-test-suite")
}

kotlin {
    explicitApi()
}

dependencies {
    implementation(libs.fastutil)
    implementation(libs.guice)
    implementation(projects.api.cache)
    implementation(projects.api.config)
    implementation(projects.api.player)
    implementation(projects.api.repo)
    implementation(projects.api.type.typeReferences)
    implementation(projects.engine.game)
    implementation(projects.engine.map)
    implementation(projects.engine.objtx)
    implementation(projects.engine.plugin)
    integrationImplementation(projects.api.config)
    integrationImplementation(projects.api.type.typeReferences)
    integrationImplementation(projects.engine.objtx)
}

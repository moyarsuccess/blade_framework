plugins {
    id("maven-publish")
    signing
}

afterEvaluate {
    publishing {
        publications {
            val publication = create<MavenPublication>("release") {
                configurePublication()
                configurePom()
            }
            signing.sign(publication)
        }

        repositories {
            maven {
                configureUrl()
                configureCredentials()
            }
        }
    }

    setProperty("signing.keyId", System.getenv("MOYAR_SIGNING_KEY_ID"))
    setProperty("signing.password", System.getenv("MOYAR_SIGNING_KEY_PASSWORD"))
}

fun MavenPublication.configurePublication() {
    groupId = project.ext.properties["PUBLICATION_GROUP_ID"].toString()
    artifactId = project.ext.properties["PUBLICATION_ARTIFACT_ID"].toString()
    version = project.ext.properties["PUBLICATION_VERSION"].toString()

    from(components["release"])
}

fun MavenPublication.configurePom() {
    pom {
        name.set(project.name)
        packaging = "aar"
        description.set("Android Blade dependency injection framework (${project.path})")
        url.set("https://moyar.dev")

        configureLicense()

        configureDeveloper()

        configureScm()
    }
}

fun MavenPom.configureLicense() {
    licenses {
        license {
            name.set("The Apache License, Version 2.0")
            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
        }
    }
}

fun MavenPom.configureDeveloper() {
    developers {
        developer {
            id.set("moyar1532")
            name.set("Mohammad Moradyar")
            email.set("moyarsuccess@gmail.com")
        }
    }
}

fun MavenPom.configureScm() {
    scm {
        url.set("https://github.com/moyarsuccess/blade_framework")
        connection.set("scm:git:git@github.com:moyarsuccess/blade_framework.git")
        developerConnection.set("scm:git:git@github.com:moyarsuccess/blade_framework.git")
    }
}

fun MavenArtifactRepository.configureUrl() {
    val releasesRepoUrl = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2"
    val snapshotsRepoUrl = "https://s01.oss.sonatype.org/content/repositories/snapshots/"

    val isSnapshot = project.ext.properties["PUBLICATION_VERSION"].toString().endsWith("SNAPSHOT", true)

    val repoUrl = if (isSnapshot) snapshotsRepoUrl else releasesRepoUrl

    setUrl(repoUrl)
}

fun MavenArtifactRepository.configureCredentials() {
    credentials {
        username = System.getenv("MOYAR_OSSR_USER_NAME")
        password = System.getenv("MOYAR_OSSR_PASSWORD")
    }
}

workspace(name = "engage")

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

RULES_JVM_EXTERNAL_TAG = "3.0"
RULES_JVM_EXTERNAL_SHA = "62133c125bf4109dfd9d2af64830208356ce4ef8b165a6ef15bbff7460b35c3a"

http_archive(
    name = "rules_jvm_external",
    sha256 = RULES_JVM_EXTERNAL_SHA,
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % RULES_JVM_EXTERNAL_TAG,
)

load("@rules_jvm_external//:defs.bzl", "maven_install")

maven_install(
    artifacts = [
        "org.json:json:20180813",
        "junit:junit:4.12",
        "com.google.truth:truth:0.44",
        "com.github.spullara.mustache.java:compiler:0.9.6",
        "org.apache.maven:maven-plugin-api:3.0",
        "org.apache.maven.plugin-tools:maven-plugin-annotations:3.4",
        "org.hjson:hjson:3.0.0",
    ],
    # Fetch srcjars. Defaults to False.
    fetch_sources = True,
    repositories = [
        "https://maven.google.com",
        "https://jcenter.bintray.com/",
        "https://repo1.maven.org/maven2",
    ],
)

bind(
    name = "junit",
    actual = "//third_party:junit",
)

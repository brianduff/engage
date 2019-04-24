java_library(
    name = "engage",
    srcs = glob(["*.java"]),
    resources = glob(["templates/**"]),
    visibility = ["//visibility:public"],
    deps = [
        "//src/main/java/org/dubh/engage/json",
        "//src/main/java/org/dubh/engage/model",
        "@maven//:com_github_spullara_mustache_java_compiler",
    ],
)

java_binary(
    name = "Generator",
    main_class = "org.dubh.engage.Generator",
    visibility = ["//visibility:public"],
    runtime_deps = [":engage"],
)

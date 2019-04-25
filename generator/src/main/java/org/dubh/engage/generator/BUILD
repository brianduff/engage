java_library(
    name = "generator",
    srcs = glob(["*.java"]),
    resources = glob(["template/**"]),
    visibility = ["//visibility:public"],
    deps = [
        "//model",
        "@maven//:com_github_spullara_mustache_java_compiler",
    ],
)

java_binary(
    name = "JavaGenerator",
    main_class = "org.dubh.engage.generator.JavaGenerator",
    visibility = ["//visibility:public"],
    runtime_deps = [":generator"],
)

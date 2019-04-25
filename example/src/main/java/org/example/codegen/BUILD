load("//:engage.bzl", "engage_java_library")

engage_java_library(
    name = "GeneratedProperties",
    java_package = "org.example.codegen",
    properties = ":engage.json",
)

java_binary(
    name = "Main",
    srcs = glob(["*.java"]),
    args = ["--name=brian"],
    main_class = "org.example.codegen.Main",
    deps = [
        ":GeneratedProperties",
        "//runtime",
    ],
)

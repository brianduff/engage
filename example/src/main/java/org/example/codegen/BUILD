load("//generator:engage.bzl", "engage_java_library")

engage_java_library(
    name = "ExampleProperties",
    java_package = "org.example.codegen",
    properties = ":engage.hjson",
)

java_binary(
    name = "Main",
    srcs = glob(["*.java"]),
    args = ["--name=brian"],
    main_class = "org.example.codegen.Main",
    deps = [
        ":ExampleProperties",
        "//runtime",
    ],
)

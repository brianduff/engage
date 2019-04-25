def _impl(ctx):
  args = ctx.actions.args()
  args.add_all(ctx.files.properties)
  args.add(ctx.outputs.out)
  args.add(ctx.attr.java_package)

  ctx.actions.run(
    inputs = ctx.files.properties,
    outputs = [ctx.outputs.out],
    arguments = [args],
    progress_message = "Generating %s" % ctx.outputs.out.short_path,
    executable = ctx.executable._generator_tool,
  )

# Rule that generates java source for an engage configuration.
engage_generate_java = rule(
  implementation = _impl,
  attrs = {
    "properties": attr.label(allow_files = True),
    "java_package": attr.string(mandatory = True),
    "out": attr.output(mandatory = True),
    "_generator_tool": attr.label(
      executable = True,
      cfg = "host",
      allow_files = True,
      default = Label("//generator/src/main/java/org/dubh/engage/generator:JavaGenerator")
    )
  }
)

# Macro that generates a java_library automatically that you can just depend on.
def engage_java_library(name, java_package=None, properties=None):
  engage_generate_java(
    name = name + "_generated",
    properties = properties,
    out = name + ".java",
    java_package = java_package
  )

  native.java_library(
    name = name,
    srcs = [ ":" + name + ".java" ],
    deps = ["//runtime"]
  )


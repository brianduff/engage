package org.dubh.engage.generator.mojo;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;

/** Says "Hi" to the user. */
@Mojo(name = "engage")
public class JavaGeneratorMojo extends AbstractMojo {
  public void execute() throws MojoExecutionException {
    getLog().info("Hello, world.");
  }
}

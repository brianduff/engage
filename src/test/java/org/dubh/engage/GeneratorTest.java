package org.dubh.engage;

import static org.dubh.engage.util.TestFiles.readFile;

import org.junit.Test;

public class GeneratorTest {
  @Test
  public void testGenerator() throws Exception {
    String json = readFile("//src/test/java/org/dubh/engage/testdata/example.json");
    Generator generator = new Generator("org.foo", "Foo");
    System.err.println(generator.generate(json));
  }
}

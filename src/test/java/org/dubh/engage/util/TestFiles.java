package org.dubh.engage.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class TestFiles {
  private TestFiles() {}

  /**
   * Gets the path of a test file (specified in the data attribute of a test rule). If the path
   * starts with //, it's interpreted as a workspace-absolute file. Otherwise, the prefix ":" is
   * removed if present, and it's interpreted as local to the currently running test target.
   */
  public static Path getTestFile(String filePath) {
    if (filePath.startsWith("//") && filePath.length() > 2) {
      return Paths.get(filePath.substring(2));
    }
    if (filePath.startsWith(":") && filePath.length() > 1) {
      filePath = filePath.substring(1);
    }
    String target = System.getenv("TEST_TARGET");
    // Chop off leading //
    target = target.substring(2);
    // Chop off trailing :.+
    target = target.split(":")[0];
    return Paths.get(target + "/" + filePath);
  }

  /** Reads a test file (located using {@link #getTestFile}) in its entirety. */
  public static String readFile(String filePath) throws IOException {
    return new String(Files.readAllBytes(getTestFile(filePath)), StandardCharsets.UTF_8);
  }
}

package org.dubh.engage;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.Rule;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;

public class CommandlineFlagParserTest {
  @Rule
  public TemporaryFolder tmp = new TemporaryFolder();

  private CommandlineFlagParser parser = new CommandlineFlagParser();

  @Test
  public void parse_withSingleArgBooleanFlag_populatesMap() {
    assertThat(parser.parse("--enableWidgets").formalArgs).containsEntry("enableWidgets", "true");
  }

  @Test
  public void parse_withBooleanFlag_populatesMap() {
    assertThat(parser.parse("--enableWidgets", "true").formalArgs).containsEntry("enableWidgets", "true");
  }

  @Test
  public void parse_withBooleanFlagAndValueFlag_populatesMap() {
    assertThat(parser.parse("--enableWidgets", "--random", "bar").formalArgs)
        .containsExactly("enableWidgets", "true", "random", "bar");
  }

  @Test
  public void parse_withStringFlagValue_populatesMap() {
    assertThat(parser.parse("--host", "localhost").formalArgs).containsEntry("host", "localhost");
  }

  @Test
  public void parse_withEquals_populatesMap() {
    assertThat(parser.parse("--host=localhost").formalArgs).containsEntry("host", "localhost");
  }

  @Test
  public void parse_withMissingAtArg_populatesMap() {
    assertThat(parser.parse("--host=localhost", "@missingfile.txt").formalArgs).containsEntry("host", "localhost");
  }

  @Test
  public void parse_withAtArg_populatesMap() throws Exception {
    String path = writeTempFile("hello --someKey somevalue world --someOtherKey \"value with   whitespace\"\n--anotherValue=true\n--trailing=\"well, well");
    CommandlineFlagParser.ParsedResults results = parser.parse("@" + path, "--simpleArg");
    Map<String, String> args = results.formalArgs;
    assertThat(args).containsEntry("someKey", "somevalue");
    assertThat(args).containsEntry("someOtherKey", "value with   whitespace");
    assertThat(args).containsEntry("anotherValue", "true");
    assertThat(args).containsEntry("simpleArg", "true");
    assertThat(args).containsEntry("trailing", "well, well");

    assertThat(results.remainingArgs).contains("hello");
    assertThat(results.remainingArgs).contains("world");
  }

  private String writeTempFile(String contents) throws IOException {
    File tempFile = tmp.newFile("args.txt");
    Files.write(tempFile.toPath(), contents.getBytes(StandardCharsets.UTF_8));
    return tempFile.getPath();
  }
}

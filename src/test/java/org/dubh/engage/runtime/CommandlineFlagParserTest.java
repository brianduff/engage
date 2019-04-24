package org.dubh.engage.runtime;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class CommandlineFlagParserTest {
  private CommandlineFlagParser parser = new CommandlineFlagParser();

  @Test
  public void parse_withSingleArgBooleanFlag_populatesMap() {
    assertThat(parser.parse("--enableWidgets")).containsEntry("enableWidgets", "true");
  }

  @Test
  public void parse_withBooleanFlag_populatesMap() {
    assertThat(parser.parse("--enableWidgets", "true")).containsEntry("enableWidgets", "true");
  }

  @Test
  public void parse_withBooleanFlagAndValueFlag_populatesMap() {
    assertThat(parser.parse("--enableWidgets", "--random", "bar"))
        .containsExactly("enableWidgets", "true", "random", "bar");
  }

  @Test
  public void parse_withStringFlagValue_populatesMap() {
    assertThat(parser.parse("--host", "localhost")).containsEntry("host", "localhost");
  }

  @Test
  public void parse_withEquals_populatesMap() {
    assertThat(parser.parse("--host=localhost")).containsEntry("host", "localhost");
  }
}

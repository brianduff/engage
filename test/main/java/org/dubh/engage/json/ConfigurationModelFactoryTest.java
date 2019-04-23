package org.dubh.engage.json;

import static org.junit.Assert.assertEquals;

import org.dubh.engage.model.ConfigurationModel;
import org.junit.Test;

public class ConfigurationModelFactoryTest {
  @Test
  public void simpleTest() throws Exception {
    ConfigurationModel model =
        new ConfigurationModelFactory()
            .fromJson(
                "{"
                    + "\"properties\": [{"
                    + "\"name\": \"host\","
                    + "\"type\": \"string\","
                    + "\"default\": \"localhost\","
                    + "}]}");
    assertEquals("host", model.getFile().getProperties().get(0).getName());
  }
}

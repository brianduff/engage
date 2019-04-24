package org.dubh.engage.json;

import static com.google.common.truth.Truth.assertThat;
import static org.dubh.engage.util.TestFiles.readFile;

import org.dubh.engage.model.ConfigurationModel;
import org.dubh.engage.model.ConfigurationProperty;
import org.dubh.engage.model.PropertyType;
import org.junit.Test;

public class ConfigurationModelFactoryTest {
  @Test
  public void simpleTest() throws Exception {
    ConfigurationModel model =
        new ConfigurationModelFactory()
            .fromJson(readFile("//src/test/java/org/dubh/engage/testdata/example.json"));
    assertThat(model.getFile().getProperties())
        .contains(new ConfigurationProperty("host", PropertyType.STRING, "localhost"));
  }
}

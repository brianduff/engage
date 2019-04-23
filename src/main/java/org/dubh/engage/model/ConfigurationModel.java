package org.dubh.engage.model;

/** Models the configuration. */
public class ConfigurationModel {
  private final ConfigurationFile file;

  public ConfigurationModel(ConfigurationFile file) {
    this.file = file;
  }

  public ConfigurationFile getFile() {
    return file;
  }
}

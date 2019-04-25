package org.dubh.engage;

import java.util.ArrayList;
import java.util.List;

/** Used to initialize the configuration engine. */
public class ConfigurationEngine {
  private List<ValueProvider> providers = new ArrayList<>();

  public ConfigurationEngine withCommandlineFlags(String[] flags) {
    providers.add(new StringMapProvider(new CommandlineFlagParser().parse(flags)));
    return this;
  }

  /**
   * This should be the terminal call on the chain of calls to ConfigurationEngine. After this
   * point, configuration is set.
   */
  public void initialize() {
    PropertyResolver.setDefaultInstance(new PropertyResolver(providers));
  }
}

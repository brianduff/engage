package org.dubh.engage.runtime;

import java.util.HashMap;
import java.util.Map;

/** Knows how to parse commandline flags. */
class CommandlineFlagParser {
  /** Processes the given set of commandline flags. */
  Map<String, String> parse(String... args) {
    Map<String, String> map = new HashMap<>();
    String currentArgName = null;
    for (String arg : args) {
      // Process a flag
      if (arg.startsWith("--") && arg.length() > 2) {
        if (currentArgName != null) {
          // We were looking for a value, but didn't find one, treat the last arg as a boolean.
          map.put(currentArgName, "true");
        }

        if (arg.indexOf('=') != -1) {
          String[] parts = arg.split("=");
          map.put(parts[0].substring(2), parts[1]);
        } else {
          currentArgName = arg.substring(2);
        }
        continue;
      }

      // Process a value
      if (currentArgName != null) {
        map.put(currentArgName, arg);
        currentArgName = null;
        continue;
      }
      // TODO(bduff): store an unconsumed cmdline arg.
    }
    if (currentArgName != null) {
      map.put(currentArgName, "true");
    }
    return map;
  }
}

package org.dubh.engage;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/** Knows how to parse commandline flags. */
class CommandlineFlagParser {
  private static final Logger LOGGER = Logger.getLogger(CommandlineFlagParser.class.getName());

  /** Processes the given set of commandline flags. */
  Map<String, String> parse(String... args) {
    Deque<String> argQueue = new ArrayDeque<>(Arrays.asList(args));

    Map<String, String> map = new HashMap<>();
    String currentArgName = null;
    while (!argQueue.isEmpty()) {
      String arg = argQueue.poll();
      if (arg.length() > 1 && arg.charAt(0) == '@') {
        List<String> argsFromFile = readArgsFromFile(arg.substring(1));
        Collections.reverse(argsFromFile);
        argsFromFile.forEach(a -> argQueue.addFirst(a));
        arg = argQueue.poll();
        // The file might be empty, in which case bail
        if (arg == null) {
          break;
        }
      }

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

  private static List<String> readArgsFromFile(String filename) {
    List<String> result = Collections.emptyList();
    File file = new File(filename);
    if (!file.isFile()) {
      LOGGER.warning(String.format("File specified by @-prefixed arg doesn't exist: %s", file));
      return result;
    }

    try {
      List<String> args = new ArrayList<>();
      String content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
      // First, split the content into lines.
      String lines[] = content.split("\\r?\\n");
      // Now, extract words, possibly quoted, from each line.
      for (String line : lines) {
        boolean inQuote = false;
        StringBuilder currentWord = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
          char c = line.charAt(i);
          if (c == '"') {
            if (inQuote) {
              inQuote = false;
              args.add(currentWord.toString());
              currentWord.setLength(0);
            } else {
              inQuote = true;
            }
          } else if (Character.isWhitespace(c)) {
            if (!inQuote && currentWord.length() > 0) {
              args.add(currentWord.toString());
              currentWord.setLength(0);
            } else if (inQuote) {
              currentWord.append(c);
            }
          } else {
            currentWord.append(c);
          }
        }
        if (currentWord.length() > 0) {
          args.add(currentWord.toString());
        }
      }

      return args;
    } catch (IOException e) {
      LOGGER.warning(String.format("Failed to read file specified by @-prefixed arg: %s", file));
      return result;

    }
  }
}

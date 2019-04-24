package org.example;

import org.dubh.engage.runtime.ConfigurationEngine;
import org.dubh.engage.runtime.Properties;

public class Main {
  public static void main(String[] args) {
    new ConfigurationEngine().withCommandlineFlags(args).initialize();

    System.out.printf("The value of the --name flag was %s\n", Properties.getString("name"));
  }
}

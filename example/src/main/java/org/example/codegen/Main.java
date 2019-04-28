package org.example.codegen;

import org.dubh.engage.ConfigurationEngine;

public class Main {
  public static void main(String[] args) {
    ConfigurationEngine ce =
        new ConfigurationEngine()
            .withCommandlineArgs(args)
            .withGeneratedProperties(ExampleProperties.get())
            .initialize();
    if (!ce.checkUsage()) {
      System.exit(1);
    }

    System.out.printf("The value of the name flag is %s\n", ExampleProperties.get().getName());
    System.out.printf("The value of the age flag is %d\n", ExampleProperties.get().getAge());
    System.out.printf("The value of the alive flag is %s\n", ExampleProperties.get().getAlive());
    System.out.printf("The value of the size flag is %s\n", ExampleProperties.get().getSize());
  }
}

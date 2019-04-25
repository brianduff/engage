package org.example.codegen;

import org.dubh.engage.runtime.ConfigurationEngine;

public class Main {
  public static void main(String[] args) {
    new ConfigurationEngine().withCommandlineFlags(args).initialize();

    System.out.printf("The value of the name flag is %s\n", GeneratedProperties.getName());
    System.out.printf("The value of the age flag is %d\n", GeneratedProperties.getAge());
  }
}

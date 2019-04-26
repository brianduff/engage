package org.example;

import org.dubh.engage.ConfigurationEngine;
import org.dubh.engage.Properties;
import org.dubh.engage.PropertyInjector;
import org.dubh.engage.annotations.Property;

public class Main {
  public static void main(String[] args) {
    new ConfigurationEngine().withCommandlineArgs(args).initialize();

    System.out.printf("The value of the --name flag was %s\n", Properties.getString("name"));

    Arguments a = new Arguments();
    PropertyInjector.getInstance().inject(a);

    System.out.printf("The value of the --age flag was %s\n", a.age);
    System.out.printf("The value of the --isAlive flag was %s\n", a.isAlive);
  }

  private static class Arguments {
    @Property private int age;

    @Property(name = "alive")
    private Boolean isAlive;
  }
}

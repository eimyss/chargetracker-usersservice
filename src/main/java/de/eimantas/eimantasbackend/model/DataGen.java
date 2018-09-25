package de.eimantas.eimantasbackend.model;

import java.math.BigDecimal;
import java.util.Random;

public class DataGen {

  private static String[] cities = {"Wiesbaden", "Mainz", "Bingen", "Bonn", "Frankfurt", "Ockenheim", "Barcelona",
      "Milano"};

  private static Random random;

  public static String getOrt() {
    int idx = new Random().nextInt(cities.length);
    return cities[idx];
  }

  public static BigDecimal getAmount() {
    if (random == null) {
      random = new Random();
    }
    int number = random.nextInt(500);
    return BigDecimal.valueOf(number);
  }


}

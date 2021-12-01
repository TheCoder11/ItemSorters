package com.somemone.itemsorters.config;

public class ConfigSettings {

  public static ConfigSettings getDefault() {
    return new Sorter(true, true, 500, 18);
  }


  public boolean usingEconomy;
  public boolean priceIncrease;
  public double startingPrice;
  public double multiplier;

  public ConfigSettings(boolean uE, boolean pI, double sP, double m) {
    usingEconomy = uE;
    priceIncrease = pI;
    startingPrice = sP;
    multiplier = m;
  }

  public double getCost(int containerNum) {
    if (!usingEconomy) return 0;

    double totalCost = startingPrice;
    if (priceIncrease) {
      totalCost += (containerNum * multiplier);
    }

    return totalCost;
  }

  public boolean usingEconomy() {return usingEconomy;}


}
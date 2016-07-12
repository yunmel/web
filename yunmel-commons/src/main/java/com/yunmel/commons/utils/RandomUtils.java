package com.yunmel.commons.utils;

import java.security.SecureRandom;

public class RandomUtils {
  // Maxim: Copied from UUID implementation :)
  private static volatile SecureRandom numberGenerator = null;
  private static final long MSB = 0x8000000000000000L;

  public static String genRandom32Hex() {
    SecureRandom ng = numberGenerator;
    if (ng == null) {
      numberGenerator = ng = new SecureRandom();
    }

    return Long.toHexString(MSB | ng.nextLong()) + Long.toHexString(MSB | ng.nextLong());
  }

  public static void main(String[] args) {
    System.out.println(RandomUtils.genRandom32Hex());
  }
}
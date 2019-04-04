package com.paxos.techtech.challenge2.decisioning;

import com.paxos.techtech.challenge2.data.GiftShop;
import java.io.File;
import org.junit.jupiter.api.Test;

class DecisionStrategyTest {

  @Test
  void findPairs() {
  }

  @Test
  void generateUniqueCombinations() throws Exception {
    GiftShop giftShop = new GiftShop(getFullFilePath("testData.txt"));
  }

  public String getFullFilePath(String filename) {
    ClassLoader classLoader = getClass().getClassLoader();
    return new File(classLoader.getResource(filename).getFile()).getPath();
  }
}
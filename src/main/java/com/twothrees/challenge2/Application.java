package com.twothrees.challenge2;

import com.twothrees.challenge2.data.Gift;
import com.twothrees.challenge2.data.GiftShop;
import com.twothrees.challenge2.decisioning.DecisionStrategy;
import com.twothrees.challenge2.decisioning.DecisionStrategy.Strategy;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Application {

  public static void main(String[] args) {
    try {
      String giftDataFile = args[0];
      int totalSpendAmount = Integer.parseInt(args[1]);

      DecisionStrategy decisionStrategy = new DecisionStrategy(new GiftShop(giftDataFile));
      Map<Strategy, List<Set<Gift>>> result = decisionStrategy.findGifts(totalSpendAmount, 2);

      List<Set<Gift>> bestOutcomeResult = result.get(Strategy.BEST_OUTCOME);
      List<Set<Gift>> bestEffortResult = result.get(Strategy.BEST_EFFORT);

      if(!bestOutcomeResult.isEmpty()) {
        printGiftList(bestOutcomeResult);
      } else if(!bestEffortResult.isEmpty()) {
        printGiftList(bestEffortResult);
      } else {
        System.out.println("Not possible to find compatible pairing!");
      }
    } catch (Exception e) {
      System.out.println("Encountered unhandled error! " + e.toString());
    }
  }

  private static void printGiftList(List<Set<Gift>> giftList) {
    giftList.forEach(g -> System.out.println("- " + g.stream().map(Gift::toString).collect(Collectors.joining(", "))));
  }

}

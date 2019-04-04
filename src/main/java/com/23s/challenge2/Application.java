package com.paxos.techtech.challenge2;

import com.paxos.techtech.challenge2.data.Gift;
import com.paxos.techtech.challenge2.data.GiftShop;
import com.paxos.techtech.challenge2.decisioning.DecisionStrategy;
import com.paxos.techtech.challenge2.decisioning.DecisionStrategy.Strategy;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Application {

  public static void main(String[] args) {
    try {
      String giftDataFile = args[0];
      int totalSpendAmount = Integer.parseInt(args[1]);

      DecisionStrategy decisionStrategy = new DecisionStrategy(new GiftShop(giftDataFile));
      Map<Strategy, List<Set<Gift>>> result = decisionStrategy.findGifts(totalSpendAmount, 2);

      Optional<List<Set<Gift>>> giftList
          = Optional.of(Optional.of(result.get(Strategy.BEST_OUTCOME)).orElse(result.get(Strategy.BEST_EFFORT)));

      if(giftList.isPresent()) {
        printGiftList(giftList.get());
      } else {
        System.out.println("Not possible to find compatible pairing!");
      }
    } catch (Exception e) {
      System.out.println("Encountered unhandled error! " + e.toString());
    }
  }

  private static void printGiftList(List<Set<Gift>> giftList) {
    // TODO - nested streams feels bad..
    giftList.forEach(g -> g.stream().forEach(go -> System.out.println(go.getName() + ":" + go.getCost())));
  }

}

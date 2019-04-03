package com.paxos.techtech.challenge2.decisioning;

import com.paxos.techtech.challenge2.data.Gift;
import com.paxos.techtech.challenge2.data.GiftShop;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class DecisionStrategy {

  public enum Strategy {
    BEST_OUTCOME,
    BEST_EFFORT
  }

  private GiftShop giftShop;

  public DecisionStrategy(GiftShop giftShop) {
    this.giftShop = giftShop;
  }

  public Map<Strategy, Set<Pair<Gift, Gift>>> findPairs(int totalSpendAmount) {
    Set<Gift> giftsWithinPriceRange = giftShop.getGiftsWithinPriceRange(totalSpendAmount);

    Map<Boolean, List<Pair<Gift, Gift>>> giftPairExactlyEqualToSpend = generateUniqueCombinations(giftsWithinPriceRange).stream()
        .filter(p -> totalCostOfGiftPairIsLessThanTotalSpend(p, totalSpendAmount))
        .collect(Collectors.partitioningBy((Pair<Gift, Gift> p) -> totalCostOfGiftPairIsEqualToTotalSpend(p, totalSpendAmount)));

    Map<Strategy, Set<Pair<Gift, Gift>>> partitionedGifts = new HashMap<>();

    partitionedGifts.put(Strategy.BEST_OUTCOME, new HashSet<>(giftPairExactlyEqualToSpend.get(true)));
    partitionedGifts.put(Strategy.BEST_EFFORT, new HashSet<>(giftPairExactlyEqualToSpend.get(false)));

    return partitionedGifts;
  }


  public Set<Pair<Gift, Gift>> generateUniqueCombinations(Set<Gift> gifts) {
    return gifts.stream()
        .flatMap(g1 -> gifts.stream().map(g2 -> new ImmutablePair<>(g1, g2)))
        .collect(Collectors.toSet());
  }

  private boolean totalCostOfGiftPairIsLessThanTotalSpend(Pair<Gift, Gift> giftPair, int totalSpend) {
    return (giftPair.getLeft().getCost() + giftPair.getRight().getCost()) < totalSpend;
  }

  private boolean totalCostOfGiftPairIsEqualToTotalSpend(Pair<Gift, Gift> giftPair, int totalSpend) {
    return (giftPair.getLeft().getCost() + giftPair.getRight().getCost()) == totalSpend;
  }

}
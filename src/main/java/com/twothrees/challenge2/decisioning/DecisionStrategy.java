package com.twothrees.challenge2.decisioning;

import static com.google.common.collect.Sets.powerSet;

import com.twothrees.challenge2.data.Gift;
import com.twothrees.challenge2.data.GiftShop;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DecisionStrategy {

  public enum Strategy {
    BEST_OUTCOME,
    BEST_EFFORT
  }

  private GiftShop giftShop;

  public DecisionStrategy(GiftShop giftShop) {
    this.giftShop = giftShop;
  }

  public Map<Strategy, List<Set<Gift>>> findGifts(int totalSpendAmount, int numberOfGifts) {
    Set<Gift> giftsWithinPriceRange = giftShop.getGiftsWithinPriceRange(totalSpendAmount);

    Map<Boolean, List<Set<Gift>>> giftsExactlyEqualToSpendAmount
        = generateUniqueCombinations(giftsWithinPriceRange, numberOfGifts).stream()
        .filter(p -> totalCostOfGiftPairIsLessThanTotalSpend(p, totalSpendAmount))
        .collect(Collectors.partitioningBy((Set<Gift> g) -> totalCostOfGiftPairIsEqualToTotalSpend(g, totalSpendAmount)));

    Map<Strategy, List<Set<Gift>>> partitionedGifts = new HashMap<>();

    partitionedGifts.put(Strategy.BEST_OUTCOME, giftsExactlyEqualToSpendAmount.get(true));
    partitionedGifts.put(Strategy.BEST_EFFORT, giftsExactlyEqualToSpendAmount.get(false));

    return partitionedGifts;
  }

  private Set<Set<Gift>> generateUniqueCombinations(Set<Gift> gifts, int numberOfGifts) {
    return powerSet(gifts).stream()
        .filter(s -> s.size() == numberOfGifts)
        .collect(Collectors.toSet());
  }

  private boolean totalCostOfGiftPairIsLessThanTotalSpend(Set<Gift> gifts, int totalSpend) {
    return gifts.stream().mapToInt(Gift::getCost).sum() <= totalSpend;
  }

  private boolean totalCostOfGiftPairIsEqualToTotalSpend(Set<Gift> gifts, int totalSpend) {
    return gifts.stream().mapToInt(Gift::getCost).sum() == totalSpend;
  }

}
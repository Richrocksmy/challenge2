package com.twothrees.challenge2.decisioning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import com.twothrees.challenge2.data.Gift;
import com.twothrees.challenge2.data.GiftShop;
import com.twothrees.challenge2.decisioning.DecisionStrategy.Strategy;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DecisionStrategyTest {

  @Mock
  private GiftShop giftShop;

  @Test
  public void shouldFindZeroCombinations() {
    // Given
    Set<Gift> gifts = new HashSet<>();
    gifts.add(new Gift("Toothbrush", 800));
    gifts.add(new Gift("Picture", 1000));
    gifts.add(new Gift("Toy", 900));

    when(giftShop.getGiftsWithinPriceRange(anyInt())).thenReturn(gifts);

    // When
    Map<Strategy, List<Set<Gift>>> giftResults =  new DecisionStrategy(giftShop).findGifts(1200, 2);

    // Then
    assertThat(giftResults.get(Strategy.BEST_OUTCOME)).isEmpty();
    assertThat(giftResults.get(Strategy.BEST_EFFORT)).isEmpty();
  }

  @Test
  public void shouldFindOnlyBestOutcomeCombinations() {
    // Given
    Set<Gift> gifts = new HashSet<>();

    Gift toothbrush = new Gift("Toothbrush", 300);
    Gift picture = new Gift("Picture", 1000);
    Gift toy = new Gift("Toy", 900);

    gifts.add(toothbrush);
    gifts.add(picture);
    gifts.add(toy);

    when(giftShop.getGiftsWithinPriceRange(anyInt())).thenReturn(gifts);

    // When
    Map<Strategy, List<Set<Gift>>> giftResults =  new DecisionStrategy(giftShop).findGifts(1200, 2);

    // Then
    assertThat(giftResults.get(Strategy.BEST_OUTCOME)).hasSize(1);
    assertThat(giftResults.get(Strategy.BEST_OUTCOME).get(0)).containsOnly(toothbrush, toy);

    assertThat(giftResults.get(Strategy.BEST_EFFORT)).isEmpty();
  }

  @Test
  public void shouldFindOnlyBestEffortCombinations() {
    // Given
    Set<Gift> gifts = new HashSet<>();

    Gift toothbrush = new Gift("Toothbrush", 300);
    Gift picture = new Gift("Picture", 500);
    Gift toy = new Gift("Toy", 5000);

    gifts.add(toothbrush);
    gifts.add(picture);
    gifts.add(toy);

    when(giftShop.getGiftsWithinPriceRange(anyInt())).thenReturn(gifts);

    // When
    Map<Strategy, List<Set<Gift>>> giftResults =  new DecisionStrategy(giftShop).findGifts(1200, 2);

    // Then
    assertThat(giftResults.get(Strategy.BEST_EFFORT)).hasSize(1);
    assertThat(giftResults.get(Strategy.BEST_EFFORT).get(0)).containsOnly(picture, toothbrush);

    assertThat(giftResults.get(Strategy.BEST_OUTCOME)).isEmpty();
  }

  @Test
  public void shouldFindBothTypesOfCombination() {
    // Given
    Set<Gift> gifts = new HashSet<>();

    Gift toothbrush = new Gift("Toothbrush", 300);
    Gift picture = new Gift("Picture", 500);
    Gift toy = new Gift("Toy", 600);
    Gift boat = new Gift("Boat", 600);

    gifts.add(toothbrush);
    gifts.add(picture);
    gifts.add(toy);
    gifts.add(boat);

    when(giftShop.getGiftsWithinPriceRange(anyInt())).thenReturn(gifts);

    // When
    Map<Strategy, List<Set<Gift>>> giftResults =  new DecisionStrategy(giftShop).findGifts(1200, 2);

    // Then
    assertThat(giftResults.get(Strategy.BEST_EFFORT)).hasSize(5);

    assertThat(giftResults.get(Strategy.BEST_OUTCOME)).hasSize(1);
    assertThat(giftResults.get(Strategy.BEST_OUTCOME).get(0)).containsOnly(toy, boat);
  }

}
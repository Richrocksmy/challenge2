package com.twothrees.challenge2.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.File;
import java.nio.file.NoSuchFileException;
import java.util.Set;
import org.junit.jupiter.api.Test;

class GiftShopTest {

  @Test
  public void shouldLoadGiftsFromFile() throws Exception {
    GiftShop giftShop = new GiftShop(getFullFilePath("testData.txt"));

    Set<Gift> gifts = giftShop.getGifts();
    assertThat(gifts).isNotNull();
    assertThat(gifts).containsExactlyInAnyOrder(new Gift("Candy Bar", 500),
        new Gift("Paperback Book", 700), new Gift("Detergent", 1000));
  }

  @Test
  public void shouldThrowExceptionWhenInvalidFilename() {
    assertThatThrownBy(() -> new GiftShop("notAFile")).isInstanceOf(NoSuchFileException.class);
  }

  @Test
  public void shouldThrowExceptionWhenFileIsInvalidFormat_1() {
    assertThatThrownBy(() ->  new GiftShop(getFullFilePath("invalidTestData1.txt"))).isInstanceOf(Exception.class);
  }

  @Test
  public void shouldThrowExceptionWhenFileIsInvalidFormat_2() {
    assertThatThrownBy(() ->  new GiftShop(getFullFilePath("invalidTestData2.txt"))).isInstanceOf(Exception.class);
  }

  @Test
  public void shouldGetGiftsWithinPriceRange()  throws Exception {
    GiftShop giftShop = new GiftShop(getFullFilePath("testData.txt"));

    Set<Gift> gifts = giftShop.getGiftsWithinPriceRange(800);
    assertThat(gifts).isNotNull();
    assertThat(gifts).containsExactlyInAnyOrder(new Gift("Candy Bar", 500),
        new Gift("Paperback Book", 700));
  }

  public String getFullFilePath(String filename) {
    ClassLoader classLoader = getClass().getClassLoader();
    return new File(classLoader.getResource(filename).getFile()).getPath();
  }
}
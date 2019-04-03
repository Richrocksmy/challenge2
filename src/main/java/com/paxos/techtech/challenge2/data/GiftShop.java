package com.paxos.techtech.challenge2.data;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import org.apache.commons.io.FileUtils;

@Getter
public class GiftShop {

  private Set<Gift> gifts;

  public GiftShop(String file) throws IOException {
    loadDataFromFile(file);
  }

  private void loadDataFromFile(String file) throws IOException {
    gifts = FileUtils.readLines(new File(file), UTF_8).stream()
        .map(e -> new Gift(e.split(",")[0],
            Integer.parseInt(e.split(",")[1].trim())))
        .collect(Collectors.toSet());
  }

  public Set<Gift> getGiftsWithinPriceRange(int totalSpendAmount) {
    return gifts.stream()
        .filter(g -> g.getCost() <= totalSpendAmount)
        .collect(Collectors.toSet());
  }

}

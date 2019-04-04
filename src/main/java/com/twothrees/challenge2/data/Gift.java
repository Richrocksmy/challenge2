package com.twothrees.challenge2.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Gift {

  private String name;

  private int cost;

  @Override
  public String toString() {
    return name + " : " + cost;
  }

}

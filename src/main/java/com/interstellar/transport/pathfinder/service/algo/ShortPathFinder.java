package com.interstellar.transport.pathfinder.service.algo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by E076103 on 14-03-2019.
 */
@Component
@Slf4j
public class ShortPathFinder {

  private final RouteInformationHolder routeInformationHolder;

  @Autowired
  public ShortPathFinder(
      RouteInformationHolder routeInformationHolder) {
    this.routeInformationHolder = routeInformationHolder;
  }


  public void getShortestPath(String from, String to){

    String f = "A";
    String t= "Z";


     //1.) search the start point
    //2.) find neigbours
    //3.) find path
    //4.) go to next min path and then find and update path if it is less.
    //5.)mark as visited and don't take it again for consderation

    List<String> visited = new ArrayList<>();
    Map<String, BigDecimal> distance = new HashMap<>();
    distance.put("B", new BigDecimal("99999999999999999"));
    distance.put("C", new BigDecimal("99999999999999999"));
    distance.put("D", new BigDecimal("99999999999999999"));
    distance.put("E", new BigDecimal("99999999999999999"));
    /*int[] distance = new int[5];
    distance[0] = 0;

    for (int i = 1; i < 5; i++) {
      distance[i] = Integer.MAX_VALUE;
    }*/

    routeInformationHolder.getRouteConnections().forEach((key, value) -> {

      findNeighbouringDistance(visited, distance, key, value);

    });

  }

  private void findNeighbouringDistance(List<String> visited, Map<String, BigDecimal> distance, String key,
      List<EdgeInformation> value) {
    if(!visited.contains(key)){
      value.forEach(edgeInformation -> {
        BigDecimal newWeight = distance.get(key).add(edgeInformation.getWeight());
        if(newWeight.compareTo(edgeInformation.getWeight()) < 0){
          distance.put(key, newWeight);
        }
      });
    }

  }


}

package com.interstellar.transport.pathfinder.service.algo;

import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by E076103 on 14-03-2019.
 */
@Component
@Slf4j
@Data
public class RouteInformationHolder {

  private int routeCount;
  private int planetCount;
  private Map<String, List<EdgeInformation>> routeConnections;

}

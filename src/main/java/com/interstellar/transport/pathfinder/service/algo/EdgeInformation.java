package com.interstellar.transport.pathfinder.service.algo;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by E076103 on 14-03-2019.
 */
@Slf4j
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EdgeInformation {

  private String destination;
  private BigDecimal weight;


}

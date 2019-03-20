package com.interstellar.transport.pathfinder.domain;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Route {

  @javax.persistence.Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String origin;

  private String destination;

  private BigDecimal distance;

}

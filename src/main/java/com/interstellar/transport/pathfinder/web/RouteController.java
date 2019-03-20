package com.interstellar.transport.pathfinder.web;

import com.interstellar.transport.pathfinder.service.RouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class RouteController {

  private final RouteService routeService;

  @Autowired
  public RouteController(RouteService routeService) {
    this.routeService = routeService;
  }

  public void getRouteDetails(){
    //routeService.
  }

  @GetMapping("/search")
  public String getIndexPage(){
    return "search";
  }

}

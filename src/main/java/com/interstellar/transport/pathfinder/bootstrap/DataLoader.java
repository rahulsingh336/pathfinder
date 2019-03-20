package com.interstellar.transport.pathfinder.bootstrap;

//import org.springframework.context.ApplicationContext;

import static java.lang.System.out;

import com.interstellar.transport.pathfinder.dao.RouteRepository;
import com.interstellar.transport.pathfinder.domain.Route;
import com.interstellar.transport.pathfinder.service.FileService;
import com.interstellar.transport.pathfinder.service.algo.EdgeInformation;
import com.interstellar.transport.pathfinder.service.algo.RouteInformationHolder;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class DataLoader  implements ApplicationListener<ContextRefreshedEvent> {

  private final ApplicationContext applicationContext;
  private final RouteRepository routeRepository;
  private final FileService fileService;
  private final RouteInformationHolder routeInformationHolder;

  public DataLoader(ApplicationContext applicationContext,
      RouteRepository routeRepository,
      FileService fileService,
      RouteInformationHolder routeInformationHolder) {
    this.applicationContext = applicationContext;
    this.routeRepository = routeRepository;
    this.fileService = fileService;
    this.routeInformationHolder = routeInformationHolder;
  }

  private void dumpDatainDB() throws IOException, URISyntaxException {

    loadRoutes();

  }

  private void loadRoutes() throws IOException, URISyntaxException {
    log.info("loading routes in DB");
    Resource resource = applicationContext.getResource("classpath:" + "/data/data.xlsx");
    Path fileName = fileService.getLatestFileName(resource);

    File file = fileService.getFileToRead(fileName, resource);

    XSSFSheet sheet;
    Iterator<Row> rowIterator;

    try (XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file))) {
      sheet = workbook.getSheetAt(1);
    }
    rowIterator = sheet.iterator();

    while (rowIterator.hasNext()) {
      buildAndSaveData(rowIterator);
    }
    log.info("loaded routes in DB");
  }

  private void buildAndSaveData(Iterator<Row> rowIterator) {
    Row row = rowIterator.next();
    String cell = row.getCell(0).toString();
    if (!cell.equals("Route Id")) {
      row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
      row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
      row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
      Long routeId = Long.valueOf(row.getCell(0).toString());
      String origin = row.getCell(1).toString();
      String destination = row.getCell(2).toString();
      BigDecimal distance = new BigDecimal(row.getCell(3).toString());
      Route route = Route.builder().id(routeId)
          .origin(origin)
          .destination(destination)
          .distance(distance)
          .build();
      Route savedRoute = routeRepository.save(route);

    //TODO can be debug
    log.info("saved route for id {}", savedRoute);
    }
  }

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    try {
      dumpDatainDB();

      printRouteInformation();
    } catch (IOException | URISyntaxException e) {
      log.error("unable to save data");
      System.exit(336);
    }
  }

  private void printRouteInformation() {
    init();
  }

  private void init(){
    routeInformationHolder.setRouteConnections(new HashMap<>(routeInformationHolder.getPlanetCount()));
    //setup route information
    Iterable<Route> route =  routeRepository.findAll();
    /*route.forEach(rt -> {
      routeInformationHolder.getRouteConnections().put(rt.getOrigin(), new ArrayList<>());
    });*/

      routeInformationHolder.getRouteConnections().put("A", new ArrayList<>());
      routeInformationHolder.getRouteConnections().put("B", new ArrayList<>());
      routeInformationHolder.getRouteConnections().put("C", new ArrayList<>());
      routeInformationHolder.getRouteConnections().put("D", new ArrayList<>());

/*
    route.forEach(r -> {
      EdgeInformation edgeInformation = EdgeInformation.builder()
          .destination(r.getDestination()).weight(r.getDistance()).build();
      routeInformationHolder.getRouteConnections().get(r.getOrigin()).add(edgeInformation);
    });
*/
    populateDummyData();

    log.info("Below are the routes connected");
    routeInformationHolder.getRouteConnections().forEach((key, value) -> {
      out.println("Vertex is :: " + key  + " linked edges :: - " + getLinkedVertex(key));
    });
  }

  private void populateDummyData() {
    EdgeInformation edgeInformation1 = EdgeInformation.builder()
        .destination("B").weight(new BigDecimal(4)).build();
    EdgeInformation edgeInformation2 = EdgeInformation.builder()
        .destination("C").weight(new BigDecimal(8)).build();
    routeInformationHolder.getRouteConnections().get("A").add(edgeInformation1);
    routeInformationHolder.getRouteConnections().get("A").add(edgeInformation2);

    EdgeInformation edgeInformation3 = EdgeInformation.builder()
        .destination("D").weight(new BigDecimal(5)).build();
    EdgeInformation edgeInformation4 = EdgeInformation.builder()
        .destination("C").weight(new BigDecimal(2)).build();
    routeInformationHolder.getRouteConnections().get("B").add(edgeInformation3);
    routeInformationHolder.getRouteConnections().get("B").add(edgeInformation4);

    EdgeInformation edgeInformation5 = EdgeInformation.builder()
        .destination("D").weight(new BigDecimal(5)).build();
    EdgeInformation edgeInformation6 = EdgeInformation.builder()
        .destination("E").weight(new BigDecimal(9)).build();
    routeInformationHolder.getRouteConnections().get("C").add(edgeInformation5);
    routeInformationHolder.getRouteConnections().get("C").add(edgeInformation6);

    EdgeInformation edgeInformation7 = EdgeInformation.builder()
        .destination("E").weight(new BigDecimal(4)).build();
    routeInformationHolder.getRouteConnections().get("D").add(edgeInformation7);


  }


  private List<EdgeInformation> getLinkedVertex(String key){
    return routeInformationHolder.getRouteConnections().get(key);
  }

}

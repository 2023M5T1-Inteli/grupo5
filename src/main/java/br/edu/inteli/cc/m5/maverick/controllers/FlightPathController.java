package br.edu.inteli.cc.m5.maverick.controllers;

import br.edu.inteli.cc.m5.maverick.models.FlightPathNode;
import br.edu.inteli.cc.m5.maverick.services.DTEDDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flight-path")
public class FlightPathController {

    private final DTEDDatabaseService dtedDatabaseService;

    public FlightPathController(DTEDDatabaseService dtedDatabaseService) throws Exception {
        this.dtedDatabaseService = dtedDatabaseService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }

    @GetMapping("/nodes")
    public void getNodes() {
        dtedDatabaseService.readPointsFromDataset();
    }

}

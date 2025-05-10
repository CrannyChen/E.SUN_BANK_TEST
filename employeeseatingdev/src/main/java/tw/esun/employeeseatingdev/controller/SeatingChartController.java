package tw.esun.employeeseatingdev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.esun.employeeseatingdev.entity.SeatingChart;
import tw.esun.employeeseatingdev.service.SeatingChartService;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatingChartController {

    @Autowired
    private SeatingChartService seatingChartService;

    @GetMapping
    public List<SeatingChart> getAllSeats() {
        return seatingChartService.getAllSeats();
    }

    @GetMapping("/active")
    public List<SeatingChart> getActiveSeats() {
        return seatingChartService.getActiveSeats();
    }
}

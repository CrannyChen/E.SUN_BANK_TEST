package tw.esun.employeeseatingdev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.esun.employeeseatingdev.entity.SeatingChart;
import tw.esun.employeeseatingdev.repository.SeatingChartRepository;

import java.util.List;

@Service
public class SeatingChartService {

    @Autowired
    private SeatingChartRepository seatingChartRepository;

    public List<SeatingChart> getAllSeats() {
        return seatingChartRepository.findAll();
    }

    public List<SeatingChart> getActiveSeats() {
        return seatingChartRepository.findByIsActiveTrue();
    }
}

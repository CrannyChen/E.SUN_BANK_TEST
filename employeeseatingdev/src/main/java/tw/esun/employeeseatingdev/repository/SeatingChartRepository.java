package tw.esun.employeeseatingdev.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.esun.employeeseatingdev.entity.SeatingChart;

public interface SeatingChartRepository extends JpaRepository<SeatingChart, Integer> {
	
	List<SeatingChart> findByIsActiveTrue();
	
}

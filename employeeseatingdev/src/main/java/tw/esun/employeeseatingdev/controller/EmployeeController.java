package tw.esun.employeeseatingdev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.esun.employeeseatingdev.entity.Employee;
import tw.esun.employeeseatingdev.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping
	public List<Employee> getAllEmployees() {
		return employeeService.getAllEmployees();
	}

	@PostMapping("/assign-seat")
	public ResponseEntity<?> assignSeat(@RequestParam String empId, @RequestParam Integer seatId) {
		employeeService.assignSeatToEmployee(empId, seatId);
		return ResponseEntity.ok("Seat assigned.");
	}

	@PostMapping("/clear-seat")
	public ResponseEntity<?> clearSeat(@RequestParam Integer seatId) {
		employeeService.clearSeat(seatId);
		return ResponseEntity.ok("Seat cleared.");
	}
}

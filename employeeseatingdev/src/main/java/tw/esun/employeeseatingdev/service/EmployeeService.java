package tw.esun.employeeseatingdev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.esun.employeeseatingdev.entity.Employee;
import tw.esun.employeeseatingdev.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	public Optional<Employee> getEmployeeById(String empId) {
		return employeeRepository.findById(empId);
	}

	@Transactional
	public void assignSeatToEmployee(String empId, Integer floorSeatSeq) {
		employeeRepository.assignSeatToEmployee(empId, floorSeatSeq);
	}

	@Transactional
	public void clearSeat(Integer floorSeatSeq) {
		employeeRepository.clearSeatAssignment(floorSeatSeq);
	}
}

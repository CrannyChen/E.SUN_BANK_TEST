package tw.esun.employeeseatingdev.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import tw.esun.employeeseatingdev.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String> {

	List<Employee> findByIsActiveTrue();

	@Procedure(name = "assignSeatToEmployee")
	void assignSeatToEmployee(@Param("emp_id") String empId, @Param("seat_seq") Integer seatSeq);

	@Procedure(name = "clearSeatAssignment")
	void clearSeatAssignment(@Param("seat_seq") Integer seatSeq);
}
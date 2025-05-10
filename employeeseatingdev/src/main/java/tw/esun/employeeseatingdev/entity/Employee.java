package tw.esun.employeeseatingdev.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "employee")
@NamedStoredProcedureQueries({
		@NamedStoredProcedureQuery(name = "assignSeatToEmployee", procedureName = "assign_seat_to_employee", parameters = {
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "emp_id", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "seat_seq", type = Integer.class) }),
		@NamedStoredProcedureQuery(name = "clearSeatAssignment", procedureName = "clear_seat_assignment", parameters = {
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "seat_seq", type = Integer.class) }) })
@Data
public class Employee {

	@Id
	@Column(name = "emp_id", length = 5)
	private String empId;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "email")
	private String email;

	@Column(name = "is_active", nullable = false)
	private Boolean isActive = true;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@ManyToOne
	@JoinColumn(name = "floor_seat_seq")
	private SeatingChart seatingChart;
}
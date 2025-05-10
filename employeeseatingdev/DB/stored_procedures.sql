USE employee_seating_dev;

CREATE PROCEDURE assign_seat_to_employee
    @emp_id CHAR(5),
    @seat_seq INT
AS
BEGIN
    SET NOCOUNT ON;

    UPDATE employee
    SET floor_seat_seq = @seat_seq,
        updated_at = GETDATE()
    WHERE emp_id = @emp_id;
END;

CREATE PROCEDURE clear_seat_assignment
    @seat_seq INT
AS
BEGIN
    SET NOCOUNT ON;

    UPDATE employee
    SET floor_seat_seq = NULL,
        updated_at = GETDATE()
    WHERE floor_seat_seq = @seat_seq;
END;
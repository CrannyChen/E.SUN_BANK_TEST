package tw.esun.employeeseatingdev.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "seating_chart")
@Data
public class SeatingChart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "floor_seat_seq")
    private Integer floorSeatSeq;

    @Column(name = "floor_no", nullable = false)
    private Integer floorNo;

    @Column(name = "seat_no", nullable = false)
    private String seatNo;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "note")
    private String note;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;
}

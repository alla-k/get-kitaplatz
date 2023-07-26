package project.my.place.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@Entity
@Table(name = "applications_queue")
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationsQueue {
    @Id
    private String id;
    private String kitaId;
    private LocalDate startDate;
}

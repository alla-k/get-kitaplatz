package project.my.kita.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Builder
@Entity
@Table(name = "kita_place")
@NoArgsConstructor
@AllArgsConstructor
public class KitaPlace {
    @Id
//    @GeneratedValue(strategy = IDENTITY)
    private String id;
    private String kitaId;
    private LocalDate contractStartDate;
    private LocalDate contractEndDate;
    private String applicationId;
    private Status status;

}

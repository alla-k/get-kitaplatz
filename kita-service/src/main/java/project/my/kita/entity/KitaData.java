package project.my.kita.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "kita_data")
public class KitaData {
    @Id
    private String id;
    private int placeNumber;
    private String address;
    private String kitaName;
    private String telNumber;
//    private List<String> placeIdList;
}

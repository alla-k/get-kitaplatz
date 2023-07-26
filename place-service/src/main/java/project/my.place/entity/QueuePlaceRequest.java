package project.my.place.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueuePlaceRequest {
    private String kitaId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
}

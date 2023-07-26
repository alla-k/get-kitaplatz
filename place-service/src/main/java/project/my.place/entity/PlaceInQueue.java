package project.my.place.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "place_queue")
@AllArgsConstructor
@NoArgsConstructor
public class PlaceInQueue {
    @Id
    private String id;
    private int position;
    private String queueId;
    private String applicationId;

}

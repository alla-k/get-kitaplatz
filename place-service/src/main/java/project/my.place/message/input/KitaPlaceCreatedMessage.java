package project.my.place.message.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.my.place.message.BaseMessage;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class KitaPlaceCreatedMessage implements BaseMessage {
        private String id;
        private String kitaId;
        private LocalDate contractStartDate;
        private LocalDate contractEndDate;
        private String applicationId;
        private String status;
        private String timestamp;

}

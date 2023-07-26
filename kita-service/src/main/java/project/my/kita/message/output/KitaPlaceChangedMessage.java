package project.my.kita.message.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.my.kita.entity.Status;
import project.my.kita.message.BaseMessage;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class KitaPlaceChangedMessage implements BaseMessage {
        private String id;
        private String kitaId;
        private LocalDate contractStartDate;
        private LocalDate contractEndDate;
        private String applicationId;
        private Status status;
        private String timestamp;

}

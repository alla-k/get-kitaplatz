package project.my.place.message.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.my.place.message.BaseMessage;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmationDataMessage implements BaseMessage {
    private String id;
    private String kitaId;
    private String applicationId;
    private String reservationId;
    private String confirmationToken;
    private String placeId;
}

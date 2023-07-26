package project.my.kita.message.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.my.kita.message.BaseMessage;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceClaimedMessage implements BaseMessage {

    private String id;
    private String accountId;
    private String kitaId;
    private String applicationId;
    private String placeId;

}

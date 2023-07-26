package project.my.account.message.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.my.account.message.BaseMessage;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceClaimedMessage implements BaseMessage {

    private String id;
    private String kitaId;
    private String applicationId;
    private String placeId;

}

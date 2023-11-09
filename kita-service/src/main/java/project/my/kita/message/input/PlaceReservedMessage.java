package project.my.kita.message.input;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.my.kita.message.BaseMessage;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceReservedMessage implements BaseMessage {
    private String id;
    private String kitaId;
    private String applicationId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String endDate;
    private int placeId;

}

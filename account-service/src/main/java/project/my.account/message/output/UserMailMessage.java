package project.my.account.message.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.my.account.message.BaseMessage;;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMailMessage implements BaseMessage {
    private String id;
    private String email;
    private String subject;
    private String userId;
    private String content;
}

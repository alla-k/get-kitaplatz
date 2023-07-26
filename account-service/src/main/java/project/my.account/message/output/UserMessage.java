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
public class UserMessage implements BaseMessage {
    private String id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}

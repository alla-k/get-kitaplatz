package project.my.account.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "reservation")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfirmationData {
    @Id
    private String id;
    private String kitaId;
    private String applicationId;
    private String confirmationToken;
    private String placeId;
    private boolean confirmed;
}

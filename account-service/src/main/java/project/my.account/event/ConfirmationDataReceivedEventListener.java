package project.my.account.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.my.account.entity.ConfirmationData;
import project.my.account.message.output.UserMailMessage;
import project.my.account.producer.UserMailProducer;
import project.my.account.repository.ReservationRepository;

import java.util.UUID;

@Slf4j
@Component
public class ConfirmationDataReceivedEventListener {
    @Autowired
    private UserMailProducer userMailProducer;
    @Autowired
   private ReservationRepository reservationRepository;
   @Autowired
    public ConfirmationDataReceivedEventListener() {
    }

    public void onApplicationEvent(ConfirmationDataReceivedEvent confirmationDataReceivedEvent) {
        log.info("Received new confirmation data");
        String reservationId = confirmationDataReceivedEvent.getReservationId();

        ConfirmationData confirmationData = ConfirmationData.builder()
                .applicationId(confirmationDataReceivedEvent.getApplicationId())
                .confirmationToken(confirmationDataReceivedEvent.getConfirmationToken())
                .placeId(confirmationDataReceivedEvent.getPlaceId())
                .id(reservationId)
                .kitaId(confirmationDataReceivedEvent.getKitaId())
                .confirmed(false)
                .build();

        reservationRepository.save(confirmationData);


        String content = "Please confirm that you're going to claim the place at kita " +
                confirmationDataReceivedEvent.getKitaId()  +
                " by clicking the link below: " + "http://localhost:8080/confirm-reservation?reservationId=" +
                reservationId + "&confirmationToken=" + confirmationData.getConfirmationToken();
        UserMailMessage userMailMessage = UserMailMessage.builder()
                .id(UUID.randomUUID().toString())
                .subject("Reservation confirmation")
                .content(content)
//                .email(kitaPlaceChangedEvent.getEmail())
//                .userId(kitaPlaceChangedEvent.getUserId())
                .build();

        userMailProducer.sendMessage(userMailMessage);
    }
}

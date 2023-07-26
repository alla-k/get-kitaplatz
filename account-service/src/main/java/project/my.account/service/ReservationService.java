package project.my.account.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.my.account.entity.ConfirmationData;
import project.my.account.entity.KitaPlaceApplication;
import project.my.account.message.output.PlaceApplicationCreatedMessage;
import project.my.account.message.output.PlaceClaimedMessage;
import project.my.account.producer.ReservationConfirmationProducer;
import project.my.account.producer.KitaPlaceReservationProducer;
import project.my.account.repository.KitaPlaceApplicationRepository;
import project.my.account.repository.ReservationRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class ReservationService {
    private final KitaPlaceReservationProducer messageProducer;

    private final ReservationRepository reservationRepository;

    private final ReservationConfirmationProducer reservationConfirmationProducer;
    private final KitaPlaceApplicationRepository applicationRepository;

    public ReservationService(KitaPlaceReservationProducer messageProducer,
                              ReservationRepository reservationRepository, ReservationConfirmationProducer reservationConfirmationProducer,
                              KitaPlaceApplicationRepository applicationRepository) {
        this.messageProducer = messageProducer;
        this.reservationRepository = reservationRepository;
        this.reservationConfirmationProducer = reservationConfirmationProducer;
        this.applicationRepository = applicationRepository;
    }

    //check that reservation is not already created
    public String createReservation(KitaPlaceApplication kitaPlaceApplication) {

        //check that reservation is not already created for same user and same child

        Optional<List<KitaPlaceApplication>> applications = applicationRepository
                .findByAccountId(kitaPlaceApplication.getAccountId());

        if (applications.isPresent()) {
            for (KitaPlaceApplication application : applications.get()) {
                if (application.getChildName().equals(kitaPlaceApplication.getChildName())) {
                    throw new RuntimeException("Reservation is already created for this user and child");
                }
            }
        }

        PlaceApplicationCreatedMessage message = PlaceApplicationCreatedMessage.builder()
                .id(kitaPlaceApplication.getId())
                .kitaIds(kitaPlaceApplication.getKitaIds())
                .birthDate(kitaPlaceApplication.getBirthDate())
                .childName(kitaPlaceApplication.getChildName())
                .minEntranceDate(kitaPlaceApplication.getMinEntranceDate())
                .accountId(kitaPlaceApplication.getAccountId()) //TODO: get from security context
                .build();
        messageProducer.sendMessage(message);
        log.info("Message was sent to kafka: {}", message);

        applicationRepository.save(kitaPlaceApplication);

        return kitaPlaceApplication.getId();
    }

    public String claimPlace(String reservationId, String confirmationId) {

        Optional<ConfirmationData> reservation = reservationRepository.findById(reservationId);

        if (reservation.isPresent() && reservation.get().getConfirmationToken().equals(confirmationId)) {
            reservation.get().setConfirmed(true);
            reservationRepository.save(reservation.get());

            String applicationId = reservation.get().getApplicationId();

            PlaceClaimedMessage message = PlaceClaimedMessage.builder()
                    .id(reservation.get().getId())
                    .kitaId(reservation.get().getKitaId())
                    .placeId(reservation.get().getPlaceId())
                    .applicationId(applicationId)
                    .build();

            reservationConfirmationProducer.sendMessage(message);
            log.info("Message was sent to kafka: {}", message);

            return applicationId;
        } else {
            throw new RuntimeException("Reservation with id " + reservationId + " was not found");
        }
    }
}

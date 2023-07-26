package service;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import project.my.account.entity.KitaPlaceApplication;
import project.my.account.message.output.PlaceApplicationCreatedMessage;
import project.my.account.producer.KitaPlaceReservationProducer;
import project.my.account.repository.KitaPlaceApplicationRepository;
import project.my.account.service.ReservationService;

public class ReservationServiceTest {
    @Mock
    private KitaPlaceApplicationRepository applicationRepository;

    @Mock
    private KitaPlaceReservationProducer messageProducer;

    @InjectMocks
    private ReservationService reservationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateReservation_Success() {
        // Prepare test data
        KitaPlaceApplication kitaPlaceApplication = new KitaPlaceApplication();
        kitaPlaceApplication.setAccountId("user123");
        kitaPlaceApplication.setChildName("John");
        kitaPlaceApplication.setKitaIds(Arrays.asList("13", "3", "8"));
        kitaPlaceApplication.setBirthDate(LocalDate.parse("2021-01-01"));
        kitaPlaceApplication.setMinEntranceDate(LocalDate.parse("2023-11-01"));
        kitaPlaceApplication.setId(UUID.randomUUID().toString());

        // Mock applicationRepository.findByAccountId
        when(applicationRepository.findByAccountId(anyString()))
                .thenReturn(Optional.empty());

        // Mock applicationRepository.save
        when(applicationRepository.save(any(KitaPlaceApplication.class)))
                .thenReturn(kitaPlaceApplication);

        // Perform the test
        String result = reservationService.createReservation(kitaPlaceApplication);

        // Verify that applicationRepository.save is called once
        verify(applicationRepository, times(1)).save(any(KitaPlaceApplication.class));

        // Verify that messageProducer.sendMessage is called once
        verify(messageProducer, times(1)).sendMessage(any(PlaceApplicationCreatedMessage.class));

        // Verify the result
        Assertions.assertEquals(kitaPlaceApplication.getId(), result);
    }

    @Test
    public void testCreateReservation_ReservationAlreadyExists() {
        // Prepare test data
        KitaPlaceApplication kitaPlaceApplication = new KitaPlaceApplication();
        kitaPlaceApplication.setAccountId("user123");
        kitaPlaceApplication.setChildName("John");

        List<KitaPlaceApplication> existingApplications = new ArrayList<>();
        existingApplications.add(kitaPlaceApplication);

        // Mock applicationRepository.findByAccountId
        when(applicationRepository.findByAccountId(anyString()))
                .thenReturn(Optional.of(existingApplications));

        // Perform the test and verify that a RuntimeException is thrown
        Assertions.assertThrows(RuntimeException.class, () -> {
            reservationService.createReservation(kitaPlaceApplication);
        });

        // Verify that applicationRepository.save is not called
        verify(applicationRepository, times(0)).save(any(KitaPlaceApplication.class));

        // Verify that messageProducer.sendMessage is not called
        verify(messageProducer, times(0)).sendMessage(any(PlaceApplicationCreatedMessage.class));
    }
}


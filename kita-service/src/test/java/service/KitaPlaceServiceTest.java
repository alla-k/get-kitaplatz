package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import project.my.kita.entity.KitaPlace;
import project.my.kita.entity.Status;
import project.my.kita.message.output.KitaPlaceChangedMessage;
import project.my.kita.producer.KitaPlaceProducer;
import project.my.kita.repository.KitaPlaceRepository;
import project.my.kita.service.KitaPlaceService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class KitaPlaceServiceTest {

    @Mock
    private KitaPlaceRepository kitaPlaceRepository;
    @Mock
    private KitaPlaceProducer kitaPlaceProducer;

    @InjectMocks
    private KitaPlaceService kitaPlaceService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateKitaPlace_Success() {
    //prepare test data
        KitaPlace kitaPlace = KitaPlace.builder()
                .id("1232")
                .kitaId("kita123")
                .status(Status.AVAILABLE)
                .build();

        //mock kitaPlaceRepository.save
        when(kitaPlaceRepository.save(any(KitaPlace.class)))
                .thenReturn(kitaPlace);

        String result = kitaPlaceService.createPlace(kitaPlace);

        verify(kitaPlaceRepository, times(1)).save(any(KitaPlace.class));

        // Verify that messageProducer.sendMessage is called once
        verify(kitaPlaceProducer, times(1)).sendMessage(any(KitaPlaceChangedMessage.class));

        // Verify the result
        assertEquals(kitaPlace.getId(), result);
    }

    @Test
    public void testCreateKItaPlace_wrongStatusAssigned(){
        KitaPlace kitaPlace = KitaPlace.builder()
                .id("1232")
                .kitaId("kita123")
                .status(Status.ASSIGNED)
                .build();

        Assertions.assertThrows(RuntimeException.class, () -> {
            kitaPlaceService.createPlace(kitaPlace);
        });

        verify(kitaPlaceRepository, times(0)).save(any(KitaPlace.class));

        // Verify that messageProducer.sendMessage is called once
        verify(kitaPlaceProducer, times(0)).sendMessage(any(KitaPlaceChangedMessage.class));
    }

    @Test
    public void testCreateKItaPlace_wrongStatusAvailable(){
        KitaPlace kitaPlace = KitaPlace.builder()
                .id("1232")
                .kitaId("kita123")
                .contractStartDate(LocalDate.parse("2023-10-10"))
                .contractEndDate(LocalDate.parse("2025-10-10"))
                .status(Status.AVAILABLE)
                .build();

        Throwable ex = Assertions.assertThrows(RuntimeException.class, () -> {
            kitaPlaceService.createPlace(kitaPlace);
        });

        verify(kitaPlaceRepository, times(0)).save(any(KitaPlace.class));

        // Verify that messageProducer.sendMessage is called once
        verify(kitaPlaceProducer, times(0)).sendMessage(any(KitaPlaceChangedMessage.class));
        assertEquals("Status can't be AVAILABLE with dates set", ex.getMessage());
    }
}

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import project.my.place.entity.ApplicationsQueue;
import project.my.place.entity.PlaceInQueue;
import project.my.place.repository.ApplicationsQueueRepository;
import project.my.place.repository.PlaceInQueueRepository;
import project.my.place.repository.PlaceInQueueRepositoryCustom;
import project.my.place.service.QueueAssignmentService;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

public class QueueServiceAddTest {

    @Mock
    private ApplicationsQueueRepository applicationsQueueRepository;
    @Mock
    private PlaceInQueueRepository placeInQueueRepository;
    @Mock
    private PlaceInQueueRepositoryCustom placeInQueueRepositoryCustom;

    @InjectMocks
    private QueueAssignmentService queueAssignmentService;

    private static UUID mockedUUID;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeAll
    public static void mockUUID(){
        mockedUUID = UUID.randomUUID();

        MockedStatic<UUID> mocked = mockStatic(UUID.class);
        mocked.when(UUID::randomUUID).thenReturn(mockedUUID);
    }

    @Test
    public void testCreateQueueAssignment_queueExists() {
        // Arrange
        String applicationId = "app_123";
        String kitaId = "kita_456";

        ApplicationsQueue newQueue = ApplicationsQueue.builder().kitaId(kitaId)
                .id(UUID.randomUUID().toString())
                .startDate(LocalDate.now()).build();

        Mockito.when(applicationsQueueRepository.findByKitaIdAndStartDate(kitaId, LocalDate.now()))
                .thenReturn(Optional.of(newQueue));

        Mockito.when(placeInQueueRepositoryCustom.countByQueueId(newQueue.getId()))
                .thenReturn(3);

        // Act
      queueAssignmentService.createQueueAssignment(kitaId, LocalDate.now(), applicationId);

        PlaceInQueue placeInQueue = PlaceInQueue.builder()
                .id(mockedUUID.toString())
                .applicationId(applicationId)
                .queueId(newQueue.getId())
                .position(4)
                .build();
        // Assert
            Mockito.verify(placeInQueueRepository).save(placeInQueue);
    }


    @Test
    public void testCreateQueueAssignment_queueDoesntExist() {
        // Arrange
        String applicationId = "app_123";
        String kitaId = "kita_456";

        ApplicationsQueue newQueue = ApplicationsQueue.builder().kitaId(kitaId)
                .id(mockedUUID.toString())
                .startDate(LocalDate.now()).build();

        Mockito.when(applicationsQueueRepository.findByKitaIdAndStartDate(kitaId, LocalDate.now()))
                .thenReturn(Optional.ofNullable(null));

        Mockito.when(placeInQueueRepositoryCustom.countByQueueId(newQueue.getId()))
                .thenReturn(0);

        // Act
        queueAssignmentService.createQueueAssignment(kitaId, LocalDate.now(), applicationId);

        PlaceInQueue placeInQueue = PlaceInQueue.builder()
                .id(mockedUUID.toString())
                .applicationId(applicationId)
                .queueId(newQueue.getId())
                .position(1)
                .build();
        // Assert
        Mockito.verify(applicationsQueueRepository).save(newQueue);
        Mockito.verify(placeInQueueRepository).save(placeInQueue);
    }


    @Test
    public void testAddToQueue_3years() {
        // Arrange
        String applicationId = "app_123";
        String kitaId = "kita_456";
        LocalDate birthDate = LocalDate.parse("2021-08-01");
        LocalDate startDate = LocalDate.parse("2024-08-01");

        // Act
        queueAssignmentService.addToQueue(kitaId, startDate, applicationId, birthDate);

        Mockito.verify(applicationsQueueRepository,times(30))
                .save(any());

        Mockito.verify(placeInQueueRepository,times(30))
                .save(any());
    }


    @Test
    public void testAddToQueue_less6months() {
        // Arrange
        String applicationId = "app_123";
        String kitaId = "kita_456";
        LocalDate birthDate = LocalDate.parse("2021-08-01");
        LocalDate startDate = LocalDate.parse("2027-05-01");

        // Act
        queueAssignmentService.addToQueue(kitaId, startDate, applicationId, birthDate);

        Mockito.verify(applicationsQueueRepository,times(0))
                .save(any());

        Mockito.verify(placeInQueueRepository,times(0))
                .save(any());
    }

    @Test
    public void testAddToQueue_18months() {
        // Arrange
        String applicationId = "app_123";
        String kitaId = "kita_456";
        LocalDate birthDate = LocalDate.parse("2021-08-01");
        LocalDate startDate = LocalDate.parse("2026-02-01");

        // Act
        queueAssignmentService.addToQueue(kitaId, startDate, applicationId, birthDate);

        Mockito.verify(applicationsQueueRepository,times(12))
                .save(any());

        Mockito.verify(placeInQueueRepository,times(12))
                .save(any());
    }
}

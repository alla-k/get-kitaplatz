import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import project.my.place.entity.PlaceInQueue;
import project.my.place.repository.ApplicationsQueueRepository;
import project.my.place.repository.PlaceInQueueRepository;
import project.my.place.repository.PlaceInQueueRepositoryCustom;
import project.my.place.service.QueueAssignmentService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class QueueServiceRemoveTest {
    @Mock
    private ApplicationsQueueRepository applicationsQueueRepository;
    @Mock
    private PlaceInQueueRepository placeInQueueRepository;
    @Mock
    private PlaceInQueueRepositoryCustom placeInQueueRepositoryCustom;


    @InjectMocks
    private QueueAssignmentService queueAssignmentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRemoveApplicationFromQueue_ApplicationExists() {
        // Arrange
        String applicationId = "app_123";
        String kitaId = "kita_456";

        PlaceInQueue placeInQueue = PlaceInQueue.builder().applicationId(applicationId).build();

        Mockito.when(placeInQueueRepositoryCustom.findByApplicationIdAndKitaId(applicationId, kitaId))
                .thenReturn(Optional.of(placeInQueue));

        // Act
        queueAssignmentService.removeApplicationFromQueue(applicationId, kitaId);

        // Assert
        Mockito.verify(placeInQueueRepository).delete(placeInQueue);
    }

    @Test
    public void testRemoveApplicationFromQueue_ApplicationDoesntExist() {
        // Arrange
        String applicationId = "app_123";
        String kitaId = "kita_456";

        Mockito.when(placeInQueueRepositoryCustom.findByApplicationIdAndKitaId(applicationId, kitaId))
                .thenReturn(Optional.empty());
        // Act
        queueAssignmentService.removeApplicationFromQueue(applicationId, kitaId);

        // Assert
        Mockito.verify(placeInQueueRepository, Mockito.never()).delete(Mockito.any());
    }

    @Test
    public void testRemoveApplicationFromQueue_EmptyQueue() {
        // Arrange
        String applicationId = "app_123";
        String kitaId = "kita_456";

        Mockito.when(placeInQueueRepositoryCustom.findByApplicationIdAndKitaId(applicationId, kitaId))
                .thenReturn(Optional.ofNullable(null));
        // Act
        queueAssignmentService.removeApplicationFromQueue(applicationId, kitaId);

        // Assert
        Mockito.verify(placeInQueueRepository, Mockito.never()).delete(Mockito.any());
    }

    @Test
    public void testRemoveApplicationFromAllQueues_ApplicationExists() {
        // Arrange
        String applicationId = "app_123";

        PlaceInQueue placeInQueue1 = PlaceInQueue.builder().id("place1")
                .applicationId(applicationId)
                .queueId("q1")
                .build();
        PlaceInQueue placeInQueue2 = PlaceInQueue.builder()
                .id("place1")
                .queueId("q2")
                .applicationId(applicationId).build();

        List<PlaceInQueue> placeInQueues = Arrays.asList(placeInQueue1, placeInQueue2);


        Mockito.when(placeInQueueRepositoryCustom.findByApplicationId(applicationId))
                .thenReturn(placeInQueues);

        // Act
        queueAssignmentService.removeApplicationFromAllQueues(applicationId);

        // Assert
        Mockito.verify(placeInQueueRepository).delete(placeInQueue1);
        Mockito.verify(placeInQueueRepository).delete(placeInQueue2);
    }

}

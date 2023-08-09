import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import project.my.place.entity.ApplicationsQueue;
import project.my.place.entity.PlaceInQueue;
import project.my.place.repository.ApplicationsQueueRepository;
import project.my.place.repository.PlaceInQueueRepository;
import project.my.place.repository.PlaceInQueueRepositoryCustom;
import project.my.place.service.QueueAssignmentService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueueServicePredictionTest {
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
    public void testGetPredictedPlace_ProjectsPlaceInQueue() {
        // Arrange
        String kitaId = "kita_123";
        LocalDate date = LocalDate.now();

        ApplicationsQueue queue = new ApplicationsQueue();
        queue.setId("queue_1");
        queue.setStartDate(date);
        Mockito.when(applicationsQueueRepository.findByKitaIdAndStartDate(kitaId, date))
                .thenReturn(Optional.of(queue));

        List<PlaceInQueue> queueEntries = Arrays.asList(
                PlaceInQueue.builder().id("entry_1").queueId(queue.getId()).position(1).applicationId("user_1").build(),
                PlaceInQueue.builder().id("entry_2").queueId(queue.getId()).position(2).applicationId("user_2").build()
        );
        Mockito.when(placeInQueueRepositoryCustom.countByQueueId(queue.getId()))
                .thenReturn(queueEntries.size());

        // Act
        int result = queueAssignmentService.getPredictedPlace(kitaId, date);

        // Assert
        assertEquals(3, result); // Expected value based on queueEntries.size() + 1
    }

    @Test
    public void testGetPredictedPlace_ProjectsPlaceInEmptyQueue() {
        // Arrange
        String kitaId = "kita_123";
        LocalDate date = LocalDate.now();

        Mockito.when(applicationsQueueRepository.findByKitaIdAndStartDate(kitaId, date))
                .thenReturn(Optional.empty());
        // Act
        int result = queueAssignmentService.getPredictedPlace(kitaId, date);

        // Assert
        assertEquals(1, result); // Expected value based on queueEntries.size() + 1
    }


}

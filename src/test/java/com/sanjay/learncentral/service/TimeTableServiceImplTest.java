//package com.sanjay.learncentral.service;
//
//import com.sanjay.learncentral.model.Course;
//import com.sanjay.learncentral.model.Session;
//import com.sanjay.learncentral.model.TimeTable;
//import com.sanjay.learncentral.repository.CourseRepository;
//import com.sanjay.learncentral.repository.SessionRepository;
//import com.sanjay.learncentral.repository.TimeTableRepository;
//import com.sanjay.learncentral.service.EnrollmentService;
//import com.sanjay.learncentral.service.serviceImpl.EmailService;
//import com.sanjay.learncentral.service.serviceImpl.TimeTableServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class TimeTableServiceImplTest {
//
//    @Mock
//    private TimeTableRepository timeTableRepository;
//
//    @Mock
//    private SessionRepository sessionRepository;
//
//    @Mock
//    private CourseRepository courseRepository;
//
//    @Mock
//    private EnrollmentService enrollmentService;
//
//    @Mock
//    private EmailService emailService;
//
//    @InjectMocks
//    private TimeTableServiceImpl timeTableService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    void testGetTimeTableById_WhenValidId() {
//        // Mocking behavior
//        String id = "1";
//        TimeTable expectedTimeTable = new TimeTable(id, "TimeTable 1", "Week 1", Collections.emptyList(), "2024-03-22", "2024-03-28", "course1");
//        when(timeTableRepository.findById(id)).thenReturn(Optional.of(expectedTimeTable));
//
//        // Test
//        TimeTable actualTimeTable = timeTableService.getTimeTableById(id);
//
//        // Assertion
//        assertEquals(expectedTimeTable, actualTimeTable);
//    }
//
//    @Test
//    void testGetTimeTableByName_WhenValidName() {
//        // Mocking behavior
//        String name = "TimeTable 1";
//        TimeTable expectedTimeTable = new TimeTable("1", name, "Week 1", Collections.emptyList(), "2024-03-22", "2024-03-28", "course1");
//        when(timeTableRepository.findByName(name)).thenReturn(expectedTimeTable);
//
//        // Test
//        TimeTable actualTimeTable = timeTableService.getTimeTableByName(name);
//
//        // Assertion
//        assertEquals(expectedTimeTable, actualTimeTable);
//    }
//
//    @Test
//    void testGetTimeTableByName_WhenInvalidName() {
//        // Mocking behavior
//        String name = "Invalid Name";
//        when(timeTableRepository.findByName(name)).thenReturn(null);
//
//        // Test
//        TimeTable actualTimeTable = timeTableService.getTimeTableByName(name);
//
//        // Assertion
//        assertNull(actualTimeTable);
//    }
//
//    @Test
//    void testCreateTimeTable_WithValidData() {
//        // Mocking behavior
//        TimeTable timeTable = new TimeTable("1", "TimeTable 1", "Week 1", Collections.emptyList(), "2024-03-22", "2024-03-28", "course1");
//        when(timeTableRepository.save(timeTable)).thenReturn(timeTable);
//
//        // Test
//        TimeTable savedTimeTable = timeTableService.createTimeTable(timeTable);
//
//        // Assertion
//        assertNotNull(savedTimeTable);
//        assertEquals(timeTable, savedTimeTable);
//    }
//
//    @Test
//    void testCreateTimeTable_WithInvalidData() {
//        // Test with invalid sessions
//        TimeTable timeTable = new TimeTable("1", "TimeTable 1", "Week 1", Collections.singletonList("invalid_session"), "2024-03-22", "2024-03-28", "course1");
//        assertThrows(NullPointerException.class, () -> timeTableService.createTimeTable(timeTable));
//
//        // Test with null course ID
//        timeTable.setSessionsId(Collections.emptyList());
//        timeTable.setCourseId(null);
//        assertThrows(NullPointerException.class, () -> timeTableService.createTimeTable(timeTable));
//    }
//
//    @Test
//    void testGetAllTimeTables() {
//        // Mocking behavior
//        List<TimeTable> expectedTimeTables = Arrays.asList(
//                new TimeTable("1", "TimeTable 1", "Week 1", Collections.emptyList(), "2024-03-22", "2024-03-28", "course1"),
//                new TimeTable("2", "TimeTable 2", "Week 2", Collections.emptyList(), "2024-03-29", "2024-04-04", "course2")
//        );
//        when(timeTableRepository.findAll()).thenReturn(expectedTimeTables);
//
//        // Test
//        List<TimeTable> actualTimeTables = timeTableService.getAllTimeTables();
//
//        // Assertion
//        assertEquals(expectedTimeTables.size(), actualTimeTables.size());
//        for (int i = 0; i < expectedTimeTables.size(); i++) {
//            assertEquals(expectedTimeTables.get(i), actualTimeTables.get(i));
//        }
//    }
//
//    @Test
//    void testGetTimeTableById_WhenInvalidId() {
//        // Mocking behavior
//        String id = "invalid_id";
//        when(timeTableRepository.findById(id)).thenReturn(Optional.empty());
//
//        // Test
//        TimeTable actualTimeTable = timeTableService.getTimeTableById(id);
//
//        // Assertion
//        assertNull(actualTimeTable);
//    }
//
//    @Test
//    void testCreateTimeTable_ValidTimeTable() {
//
//        LocalDateTime startTime = LocalDateTime.of(2024, 3, 22, 0, 0);
//        LocalDateTime endTime = LocalDateTime.of(2024, 3, 28, 23, 59, 59);
//
//        // Mocking behavior for sessions
//        Session session1 = new Session("temp0001", "Session 1","dgfdg","fdsfdg", "gfdgfdgh", startTime, endTime);
//        when(sessionRepository.existsByIdIn(anyList())).thenReturn(true);
//        when(sessionRepository.findById(anyString())).thenReturn(Optional.of(session1));
//
//        // Mocking behavior for course
//        Course course = new Course("temp0001", "Software Engineering", "SE3010", "Course description", 3);
//        when(courseRepository.existsById(anyString())).thenReturn(true);
//
//        // Mocking behavior for time table
//        TimeTable timeTable = new TimeTable("1", "TimeTable 1", "Week 1", Collections.singletonList("session1"), "2024-03-22", "2024-03-28", "SE3010");
//        when(timeTableRepository.save(any(TimeTable.class))).thenReturn(timeTable);
//
//        // Test
//        TimeTable createdTimeTable = timeTableService.createTimeTable(timeTable);
//
//        // Assertion
//        assertNotNull(createdTimeTable);
//        assertEquals(timeTable, createdTimeTable);
//    }
//
//    @Test
//    void testCreateTimeTable_NullTimeTableId() {
//        // Mocking behavior
//        TimeTable timeTable = new TimeTable(null, "TimeTable 1", "Week 1", Collections.emptyList(), "2024-03-22", "2024-03-28", "course1");
//
//        // Test and assertion
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> timeTableService.createTimeTable(timeTable));
//        assertEquals("TimeTable ID cannot be null.", exception.getMessage());
//    }
//
//    // Add similar test cases for createTimeTable with other invalid scenarios
//
//    @Test
//    void testDeleteTimeTable_ValidId() {
//        // Mocking behavior
//        String id = "1";
//        doNothing().when(timeTableRepository).deleteById(id);
//
//        // Test
//        timeTableService.deleteTimeTable(id);
//
//        // Verification
//        verify(timeTableRepository, times(1)).deleteById(id);
//    }
//
//    @Test
//    void testDeleteTimeTable_InvalidId() {
//        // Mocking behavior
//        String id = "invalid_id";
//        doNothing().when(timeTableRepository).deleteById(id);
//
//        // Test and assertion
//        assertDoesNotThrow(() -> timeTableService.deleteTimeTable(id));
//    }
//
//}

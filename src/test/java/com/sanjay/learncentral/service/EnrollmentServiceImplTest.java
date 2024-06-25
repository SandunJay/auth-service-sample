//package com.sanjay.learncentral.service;
//
//import com.sanjay.learncentral.model.User;
//import com.sanjay.learncentral.repository.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static com.sanjay.learncentral.model.Role.ADMIN;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class EnrollmentServiceImplTest {
//
//    @Mock
//    private EnrollmentRepository enrollmentRepository;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private CourseRepository courseRepository;
//
//    @InjectMocks
//    private EnrollmentServiceImpl enrollmentService;
//
////    @BeforeEach
////    void setUp() {
////        MockitoAnnotations.initMocks(this);
////    }
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this); // Ensure mocks are initialized
//    }
//
//    @Test
//    void testFindById() {
//        Enrollment enrollment = new Enrollment("1", "temp0001", Collections.singletonList("1"));
//
//        when(enrollmentRepository.findById("1")).thenReturn(Optional.of(enrollment));
//
//        Optional<Enrollment> foundEnrollment = enrollmentService.findById("1");
//
//        assertTrue(foundEnrollment.isPresent());
//        assertEquals(enrollment, foundEnrollment.get());
//    }
//
//
//    @Test
//    void testCreateEnrollment() {
//        // Given
//        String userId = "temp0001";
//        String courseId = "1";
//
//        User user = new User(userId, "John123", "John", "Doe", "sandunjayawardhana2020@gmail.com", "password", ADMIN, null, true);
//        Course course = new Course(courseId, "Math", "MATH101", "Introduction to Math", 3, null);
//
//        Enrollment enrollment = new Enrollment(null, userId, Collections.singletonList(courseId));
//
//        // When
//        when(userRepository.findById(userId)).thenReturn(user); // Use Optional
//        when(courseRepository.findAllById(Collections.singletonList(courseId))).thenReturn(Collections.singletonList(course));
//        when(enrollmentRepository.save(any(Enrollment.class))).thenAnswer(invocation -> {
//            Enrollment arg = invocation.getArgument(0);
//            arg.setId("1"); // Set ID upon save
//            return arg;
//        });
//
//        Enrollment savedEnrollment = enrollmentService.createEnrollment(enrollment);
//
//        // Then
//        assertNotNull(savedEnrollment.getId(), "Enrollment ID should not be null");
//        assertEquals("1", savedEnrollment.getId()); // Check the ID is set
//        assertEquals(userId, savedEnrollment.getStudentId());
//        assertEquals(Collections.singletonList(courseId), savedEnrollment.getCourses());
//    }
//
//    @Test
//    void testUpdate() {
//        String enrollmentId = "1";
//        Enrollment enrollment = new Enrollment(enrollmentId, "temp0001", Collections.singletonList("1"));
//
//        when(enrollmentRepository.existsById(enrollmentId)).thenReturn(true);
//        when(enrollmentRepository.save(enrollment)).thenReturn(enrollment);
//
//        Enrollment updatedEnrollment = enrollmentService.update(enrollmentId, enrollment);
//
//        assertNotNull(updatedEnrollment);
//        assertEquals(enrollmentId, updatedEnrollment.getId());
//    }
//
//    @Test
//    void testGetAllEnrollments() {
//        // Given
//        Enrollment enrollment = new Enrollment();
//        List<Enrollment> enrollmentList = Collections.singletonList(enrollment);
//
//        // Mocking repository method
//        when(enrollmentRepository.findAll()).thenReturn(enrollmentList);
//
//        // When
//        List<Enrollment> retrievedEnrollments = enrollmentService.getAllEnrollments();
//
//        // Then
//        assertEquals(enrollmentList, retrievedEnrollments);
//    }
//
//    @Test
//    void testDeleteById() {
//        // Given
//        String id = "1";
//
//        // When
//        enrollmentService.deleteById(id);
//
//        // Then
//        verify(enrollmentRepository, times(1)).deleteById(id);
//    }
//
//    @Test
//    void testFindByStudent() {
//        // Given
//        String studentId = "1";
//        Enrollment enrollment = new Enrollment();
//
//        // Mocking repository method
//        when(enrollmentRepository.findByStudentId(studentId)).thenReturn(Collections.singletonList(enrollment));
//
//        // When
//        List<Enrollment> retrievedEnrollments = enrollmentService.findByStudent(studentId);
//
//        // Then
//        assertEquals(Collections.singletonList(enrollment), retrievedEnrollments);
//    }
//
//    @Test
//    void testFindByCourseId() {
//        // Given
//        String courseId = "1";
//        Enrollment enrollment = new Enrollment();
//
//        // Mocking repository method
//        when(enrollmentRepository.findByCourses(courseId)).thenReturn(Collections.singletonList(enrollment));
//
//        // When
//        List<Enrollment> retrievedEnrollments = enrollmentService.findByCourseId(courseId);
//
//        // Then
//        assertEquals(Collections.singletonList(enrollment), retrievedEnrollments);
//    }
//
//    @Test
//    void testCountByStudentId() {
//        // Given
//        String studentId = "1";
//        long count = 5;
//
//        // Mocking repository method
//        when(enrollmentRepository.countByStudentId(studentId)).thenReturn(count);
//
//        // When
//        long retrievedCount = enrollmentService.countByStudentId(studentId);
//
//        // Then
//        assertEquals(count, retrievedCount);
//    }
//
//    @Test
//    void testCountByCourseId() {
//        // Given
//        String courseId = "1";
//        long count = 5;
//
//        // Mocking repository method
//        when(enrollmentRepository.countByCourses(courseId)).thenReturn(count);
//
//        // When
//        long retrievedCount = enrollmentService.countByCourseId(courseId);
//
//        // Then
//        assertEquals(count, retrievedCount);
//    }
//
//    @Test
//    void testFindByStudentIdAndCourseId() {
//        // Given
//        String studentId = "1";
//        String courseId = "1";
//        Enrollment enrollment = new Enrollment();
//
//        // Mocking repository method
//        when(enrollmentRepository.findByStudentIdAndCourses(studentId, courseId)).thenReturn(Optional.of(enrollment));
//
//        // When
//        Optional<Enrollment> retrievedEnrollment = enrollmentService.findByStudentIdAndCourseId(studentId, courseId);
//
//        // Then
//        assertTrue(retrievedEnrollment.isPresent());
//        assertEquals(enrollment, retrievedEnrollment.get());
//    }
//
//    @Test
//    void testExistsByStudentIdAndCourseId() {
//        // Given
//        String studentId = "1";
//        String courseId = "1";
//
//        // Mocking repository method
//        when(enrollmentRepository.existsByStudentIdAndCourses(studentId, courseId)).thenReturn(true);
//
//        // When
//        boolean exists = enrollmentService.existsByStudentIdAndCourseId(studentId, courseId);
//
//        // Then
//        assertTrue(exists);
//    }
//
//    @Test
//    void testSaveAll() {
//        // Given
//        List<Enrollment> enrollments = new ArrayList<>();
//        Enrollment enrollment = new Enrollment();
//        enrollments.add(enrollment);
//
//        // Mocking repository method
//        when(enrollmentRepository.saveAll(enrollments)).thenReturn(enrollments);
//
//        // When
//        List<Enrollment> savedEnrollments = enrollmentService.saveAll(enrollments);
//
//        // Then
//        assertEquals(enrollments, savedEnrollments);
//    }
//}
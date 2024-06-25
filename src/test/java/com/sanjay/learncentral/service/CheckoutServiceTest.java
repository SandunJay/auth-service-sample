//package com.sanjay.learncentral.service;
//
//import com.sanjay.learncentral.model.Checkout;
//import com.sanjay.learncentral.model.LibraryItem;
//import com.sanjay.learncentral.model.Type;
//import com.sanjay.learncentral.model.User;
//import com.sanjay.learncentral.repository.CheckoutRepository;
//import com.sanjay.learncentral.repository.LibraryItemRepository;
//import com.sanjay.learncentral.repository.UserRepository;
//import com.sanjay.learncentral.service.serviceImpl.CheckoutServiceImpl;
//import com.sanjay.learncentral.token.Token;
//import com.sanjay.learncentral.token.TokenType;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static com.sanjay.learncentral.model.Role.ADMIN;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class CheckoutServiceTest {
//
//
//    @Mock
//    private CheckoutRepository checkoutRepository;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private LibraryItemRepository libraryItemRepository;
//
//    @InjectMocks
//    private CheckoutServiceImpl checkoutService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    void testCreateCheckout() {
//        // Given
//        User user = new User("temp0001", "John123", "John", "Doe", "sandunjayawardhana2020@gmail.com", "password", ADMIN, null, true);
//        LibraryItem libraryItem = new LibraryItem("temp0001", "Book Title", "Author", Type.STATIONERY);
//        Token token = new Token("tokenValue", "someOtherField", TokenType.BEARER, true, false, user);
//        user.setTokens(Collections.singletonList(token));
//        Checkout checkout = new Checkout("temp0001", user.getId(), libraryItem.getId(), LocalDate.now(),LocalDate.now(), false);
//
//        // Mocking repository methods
//        when(userRepository.findById(user.getId())).thenReturn(user);
//        when(libraryItemRepository.findLibraryItemById(libraryItem.getId())).thenReturn(libraryItem);
//        when(checkoutRepository.save(checkout)).thenReturn(checkout);
//
//        // When
//        Checkout savedCheckout = checkoutService.createCheckout(checkout);
//
//        // Then
//        assertEquals(checkout, savedCheckout);
//        verify(userRepository, times(1)).findById(user.getId());
//        verify(libraryItemRepository, times(1)).findLibraryItemById(libraryItem.getId());
//        verify(checkoutRepository, times(1)).save(checkout);
//    }
//
//    @Test
//    void testGetAllCheckouts() {
//        // Given
//        Checkout checkout = new Checkout();
//        List<Checkout> checkoutList = Collections.singletonList(checkout);
//
//        // When
//        when(checkoutRepository.findAll()).thenReturn(checkoutList);
//
//        // Then
//        List<Checkout> retrievedCheckouts = checkoutService.getAllCheckouts();
//        assertEquals(checkoutList, retrievedCheckouts);
//
//        verify(checkoutRepository, times(1)).findAll();
//    }
//
//    @Test
//    void testGetCheckoutById() {
//        // Given
//        String id = "temp0001";
//        Checkout checkout = new Checkout();
//        when(checkoutRepository.findById(id)).thenReturn(Optional.of(checkout));
//
//        // When
//        Checkout retrievedCheckout = checkoutService.getCheckoutById(id);
//
//        // Then
//        assertEquals(checkout, retrievedCheckout);
//        verify(checkoutRepository, times(1)).findById(id);
//    }
//
//    @Test
//    void testGetCheckoutsByUserId() {
//        // Given
//        String userId = "temp0001";
//        Checkout checkout = new Checkout();
//        List<Checkout> checkoutList = Collections.singletonList(checkout);
//        when(checkoutRepository.findByUser(userId)).thenReturn(checkoutList);
//
//        // When
//        List<Checkout> retrievedCheckouts = checkoutService.getCheckoutsByUserId(userId);
//
//        // Then
//        assertEquals(checkoutList, retrievedCheckouts);
//        verify(checkoutRepository, times(1)).findByUser(userId);
//    }
//
//    @Test
//    void testGetCheckoutsByLibraryItemId() {
//        // Given
//        String itemId = "temp0001";
//        Checkout checkout = new Checkout();
//        List<Checkout> checkoutList = Collections.singletonList(checkout);
//        when(checkoutRepository.findByLibraryItem((itemId)).thenReturn(checkoutList);
//
//        // When
//        List<Checkout> retrievedCheckouts = checkoutService.getCheckoutsByLibraryItemId(itemId);
//
//        // Then
//        assertEquals(checkoutList, retrievedCheckouts);
//        verify(checkoutRepository, times(1)).findByLibraryItemId(itemId);
//    }
//
//    @Test
//    void testReturnLibraryItem() {
//        // Given
//        String checkoutId = "temp0001";
//        Checkout checkout = new Checkout(checkoutId, "65fa181937eeba6ba6f78f81", "libraryItem", LocalDate.now(), LocalDate.now(), true);
//
//        // Mocking repository methods
//        when(checkoutRepository.findById(eq(checkoutId))).thenReturn(Optional.of(checkout));
//        when(checkoutRepository.save(checkout)).thenReturn(checkout);
//
//        // When
//        Checkout returnedCheckout = checkoutService.returnLibraryItem(checkoutId);
//
//        // Then
//        assertNotNull(returnedCheckout);
//        assertNull(returnedCheckout.getReturnDate());
//
//        verify(checkoutRepository, times(1)).findById(eq(checkoutId));
//        verify(checkoutRepository, times(1)).save(checkout);
//    }
//
//    @Test
//    void testDeleteCheckout() {
//        // Given
//        String id = "temp0001";
//
//        // When
//        checkoutService.deleteCheckout(id);
//
//        // Then
//        verify(checkoutRepository, times(1)).deleteById(id);
//    }
//}
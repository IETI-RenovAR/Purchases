package org.adaschool.project.controller.purchase;

import org.adaschool.project.dto.PurchaseDTO;
import org.adaschool.project.model.Purchase;
import org.adaschool.project.service.PurchaseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PurchaseControllerTest {

    @Mock
    private PurchaseService purchaseService;

    @InjectMocks
    private PurchaseController purchaseController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPurchases() {
        // Arrange
        Purchase purchase1 = new Purchase("1", "user1", "product1", 2, 100.0, new Date());
        Purchase purchase2 = new Purchase("2", "user2", "product2", 1, 50.0, new Date());
        List<Purchase> purchases = Arrays.asList(purchase1, purchase2);
        when(purchaseService.getAllPurchases()).thenReturn(purchases);

        // Act
        ResponseEntity<List<Purchase>> response = purchaseController.getAllPurchases();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(purchases, response.getBody());
    }


    @Test
    void testCreatePurchase() {
        // Arrange
        PurchaseDTO purchaseDTO = new PurchaseDTO("user1", "product1", 2, 100.0, new Date());
        Purchase createdPurchase = new Purchase("1", "user1", "product1", 2, 100.0, new Date());
        when(purchaseService.createPurchase(purchaseDTO)).thenReturn(createdPurchase);

        // Act
        ResponseEntity<Purchase> response = purchaseController.createPurchase(purchaseDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdPurchase, response.getBody());
    }

    @Test
    void testDeletePurchase() {
        // Arrange
        doNothing().when(purchaseService).deletePurchase("1");

        // Act
        ResponseEntity<Void> response = purchaseController.deletePurchase("1");

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testGetShoppingCart() {
        // Arrange
        Map<String, List<String>> shoppingCart = new HashMap<>();
        shoppingCart.put("seller1", Arrays.asList("product1", "product2"));
        when(purchaseService.getProductsById("user1")).thenReturn(shoppingCart);

        // Act
        Map<String, List<String>> response = purchaseController.getShoppingCart("user1");

        // Assert
        assertEquals(shoppingCart, response);
    }

    @Test
    void testAddToShoppingCart() {
        // Arrange
        Map<String, String> request = new HashMap<>();
        request.put("idUser", "user1");
        request.put("idProduct", "product1");
        doNothing().when(purchaseService).addProductShoppingCart("user1", "product1");

        // Act
        ResponseEntity<String> response = purchaseController.addToShoppingCart(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("product1", response.getBody());
    }
}
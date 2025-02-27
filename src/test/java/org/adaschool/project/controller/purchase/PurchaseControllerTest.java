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

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

        Purchase purchase1 = new Purchase("1", "user1", "product1", 2, 100.0, new Date());
        Purchase purchase2 = new Purchase("2", "user2", "product2", 1, 50.0, new Date());
        List<Purchase> purchases = Arrays.asList(purchase1, purchase2);
        when(purchaseService.getAllPurchases()).thenReturn(purchases);

        ResponseEntity<List<Purchase>> response = purchaseController.getAllPurchases();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(purchases, response.getBody());
    }

    @Test
    void testGetPurchaseById() {

        Purchase purchase = new Purchase("1", "user1", "product1", 2, 100.0, new Date());
        when(purchaseService.getPurchaseById("1")).thenReturn(purchase);

        ResponseEntity<Purchase> response = purchaseController.getPurchaseById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(purchase, response.getBody());
    }

    @Test
    void testCreatePurchase() {

        PurchaseDTO purchaseDTO = new PurchaseDTO("user1", "product1", 2, 100.0, new Date());
        Purchase createdPurchase = new Purchase("1", "user1", "product1", 2, 100.0, new Date());
        when(purchaseService.createPurchase(purchaseDTO)).thenReturn(createdPurchase);

        ResponseEntity<Purchase> response = purchaseController.createPurchase(purchaseDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdPurchase, response.getBody());
    }

    @Test
    void testDeletePurchase() {

        doNothing().when(purchaseService).deletePurchase("1");

        ResponseEntity<Void> response = purchaseController.deletePurchase("1");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
package org.adaschool.project.service;

import org.adaschool.project.dto.PurchaseDTO;
import org.adaschool.project.exception.PurchaseNotFoundException;
import org.adaschool.project.model.Purchase;
import org.adaschool.project.repository.PurchaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PurchaseServiceTest {

    @Mock
    private PurchaseRepository purchaseRepository;

    @InjectMocks
    private PurchaseService purchaseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPurchases() {

        Purchase purchase1 = new Purchase("1", "user1", "product1", 2, 100.0, new Date());
        Purchase purchase2 = new Purchase("2", "user2", "product2", 1, 50.0, new Date());
        List<Purchase> purchases = Arrays.asList(purchase1, purchase2);
        when(purchaseRepository.findAll()).thenReturn(purchases);

        List<Purchase> result = purchaseService.getAllPurchases();

        assertEquals(purchases, result);
    }

    @Test
    void testGetPurchaseById() {

        Purchase purchase = new Purchase("1", "user1", "product1", 2, 100.0, new Date());
        when(purchaseRepository.findById("1")).thenReturn(Optional.of(purchase));

        Purchase result = purchaseService.getPurchaseById("1");

        assertEquals(purchase, result);
    }

    @Test
    void testGetPurchaseByIdNotFound() {

        when(purchaseRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(PurchaseNotFoundException.class, () -> purchaseService.getPurchaseById("1"));
    }

    @Test
    void testCreatePurchase() {

        PurchaseDTO purchaseDTO = new PurchaseDTO("user1", "product1", 2, 100.0, new Date());
        Purchase purchase = new Purchase();
        purchase.setUserId(purchaseDTO.getUserId());
        purchase.setProductId(purchaseDTO.getProductId());
        purchase.setQuantity(purchaseDTO.getQuantity());
        purchase.setTotalPrice(purchaseDTO.getTotalPrice());
        purchase.setPurchaseDate(purchaseDTO.getPurchaseDate());
        when(purchaseRepository.save(any(Purchase.class))).thenReturn(purchase);

        Purchase result = purchaseService.createPurchase(purchaseDTO);

        assertEquals(purchase, result);
    }

    @Test
    void testDeletePurchase() {

        doNothing().when(purchaseRepository).deleteById("1");

        purchaseService.deletePurchase("1");

        verify(purchaseRepository, times(1)).deleteById("1");
    }
}
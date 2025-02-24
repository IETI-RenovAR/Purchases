package org.adaschool.project.service;

import org.adaschool.project.dto.PurchaseDTO;
import org.adaschool.project.exception.PurchaseNotFoundException;
import org.adaschool.project.model.Purchase;
import org.adaschool.project.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    public Purchase getPurchaseById(String id) {
        Optional<Purchase> purchase = purchaseRepository.findById(id);
        return purchase.orElseThrow(() -> new PurchaseNotFoundException(id));
    }

    public Purchase createPurchase(PurchaseDTO purchaseDTO) {
        Purchase purchase = new Purchase();
        purchase.setUserId(purchaseDTO.getUserId());
        purchase.setProductId(purchaseDTO.getProductId());
        purchase.setQuantity(purchaseDTO.getQuantity());
        purchase.setTotalPrice(purchaseDTO.getTotalPrice());
        purchase.setPurchaseDate(new Date());

        return purchaseRepository.save(purchase);
    }

    public void deletePurchase(String id) {
        purchaseRepository.deleteById(id);
    }
}
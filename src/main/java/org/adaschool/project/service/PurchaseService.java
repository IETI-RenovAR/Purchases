package org.adaschool.project.service;

import org.adaschool.project.dto.PurchaseDTO;
import org.adaschool.project.exception.PurchaseNotFoundException;
import org.adaschool.project.model.Purchase;
import org.adaschool.project.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Purchase getPurchaseById(String id) throws Exception {
        Optional<Purchase> purchase = purchaseRepository.findById(id);
        if(purchase.isEmpty()){
            throw new Exception("purchase not found");
        }
        return purchase.get();
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

    public Map<String, List<Purchase>> groupPurchasesBySeller(List<Purchase> purchases) {
        return purchases.stream()
                .collect(Collectors.groupingBy(
                        purchase -> {
                            try {
                                return getSellerForProduct(purchase.getProductId());
                            } catch (IOException | InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                ));
    }

    private String getSellerForProduct(String productId) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api")) //cambiar url
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public void deletePurchase(String id) {
        purchaseRepository.deleteById(id);
    }
}
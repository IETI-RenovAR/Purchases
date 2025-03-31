package org.adaschool.project.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.adaschool.project.dto.PurchaseDTO;
import org.adaschool.project.model.Purchase;
import org.adaschool.project.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {

    private Map<String, List<String>> shoppingCart = new HashMap<>();
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

    private Map<String, List<String>> groupProductsBySeller(List<String> products) {
        return products.stream()
                .collect(Collectors.groupingBy(product -> {
                    try {
                        return getSellerForProduct(product);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                        return "UnknownSeller";
                    }
                }));
    }

    private String getSellerForProduct(String productId) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://20.83.172.95:8082/v1/products/seller/" + productId)) //cambiar url
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public void deletePurchase(String id) {
        purchaseRepository.deleteById(id);
    }

    public void addProductShoppingCart(String idUser, String idProduct){
        List<String> shopping = shoppingCart.get(idUser);
        if(shopping == null){shopping = new ArrayList<>();}
        shopping.add(idProduct);
        shoppingCart.put(idUser, shopping);
    }

    public Map<String, List<String>> getProductsById(String id){
        return sortProducts(shoppingCart.get(id));
    }

    public Map<String, List<String>> sortProducts(List<String> products){
        return groupProductsBySeller(products);
    }

}
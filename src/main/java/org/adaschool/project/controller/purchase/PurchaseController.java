package org.adaschool.project.controller.purchase;

import org.adaschool.project.dto.PurchaseDTO;
import org.adaschool.project.model.Purchase;
import org.adaschool.project.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping
    public ResponseEntity<List<Purchase>> getAllPurchases() {
        List<Purchase> purchases = purchaseService.getAllPurchases();
        return ResponseEntity.ok(purchases);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Purchase> getPurchaseById(@PathVariable("id") String id) {
        Purchase purchase = null;
        try {
            purchase = purchaseService.getPurchaseById(id);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(purchase);
    }

    @PostMapping
    public ResponseEntity<Purchase> createPurchase(@RequestBody PurchaseDTO purchaseDTO) {
        Purchase createdPurchase = purchaseService.createPurchase(purchaseDTO);
        URI createdPurchaseUri = URI.create("/v1/purchases/" + createdPurchase.getId());
        return ResponseEntity.created(createdPurchaseUri).body(createdPurchase);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePurchase(@PathVariable("id") String id) {
        purchaseService.deletePurchase(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/shoppingCart/{id}")
    public Map<String, List<String>> getShoppingCart(@PathVariable String id){
        return purchaseService.getProductsById(id);
    }

    @PostMapping("/shoppingCart")
    public ResponseEntity<String> addToShoppingCart(@RequestBody Map<String, String> request) {
        String idUser = request.get("idUser");
        String idProduct = request.get("idProduct");
        purchaseService.addProductShoppingCart(idUser, idProduct);
        return ResponseEntity.ok(idProduct);
    }


}
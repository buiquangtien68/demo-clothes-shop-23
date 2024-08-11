package com.example.demo_clothes_shop_23.rest.adminApi;

import com.example.demo_clothes_shop_23.request.UpsertProductRequest;
import com.example.demo_clothes_shop_23.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/products")
@AllArgsConstructor
public class AdminProductApi {
    private ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody UpsertProductRequest upsertProductRequest) {
        productService.createProduct(upsertProductRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer productId, @RequestBody UpsertProductRequest upsertProductRequest) {
        productService.updateProduct(productId,upsertProductRequest);
        return ResponseEntity.ok().build();
    }
}

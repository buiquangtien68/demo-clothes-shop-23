package com.example.demo_clothes_shop_23.rest;

import com.example.demo_clothes_shop_23.entities.Address;
import com.example.demo_clothes_shop_23.request.UpsertAddressRequest;
import com.example.demo_clothes_shop_23.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressApi {
    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<?> createAddress(@Valid @RequestBody UpsertAddressRequest addressRequest) {
        List<Address> addresses = addressService.createAddress(addressRequest);
        return new ResponseEntity<>(addresses, HttpStatus.CREATED); //201
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAddress(@Valid @PathVariable Integer id, @Valid @RequestBody UpsertAddressRequest addressRequest) {
        List<Address> addresses = addressService.updateAddress(addressRequest, id);
        return ResponseEntity.ok(addresses); //200
    }

    @PutMapping("/updateChosen/{id}")
    public ResponseEntity<?> updateChosenAddress(@Valid @PathVariable Integer id) {
        List<Address> addresses = addressService.updateChosen(id);
        return ResponseEntity.ok(addresses); //200
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable Integer id) {
        List<Address> addresses = addressService.deleteAddress(id);
        return ResponseEntity.ok(addresses); //200
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAddressById(@PathVariable Integer id) {
        Address address = addressService.getAddressById(id);
        return ResponseEntity.ok(address);
    }
}

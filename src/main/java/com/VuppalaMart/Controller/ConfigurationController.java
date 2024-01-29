package com.VuppalaMart.Controller;

import com.VuppalaMart.Models.ApiResponse;
import com.VuppalaMart.Models.DeliverySlot;
import com.VuppalaMart.Models.StoreEntity;
import com.VuppalaMart.Services.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/config")
public class ConfigurationController {

    @Autowired
    private ConfigurationService configurationService;

    @PostMapping("/store")
    public ResponseEntity<ApiResponse> createOrUpdateStore(@RequestBody StoreEntity store) {
        try {
            StoreEntity createdStore = configurationService.createOrUpdateStore(store);
            return new ResponseEntity<>(new ApiResponse(200, "Store created", createdStore), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{storeId}/addTimeSlots")
    public ResponseEntity<ApiResponse> addTimeSlots(@PathVariable String storeId, @RequestBody List<DeliverySlot> timeSlots) {
        ApiResponse response = configurationService.addTimeSlots(storeId, timeSlots);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{storeId}/removeTimeSlots")
    public ResponseEntity<ApiResponse> removeTimeSlots(@PathVariable String storeId, @RequestBody List<String> slotIds) {
        ApiResponse response = configurationService.removeTimeSlots(storeId, slotIds);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/stores")
    public ResponseEntity<ApiResponse> getAllStores() {
        try {
            List<StoreEntity> stores = configurationService.getAllStores();
            return new ResponseEntity<>(new ApiResponse(200, "Stores retrieved successfully", stores), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

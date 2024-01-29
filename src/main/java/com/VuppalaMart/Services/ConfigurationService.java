package com.VuppalaMart.Services;

import com.VuppalaMart.Models.ApiResponse;
import com.VuppalaMart.Models.AppointmentEntity;
import com.VuppalaMart.Models.DeliverySlot;
import com.VuppalaMart.Models.DeliverySlot.TimeSlot;
import com.VuppalaMart.Models.StoreEntity;
import com.VuppalaMart.Repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConfigurationService {

    @Autowired
    private StoreRepository storeRepository;

    public StoreEntity createOrUpdateStore(StoreEntity store) {
        return storeRepository.save(store);
    }
    public ApiResponse addTimeSlots(String storeId, List<DeliverySlot> timeSlots) {
        try {
            Optional<StoreEntity> optionalStore = storeRepository.findBySid(storeId);
            if (optionalStore.isPresent()) {
                StoreEntity store = optionalStore.get();
                List<DeliverySlot> existingSlots = store.getDeliverySlots();

                for (DeliverySlot newSlot : timeSlots) {
                    existingSlots.add(newSlot);
                }

                store.setSid(storeId);
                store.setDeliverySlots(existingSlots);
                storeRepository.save(store);
                return new ApiResponse(200, "Time slots added successfully", store);
            } else {
                return new ApiResponse(404, "Store not found", null);
            }
        } catch (Exception e) {
            System.out.println(e);
            return ApiResponse.INTERNAL_SERVER_ERROR;
        }
    }


    public ApiResponse removeTimeSlots(String storeId, List<String> slotIds) {
        try {
            Optional<StoreEntity> optionalStore = storeRepository.findById(storeId);
            if (optionalStore.isPresent()) {
                StoreEntity store = optionalStore.get();
                List<DeliverySlot> existingSlots = store.getDeliverySlots();

                existingSlots.removeIf(slot -> slotIds.contains(slot.getSlotId().name()));

                store.setDeliverySlots(existingSlots);
                storeRepository.save(store);
                return new ApiResponse(200, "Time slots removed successfully", store);
            } else {
                return new ApiResponse(404, "Store not found", null);
            }
        } catch (Exception e) {
            System.out.println(e);
            return ApiResponse.INTERNAL_SERVER_ERROR;
        }
    }
    public List<StoreEntity> getAllStores() {
        return storeRepository.findAll();
    }
}

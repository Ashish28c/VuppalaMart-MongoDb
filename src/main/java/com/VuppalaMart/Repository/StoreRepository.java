package com.VuppalaMart.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.VuppalaMart.Models.StoreEntity;

public interface StoreRepository extends MongoRepository<StoreEntity, String> {
    List<StoreEntity> findByDistributionCenterId(String distributionCenterId);
    Optional<StoreEntity> findBySid(String StoreID);
}
		
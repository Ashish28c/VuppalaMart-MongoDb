package com.VuppalaMart.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.VuppalaMart.Models.DistributionCenter;

public interface DistributionCenterRepository extends MongoRepository<DistributionCenter, String> {
}

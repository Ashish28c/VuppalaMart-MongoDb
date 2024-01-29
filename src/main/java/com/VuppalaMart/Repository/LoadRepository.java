package com.VuppalaMart.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.VuppalaMart.Models.TruckLoadEntity;

public interface LoadRepository extends MongoRepository<TruckLoadEntity, String> {

}

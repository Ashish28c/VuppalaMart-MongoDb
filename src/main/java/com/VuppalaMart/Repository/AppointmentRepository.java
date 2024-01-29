package com.VuppalaMart.Repository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.VuppalaMart.Models.AppointmentEntity;

public interface AppointmentRepository extends MongoRepository<AppointmentEntity, String> {
		
	List<AppointmentEntity> findByDestinationId(String destinationId);

	List<AppointmentEntity> findByDestinationIdAndAppointmentDateBetween(String destinationId, Date startDate, Date endDate);
}




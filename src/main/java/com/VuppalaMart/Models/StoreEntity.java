package com.VuppalaMart.Models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "stores")

public class StoreEntity {
	@Id
	 private String sid;
	    private String distributionCenterId;
	    private List<DeliverySlot> deliverySlots;

}

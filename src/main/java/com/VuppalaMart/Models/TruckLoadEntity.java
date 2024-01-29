package com.VuppalaMart.Models;

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
@Document(collection = "loads")
public class TruckLoadEntity {
    @Id
    private String loadId;
    private String sourceId;
    private String destinationId;

    
}

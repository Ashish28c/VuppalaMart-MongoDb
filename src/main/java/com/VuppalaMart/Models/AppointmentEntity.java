package com.VuppalaMart.Models;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "appointments")
public class AppointmentEntity {
    @Id
    private String appointmentId;
    private String loadId;
    private String sourceId; // DistributionCenterId
    private String destinationId; // storeID
    private Date appointmentDate;
    private String timeSlot;
    private AppointmentStatus status;
}

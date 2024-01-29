package com.VuppalaMart.Controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.VuppalaMart.Models.ApiResponse;
import com.VuppalaMart.Models.AppointmentEntity;
import com.VuppalaMart.Services.AppointmentService;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createAppointment(
            @RequestParam String loadId,
            @RequestParam String sourceId,
            @RequestParam String destinationId) {
        
        AppointmentEntity appointment = appointmentService.createAppointment(loadId, sourceId, destinationId);

        if (appointment != null) {
            return new ResponseEntity<>(new ApiResponse(200, "Appointment created successfully", appointment), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse(500, "Appointment Not Created", null), HttpStatus.NOT_FOUND);
        }
    }

}

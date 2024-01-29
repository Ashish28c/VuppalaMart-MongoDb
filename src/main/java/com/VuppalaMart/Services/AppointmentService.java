package com.VuppalaMart.Services;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.VuppalaMart.Models.AppointmentEntity;
import com.VuppalaMart.Models.AppointmentStatus;
import com.VuppalaMart.Models.DeliverySlot;
import com.VuppalaMart.Models.StoreEntity;
import com.VuppalaMart.Repository.AppointmentRepository;
import com.VuppalaMart.Repository.StoreRepository;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private StoreRepository storeRepository;

    public AppointmentEntity createAppointment(String loadId, String sourceId, String destinationId) {
        Date today = new Date();
        Date nextAvailableDate = today;

        while (nextAvailableDate != null) {
            List<AppointmentEntity> appointments = appointmentRepository
                    .findByDestinationIdAndAppointmentDateBetween(destinationId, startOfDay(nextAvailableDate), endOfDay(nextAvailableDate));

            List<String> existingSlots = getAppointmentsTimeSlots(appointments);
            List<String> storeSlots = getStoreTimeSlots(destinationId);
            List<String> availableSlots = findAvailableSlots(storeSlots, existingSlots);

            if (!availableSlots.isEmpty()) {
                return createAppointment(loadId, sourceId, destinationId, availableSlots.get(0), nextAvailableDate);
            }

            nextAvailableDate = findNextAvailableDate(destinationId, nextAvailableDate);
        }

        return null;
    }

    private AppointmentEntity createAppointment(String loadId, String sourceId, String destinationId, String timeSlot, Date appointmentDate) {
        AppointmentEntity appointment = new AppointmentEntity();
        appointment.setLoadId(loadId);
        appointment.setSourceId(sourceId);
        appointment.setDestinationId(destinationId);
        appointment.setAppointmentDate(appointmentDate);
        appointment.setTimeSlot(timeSlot);
        appointment.setStatus(AppointmentStatus.APPROVED);

        return appointmentRepository.save(appointment);
    }

    private List<String> getAppointmentsTimeSlots(List<AppointmentEntity> appointments) {
        return appointments.stream()
                .map(AppointmentEntity::getTimeSlot)
                .collect(Collectors.toList());
    }

    private List<String> getStoreTimeSlots(String destinationId) {
        Optional<StoreEntity> storeOptional = storeRepository.findById(destinationId);
        return storeOptional.map(storeEntity ->
                storeEntity.getDeliverySlots().stream()
                        .map(DeliverySlot::getSlotId)
                        .map(DeliverySlot.TimeSlot::getTime)
                        .collect(Collectors.toList())
        ).orElse(Collections.emptyList());
    }

    private List<String> findAvailableSlots(List<String> storeSlots, List<String> existingSlots) {
        return storeSlots.stream()
                .filter(slot -> !existingSlots.contains(slot))
                .collect(Collectors.toList());
    }

    private Date findNextAvailableDate(String destinationId, Date fromDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fromDate);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date nextDate = calendar.getTime();

        while (!hasAvailableSlots(destinationId, nextDate)) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            nextDate = calendar.getTime();
        }

        return nextDate;
    }

    private boolean hasAvailableSlots(String destinationId, Date date) {
        List<AppointmentEntity> appointments = appointmentRepository
                .findByDestinationIdAndAppointmentDateBetween(destinationId, startOfDay(date), endOfDay(date));

        List<String> existingSlots = getAppointmentsTimeSlots(appointments);
        List<String> storeSlots = getStoreTimeSlots(destinationId);
        List<String> availableSlots = findAvailableSlots(storeSlots, existingSlots);

        return !availableSlots.isEmpty();
    }

    private Date startOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private Date endOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }
}

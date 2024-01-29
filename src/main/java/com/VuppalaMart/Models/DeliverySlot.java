package com.VuppalaMart.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeliverySlot {

    public enum TimeSlot {
        SLOT1("9AM-1PM"),
        SLOT2("2PM-6PM"),
        SLOT3("7PM-11PM"),
        SLOT4("12AM-4AM");

        private final String time;

        TimeSlot(String time) {
            this.time = time;
        }

        public String getTime() {
            return time;
        }

        public static TimeSlot fromString(String text) {
            for (TimeSlot slot : TimeSlot.values()) {
                if (slot.getTime().equalsIgnoreCase(text)) {
                    return slot;
                }
            }
            return null; 
        }
    }

    private TimeSlot slotId;

    public DeliverySlot(String slotId) {
        this.slotId = TimeSlot.fromString(slotId);
    }
}

package com.pastaebasta.eist.server.service;

import com.pastaebasta.eist.shared.model.DateTimeSlot;
import com.pastaebasta.eist.server.repository.DateTimeSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DateTimeSlotService {

    private final DateTimeSlotRepository dateTimeSlotRepository;

    public List<DateTimeSlot> getDateTimeSlots(String restaurantId) {
        return dateTimeSlotRepository.getAllByRestaurantId(restaurantId);
    }
}

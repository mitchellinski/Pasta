package com.pastaebasta.eist.service;

import com.pastaebasta.eist.model.DateTimeSlot;
import com.pastaebasta.eist.repository.DateTimeSlotRepository;
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

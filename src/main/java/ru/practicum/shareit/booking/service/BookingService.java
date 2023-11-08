package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.repository.BookingRepositoryImpl;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepositoryImpl bookingRepository;
}

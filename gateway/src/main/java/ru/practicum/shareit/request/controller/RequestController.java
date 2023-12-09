package ru.practicum.shareit.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.client.RequestClient;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import static ru.practicum.shareit.constant.Constants.USER_HEADER_ID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
public class RequestController {

    private final RequestClient requestClient;

    @PostMapping
    public ResponseEntity<Object> addRequest(@Valid @RequestBody ItemRequestDto itemRequestDto,
                                             @RequestHeader(USER_HEADER_ID) @Min(1) long userId) {
        log.info("Запрос на создание запроса вещи от юзера id = {}", userId);
        return requestClient.addRequest(itemRequestDto, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getAllUsersItemRequest(@RequestHeader(USER_HEADER_ID) @Min(1) long userId) {
        log.info("Запрос на возврат списка запросов юзера: " + userId);
        return requestClient.getAllUsersItemRequest(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllOtherUsersItemRequest(
            @RequestHeader(USER_HEADER_ID) @Min(1) long userId,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int size) {
        log.info(String.format("Запрос от юзера id = %d, " +
                "параметр from = %d, параметр size = %d", userId, from, size));
        return requestClient.getAllOtherUsersItemRequest(userId, from, size);
    }

    @GetMapping("{requestId}")
    public ResponseEntity<Object> getItemRequest(@PathVariable @Min(1) long requestId,
                                                 @RequestHeader(USER_HEADER_ID) @Min(1) long userId) {
        log.info("Запрос на возврат запроса с id = " + requestId + " от юзера id = " + userId);
        return requestClient.getItemRequest(userId, requestId);
    }
}

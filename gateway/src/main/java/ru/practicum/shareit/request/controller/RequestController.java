package ru.practicum.shareit.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.client.RequestClient;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import javax.validation.Valid;

import java.util.List;

import static ru.practicum.shareit.constant.Constants.USER_HEADER_ID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
public class RequestController {

    private final RequestClient requestClient;

    @PostMapping
    public ItemRequestDto addRequest(@Valid @RequestBody ItemRequestDto itemRequestDto,
                                     @RequestHeader(USER_HEADER_ID) long userId) {
        log.info("Запрос на создание запроса вещи от юзера id = {}", userId);
        return null; //itemRequestService.addRequest(itemRequestDto, userId);
    }

    @GetMapping
    public List<ItemRequestDto> getAllUsersItemRequest(@RequestHeader(USER_HEADER_ID) long userId) {
        log.info("Запрос на возврат списка запросов юзера: " + userId);
        return null; //itemRequestService.getAllItemRequest(userId);
    }

    @GetMapping("/all")
    public List<ItemRequestDto> getAllOtherUsersItemRequest(
            @RequestHeader(USER_HEADER_ID) long userId,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int size) {
        log.info(String.format("Запрос от юзера id = %d, " +
                "параметр from = %d, параметр size = %d", userId, from, size));
        return null; //itemRequestService.getAllOtherUsersItemRequest(userId, from, size);
    }

    @GetMapping("{requestId}")
    public ItemRequestDto getItemRequest(@PathVariable long requestId,
                                         @RequestHeader(USER_HEADER_ID) long userId) {
        log.info("Запрос на возврат запроса с id = " + requestId + " от юзера id = " + userId);
        return null; //itemRequestService.getItemRequest(requestId, userId);
    }
}

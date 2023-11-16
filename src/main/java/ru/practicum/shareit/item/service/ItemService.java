package ru.practicum.shareit.item.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exceptions.ex.CheckUserException;
import ru.practicum.shareit.exceptions.ex.ItemNotFoundException;
import ru.practicum.shareit.exceptions.ex.NoAccessCommentException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemOwnerDto;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentsRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class ItemService {
    private final ItemRepository itemStorage;
    private final UserService userService;
    private final CommentsRepository commentStorage;
    private final BookingRepository bookingStorage;

    public ItemDto createItem(ItemDto itemDto, long userId) {
        Item item = ItemMapper.toItem(itemDto);
        User owner = UserMapper.toUser(userService.getUserById(userId));
        item.setOwner(owner);
        return ItemMapper.toItemDto(itemStorage.save(item));
    }

    public ItemDto updateItem(ItemDto itemDto, long userId, long itemId) {
        Item itemFromBd = itemStorage.getReferenceById(itemId);
        boolean checkUser = itemFromBd.getOwner().getId() == userId;

        if (!checkUser) {
            throw new CheckUserException("Отсутствует доступ к вещи id = " + itemId);
        }

        if (itemDto.getName() != null) {
            itemFromBd.setName(itemDto.getName());
        }

        if (itemDto.getDescription() != null) {
            itemFromBd.setDescription(itemDto.getDescription());
        }

        if (itemDto.getAvailable() != null) {
            itemFromBd.setAvailable(itemDto.getAvailable());
        }

        return ItemMapper.toItemDto(itemStorage.save(itemFromBd));
    }

    public ItemDto getItemById(long itemId, long userId) {

        if (itemStorage.findById(itemId).isEmpty()) {
            throw new ItemNotFoundException("Отсутствует вещь с id = " + itemId);
        }

        List<Item> items = new ArrayList<>();
        Item item = itemStorage.getReferenceById(itemId);
        log.info("Владелец вещи id = " + item.getOwner().getId() + ", userId = " + userId);
        items.add(item);
        log.info("Начинаем искать комменты для вещи " + itemId);
        List<CommentDto> comments = commentStorage.findByItemId(itemId).stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
        log.info("Нашли комментов " + comments.size());
        ItemOwnerDto itemOwnerDto = buildListItemOwnerDto(items).get(0);
        itemOwnerDto.setComments(comments);
        if (item.getOwner().getId() == userId) {
            return ItemMapper.toItemDto(itemOwnerDto);
        } else {
            ItemDto itemDto = ItemMapper.toItemDto(item);
            itemDto.setComments(comments);
            return itemDto;
        }
    }

    public List<ItemOwnerDto> findAllUsersItems(long userId) {
        userService.checkExistsUser(userId);
        List<Item> items = itemStorage.findByOwnerId(userId);
        return buildListItemOwnerDto(items);
    }

    private List<ItemOwnerDto> buildListItemOwnerDto(List<Item> items) {
        List<ItemOwnerDto> itemOwnerDtos = new ArrayList<>();

        for (Item item : items) {
            ItemOwnerDto itemOwnerDto = ItemMapper.toItemOwnerDto(item);
            log.info("вещь - " + itemOwnerDtos);
            List<Booking> rawBookings = bookingStorage.findByItemId(item.getId());
            log.info("Нашлось аренд для вещи: " + item.getId() + ", " + rawBookings);
            List<Booking> bookings = rawBookings.stream()
                    .filter(x -> !x.getStatus().equals(BookingStatus.REJECTED))
                    .filter(x -> x.getStart().isAfter(LocalDateTime.now()))
                    .sorted((x, x1) -> x.getStart().compareTo(x1.getStart()))
                    .collect(Collectors.toList());

            if (bookings.size() != 0) {
                itemOwnerDto.setNextBooking(BookingMapper.toBookingOwnerDto(bookings.get(0)));
            }

            bookings = rawBookings.stream()
                    .filter(x -> !x.getStatus().equals(BookingStatus.REJECTED))
                    .filter(x -> x.getEnd().isAfter(LocalDateTime.now()) && x.getStart().isBefore(LocalDateTime.now()))
                    .sorted((x, x1) -> x1.getEnd().compareTo(x.getEnd()))
                    .collect(Collectors.toList());

            log.info("bookings после отбора - " + bookings);

            if (bookings.size() == 0) {
                bookings = rawBookings.stream()
                        .filter(x -> !x.getStatus().equals(BookingStatus.REJECTED))
                        .filter(x -> x.getEnd().isBefore(LocalDateTime.now()))
                        .sorted((x, x1) -> x1.getEnd().compareTo(x.getEnd()))
                        .collect(Collectors.toList());
            }

            if (bookings.size() != 0) {
                itemOwnerDto.setLastBooking(BookingMapper.toBookingOwnerDto(bookings.get(0)));
            }

            itemOwnerDtos.add(itemOwnerDto);
        }
        log.info("ItemOwnerDtos = " + itemOwnerDtos);
        return itemOwnerDtos;
    }

    public List<ItemDto> searchItem(String text) {

        if (text.isBlank()) {
            return new ArrayList<>();
        }

        return getListItemDto(itemStorage.search(text.toLowerCase().trim()));
    }

    private List<ItemDto> getListItemDto(List<Item> items) {
        return items.stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    public boolean checkIsAvailableItem(long itemId) {
        Optional<Item> optionalItem = itemStorage.findById(itemId);

        if (optionalItem.isEmpty()) {
            throw new ItemNotFoundException("Отсутствует вещь с id = " + itemId);
        }

        return optionalItem.get().getAvailable();
    }

    public Item getItem(long id) {
        return itemStorage.getReferenceById(id);
    }

    public CommentDto addComment(CommentDto commentDto, long itemId, long userId) {
        Comment comment = CommentMapper.toComment(commentDto);
        boolean isBooking = false;

        for (Booking booking : bookingStorage.findByBookerIdAndEndBefore(userId, LocalDateTime.now())) {
            if (booking.getItem().getId() == itemId) {
                log.info("Юзер " + userId + " брал в аренду вещь " + itemId);
                isBooking = true;
                break;
            }
        }

        if (!isBooking) {
            throw new NoAccessCommentException("Нельзя оставить комментарий вещи, не взяв её в аренду");
        }

        comment.setItem(itemStorage.getReferenceById(itemId));
        comment.setAuthor(UserMapper.toUser(userService.getUserById(userId)));
        comment.setCreated(LocalDateTime.now());
        log.info("Отзыв: " + comment);
        return CommentMapper.toCommentDto(commentStorage.save(comment));
    }
}

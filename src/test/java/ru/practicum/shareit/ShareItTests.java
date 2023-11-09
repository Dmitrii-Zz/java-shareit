package ru.practicum.shareit;

import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
//import ru.practicum.shareit.item.controller.ItemController;
//import ru.practicum.shareit.item.dto.ItemDto;
//import ru.practicum.shareit.item.repository.ItemRepository;
//import ru.practicum.shareit.item.repository.ItemRepositoryImpl;
//import ru.practicum.shareit.item.service.ItemService;
//import ru.practicum.shareit.user.controller.UserController;
//import ru.practicum.shareit.user.dto.UserDto;
//import ru.practicum.shareit.user.repository.UserRepository;
//import ru.practicum.shareit.user.service.UserService;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
@SpringBootTest
class ShareItTests {
//	private final UserRepository userStorage = new UserRepositoryImpl(new HashMap<>());
//	private final UserService userService = new UserService(userStorage);
//	private final UserController userController = new UserController(userService);
//	private final ItemRepository itemStorage = new ItemRepositoryImpl();
//	private final ItemService itemService = new ItemService(itemStorage, userService);
//	private final ItemController itemController = new ItemController(itemService);
//
//	@Test
//	void shareItTest() {
//		UserDto user = UserDto.builder().build();
//		user.setName("User1");
//		user.setEmail("User1@mail.ru");
//
//		UserDto saveUser = userController.createUser(user);
//
//		assertAll("Проверка пользователя",
//				() -> assertEquals(1, saveUser.getId()),
//				() -> assertEquals("User1", saveUser.getName()),
//				() -> assertEquals("User1@mail.ru", saveUser.getEmail()));
//
//		ItemDto itemDto = ItemDto.builder().build();
//		itemDto.setName("Item1");
//		itemDto.setDescription("Description Item1");
//		itemDto.setAvailable(true);
//
//		ItemDto saveItem = itemController.createItem(1, itemDto);
//
//		assertAll("Проверка вещи",
//				() -> assertEquals(1, saveItem.getId()),
//				() -> assertEquals("Item1", saveItem.getName()),
//				() -> assertEquals("Description Item1", saveItem.getDescription()));
//	}
}

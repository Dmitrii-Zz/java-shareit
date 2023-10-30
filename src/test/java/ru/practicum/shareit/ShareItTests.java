package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.item.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.*;
import ru.practicum.shareit.user.controller.UserController;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.repository.UserRepositoryImpl;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ShareItTests {
	private final UserRepository userStorage = new UserRepositoryImpl(new HashMap<>());
	private final UserService userService = new UserService(userStorage);
	private final UserController userController = new UserController(userService);
	private final ItemMapper itemMapper = new ItemMapper();
	private final ItemRepository itemStorage = new ItemRepositoryImpl();
	private final ItemService itemService = new ItemService(itemStorage, userService, itemMapper);
	private final ItemController itemController = new ItemController(itemService);

	@Test
	void shareItTest() {
		User user = new User();
		user.setName("User1");
		user.setEmail("User1@mail.ru");

		User saveUser = userController.createUser(user);

		assertAll("Проверка пользователя",
				() -> assertEquals(1, saveUser.getId()),
				() -> assertEquals("User1", saveUser.getName()),
				() -> assertEquals("User1@mail.ru", saveUser.getEmail()));

		ItemDto itemDto = new ItemDto();
		itemDto.setName("Item1");
		itemDto.setDescription("Description Item1");
		itemDto.setAvailable(true);

		ItemDto saveItem = itemController.createItem(1, itemDto);

		assertAll("Проверка вещи",
				() -> assertEquals(1, saveItem.getId()),
				() -> assertEquals("Item1", saveItem.getName()),
				() -> assertEquals("Description Item1", saveItem.getDescription()));
	}
}

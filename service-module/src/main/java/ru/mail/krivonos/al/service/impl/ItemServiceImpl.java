package ru.mail.krivonos.al.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.mail.krivonos.al.repository.ItemRepository;
import ru.mail.krivonos.al.repository.connection.ConnectionHandler;
import ru.mail.krivonos.al.repository.exceptions.ItemRepositoryException;
import ru.mail.krivonos.al.repository.model.Item;
import ru.mail.krivonos.al.service.ItemService;
import ru.mail.krivonos.al.service.converter.ItemConverter;
import ru.mail.krivonos.al.service.exceptions.ConnectionAutoCloseException;
import ru.mail.krivonos.al.service.exceptions.ItemServiceException;
import ru.mail.krivonos.al.service.model.ItemDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service("itemService")
public class ItemServiceImpl implements ItemService {

    private static final String ITEMS_GETTING_ERROR_MESSAGE = "Error while getting Items list.";
    private static final String CONNECTION_CLOSE_ERROR_MESSAGE = "Error while closing connection.";

    private static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);

    private final ItemRepository itemRepository;
    private final ItemConverter itemConverter;
    private final ConnectionHandler connectionHandler;

    public ItemServiceImpl(ItemRepository itemRepository, ItemConverter itemConverter, ConnectionHandler connectionHandler) {
        this.itemRepository = itemRepository;
        this.itemConverter = itemConverter;
        this.connectionHandler = connectionHandler;
    }

    @Override
    public List<ItemDTO> getItems() {
        try (Connection connection = connectionHandler.getConnection()) {
            try {
                connection.setAutoCommit(false);
                List<Item> items = itemRepository.findItems(connection);
                List<ItemDTO> itemDTOs = getItemDTOs(items);
                connection.commit();
                return itemDTOs;
            } catch (SQLException | ItemRepositoryException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ItemServiceException(ITEMS_GETTING_ERROR_MESSAGE, e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionAutoCloseException(CONNECTION_CLOSE_ERROR_MESSAGE, e);
        }
    }

    private List<ItemDTO> getItemDTOs(List<Item> items) {
        return items.stream()
                .map(itemConverter::toDTO)
                .collect(Collectors.toList());
    }
}

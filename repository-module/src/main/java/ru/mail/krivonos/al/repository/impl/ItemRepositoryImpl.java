package ru.mail.krivonos.al.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.mail.krivonos.al.repository.ItemRepository;
import ru.mail.krivonos.al.repository.exceptions.ItemRepositoryException;
import ru.mail.krivonos.al.repository.model.Item;
import ru.mail.krivonos.al.repository.model.ItemStatusEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository("itemRepository")
public class ItemRepositoryImpl implements ItemRepository {

    private static final String QUERY_EXCEPTION_ERROR_MESSAGE = "Can't execute query: \"%s\".";
    private static final String ITEMS_EXTRACTION_ERROR_MESSAGE = "Something goes wrong while extracting Items list.";

    private static final Logger logger = LoggerFactory.getLogger(ItemRepositoryImpl.class);

    @Override
    public List<Item> findItems(Connection connection) throws ItemRepositoryException {
        String sql = "SELECT id, name, status FROM Item WHERE deleted = FALSE";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return getItems(resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ItemRepositoryException(String.format(QUERY_EXCEPTION_ERROR_MESSAGE, sql), e);
        }
    }

    private List<Item> getItems(ResultSet resultSet) throws ItemRepositoryException {
        List<Item> items = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Item item = new Item();
                item.setId(resultSet.getLong("id"));
                item.setName(resultSet.getString("name"));
                item.setStatus(ItemStatusEnum.valueOf(resultSet.getString("status")));
                items.add(item);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ItemRepositoryException(ITEMS_EXTRACTION_ERROR_MESSAGE, e);
        }
        return items;
    }
}

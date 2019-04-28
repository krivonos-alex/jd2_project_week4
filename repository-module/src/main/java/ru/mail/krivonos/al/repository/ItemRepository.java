package ru.mail.krivonos.al.repository;

import ru.mail.krivonos.al.repository.exceptions.ItemRepositoryException;
import ru.mail.krivonos.al.repository.model.Item;

import java.sql.Connection;
import java.util.List;

public interface ItemRepository {

    List<Item> findItems(Connection connection) throws ItemRepositoryException;
}

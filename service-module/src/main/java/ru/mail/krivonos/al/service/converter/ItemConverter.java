package ru.mail.krivonos.al.service.converter;

import ru.mail.krivonos.al.repository.model.Item;
import ru.mail.krivonos.al.service.model.ItemDTO;

public interface ItemConverter {

    Item fromDTO(ItemDTO itemDTO);

    ItemDTO toDTO(Item item);
}

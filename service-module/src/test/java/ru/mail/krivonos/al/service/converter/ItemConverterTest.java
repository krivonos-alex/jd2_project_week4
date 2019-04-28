package ru.mail.krivonos.al.service.converter;

import org.junit.Before;
import org.junit.Test;
import ru.mail.krivonos.al.repository.model.Item;
import ru.mail.krivonos.al.repository.model.ItemStatusEnum;
import ru.mail.krivonos.al.service.converter.impl.ItemConverterImpl;
import ru.mail.krivonos.al.service.model.ItemDTO;

import static org.junit.Assert.assertEquals;

public class ItemConverterTest {

    private ItemConverter itemConverter;

    @Before
    public void init() {
        itemConverter = new ItemConverterImpl();
    }

    @Test
    public void shouldReturnItemWithSameID() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(1L);
        Item item = itemConverter.fromDTO(itemDTO);
        assertEquals(itemDTO.getId(), item.getId());
    }

    @Test
    public void shouldReturnItemDTOWithSameID() {
        Item item = new Item();
        item.setId(1L);
        ItemDTO itemDTO = itemConverter.toDTO(item);
        assertEquals(item.getId(), itemDTO.getId());
    }

    @Test
    public void shouldReturnItemWithSameName() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setName("name");
        Item item = itemConverter.fromDTO(itemDTO);
        assertEquals(itemDTO.getName(), item.getName());
    }

    @Test
    public void shouldReturnItemDTOWithSameName() {
        Item item = new Item();
        item.setName("name");
        ItemDTO itemDTO = itemConverter.toDTO(item);
        assertEquals(item.getName(), itemDTO.getName());
    }

    @Test
    public void shouldReturnItemWithSameStatus() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setStatus(ItemStatusEnum.READY);
        Item item = itemConverter.fromDTO(itemDTO);
        assertEquals(itemDTO.getStatus(), item.getStatus());
    }

    @Test
    public void shouldReturnItemDTOWithSameStatus() {
        Item item = new Item();
        item.setStatus(ItemStatusEnum.READY);
        ItemDTO itemDTO = itemConverter.toDTO(item);
        assertEquals(item.getStatus(), itemDTO.getStatus());
    }
}

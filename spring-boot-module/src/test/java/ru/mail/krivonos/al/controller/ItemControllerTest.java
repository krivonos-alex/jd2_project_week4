package ru.mail.krivonos.al.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import ru.mail.krivonos.al.service.ItemService;
import ru.mail.krivonos.al.service.model.ItemDTO;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class ItemControllerTest {

    @Mock
    private ItemService itemService;
    @Mock
    private Model model;

    private List<ItemDTO> items;

    private ItemController itemController;

    private MockMvc mockMvc;

    @Before
    public void init() {
        itemController = new ItemController(itemService);
        mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
        items = new ArrayList<>();
        items.add(new ItemDTO());
        when(itemService.getItems()).thenReturn(items);
    }


    @Test
    public void shouldReturnItemsPageForItemsGetRequest() {
        String items = itemController.getItems(model);
        Assert.assertEquals("items", items);
    }

    @Test
    public void requestForItemsIsSuccessfullyProcessedWithAvailableItemsList() throws Exception {
        this.mockMvc.perform(get("/items.html"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("items", equalTo(items)))
                .andExpect(forwardedUrl("items"));
    }
}

package ru.mail.krivonos.al.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.mail.krivonos.al.service.ItemService;
import ru.mail.krivonos.al.service.model.ItemDTO;

import java.util.List;

import static ru.mail.krivonos.al.controller.constant.URLConstants.ITEMS_PAGE_URL;

@Controller("itemController")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping(ITEMS_PAGE_URL)
    public String getItems(Model model) {
        List<ItemDTO> items = itemService.getItems();
        model.addAttribute("items", items);
        return "items";
    }
}

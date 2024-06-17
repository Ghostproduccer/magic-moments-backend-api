package com.magicmoments.backendapi.controllers;

import com.magicmoments.backendapi.model.Items;
import com.magicmoments.backendapi.service.dto.ItemsDto;
import com.magicmoments.backendapi.service.srv.ItemsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/items")
@Tag(name = "Company items")
public class ItemsController {

    @Autowired
    private ItemsService itemsService;

    @Operation(summary = "Get all items and the associated colors")
    @GetMapping("/itemsColors")
    public List<ItemsDto> items() {
        return itemsService.getAllItemsWithColor();
    }

    @Operation(summary = "Get an item by id")
    @GetMapping("/itemsColors/{id}")
    public ItemsDto getById(@PathVariable("id") int id) {
        return itemsService.getItemsWithColorById(id);
    }

    public Items prueba() { return null; }
}

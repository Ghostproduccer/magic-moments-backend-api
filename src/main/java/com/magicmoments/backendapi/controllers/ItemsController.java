package com.magicmoments.backendapi.controllers;

import com.magicmoments.backendapi.model.Colors;
import com.magicmoments.backendapi.model.Items;
import com.magicmoments.backendapi.service.dto.ItemsDto;
import com.magicmoments.backendapi.service.srv.ItemsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/prueba")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('MODERATOR')")
    public Items prueba() { return null; }
}

package com.magicmoments.backendapi.service.srv;

import com.magicmoments.backendapi.model.Items;
import com.magicmoments.backendapi.model.ItemsColors;
import com.magicmoments.backendapi.service.dto.ItemsColorsDto;
import com.magicmoments.backendapi.service.dto.ItemsDto;
import com.magicmoments.backendapi.service.mappers.ItemsColorsMapper;
import com.magicmoments.backendapi.service.mappers.ItemsMapper;
import com.magicmoments.backendapi.service.repositories.ItemsColorsRepository;
import com.magicmoments.backendapi.service.repositories.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemsService {
    @Autowired
    ItemsRepository itemsRepo;

    @Autowired
    ItemsColorsRepository itemsColorsRepo;

    @Autowired
    ItemsMapper itemsMapper;

    @Autowired
    ItemsColorsMapper itemsColorMapper;

    public ItemsDto getItemsWithColorById(int itemId) {
        Items item = itemsRepo.findById(itemId);
        ItemsDto dto = itemsMapper.toDto(item);
        if (dto != null) {
            List<ItemsColors> itemsColorsList = itemsColorsRepo.findByItemId(itemId);

            if (itemsColorsList != null) {
                List<ItemsColorsDto> itemsColorsDtoList = itemsColorMapper.toDtoList(itemsColorsList);
                dto.setItem_color(itemsColorsDtoList);
            }
        }

        return dto;
    }

    /**
     * This method returns a list of every item with their associated color (item_color) using DTOs
     * @return
     */
    public List<ItemsDto> getAllItemsWithColor() {
        // We get all the items from the repo
        List<Items> itemsList = itemsRepo.findAll();
        List<ItemsDto> itemsDtosList = itemsList.stream()
                .map(itemsMapper::toDto)
                .collect(Collectors.toList());

        for (ItemsDto itemDto : itemsDtosList) {
            List<ItemsColors> itemsColorsList = itemsColorsRepo.findByItemId(itemDto.getId());
            List<ItemsColorsDto> itemsColorsDtoList = itemsColorMapper.toDtoList(itemsColorsList);
            if (itemsColorsDtoList != null) {
                itemDto.setItem_color(itemsColorsDtoList);
            }
        }

        return itemsDtosList;

    }
}

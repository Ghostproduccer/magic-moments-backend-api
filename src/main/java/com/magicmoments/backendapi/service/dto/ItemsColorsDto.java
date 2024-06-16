package com.magicmoments.backendapi.service.dto;

import lombok.Data;

@Data
public class ItemsColorsDto {
    private int itemId;
    private ColorsDto colorName;
    private int active;
    private String item_color_url;
}

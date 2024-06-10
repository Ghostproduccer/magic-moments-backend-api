package com.magicmoments.backendapi.controllers.helpers;

import com.magicmoments.backendapi.controllers.forms.BaseForm;
import org.springframework.beans.BeanUtils;

public class ControllersHelper {

    public static <R> R formToDto(BaseForm form, R dto) {
        BeanUtils.copyProperties(form, dto);
        return dto;
    }

    public static <R> BaseForm dtoToForm(BaseForm form, R dto) {
        BeanUtils.copyProperties(dto, form);
        return form;
    }
}

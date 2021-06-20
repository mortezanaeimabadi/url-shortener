package com.company.urlshortener.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ShortUrlRequestDto {
    //url must start with http:// or https:// which is min=7 character
    @NotNull
    @Size(min = 9, max = 1500)
    private String url;
}

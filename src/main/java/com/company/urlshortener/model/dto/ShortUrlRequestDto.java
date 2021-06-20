package com.company.urlshortener.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShortUrlRequestDto {
    //url must start with http:// or https:// which is min=7 character
    @NotNull
    @Size(min = 9, max = 1500)
    private String url;
}

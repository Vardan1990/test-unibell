package com.test.unibell.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddContactsDto {

    @NotBlank
    private String contact;
    @NotBlank
    private String contactType;

}

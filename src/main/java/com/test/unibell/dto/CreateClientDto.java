package com.test.unibell.dto;

import com.test.unibell.entity.ClientContacts;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateClientDto {

    @NotBlank
    private String name;
    @NotBlank
    private String surname;

    private List<ClientContacts> clientContacts;

}

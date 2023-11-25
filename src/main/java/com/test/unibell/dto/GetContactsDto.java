package com.test.unibell.dto;

import com.test.unibell.entity.ClientContacts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetContactsDto {

    private List<ClientContacts> clientContacts;

}

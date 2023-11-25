package com.test.unibell.service;


import com.test.unibell.dto.AddContactsDto;
import com.test.unibell.dto.CreateClientDto;
import com.test.unibell.dto.GetContactsDto;
import com.test.unibell.entity.Client;
import com.test.unibell.entity.ClientContacts;
import com.test.unibell.exception.ClientContactException;
import com.test.unibell.exception.ClientException;

import java.util.List;

public interface ClientService {

    Client createClientFromDto(CreateClientDto createClientDto);

    void setNewClientContactById(Long clientId, AddContactsDto addContactsDto) throws ClientException, ClientContactException;

    List<Client> getClientsList();

    Client findClientById(Long clientId) throws ClientException;

    GetContactsDto getClientContactsById(Long clientId);

    List<ClientContacts> getClientContactsByContactTypeAndById(String contactType, Long clientId) throws ClientException;


}

package com.test.unibell.service;


import com.test.unibell.dto.AddContactsDto;
import com.test.unibell.dto.CreateClientDto;
import com.test.unibell.dto.GetContactsDto;
import com.test.unibell.entity.Client;
import com.test.unibell.entity.ClientContacts;
import com.test.unibell.enums.ContactsType;
import com.test.unibell.exception.ClientContactException;
import com.test.unibell.exception.ClientException;
import com.test.unibell.repo.ClientRepo;
import com.test.unibell.repo.ClientContactsRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepo clientRepo;
    private final ClientContactsRepo clientContactsRepo;

    @Override
    @Transactional
    public Client createClientFromDto(CreateClientDto createClientDto) {
        Optional<Client> clientOptional = clientRepo.findByNameAndSurname(createClientDto.getName(), createClientDto.getSurname());
        if (clientOptional.isPresent()) {
            return clientOptional.get();
        }
        Client client = new Client();
        client.setName(createClientDto.getName());
        client.setSurname(createClientDto.getSurname());
        if (!createClientDto.getClientContacts().isEmpty()) {
            for (ClientContacts clientContacts : createClientDto.getClientContacts()) {
                clientContacts.setClient(client);
                clientContactsRepo.save(clientContacts);
            }
            client.setClientContacts(createClientDto.getClientContacts());
        }
        log.info("going save client into repo");
        return clientRepo.save(client);
    }

    @Override
    public void setNewClientContactById(Long clientId, AddContactsDto addContactsDto) throws ClientContactException, ClientException {
        Optional<Client> clientOptional = clientRepo.findClientById(clientId);
        if (clientOptional.isEmpty()) {
            throw new ClientException("client by this id is not present");
        }
        Client client = clientOptional.get();
        List<ClientContacts> clientContactsList = client.getClientContacts();
        if (addContactsDto.getContact() == null || addContactsDto.getContactType() == null) {
            throw new ClientContactException("contact or contactType is null");
        }
        ClientContacts clientContacts = new ClientContacts();
        clientContacts.setContact(addContactsDto.getContact());
        clientContacts.setContactsType(ContactsType.valueOf(addContactsDto.getContactType()));
        clientContacts.setClient(client);
        clientContactsRepo.save(clientContacts);
        clientContactsList.add(clientContacts);
        client.setClientContacts(clientContactsList);
        clientRepo.save(client);
    }

    @Override
    public List<Client> getClientsList() {
        return clientRepo.findAll();
    }

    @Override
    public Client findClientById(Long clientId) throws ClientException {
        Optional<Client> clientOptional = clientRepo.findClientById(clientId);
        if (clientOptional.isPresent()) {
            return clientOptional.get();
        }
        throw new ClientException("Client by this id not found");
    }

    @Override
    public GetContactsDto getClientContactsById(Long clientId) {
        Optional<Client> clientOptional = clientRepo.findClientById(clientId);
        return clientOptional.map(client -> new GetContactsDto(client.getClientContacts())).orElseThrow();
    }

    @Override
    public List<ClientContacts> getClientContactsByContactTypeAndById(String contactType, Long clientId) throws ClientException {
        Optional<Client> clientOptional = clientRepo.findClientById(clientId);
        if (clientOptional.isEmpty()) {
            throw new ClientException("can not return contacts Client by this id not found ");
        }
        Client client = clientOptional.get();
        return client.getClientContacts()
                .stream()
                .filter(clientContacts -> clientContacts.getContactsType().name().equalsIgnoreCase(contactType))
                .collect(Collectors.toList());
    }
}
package com.test.unibell.controllers;

import com.test.unibell.dto.AddContactsDto;
import com.test.unibell.dto.CreateClientDto;
import com.test.unibell.dto.GetContactsDto;
import com.test.unibell.entity.Client;
import com.test.unibell.entity.ClientContacts;
import com.test.unibell.exception.ClientContactException;
import com.test.unibell.exception.ClientException;
import com.test.unibell.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(path = "/api/client")
@RequiredArgsConstructor
public class ClientController {


    private final ClientService clientService;


    @PostMapping(path = "/createClient", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Client> createClient(@RequestBody CreateClientDto createClientDto) throws ClientException {
        log.info("Client#create by dto {}", createClientDto);
        if (createClientDto.getName() == null || createClientDto.getSurname() == null) {
            throw new ClientException("wrong client name or surname");
        }
        return ResponseEntity.ok(clientService.createClientFromDto(createClientDto));
    }


    @PutMapping(path = "/setNewContact", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> setNewContact(@RequestParam(name = "clientId") Long clientId, @RequestBody AddContactsDto addContactsDto) throws ClientContactException, ClientException {
        log.info("Client#set new contact  {}", addContactsDto);
        clientService.setNewClientContactById(clientId, addContactsDto);
        return ResponseEntity.ok().build();
    }


    @GetMapping(path = "/getClientList")
    public ResponseEntity<List<Client>> getClientList() {
        log.info("Client#get list");
        return ResponseEntity.ok(clientService.getClientsList());

    }


    @GetMapping(path = "/getById")
    public ResponseEntity<Client> getClientById(@RequestParam(name = "clientId") Long clientId) throws ClientException {
        log.info("Client#get by id  {}", clientId);
        return ResponseEntity.ok(clientService.findClientById(clientId));
    }


    @GetMapping(path = "/getContacts")
    public ResponseEntity<GetContactsDto> getClientContactsById(@RequestParam(name = "clientId") Long clientId) {
        log.info("Client#get contacts by id {}", clientId);
        return ResponseEntity.ok(clientService.getClientContactsById(clientId));
    }


    @GetMapping(path = "/getContactsByTypeAndId")
    public ResponseEntity<List<ClientContacts>> getContactsByTypeAndId(@RequestParam(name = "contactType") String contactType, @RequestParam(name = "clientId") Long clientId) throws ClientException {
        log.info("Client#et contacts bay contactType and by id  {} ,{}", contactType, clientId);
        return ResponseEntity.ok(clientService.getClientContactsByContactTypeAndById(contactType, clientId));
    }
}

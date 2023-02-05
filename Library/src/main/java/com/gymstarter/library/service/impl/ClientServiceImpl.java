package com.gymstarter.library.service.impl;

import com.gymstarter.library.dto.ClientDto;
import com.gymstarter.library.model.Client;
import com.gymstarter.library.repository.ClientRepository;
import com.gymstarter.library.repository.RoleRepository;
import com.gymstarter.library.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public ClientDto save(ClientDto clientDto) {
        Client client = new Client();
        client.setFirstName(clientDto.getFirstName());
        client.setLastName(clientDto.getLastName());
        client.setUsername(clientDto.getUsername());
        client.setPassword(clientDto.getPassword());
        client.setRoles(Arrays.asList(roleRepository.findByName("CUSTOMER")));
        Client clientSave = clientRepository.save(client);
        return mapperDto(clientSave);
    }

    private ClientDto mapperDto(Client client) {
        ClientDto clientDto = new ClientDto();
        clientDto.setFirstName(client.getFirstName());
        clientDto.setLastName(client.getLastName());
        clientDto.setUsername(client.getUsername());
        clientDto.setPassword(client.getPassword());
        return clientDto;
    }

    @Override
    public Client findByUsername(String username) {
        return clientRepository.findByUsername(username);
    }

    @Override
    public Client saveInfo(Client client) {
        Client client1 = clientRepository.findByUsername(client.getUsername());
        client1.setAddress(client.getAddress());
        client1.setCity(client.getCity());
        client1.setCountry(client.getCountry());
        client1.setPhoneNumber(client.getPhoneNumber());
        return clientRepository.save(client1);
    }

}

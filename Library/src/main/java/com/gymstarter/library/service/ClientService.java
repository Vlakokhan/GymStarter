package com.gymstarter.library.service;

import com.gymstarter.library.dto.ClientDto;
import com.gymstarter.library.model.Client;

public interface ClientService {

    ClientDto save(ClientDto clientDto);

    Client findByUsername(String username);

    Client saveInfo(Client client);
}

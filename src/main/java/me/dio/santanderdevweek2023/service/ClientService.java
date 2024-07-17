package me.dio.santanderdevweek2023.service;

import me.dio.santanderdevweek2023.dto.ClientDTO;
import me.dio.santanderdevweek2023.exceptions.NoSuchElementException;
import me.dio.santanderdevweek2023.model.Address;
import me.dio.santanderdevweek2023.model.Client;
import me.dio.santanderdevweek2023.repository.ClientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    ClientRepository repository;

    @Autowired
    AddressService addressService;

    @Autowired
    ModelMapper modelMapper;

    public List<Client> findAllClients(){
        return repository.findAll();
    }

    public Client findClientById(String id){
        Optional<Client> ClientFound = repository.findById(id);
        return ClientFound.orElseThrow(() -> new NoSuchElementException(id, "Client"));
    }

    public ClientDTO saveClient(ClientDTO clientDTO){
        Address addressFound = addressService.verifyExistenceOfAddress(clientDTO.getAddressDTO());
        Client cliente = modelMapper.map(clientDTO, Client.class);
        cliente.setAddress(addressFound);
        try {
            String encryptedPassword = new BCryptPasswordEncoder().encode(clientDTO.getPassword());
            cliente.setPassword(encryptedPassword);

        } catch (RuntimeException e) {
            throw new NoSuchElementException("Account can't be created! Error: " + e.getMessage());
        }
        return modelMapper.map(repository.save(cliente), ClientDTO.class);
    }

    public Client updateClient(String id, ClientDTO clientDTO){
        Optional<Client> clientFound = repository.findById(id);

        if (clientFound.isPresent()) {
            Address addressFound = addressService.verifyExistenceOfAddress(clientDTO.getAddressDTO());
            Client cliente = modelMapper.map(clientDTO, Client.class);
            cliente.setAddress(addressFound);
            return repository.save(cliente);
        } else {
            throw new NoSuchElementException("Client not found with ID: " + id);
        }
    }

    public boolean deleteClient(String id){
        Optional<Client> clientDelete = repository.findById(id);
        if (clientDelete.isPresent()){
            repository.deleteById(id);
            return true;
        }
        else{
            throw new NoSuchElementException("Client not found with ID: " + id);
        }
    }

}

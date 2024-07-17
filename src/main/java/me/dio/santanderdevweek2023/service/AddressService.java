package me.dio.santanderdevweek2023.service;

import me.dio.santanderdevweek2023.dto.AddressDTO;
import me.dio.santanderdevweek2023.dto.ViaCEPDTO;
import me.dio.santanderdevweek2023.exceptions.NoSuchElementException;
import me.dio.santanderdevweek2023.model.Address;
import me.dio.santanderdevweek2023.repository.AddressRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AddressService {
    @Autowired
    AddressRepository repository;
    @Autowired
    ViaCepService viaCepService;
    @Autowired
    ModelMapper modelMapper;
    public List<Address> findAllAddresses(){
        return repository.findAll();
    }

    public Address findAddressById(UUID id){
        Optional<Address> AddressFound = repository.findById(id);
        return AddressFound.orElseThrow(() -> new NoSuchElementException(id, "Address"));
    }

    public Address findAddressByCEP(String cep){
        Optional<Address> AddressFound = repository.findByCep(cep);
        return AddressFound.orElseThrow(() -> new NoSuchElementException(cep, "Address"));
    }

    public Address saveAddress(Address address){
        return verifyExistenceOfAddress(address);
    }

    public void updateAddress(UUID id, Address Address){
        Optional<Address> AddressFound = repository.findById(id);
        if(AddressFound.isPresent()) {
            repository.save(Address);
        }
    }

    public boolean deleteAddress(UUID id){
        repository.deleteById(id);
        Optional<Address> AddressDelete = repository.findById(id);
        return AddressDelete.isEmpty();
    }

    //Sobrecarga de Métodos
    public Address verifyExistenceOfAddress(Address address) {
        return verifyExistenceOfAddressInternal(address.getCep());
    }
    public Address verifyExistenceOfAddress(AddressDTO address) {
        return verifyExistenceOfAddressInternal(address.getCep());
    }

    private Address verifyExistenceOfAddressInternal(String cep) {
        //Verifica se o endereço existe.
        Optional<Address> addressFound = repository.findByCep(cep);
        //Caso não exista irá cadastrar
        return addressFound.orElseGet(() -> {
            ViaCEPDTO viaCepResponse;
            try{
                viaCepResponse = viaCepService.consultarCep(cep);
                viaCepResponse.setCep(viaCepResponse.getCep().replace("-",""));
            }catch(Exception ex){
                throw new NoSuchElementException( cep,"CEP");
            }

            Address convertedAddress = modelMapper.map(viaCepResponse, Address.class);
            repository.save(convertedAddress);
            return convertedAddress;
        });
    }
}

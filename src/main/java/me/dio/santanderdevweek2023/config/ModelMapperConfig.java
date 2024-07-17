package me.dio.santanderdevweek2023.config;

import me.dio.santanderdevweek2023.dto.CardDTO;
import me.dio.santanderdevweek2023.dto.ClientDTO;
import me.dio.santanderdevweek2023.dto.ViaCEPDTO;
import me.dio.santanderdevweek2023.model.Address;
import me.dio.santanderdevweek2023.model.Card;
import me.dio.santanderdevweek2023.model.Client;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        modelMapper.typeMap(ViaCEPDTO.class, Address.class).addMappings(mapper -> {
            mapper.map(ViaCEPDTO::getCep, Address::setCep);
            mapper.map(ViaCEPDTO::getLogradouro, Address::setStreet);
            mapper.map(ViaCEPDTO::getBairro, Address::setDistrict);
            mapper.map(ViaCEPDTO::getLocalidade, Address::setCity);
            mapper.map(ViaCEPDTO::getUf, Address::setUf);
        });

        modelMapper.typeMap(ClientDTO.class, Client.class).addMappings(mapper -> {
            mapper.map(ClientDTO::getCpf, Client::setCpf);
            mapper.map(ClientDTO::getName, Client::setName);
            mapper.map(ClientDTO::getBirthdate, Client::setBirthdate);
            mapper.map(src -> src.getAddressDTO().getComplement(), Client::setComplement);
            mapper.map(src -> src.getAddressDTO().getNumber(), Client::setNumber);
            mapper.map(src -> src.getAddressDTO().getCep(), (dest, value) -> dest.getAddress().setCep((String) value));
        });

        modelMapper.typeMap(Card.class, CardDTO.class).addMappings(mapper -> {
            mapper.map(Card::getId, CardDTO::setId);
            mapper.map(Card::getType, CardDTO::setType);
            mapper.map(Card::getNumber, CardDTO::setNumber);
            mapper.map(Card::getLimit, CardDTO::setLimit);
        });

        return modelMapper;
    }
}

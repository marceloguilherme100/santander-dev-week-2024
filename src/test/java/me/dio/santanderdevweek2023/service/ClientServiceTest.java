package me.dio.santanderdevweek2023.service;

import me.dio.santanderdevweek2023.dto.AddressDTO;
import me.dio.santanderdevweek2023.dto.ClientDTO;
import me.dio.santanderdevweek2023.exceptions.NoSuchElementException;
import me.dio.santanderdevweek2023.model.Address;
import me.dio.santanderdevweek2023.model.Client;
import me.dio.santanderdevweek2023.model.security.UserRole;
import me.dio.santanderdevweek2023.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ClientServiceTest {
    public static final String CPF        = "32528452719";
    public static final String NAME = "UserTest";
    public static final Date BIRTHDATE    = new Date("25/09/2003");
    public static final String COMPLEMENT = "casa";
    public static final String NUMBER        = "1234";

    public static final String PASSWORD = "123456";

    public static final UserRole USER_ROLE = UserRole.ADMIN;
    public static final int INDEX = 0;
    public static final Address ADDRESS = new Address(UUID.randomUUID(), "0000001", "Rua x", "Bairro y", "Cidade z", "AB");

    @InjectMocks //Preciso da classe de verdade
    private ClientService service;
    @Mock // Não preciso acessar o banco, quero apenas mocar a resposta.
    private ClientRepository repository;
    @Mock
    private AddressService addressService;
    @Mock
    private ModelMapper modelMapper;
    private Client client;
    private ClientDTO clientDTO;
    private Optional<Client> optionalClient;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startClient();
    }


    @Test
    void whenFindAllClientsThenReturnAListOfClients() {
        when(repository.findAll()).thenReturn(List.of(client));
        List<Client> response = service.findAllClients();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(Client.class, response.get(INDEX).getClass());

        assertEquals(NAME, response.get(INDEX).getName());
        assertEquals(CPF, response.get(INDEX).getCpf());
        assertEquals(BIRTHDATE, response.get(INDEX).getBirthdate());
        assertEquals(NUMBER, response.get(INDEX).getNumber());
        assertEquals(COMPLEMENT, response.get(INDEX).getComplement());
    }
    @Test
    void whenFindAllClientsThenReturnAnEmptyList() {
        when(repository.findAll()).thenReturn(List.of());
        List<Client> response = service.findAllClients();
        assertEquals(0, response.size());
    }


    @Test
    void whenFindClientByIdThenReturnAClientInstance() {
        when(repository.findById(Mockito.anyString())).thenReturn(optionalClient);
        Client response = service.findClientById(CPF);

        assertNotNull(response);
        assertEqualsDefault(response);
    }

    @Test
    void whenFindClientByIdThenReturnClientNotFoundException() {
        when(repository.findById(any())).thenReturn(Optional.empty());

        try{
            service.findClientById(CPF);
        }catch(Exception e){
            assertEquals(NoSuchElementException.class, e.getClass());
            assertEquals("Client not found with ID: " + CPF, e.getMessage());
        }

    }

    @Test
    void whenSaveClientThenReturnSuccess() {
        when(addressService.verifyExistenceOfAddress(ADDRESS)).thenReturn(ADDRESS);
        when(repository.save(any())).thenReturn(client);
        when(modelMapper.map(clientDTO, Client.class)).thenReturn(client);
        when(modelMapper.map(client, ClientDTO.class)).thenReturn(clientDTO);

        ClientDTO response = service.saveClient(clientDTO);

        assertEquals(Client.class, response.getClass());
        assertNotNull(response);
        verify(repository, times(1)).save(any(Client.class));
        assertEquals(CPF, response.getCpf());
        assertEquals(NAME, response.getName());
        assertEquals(BIRTHDATE, response.getBirthdate());
    }

    @Test
    void saveClient_AddressNotFound() {
        // Configurar o comportamento do mock para retornar null (endereço não encontrado)
        when(addressService.verifyExistenceOfAddress(ADDRESS)).thenReturn(null);

        // Chamar o método e verificar o resultado
        assertThrows(NullPointerException.class, () -> service.saveClient(clientDTO)); // Deve lançar uma exceção
        verify(repository, never()).save(any(Client.class)); // Verificar se o método save não foi chamado
    }

    @Test
    void whenUpdateClientThenReturnSuccess() {
        when(repository.findById(any())).thenReturn(optionalClient);
        when(addressService.verifyExistenceOfAddress(ADDRESS)).thenReturn(ADDRESS);
        when(modelMapper.map(clientDTO, Client.class)).thenReturn(client);
        when(modelMapper.map(client, ClientDTO.class)).thenReturn(clientDTO);
        when(repository.save(any())).thenReturn(client);

        ClientDTO response = service.saveClient(clientDTO);

        assertNotNull(response);
        assertEquals(Client.class, response.getClass());
        assertEquals(CPF, response.getCpf());
        assertEquals(NAME, response.getName());
        assertEquals(BIRTHDATE, response.getBirthdate());
    }

    @Test
    void whenUpdateClient_AddressNotFound() {
        when(addressService.verifyExistenceOfAddress(ADDRESS)).thenReturn(null);
        assertThrows(NoSuchElementException.class, () -> service.updateClient(CPF,clientDTO));
        verify(repository, never()).save(any(Client.class));
    }

    @Test
    void whenUpdateClientThenReturnClientNotFoundException() {
        when(repository.findById(any())).thenReturn(Optional.empty());

        try{
            service.updateClient(CPF,clientDTO);
        }catch(Exception e){
            assertEquals(NoSuchElementException.class, e.getClass());
            assertEquals("Client not found with ID: " + CPF, e.getMessage());
        }

    }
    @Test
    void whenUpdateClient_SaveClientFails() {
        when(repository.findById(any())).thenReturn(optionalClient);
        when(addressService.verifyExistenceOfAddress(ADDRESS)).thenReturn(ADDRESS);
        when(modelMapper.map(any(), any())).thenReturn(client);
        when(repository.save(any())).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> service.updateClient(CPF, clientDTO));
    }

    @Test
    void deleteClientThenReturnSuccess() {
        when(repository.findById(anyString())).thenReturn(optionalClient);
        doNothing().when(repository).deleteById(anyString());
        service.deleteClient(CPF);
        verify(repository, times(1)).deleteById(anyString());
    }

    @Test
    void whenDeleteClientThenReturnClientNotFoundException() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());
        doNothing().when(repository).deleteById(anyString());
        try{
            service.deleteClient(CPF);
        }catch(Exception e){
            assertEquals(NoSuchElementException.class, e.getClass());
            assertEquals("Client not found with ID: "+ CPF, e.getMessage());
        }
    }


    private void startClient(){
        client = new Client(CPF, NAME, PASSWORD, USER_ROLE, BIRTHDATE, COMPLEMENT, NUMBER, ADDRESS);
        clientDTO = new ClientDTO(CPF,PASSWORD, NAME, BIRTHDATE, new AddressDTO(), USER_ROLE);
        optionalClient = Optional.of(new Client(CPF, NAME, PASSWORD, USER_ROLE, BIRTHDATE, COMPLEMENT, NUMBER, ADDRESS));
    }

    private void assertEqualsDefault(Client response){
        assertEquals(CPF, response.getCpf());
        assertEquals(NAME, response.getName());
        assertEquals(BIRTHDATE, response.getBirthdate());
        assertEquals(COMPLEMENT, response.getComplement());
        assertEquals(NUMBER, response.getNumber());
    }

}
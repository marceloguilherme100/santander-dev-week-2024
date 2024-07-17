package me.dio.santanderdevweek2023.service;

import me.dio.santanderdevweek2023.dto.AddressDTO;
import me.dio.santanderdevweek2023.dto.ViaCEPDTO;
import me.dio.santanderdevweek2023.exceptions.NoSuchElementException;
import me.dio.santanderdevweek2023.model.Address;
import me.dio.santanderdevweek2023.repository.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AddressServiceTest {
    public static final UUID UUID = java.util.UUID.randomUUID();
    public static final String CEP = "25635201";
    public static final String WRONG_CEP = "000000001";
    public static final String STREET = "Rua x";
    public static final String DISTRICT = "Bairro y";
    public static final String CITY = "Cidade z";
    public static final String UF = "AB";
    public static final String COMPLEMENT = "COMPLEMENTO CASA";
    public static final String NUMBER = "NUMERO 10";
    public static final int INDEX = 0;

    @InjectMocks //Preciso da classe de verdade
    private AddressService service;
    @Mock
    AddressRepository repository;
    @Mock
    ViaCepService viaCepService;
    @Mock
    ModelMapper modelMapper;

    private Address address;
    private AddressDTO addressDTO;
    private ViaCEPDTO viaCEPDTO;
    private Optional<Address> optionalAddress;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startAddress();
    }
    @Test
    void whenFindAllAddressesThenReturnAListOfAddresses() {
        when(repository.findAll()).thenReturn(List.of(address));
        List<Address> response = service.findAllAddresses();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(Address.class, response.get(INDEX).getClass());

        assertEquals(CEP, response.get(INDEX).getCep());
        assertEquals(UUID, response.get(INDEX).getId());
        assertEquals(STREET, response.get(INDEX).getStreet());
        assertEquals(DISTRICT, response.get(INDEX).getDistrict());
        assertEquals(CITY, response.get(INDEX).getCity());
        assertEquals(UF, response.get(INDEX).getUf());
    }
    @Test
    void whenFindAllAddressesThenReturnAnEmptyList() {
        when(repository.findAll()).thenReturn(List.of());
        List<Address> response = service.findAllAddresses();
        assertEquals(0, response.size());
    }


    @Test
    void whenFindAddressByIdThenReturnAnAddressInstance() {
        when(repository.findById(any())).thenReturn(optionalAddress);
        Address response = service.findAddressById(UUID);

        assertNotNull(response);
        assertEqualsDefault(response);
    }

    @Test
    void whenFindAddressByIdThenReturnAddressNotFoundException() {
        when(repository.findById(any())).thenReturn(Optional.empty());

        try{
            service.findAddressById(UUID);
        }catch(Exception e){
            assertEquals(NoSuchElementException.class, e.getClass());
            assertEquals("Address not found with ID: " + UUID, e.getMessage());
        }

    }

    @Test
    void whenFindAddressByCEPThenReturnAnAddressInstance() {
        when(repository.findByCep(any())).thenReturn(optionalAddress);
        Address response = service.findAddressByCEP(CEP);
        assertNotNull(response);
        assertEqualsDefault(response);
    }

    @Test
    void whenFindAddressByCEPThenReturnAddressNotFoundException() {
        when(repository.findById(any())).thenReturn(Optional.empty());

        try{
            service.findAddressByCEP(CEP);
        }catch(Exception e){
            assertEquals(NoSuchElementException.class, e.getClass());
            assertEquals("Address not found with ID: " + CEP, e.getMessage());
        }

    }

    @Test
    void whenSaveAddressThenReturnSuccess() {
        when(viaCepService.consultarCep(anyString())).thenReturn(viaCEPDTO);
        when(service.verifyExistenceOfAddress(address)).thenReturn(address);
        when(repository.save(any())).thenReturn(address);
        when(modelMapper.map(any(), any())).thenReturn(address);


        Address response = service.saveAddress(address);

        assertEquals(Address.class, response.getClass());
        assertNotNull(response);
        verify(repository, times(1)).save(any(Address.class));
        assertEqualsDefault(response);
    }

    @Test
    void whenSaveAddressThenReturnViaCepNotFoundException() {
        when(viaCepService.consultarCep(WRONG_CEP)).thenReturn(null);

        try{
            service.saveAddress(address);
        }catch (Exception ex){
            assertEquals(NoSuchElementException.class, ex.getClass());
            verify(repository, never()).save(any(Address.class));
        }
    }


    @Test
    void whenUpdateAddressThenReturnSuccess() {
        when(viaCepService.consultarCep(anyString())).thenReturn(viaCEPDTO);
        when(repository.findById(any())).thenReturn(optionalAddress);
        when(service.verifyExistenceOfAddress(address)).thenReturn(address);
        when(modelMapper.map(any(), any())).thenReturn(address);
        when(repository.save(any())).thenReturn(address);

        Address response = service.saveAddress(address);

        assertNotNull(response);
        assertEquals(Address.class, response.getClass());
        assertEqualsDefault(response);
    }

    @Test
    void whenUpdateAddress_AddressNotFound() {
        when(repository.findById(any())).thenReturn(Optional.empty());
        try{
            service.saveAddress(address);
        }catch (Exception ex){
            assertEquals(NoSuchElementException.class, ex.getClass());
            verify(repository, never()).save(any(Address.class));
        }
    }


    @Test
    void whenUpdateAddress_SaveAddressFails() {
        when(viaCepService.consultarCep(anyString())).thenReturn(viaCEPDTO);
        when(repository.findById(any())).thenReturn(optionalAddress);
        when(service.verifyExistenceOfAddress(address)).thenReturn(address);
        when(modelMapper.map(any(), any())).thenReturn(address);
        when(repository.save(any())).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> service.updateAddress(UUID, address));
    }

    @Test
    void deleteAddressThenReturnSuccess() {
        when(repository.findById(any())).thenReturn(optionalAddress);
        doNothing().when(repository).deleteById(any());
        service.deleteAddress(UUID);
        verify(repository, times(1)).deleteById(any());
    }

    @Test
    void whenDeleteAddressThenReturnAddressNotFoundException() {
        when(repository.findById(any())).thenReturn(Optional.empty());
        doNothing().when(repository).deleteById(any());
        try{
            service.deleteAddress(UUID);
        }catch(Exception e){
            assertEquals(NoSuchElementException.class, e.getClass());
            assertEquals("Address not found with ID: "+ UUID, e.getMessage());
        }
    }

    @Test
    public void testVerifyExistenceOfAddress() {
        when(repository.findByCep(CEP)).thenReturn(optionalAddress);

        Address result = service.verifyExistenceOfAddress(addressDTO);

        assertEquals(address, result);
    }


    private void startAddress(){
        address = new Address(UUID, CEP, STREET, DISTRICT, CITY, UF);
        addressDTO = new AddressDTO(CEP, COMPLEMENT, NUMBER);
        optionalAddress = Optional.of(new Address(UUID, CEP, STREET, DISTRICT, CITY, UF));
        viaCEPDTO = new ViaCEPDTO(CEP, STREET, COMPLEMENT, DISTRICT, CITY, UF, "ibge", "gia", "ddd", "");
    }

    private void assertEqualsDefault(Address response){
        assertEquals(UUID, response.getId());
        assertEquals(CEP, response.getCep());
        assertEquals(STREET, response.getStreet());
        assertEquals(DISTRICT, response.getDistrict());
        assertEquals(CITY, response.getCity());
        assertEquals(UF, response.getUf());
    }

}
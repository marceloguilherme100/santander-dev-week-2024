package me.dio.santanderdevweek2023.service;

import me.dio.santanderdevweek2023.dto.ViaCEPDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "viacep", url = "https://viacep.com.br/ws")
public interface ViaCepService {
    @RequestMapping(method = RequestMethod.GET, value = "/{cep}/json/")
    ViaCEPDTO consultarCep(@PathVariable("cep") String cep);
}

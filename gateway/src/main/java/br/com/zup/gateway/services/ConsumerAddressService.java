package br.com.zup.gateway.services;

import br.com.zup.gateway.controllers.dtos.ConsumerAddressRegisterDTO;
import br.com.zup.gateway.controllers.dtos.ConsumerAddressResponseDTO;
import br.com.zup.gateway.infra.clients.address.dtos.AddressRegisterDto;
import br.com.zup.gateway.infra.clients.address.dtos.AddressResponseDTO;
import br.com.zup.gateway.infra.clients.consumer.ConsumerClient;
import br.com.zup.gateway.infra.clients.address.AddressClient;
import br.com.zup.gateway.infra.clients.consumer.dtos.ConsumerResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.azul.tooling.ConsumerManager.registerConsumer;

@Service
public class ConsumerAddressService {

    @Autowired
    private ConsumerClient consumerClient;

    @Autowired
    private AddressClient addressClient;

    @Autowired
    private ConsumerService consumerService;

    public ConsumerAddressResponseDTO registerConsumerAddress(ConsumerAddressRegisterDTO consumerAddressRegisterDTO) {

        ConsumerResponseDTO consumerResponseDTO = consumerService.registerConsumer(consumerAddressRegisterDTO);

        AddressRegisterDto addressRegisterDto = mapToAddressRegisterDTO(consumerAddressRegisterDTO, consumerResponseDTO.getId());

        AddressResponseDTO addressResponseDTO = addressClient.registerAddress(addressRegisterDto);

        ConsumerAddressResponseDTO consumerAddressResponseDTO = new ConsumerAddressResponseDTO(consumerResponseDTO, addressResponseDTO);

        return consumerAddressResponseDTO;
    }


    public ConsumerAddressResponseDTO getConsumerAddress(String consumerId) {
        ConsumerResponseDTO consumer = consumerClient.getConsumerById(consumerId);
        AddressResponseDTO address = addressClient.getAddressByConsumerId(consumerId);
        return new ConsumerAddressResponseDTO(consumer, address);
    }

    public List<ConsumerAddressResponseDTO> getAllConsumerAddresses() {
        List<ConsumerResponseDTO> consumers = consumerClient.getAllConsumers();
        List<AddressResponseDTO> addresses = addressClient.getAllAddresses();

        return consumers.stream()
                .map(consumer -> new ConsumerAddressResponseDTO(consumer, null))
                .collect(Collectors.toList());
    }

    private AddressRegisterDto mapToAddressRegisterDTO(ConsumerAddressRegisterDTO consumerAddressRegisterDTO, String consumerId) {
        AddressRegisterDto addressRegisterDto = new AddressRegisterDto();
        addressRegisterDto.setConsumerId(consumerId);
        addressRegisterDto.setCity(consumerAddressRegisterDTO.getAddress().getCity());
        addressRegisterDto.setState(consumerAddressRegisterDTO.getAddress().getState());
        addressRegisterDto.setStreet(consumerAddressRegisterDTO.getAddress().getStreet());
        addressRegisterDto.setZipCode(consumerAddressRegisterDTO.getAddress().getZipCode());
        return addressRegisterDto;
    }

}

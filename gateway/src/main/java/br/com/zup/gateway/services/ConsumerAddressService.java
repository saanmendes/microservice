package br.com.zup.gateway.services;

import br.com.zup.gateway.controllers.dtos.ConsumerAddressRegisterDTO;
import br.com.zup.gateway.controllers.dtos.ConsumerAddressResponseDTO;
import br.com.zup.gateway.infra.clients.address.AddressClient;
import br.com.zup.gateway.infra.clients.address.dtos.AddressRegisterDto;
import br.com.zup.gateway.infra.clients.address.dtos.AddressResponseDTO;
import br.com.zup.gateway.infra.clients.consumer.ConsumerClient;
import br.com.zup.gateway.infra.clients.consumer.dtos.ConsumerRegisterDTO;
import br.com.zup.gateway.infra.clients.consumer.dtos.ConsumerResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class ConsumerAddressService {

    @Autowired
    private ConsumerClient consumerClient;

    @Autowired
    private AddressClient addressClient;

    public ConsumerAddressResponseDTO registerConsumerAddress(ConsumerAddressRegisterDTO consumerAddressRegisterDTO) {
        ConsumerResponseDTO consumerResponseDTO = registerConsumer(consumerAddressRegisterDTO);
        AddressResponseDTO addressResponseDTO = registerAddress(consumerAddressRegisterDTO, consumerResponseDTO.getId());
        return new ConsumerAddressResponseDTO(consumerResponseDTO, addressResponseDTO);
    }

    private ConsumerResponseDTO registerConsumer(ConsumerAddressRegisterDTO consumerAddressRegisterDTO) {
        ConsumerRegisterDTO consumerRegisterDTO = mapToConsumerRegisterDTO(consumerAddressRegisterDTO);
        return consumerClient.registerConsumerClient(consumerRegisterDTO);
    }

    private AddressResponseDTO registerAddress(ConsumerAddressRegisterDTO consumerAddressRegisterDTO, String consumerId) {
        AddressRegisterDto addressRegisterDto = mapToAddressRegisterDTO(consumerAddressRegisterDTO, consumerId);
        return addressClient.registeAddress(addressRegisterDto);
    }

    public ConsumerResponseDTO getConsumer(String consumerId) {
        return consumerClient.getConsumer(consumerId);
    }

    public AddressResponseDTO getAddress(String addressId) {
        return addressClient.getAddress(addressId);
    }

    public ConsumerResponseDTO updateConsumer(String consumerId, ConsumerAddressRegisterDTO consumerAddressRegisterDTO) {
        ConsumerRegisterDTO consumerRegisterDTO = mapToConsumerRegisterDTO(consumerAddressRegisterDTO);
        return consumerClient.updateConsumer(consumerId, consumerRegisterDTO);
    }

    public AddressResponseDTO updateAddress(String addressId, ConsumerAddressRegisterDTO consumerAddressRegisterDTO) {
        AddressRegisterDto addressRegisterDto = mapToAddressRegisterDTO(consumerAddressRegisterDTO, addressId);
        return addressClient.updateAddress(addressId, addressRegisterDto);
    }

    public void deleteConsumer(String consumerId) {
        consumerClient.deleteConsumer(consumerId);
    }

    public void deleteAddress(String addressId) {
        addressClient.deleteAddress(addressId);
    }


    private ConsumerRegisterDTO mapToConsumerRegisterDTO(ConsumerAddressRegisterDTO consumerAddressRegisterDTO) {
        ConsumerRegisterDTO consumerRegisterDTO = new ConsumerRegisterDTO();
        consumerRegisterDTO.setAge(consumerAddressRegisterDTO.getAge());
        consumerRegisterDTO.setEmail(consumerAddressRegisterDTO.getEmail());
        consumerRegisterDTO.setName(consumerAddressRegisterDTO.getName());
        return consumerRegisterDTO;
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
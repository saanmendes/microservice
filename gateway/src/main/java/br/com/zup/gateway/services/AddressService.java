package br.com.zup.gateway.services;

import br.com.zup.gateway.controllers.dtos.ConsumerAddressRegisterDTO;
import br.com.zup.gateway.infra.clients.address.AddressClient;
import br.com.zup.gateway.infra.clients.address.dtos.AddressRegisterDto;
import br.com.zup.gateway.infra.clients.address.dtos.AddressResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressClient addressClient;

    public AddressResponseDTO registerAddress(AddressRegisterDto addressDTO) {
        try {
            return addressClient.registerAddress(addressDTO);
        } catch (Exception e) {
            throw new RuntimeException("Failed to register address", e);
        }
    }

    public AddressResponseDTO updateAddress(String addressId, AddressRegisterDto addressDTO) {
        return addressClient.updateAddress(addressId, addressDTO);
    }

    public void deleteAddress(String addressId) {
        addressClient.deleteAddress(addressId);
    }

    public List<AddressResponseDTO> getAllAddresses() {
        List<AddressResponseDTO> addressesList = addressClient.getAllAddresses();
        return addressesList;
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


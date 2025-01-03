package br.com.zup.gateway.services;

import br.com.zup.gateway.controllers.dtos.ConsumerAddressRegisterDTO;
import br.com.zup.gateway.infra.clients.consumer.ConsumerClient;
import br.com.zup.gateway.infra.clients.consumer.dtos.ConsumerRegisterDTO;
import br.com.zup.gateway.infra.clients.consumer.dtos.ConsumerResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsumerService {

    @Autowired
    private ConsumerClient consumerClient;

    public ConsumerResponseDTO registerConsumer(ConsumerAddressRegisterDTO consumerAddressRegisterDTO) {
        ConsumerRegisterDTO consumerRegisterDTO = mapToConsumerRegisterDTO(consumerAddressRegisterDTO);
        return consumerClient.registerConsumer(consumerRegisterDTO);
    }

    public ConsumerResponseDTO updateConsumer(String consumerId, ConsumerAddressRegisterDTO registerDTO) {
        return consumerClient.updateConsumer(consumerId, new ConsumerRegisterDTO());
    }

    public void deleteConsumer(String consumerId) {
        consumerClient.deleteConsumer(consumerId);
    }

    public List<ConsumerResponseDTO> getAllConsumers() {
        return consumerClient.getAllConsumers();
    }

    private ConsumerRegisterDTO mapToConsumerRegisterDTO(ConsumerAddressRegisterDTO consumerAddressRegisterDTO) {
        ConsumerRegisterDTO consumerRegisterDTO = new ConsumerRegisterDTO();
        consumerRegisterDTO.setAge(consumerAddressRegisterDTO.getAge());
        consumerRegisterDTO.setEmail(consumerAddressRegisterDTO.getEmail());
        consumerRegisterDTO.setName(consumerAddressRegisterDTO.getName());
        return consumerRegisterDTO;
    }
}

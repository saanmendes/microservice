package br.com.zup.gateway.controllers;

import br.com.zup.gateway.controllers.dtos.ConsumerAddressRegisterDTO;
import br.com.zup.gateway.controllers.dtos.ConsumerAddressResponseDTO;
import br.com.zup.gateway.infra.clients.consumer.dtos.ConsumerRegisterDTO;
import br.com.zup.gateway.services.ConsumerAddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consumer-address")
public class ConsumerAddressController {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerAddressController.class);

    @Autowired
    private ConsumerAddressService consumerAddressService;

    @PostMapping
    public ResponseEntity<ConsumerAddressResponseDTO> registerConsumerAddress(@RequestBody ConsumerAddressRegisterDTO registerDTO) {
        logger.info("Request to register a new consumer with address.");
        try {
            ConsumerAddressResponseDTO consumerAddress = consumerAddressService.registerConsumerAddress(registerDTO);
            logger.info("Successfully registered consumer with address, ID: {}", consumerAddress.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(consumerAddress);
        } catch (Exception ex) {
            logger.error("Error occurred while registering consumer with address: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ConsumerAddressResponseDTO(null, null));
        }
    }

    @GetMapping("/{consumerId}")
    public ResponseEntity<ConsumerAddressResponseDTO> getConsumerAddress(@PathVariable String consumerId) {
        logger.info("Request to get consumer and address details for Consumer ID: {}", consumerId);
        try {
            ConsumerAddressResponseDTO consumerAddressResponse = consumerAddressService.getConsumerAddress(consumerId);
            return ResponseEntity.ok(consumerAddressResponse);
        } catch (Exception ex) {
            logger.error("Error occurred while fetching consumer address: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ConsumerAddressResponseDTO(null, null));
        }
    }

    @GetMapping
    public ResponseEntity<List<ConsumerAddressResponseDTO>> getAllConsumerAddresses() {
        logger.info("Request to retrieve all consumers with addresses.");
        try {
            List<ConsumerAddressResponseDTO> consumerAddressList = consumerAddressService.getAllConsumerAddresses();
            if (consumerAddressList.isEmpty()) {
                logger.warn("No consumer-address records found.");
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(consumerAddressList);
        } catch (Exception ex) {
            logger.error("Error occurred while fetching consumer addresses: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }
}

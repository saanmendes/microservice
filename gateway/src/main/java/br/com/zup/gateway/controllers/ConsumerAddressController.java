package br.com.zup.gateway.controllers;

import br.com.zup.gateway.controllers.dtos.ConsumerAddressRegisterDTO;
import br.com.zup.gateway.controllers.dtos.ConsumerAddressResponseDTO;
import br.com.zup.gateway.infra.clients.address.dtos.AddressResponseDTO;
import br.com.zup.gateway.infra.clients.consumer.dtos.ConsumerResponseDTO;
import br.com.zup.gateway.services.ConsumerAddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/consumer-address")
public class ConsumerAddressController {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerAddressController.class);

    @Autowired
    private ConsumerAddressService consumerAddressService;

    @PostMapping
    public ResponseEntity<ConsumerAddressResponseDTO> createConsumerAddress(@RequestBody @Validated ConsumerAddressRegisterDTO registerDTO) {
        logger.info("Starting the registration process for consumer and address.");

        try {
            ConsumerAddressResponseDTO consumerAddressResponse = consumerAddressService.registerConsumerAddress(registerDTO);

            logger.info("Successfully registered Consumer with ID: {}", consumerAddressResponse.getConsumer().getId());
            logger.info("Registered Address: {}", consumerAddressResponse.getAddress().getStreet());

            return ResponseEntity.status(HttpStatus.CREATED).body(consumerAddressResponse);
        } catch (Exception ex) {
            logger.error("An error occurred during the registration of Consumer and Address: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping("/consumer/{consumerId}")
    public ResponseEntity<?> getConsumer(@PathVariable String consumerId) {
        logger.info("Received request to get Consumer details for Consumer ID: {}", consumerId);
        try {
            ConsumerResponseDTO consumerResponseDTO = consumerAddressService.getConsumer(consumerId);
            return ResponseEntity.ok(consumerResponseDTO);
        } catch (Exception ex) {
            logger.error("Error occurred while fetching consumer: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Consumer not found: " + ex.getMessage());
        }
    }

    @PutMapping("/consumer/{consumerId}")
    public ResponseEntity<?> updateConsumer(@PathVariable String consumerId, @RequestBody ConsumerAddressRegisterDTO registerDTO) {
        logger.info("Received request to update Consumer details for Consumer ID: {}", consumerId);
        try {
            ConsumerResponseDTO updatedConsumer = consumerAddressService.updateConsumer(consumerId, registerDTO);
            logger.info("Successfully updated Consumer details for Consumer ID: {}", updatedConsumer.getId());
            return ResponseEntity.ok(updatedConsumer);
        } catch (Exception ex) {
            logger.error("Error occurred while updating consumer with Consumer ID: {}", consumerId, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update Consumer: " + ex.getMessage());
        }
    }

    @DeleteMapping("/consumer/{consumerId}")
    public ResponseEntity<?> deleteConsumer(@PathVariable String consumerId) {
        logger.info("Received request to delete Consumer with Consumer ID: {}", consumerId);
        try {
            consumerAddressService.deleteConsumer(consumerId);
            logger.info("Successfully deleted Consumer with Consumer ID: {}", consumerId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception ex) {
            logger.error("Error occurred while deleting consumer with Consumer ID: {}", consumerId, ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Consumer not found: " + ex.getMessage());
        }
    }

    @GetMapping("/address/{addressId}")
    public ResponseEntity<?> getAddress(@PathVariable String addressId) {
        logger.info("Received request to get Address details for Address ID: {}", addressId);
        try {
            AddressResponseDTO addressResponseDTO = consumerAddressService.getAddress(addressId);
            return ResponseEntity.ok(addressResponseDTO);
        } catch (Exception ex) {
            logger.error("Error occurred while fetching address: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Address not found: " + ex.getMessage());
        }
    }

    @PutMapping("/address/{addressId}")
    public ResponseEntity<?> updateAddress(@PathVariable String addressId,
                                           @RequestBody ConsumerAddressRegisterDTO consumerAddressRegisterDTO) {
        logger.info("Received request to update Address details for Address ID: {}", addressId);
        try {
            AddressResponseDTO updatedAddress = consumerAddressService.updateAddress(addressId, consumerAddressRegisterDTO);
            logger.info("Successfully updated Address details for Address ID: {}", updatedAddress.getId());
            return ResponseEntity.ok(updatedAddress);
        } catch (Exception ex) {
            logger.error("Error occurred while updating address with Address ID: {}", addressId, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update Address: " + ex.getMessage());
        }
    }

    @DeleteMapping("/address/{addressId}")
    public ResponseEntity<?> deleteAddress(@PathVariable String addressId) {
        logger.info("Received request to delete Address with Address ID: {}", addressId);
        try {
            consumerAddressService.deleteAddress(addressId);
            logger.info("Successfully deleted Address with Address ID: {}", addressId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception ex) {
            logger.error("Error occurred while deleting address with Address ID: {}", addressId, ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Address not found: " + ex.getMessage());
        }
    }

    @GetMapping("/consumer-address/{consumerId}")
    public ResponseEntity<?> getConsumerAddress(@PathVariable String consumerId) {
        logger.info("Received request to get Consumer and Address details for Consumer ID: {}", consumerId);
        try {
            ConsumerResponseDTO consumerResponseDTO = consumerAddressService.getConsumer(consumerId);
            AddressResponseDTO addressResponseDTO = consumerAddressService.getAddress(consumerResponseDTO.getId());
            ConsumerAddressResponseDTO responseDTO = new ConsumerAddressResponseDTO(consumerResponseDTO, addressResponseDTO);
            logger.info("Successfully retrieved Consumer and Address details for Consumer ID: {}", consumerResponseDTO.getId());
            return ResponseEntity.ok(responseDTO);
        } catch (Exception ex) {
            logger.error("Error occurred while fetching Consumer and Address with Consumer ID: {}", consumerId, ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Consumer or Address not found: " + ex.getMessage());
        }
    }
}

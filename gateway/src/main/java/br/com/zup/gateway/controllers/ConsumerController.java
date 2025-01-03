package br.com.zup.gateway.controllers;

import br.com.zup.gateway.controllers.dtos.ConsumerAddressRegisterDTO;
import br.com.zup.gateway.controllers.dtos.ConsumerAddressResponseDTO;
import br.com.zup.gateway.infra.clients.consumer.dtos.ConsumerRegisterDTO;
import br.com.zup.gateway.infra.clients.consumer.dtos.ConsumerResponseDTO;
import br.com.zup.gateway.services.ConsumerService;
import br.com.zup.gateway.services.ConsumerAddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerController.class);

    @Autowired
    private ConsumerService consumerService;

    @Autowired
    private ConsumerAddressService consumerAddressService;

    @PostMapping
    public ResponseEntity<ConsumerResponseDTO> registerConsumer(@RequestBody ConsumerAddressRegisterDTO registerDTO) {
        logger.info("Request to register a new consumer.");
        try {
            ConsumerResponseDTO consumer = consumerService.registerConsumer(registerDTO);
            logger.info("Successfully registered consumer with ID: {}", consumer.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(consumer);
        } catch (Exception ex) {
            logger.error("Error occurred while registering consumer: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ConsumerResponseDTO());
        }
    }

    @PutMapping("/{consumerId}")
    public ResponseEntity<ConsumerResponseDTO> updateConsumer(@PathVariable String consumerId, @RequestBody ConsumerAddressRegisterDTO registerDTO) {
        logger.info("Request to update consumer with ID: {}", consumerId);
        try {
            ConsumerResponseDTO updatedConsumer = consumerService.updateConsumer(consumerId, registerDTO);
            logger.info("Successfully updated consumer with ID: {}", updatedConsumer.getId());
            return ResponseEntity.ok(updatedConsumer);
        } catch (Exception ex) {
            logger.error("Error occurred while updating consumer: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ConsumerResponseDTO());
        }
    }

    @DeleteMapping("/{consumerId}")
    public ResponseEntity<Void> deleteConsumer(@PathVariable String consumerId) {
        logger.info("Request to delete consumer with ID: {}", consumerId);
        try {
            consumerService.deleteConsumer(consumerId);
            logger.info("Successfully deleted consumer with ID: {}", consumerId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception ex) {
            logger.error("Error occurred while deleting consumer: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ConsumerResponseDTO>> getAllConsumers() {
        logger.info("Request to retrieve all consumers.");
        try {
            List<ConsumerResponseDTO> consumers = consumerService.getAllConsumers();
            if (consumers.isEmpty()) {
                logger.warn("No consumers found.");
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(consumers);
        } catch (Exception ex) {
            logger.error("Error occurred while fetching all consumers: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }
}

package br.com.zup.gateway.controllers;

import br.com.zup.gateway.controllers.dtos.ConsumerAddressRegisterDTO;
import br.com.zup.gateway.infra.clients.address.dtos.AddressRegisterDto;
import br.com.zup.gateway.infra.clients.address.dtos.AddressResponseDTO;
import br.com.zup.gateway.services.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    private static final Logger logger = LoggerFactory.getLogger(AddressController.class);

    @Autowired
    private AddressService addressService;


    @PostMapping
    public ResponseEntity<AddressResponseDTO> registerAddress(@RequestBody AddressRegisterDto addressDTO) {
        logger.info("Request to register a new address with data: {}", addressDTO);

        // Verifique se o DTO está sendo passado corretamente
        if (addressDTO == null) {
            logger.error("Received addressDTO is null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        try {
            AddressResponseDTO address = addressService.registerAddress(addressDTO);
            logger.info("Successfully registered address with ID: {}", address.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(address);
        } catch (Exception ex) {
            logger.error("Error occurred while registering address: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AddressResponseDTO());
        }
    }


    @PutMapping("/{addressId}")
    public ResponseEntity<AddressResponseDTO> updateAddress(@PathVariable String addressId, @RequestBody AddressRegisterDto addressDTO) {
        logger.info("Request to update address with ID: {}", addressId);
        try {
            AddressResponseDTO updatedAddress = addressService.updateAddress(addressId, addressDTO);
            logger.info("Successfully updated address with ID: {}", updatedAddress.getId());
            return ResponseEntity.ok(updatedAddress);
        } catch (Exception ex) {
            logger.error("Error occurred while updating address: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AddressResponseDTO());
        }
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable String addressId) {
        logger.info("Request to delete address with ID: {}", addressId);
        try {
            addressService.deleteAddress(addressId);
            logger.info("Successfully deleted address with ID: {}", addressId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception ex) {
            logger.error("Error occurred while deleting address: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<AddressResponseDTO>> getAllAddresses() {
        logger.info("Request to retrieve all addresses.");
        try {
            List<AddressResponseDTO> addresses = addressService.getAllAddresses();
            if (addresses.isEmpty()) {
                logger.warn("No addresses found.");
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(addresses);
        } catch (Exception ex) {
            logger.error("Error occurred while fetching all addresses: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }
}

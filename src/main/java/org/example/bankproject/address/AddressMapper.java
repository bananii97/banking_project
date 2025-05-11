package org.example.bankproject.address;

import org.example.bankproject.address.dto.AddressDto;
import org.example.bankproject.address.model.Address;

public class AddressMapper {

    public static Address fromDto(AddressDto addressDto) {
        return Address.builder()
                .city(addressDto.getCity())
                .street(addressDto.getStreet())
                .postalCode(addressDto.getPostalCode())
                .apartmentNumber(addressDto.getApartmentNumber())
                .build();
    }

    public static AddressDto toDto(Address address) {
        return AddressDto.builder()
                .city(address.getCity())
                .street(address.getStreet())
                .postalCode(address.getPostalCode())
                .apartmentNumber(address.getApartmentNumber())
                .build();
    }
}

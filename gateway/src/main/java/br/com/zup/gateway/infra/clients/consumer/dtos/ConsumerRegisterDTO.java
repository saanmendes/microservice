package br.com.zup.gateway.infra.clients.consumer.dtos;

import br.com.zup.gateway.infra.clients.address.dtos.AddressRegisterDto;

public class ConsumerRegisterDTO {
    private String name;
    private String age;
    private String email;

    public ConsumerRegisterDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AddressRegisterDto getAddress() {
        return new AddressRegisterDto();
    }
}


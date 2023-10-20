package ru.liga.order_service.dto.dto_model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.liga.order_service.model.Customer;
@Schema(description = "Курьер")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    @Schema(description = "ID курьера")
    private Long id;

    @Schema(description = "телефон")
    private String phone;

    @Schema(description = "email")
    private String email;

    @Schema(description = "адрес")
    private String address;

    public CustomerDto(Customer customer) {
        this.id = customer.getId();
        this.phone = customer.getPhone();
        this.email = customer.getEmail();
        this.address = customer.getAddress();
    }
 /*   public Customer convertToEntity() {
        return new Customer(id,phone,email,address,null);
    }*/

}
package ru.liga.delivery_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.liga.commons.dto.DeliveryDto;
import ru.liga.commons.dto.dto_model.OrderDto;
import ru.liga.commons.exception.RequestException;
import ru.liga.delivery_service.service.DeliveryService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DeliveryServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DeliveryService deliveryService;

    @Test
    @WithMockUser(authorities = {"SCOPE_message.read"})
    @SneakyThrows
    void testCompleteDeliveryByIdIsOk() {
        Mockito.when(deliveryService.completeDelivery(any(UUID.class),any(HttpServletRequest.class)))
                .thenReturn(new OrderDto());
        UUID uuid =  UUID.randomUUID();
        mockMvc.perform(post("/delivery-service/delivery/{order_id}/complete", uuid).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new OrderDto())));
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_message.read"})
    @SneakyThrows
    void testCompleteDeliveryByIdIfBadStatus() {
        Mockito.when(deliveryService.completeDelivery(any(UUID.class),any(HttpServletRequest.class)))
                .thenThrow(new RequestException());
        UUID uuid =  UUID.randomUUID();
        mockMvc.perform(post("/delivery-service/delivery/{order_id}/complete", uuid).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_message.read"})
    @SneakyThrows
    void testGetAllDeliveryIsOk() {
        Mockito.when(deliveryService.getActiveDeliveries(any(UUID.class)))
                .thenReturn(new ArrayList<>());
        UUID uuid =  UUID.randomUUID();
        mockMvc.perform(get("/delivery-service/delivery/courier/{courier_id}/active", uuid).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new ArrayList<>())));
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_message.read"})
    @SneakyThrows
    void testGetAllDeliveryIfBadStatus() {
        Mockito.when(deliveryService.getActiveDeliveries(any(UUID.class)))
                .thenThrow(new RequestException());
        UUID uuid =  UUID.randomUUID();
        mockMvc.perform(get("/delivery-service/delivery/courier/{courier_id}/active", uuid).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_message.read"})
    @SneakyThrows
    void testGetDeliveryByIdIsOk() {
        Mockito.when(deliveryService.getDeliveryById(any(UUID.class)))
                .thenReturn(new OrderDto());
        UUID uuid =  UUID.randomUUID();
        mockMvc.perform(get("/delivery-service/delivery/{order_id}", uuid).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new OrderDto())));
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_message.read"})
    @SneakyThrows
    void testGetDeliveryByIdIfBadStatus() {
        Mockito.when(deliveryService.getDeliveryById(any(UUID.class)))
                .thenThrow(new RequestException());
        UUID uuid =  UUID.randomUUID();
        mockMvc.perform(get("/delivery-service/delivery/{order_id}", uuid).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_message.read"})
    @SneakyThrows
    void testAcceptedDeliveryByIdIsOk() {
        Mockito.when(deliveryService.acceptedDelivery(any(UUID.class),any(UUID.class),any(HttpServletRequest.class)))
                .thenReturn(new OrderDto());
        UUID uuid =  UUID.randomUUID();
        mockMvc.perform(post("/delivery-service/delivery/accepted", uuid).with(csrf())
                        .param("id_order", String.valueOf(uuid))
                        .param("id_courier", String.valueOf(uuid))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new OrderDto())));
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_message.read"})
    @SneakyThrows
    void testAcceptedDeliveryByIdIfBadStatus() {
        Mockito.when(deliveryService.acceptedDelivery(any(UUID.class),any(UUID.class),any(HttpServletRequest.class)))
                .thenThrow(new RequestException());
        UUID uuid =  UUID.randomUUID();
        mockMvc.perform(post("/delivery-service/delivery/accepted", uuid).with(csrf())
                        .param("idOrder", String.valueOf(uuid))
                        .param("idCourier", String.valueOf(uuid))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

}

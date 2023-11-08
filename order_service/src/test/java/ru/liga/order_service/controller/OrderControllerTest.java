package ru.liga.order_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.liga.commons.dto.dto_model.OrderDto;
import ru.liga.commons.status.StatusOrders;
import ru.liga.commons.exception.ResourceNotFoundException;
import ru.liga.order_service.dto.OrderCreateRequestDto;
import ru.liga.order_service.dto.OrderCreateResponseDto;
import ru.liga.order_service.service.OrderService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;


@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @Test
    @WithMockUser(authorities = {"SCOPE_message.read"})
    @SneakyThrows
    public void testUpdateOrderStatusByIdIsOk() {
        StatusOrders status = StatusOrders.CUSTOMER_PAID;
        Mockito.when(orderService.updateOrderStatusById(any(UUID.class), ArgumentMatchers.any(StatusOrders.class)))
                .thenReturn(new OrderDto());
        UUID uuid =  UUID.randomUUID();
        mockMvc.perform(put("/order-service/order/{id}/update/status",uuid).with(csrf())
                        .param("status", status.name())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new OrderDto())));
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_message.read"})
    @SneakyThrows
    void testUpdateOrderStatusByIdIfTheParameterHasARandomStringInsteadOfAStatusOrders() {
        String testString = "test";
        Mockito.when(orderService.updateOrderStatusById(any(UUID.class), ArgumentMatchers.any(StatusOrders.class)))
                .thenReturn(new OrderDto());
        UUID uuid =  UUID.randomUUID();
        mockMvc.perform(put("/order-service/order/{id}/update/status", uuid).with(csrf())
                        .param("status", testString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_message.read"})
    @SneakyThrows
    void testUpdateOrderStatusByIdIfThereIsNoParameter() {
        Mockito.when(orderService.updateOrderStatusById(any(UUID.class), ArgumentMatchers.any(StatusOrders.class)))
                .thenReturn(new OrderDto());
        UUID uuid =  UUID.randomUUID();
        mockMvc.perform(put("/order-service/order/{id}/update/status", uuid).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_message.read"})
    @SneakyThrows
    void testSetOrderStatusIfIdIsNotNumeric() {
        StatusOrders status = StatusOrders.CUSTOMER_PAID;
        Mockito.when(orderService.updateOrderStatusById(any(UUID.class), ArgumentMatchers.any(StatusOrders.class)))
                .thenReturn(new OrderDto());
        mockMvc.perform(put("/order-service/order/{id}/update/status", "test_id").with(csrf())
                        .param("status", status.name())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_message.read"})
    @SneakyThrows
    void testSetOrderStatusIfIdNotFound() {
        StatusOrders status = StatusOrders.CUSTOMER_PAID;
        Mockito.when(orderService.updateOrderStatusById(any(UUID.class), ArgumentMatchers.any(StatusOrders.class)))
                .thenThrow(new ResourceNotFoundException());
        UUID uuid =  UUID.randomUUID();
        mockMvc.perform(put("/order-service/order/{id}/update/status", uuid).with(csrf())
                        .param("status", status.name())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_message.read"})
    @SneakyThrows
    void testOrderCreateIsOk() {
        Mockito.when(orderService.orderCreate(any(OrderCreateRequestDto.class)))
                .thenReturn(new OrderCreateResponseDto());
        UUID uuid =  UUID.randomUUID();
        mockMvc.perform(post("/order-service/order/create", uuid).with(csrf())
                        .content(getJsonFromObject(new OrderCreateRequestDto()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new OrderCreateResponseDto())));
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_message.read"})
    @SneakyThrows
    void testOrderCreateIfNotFoundCustomer() {
        Mockito.when(orderService.orderCreate(any(OrderCreateRequestDto.class)))
                .thenThrow(new ResourceNotFoundException("not found Customer"));
        UUID uuid =  UUID.randomUUID();
        mockMvc.perform(put("/order-service/order/create", uuid).with(csrf())
                        .content(getJsonFromObject(new OrderCreateRequestDto()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }


    @Test
    @WithMockUser(authorities = {"SCOPE_message.read"})
    @SneakyThrows
    void testOrderUpdateCourierAndStatusIsOk() {
        Mockito.when(orderService.orderUpdateCourierAndStatus(any(UUID.class), any(OrderDto.class)))
                .thenReturn(new OrderDto());
        UUID uuid =  UUID.randomUUID();
        mockMvc.perform(put("/order-service/order/{id}/update", uuid).with(csrf())
                        .content(getJsonFromObject(new OrderDto()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new OrderDto())));
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_message.read"})
    @SneakyThrows
    void testOrderUpdateCourierAndStatusIfNotFoundCustomer() {
        Mockito.when(orderService.orderUpdateCourierAndStatus(any(UUID.class), any(OrderDto.class)))
                .thenThrow(new ResourceNotFoundException("not found Customer"));
        UUID uuid =  UUID.randomUUID();
        mockMvc.perform(put("/order-service/order/{id}/update", uuid).with(csrf())
                        .content(getJsonFromObject(new OrderDto()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_message.read"})
    @SneakyThrows
    void testGetOrderByIdIsOk() {
        Mockito.when(orderService.getOrderById(any(UUID.class)))
                .thenReturn(new OrderDto());
        UUID uuid =  UUID.randomUUID();
        mockMvc.perform(get("/order-service/order/{id}", uuid).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new OrderDto())));
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_message.read"})
    @SneakyThrows
    void testGetOrderByIdIfNotFoundOrder() {
        Mockito.when(orderService.getOrderById(any(UUID.class)))
                .thenThrow(new ResourceNotFoundException());
        UUID uuid =  UUID.randomUUID();
        mockMvc.perform(get("/order-service/order/{id}", uuid).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_message.read"})
    @SneakyThrows
    void testGetAllOrderByCustomerIDIsOk() {
        Mockito.when(orderService.getAllOrderByCustomerID(any(UUID.class)))
                .thenReturn(new ArrayList<OrderDto>());
        UUID uuid =  UUID.randomUUID();
        mockMvc.perform(get("/order-service/order/customer/{customer_id}", uuid).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new ArrayList<OrderDto>())));
    }

    @SneakyThrows
    protected String getJsonFromObject(Object obj) {
        return objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(obj);
    }

}
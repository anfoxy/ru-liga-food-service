package ru.liga.order_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import ru.liga.order_service.service.OrderService;

import static org.mockito.ArgumentMatchers.anyLong;
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
        Mockito.when(orderService.updateOrderStatusById(anyLong(), ArgumentMatchers.any(StatusOrders.class)))
                .thenReturn(new OrderDto());
        mockMvc.perform(put("/order-service/order/{id}/update/status", 1).with(csrf())
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
        Mockito.when(orderService.updateOrderStatusById(anyLong(), ArgumentMatchers.any(StatusOrders.class)))
                .thenReturn(new OrderDto());
        mockMvc.perform(put("/order-service/order/{id}/update/status", 1).with(csrf())
                        .param("status", testString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_message.read"})
    @SneakyThrows
    void testUpdateOrderStatusByIdIfThereIsNoParameter() {
        Mockito.when(orderService.updateOrderStatusById(anyLong(), ArgumentMatchers.any(StatusOrders.class)))
                .thenReturn(new OrderDto());
        mockMvc.perform(put("/order-service/order/{id}/update/status", 1).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(authorities = {"SCOPE_message.read"})
    @SneakyThrows
    void testSetOrderStatusIfIdIsNotNumeric() {
        StatusOrders status = StatusOrders.CUSTOMER_PAID;
        Mockito.when(orderService.updateOrderStatusById(anyLong(), ArgumentMatchers.any(StatusOrders.class)))
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
        Mockito.when(orderService.updateOrderStatusById(anyLong(), ArgumentMatchers.any(StatusOrders.class)))
                .thenThrow(new ResourceNotFoundException());
        mockMvc.perform(put("/order-service/order/{id}/update/status", "test_id").with(csrf())
                        .param("status", status.name())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @SneakyThrows
    protected String getJsonFromObject(Object obj) {
        return objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(obj);
    }

}
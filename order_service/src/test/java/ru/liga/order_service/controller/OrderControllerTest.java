package ru.liga.order_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.liga.order_service.service.OrderService;


import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


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
    public void test() {
        //given

        //when
        when(orderService.getAllOrderByCustomer(any(Long.class))).thenReturn(Collections.emptyList());

        //then
        assertThatThrownBy(() -> orderService.getAllOrderByCustomer(any(Long.class)))
                .isInstanceOf(Exception.class);
    }

    @Test
    public void test2() {

        when(orderService.getAllOrderByCustomer(any(Long.class))).thenReturn(Collections.emptyList());

        assertEquals(Collections.emptyList(), orderService.getAllOrderByCustomer(any(Long.class)));
    }
}
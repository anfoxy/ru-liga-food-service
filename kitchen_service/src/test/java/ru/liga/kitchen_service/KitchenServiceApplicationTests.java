package ru.liga.kitchen_service;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.liga.commons.dto.ConfirmCourierDto;
import ru.liga.commons.dto.dto_model.OrderDto;
import ru.liga.commons.exception.RequestException;
import ru.liga.commons.exception.ResourceNotFoundException;
import ru.liga.kitchen_service.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class KitchenServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private OrderService orderService;

	@Test
	@WithMockUser(authorities = {"SCOPE_message.read"})
	@SneakyThrows
	void testDeniedOrderByIdIsOk() {
		Mockito.when(orderService.deniedOrderById(any(UUID.class),any(HttpServletRequest.class)))
				.thenReturn(new OrderDto());
		UUID uuid =  UUID.randomUUID();
		mockMvc.perform(post("/kitchen-service/order/{id}/denied", uuid).with(csrf())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(new OrderDto())));
	}

	@Test
	@WithMockUser(authorities = {"SCOPE_message.read"})
	@SneakyThrows
	void testDeniedOrderByIdIfBadStatus() {
		Mockito.when(orderService.deniedOrderById(any(UUID.class),any(HttpServletRequest.class)))
				.thenThrow(new RequestException());
		UUID uuid =  UUID.randomUUID();
		mockMvc.perform(post("/kitchen-service/order/{id}/denied", uuid).with(csrf())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
	}

	@Test
	@WithMockUser(authorities = {"SCOPE_message.read"})
	@SneakyThrows
	void testAcceptedOrderByIdIsOk() {
		Mockito.when(orderService.acceptedOrderById(any(UUID.class),any(HttpServletRequest.class)))
				.thenReturn(new OrderDto());
		UUID uuid =  UUID.randomUUID();
		mockMvc.perform(post("/kitchen-service/order/{id}/accepted", uuid).with(csrf())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(new OrderDto())));
	}

	@Test
	@WithMockUser(authorities = {"SCOPE_message.read"})
	@SneakyThrows
	void testAcceptedOrderByIdIfBadStatus() {
		Mockito.when(orderService.acceptedOrderById(any(UUID.class),any(HttpServletRequest.class)))
				.thenThrow(new RequestException());
		UUID uuid =  UUID.randomUUID();
		mockMvc.perform(post("/kitchen-service/order/{id}/accepted", uuid).with(csrf())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
	}


	@Test
	@WithMockUser(authorities = {"SCOPE_message.read"})
	@SneakyThrows
	void testCompleteOrderByIdIsOk() {
		Mockito.when(orderService.completeOrderById(any(UUID.class),any(HttpServletRequest.class)))
				.thenReturn(new OrderDto());
		UUID uuid =  UUID.randomUUID();
		mockMvc.perform(post("/kitchen-service/order/{id}/complete", uuid).with(csrf())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(new OrderDto())));
	}

	@Test
	@WithMockUser(authorities = {"SCOPE_message.read"})
	@SneakyThrows
	void testCompleteOrderByIdIfBadStatus() {
		Mockito.when(orderService.completeOrderById(any(UUID.class),any(HttpServletRequest.class)))
				.thenThrow(new RequestException());
		UUID uuid =  UUID.randomUUID();
		mockMvc.perform(post("/kitchen-service/order/{id}/complete", uuid).with(csrf())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
	}

	@Test
	@WithMockUser(authorities = {"SCOPE_message.read"})
	@SneakyThrows
	void testConfirmCourierIsOk() {
		Mockito.when(orderService.confirmCourier(any(ConfirmCourierDto.class),any(HttpServletRequest.class)))
				.thenReturn(new OrderDto());
		UUID uuid =  UUID.randomUUID();
		mockMvc.perform(post("/kitchen-service/order/courier/confirm", uuid).with(csrf())
						.content(getJsonFromObject(new ConfirmCourierDto()))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(new OrderDto())));
	}

	@Test
	@WithMockUser(authorities = {"SCOPE_message.read"})
	@SneakyThrows
	void testConfirmCourierIfNotCourier() {
		Mockito.when(orderService.confirmCourier(any(ConfirmCourierDto.class),any(HttpServletRequest.class)))
				.thenThrow(new ResourceNotFoundException());
		UUID uuid =  UUID.randomUUID();
		mockMvc.perform(post("/kitchen-service/order/courier/confirm", uuid).with(csrf())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
	}

	@SneakyThrows
	protected String getJsonFromObject(Object obj) {
		return objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(obj);
	}

}

package ru.clevertec.house.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.clevertec.house.config.PostgresSqlContainerInitializer;
import ru.clevertec.house.config.auth.TokenProvider;
import ru.clevertec.house.exception.EntityNotFoundException;
import ru.clevertec.house.model.dto.HouseDto;
import ru.clevertec.house.model.dto.PersonDto;
import ru.clevertec.house.service.HouseHistoryService;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class HouseHistoryControllerTest extends PostgresSqlContainerInitializer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HouseHistoryService houseHistoryService;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TokenProvider tokenProvider;

    @Test
    public void getAllPersonResidingInHouseShouldReturnExpectedListPersonDtoAndStatus200() throws Exception {
        UUID uuid = UUID.fromString("9dd06f39-dba5-4533-8472-f1d7be435491");

        List<PersonDto> expected = houseHistoryService.getAllPersonResidingInHouse(uuid);

        mvc.perform(get("/api/history/residing/" + uuid))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void getAllPersonResidingInHouseShouldReturnExceptionAndStatus404() throws Exception {
        UUID uuid = UUID.fromString("9dd06f39-dba5-4533-8472-f1d7be434491");

        mvc.perform(get("/api/history/residing/" + uuid))
                .andExpect(status().isNotFound())
                .andExpect(MvcResult::getResolvedException).getClass().equals(EntityNotFoundException.class);
    }

    @Test
    public void getAllPersonOwnedHouseShouldReturnExpectedListPersonDtoAndStatus200() throws Exception {
        UUID uuid = UUID.fromString("9dd06f39-dba5-4533-8472-f1d7be435491");

        List<PersonDto> expected = houseHistoryService.getAllPersonOwnedHouse(uuid);

        mvc.perform(get("/api/history/owned/" + uuid))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void getAllPersonOwnedHouseShouldReturnExceptionAndStatus404() throws Exception {
        UUID uuid = UUID.fromString("9dd06f39-dba5-4533-8472-f1d7be434491");

        mvc.perform(get("/api/history/owned/" + uuid))
                .andExpect(status().isNotFound())
                .andExpect(MvcResult::getResolvedException).getClass().equals(EntityNotFoundException.class);
    }

    @Test
    public void getAllHousesByPersonShouldReturnExpectedListHouseDtoAndStatus200() throws Exception {
        UUID uuid = UUID.fromString("faa3c8d8-b6f8-4100-b253-3cd453a03da7");

        List<HouseDto> expected = houseHistoryService.getAllHousesByPerson(uuid);

        mvc.perform(get("/api/history/resided/" + uuid))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void getAllHousesByPersonShouldReturnExceptionAndStatus404() throws Exception {
        UUID uuid = UUID.fromString("faa3c8d8-b6f8-4100-b253-5cd453a03da7");

        mvc.perform(get("/api/history/resided/" + uuid))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult -> Objects.requireNonNull(mvcResult.getResolvedException()).getClass().equals(EntityNotFoundException.class));
    }

    @Test
    public void getAllHousesOwnedByPersonShouldReturnExpectedListHouseDtoAndStatus200() throws Exception {
        UUID uuid = UUID.fromString("faa3c8d8-b6f8-4100-b253-3cd453a03da7");

        List<HouseDto> expected = houseHistoryService.getAllHousesOwnedByPerson(uuid);

        mvc.perform(get("/api/history/belonged/" + uuid))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void getAllHousesOwnedByPersonShouldReturnExceptionAndStatus404() throws Exception {
        UUID uuid = UUID.fromString("faa3c8d8-b6f8-4170-b253-3cd453a03da7");

        mvc.perform(get("/api/history/belonged/" + uuid))
                .andExpect(status().isNotFound())
                .andExpect(MvcResult::getResolvedException).getClass().equals(EntityNotFoundException.class);
    }
}

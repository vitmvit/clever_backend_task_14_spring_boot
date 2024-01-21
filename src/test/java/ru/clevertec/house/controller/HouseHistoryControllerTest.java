package ru.clevertec.house.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.house.exception.EntityNotFoundException;
import ru.clevertec.house.model.dto.HouseDto;
import ru.clevertec.house.model.dto.PersonDto;
import ru.clevertec.house.service.HouseHistoryService;
import ru.clevertec.house.util.HouseHistoryTestBuilder;
import ru.clevertec.house.util.HouseTestBuilder;
import ru.clevertec.house.util.PersonTestBuilder;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HouseHistoryController.class)
public class HouseHistoryControllerTest {

    @Autowired
    MockMvc mvc;
    @MockBean
    HouseHistoryService houseHistoryService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllPersonResidingInHouseShouldReturnExpectedListPersonDtoAndStatus200() throws Exception {
        List<PersonDto> expected = PersonTestBuilder.builder().build().getListPersonDto();
        UUID uuid = HouseHistoryTestBuilder.builder().build().getUuid();

        when(houseHistoryService.getAllPersonResidingInHouse(uuid)).thenReturn(expected);

        mvc.perform(get("/api/history/residing/" + uuid))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void getAllPersonResidingInHouseShouldReturnExceptionAndStatus404() throws Exception {
        UUID uuid = HouseHistoryTestBuilder.builder().build().getUuid();

        when(houseHistoryService.getAllPersonResidingInHouse(any())).thenThrow(new EntityNotFoundException());

        mvc.perform(get("/api/history/residing/" + uuid))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(EntityNotFoundException.class));
    }

    @Test
    public void getAllPersonOwnedHouseShouldReturnExpectedListPersonDtoAndStatus200() throws Exception {
        List<PersonDto> expected = PersonTestBuilder.builder().build().getListPersonDto();
        UUID uuid = HouseHistoryTestBuilder.builder().build().getUuid();

        when(houseHistoryService.getAllPersonOwnedHouse(uuid)).thenReturn(expected);

        mvc.perform(get("/api/history/owned/" + uuid))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void getAllPersonOwnedHouseShouldReturnExceptionAndStatus404() throws Exception {
        UUID uuid = HouseHistoryTestBuilder.builder().build().getUuid();

        when(houseHistoryService.getAllPersonOwnedHouse(any())).thenThrow(new EntityNotFoundException());

        mvc.perform(get("/api/history/owned/" + uuid))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(EntityNotFoundException.class));
    }

    @Test
    public void getAllHousesByPersonShouldReturnExpectedListHouseDtoAndStatus200() throws Exception {
        List<HouseDto> expected = HouseTestBuilder.builder().build().getListHouseDto();
        UUID uuid = HouseHistoryTestBuilder.builder().build().getUuid();

        when(houseHistoryService.getAllHousesByPerson(uuid)).thenReturn(expected);

        mvc.perform(get("/api/history/resided/" + uuid))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void getAllHousesByPersonShouldReturnExceptionAndStatus404() throws Exception {
        UUID uuid = HouseHistoryTestBuilder.builder().build().getUuid();

        when(houseHistoryService.getAllHousesByPerson(any())).thenThrow(new EntityNotFoundException());

        mvc.perform(get("/api/history/resided/" + uuid))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(EntityNotFoundException.class));
    }

    @Test
    public void getAllHousesOwnedByPersonShouldReturnExpectedListHouseDtoAndStatus200() throws Exception {
        List<HouseDto> expected = HouseTestBuilder.builder().build().getListHouseDto();
        UUID uuid = HouseHistoryTestBuilder.builder().build().getUuid();

        when(houseHistoryService.getAllHousesOwnedByPerson(uuid)).thenReturn(expected);

        mvc.perform(get("/api/history/belonged/" + uuid))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void getAllHousesOwnedByPersonShouldReturnExceptionAndStatus404() throws Exception {
        UUID uuid = HouseHistoryTestBuilder.builder().build().getUuid();

        when(houseHistoryService.getAllHousesOwnedByPerson(any())).thenThrow(new EntityNotFoundException());

        mvc.perform(get("/api/history/belonged/" + uuid))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(EntityNotFoundException.class));
    }
}

package ru.clevertec.house.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.house.exception.EntityNotFoundException;
import ru.clevertec.house.model.dto.HouseDto;
import ru.clevertec.house.model.dto.PersonDto;
import ru.clevertec.house.model.dto.create.HouseCreateDto;
import ru.clevertec.house.model.dto.update.HouseUpdateDto;
import ru.clevertec.house.service.HouseService;
import ru.clevertec.house.util.HouseTestBuilder;
import ru.clevertec.house.util.PersonTestBuilder;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.clevertec.house.constant.Constant.LIMIT;
import static ru.clevertec.house.constant.Constant.OFFSET;

@WebMvcTest(HouseController.class)
public class HouseControllerTest {

    @Autowired
    MockMvc mvc;
    @MockBean
    HouseService houseService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getByUuidShouldReturnExpectedHouseDtoAndStatus200() throws Exception {
        HouseDto expected = HouseTestBuilder.builder().build().buildHouseDto();

        when(houseService.getByUuid(expected.getUuid())).thenReturn(expected);

        mvc.perform(get("/api/houses/" + expected.getUuid()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value(expected.getUuid().toString()))
                .andExpect(jsonPath("$.area").value(expected.getArea()))
                .andExpect(jsonPath("$.country").value(expected.getCountry()))
                .andExpect(jsonPath("$.city").value(expected.getCity()))
                .andExpect(jsonPath("$.street").value(expected.getStreet()))
                .andExpect(jsonPath("$.number").value(expected.getNumber()));
    }

    @Test
    public void getByUuidShouldReturnExceptionAndStatus404() throws Exception {
        UUID uuid = HouseTestBuilder.builder().build().getUuid();

        when(houseService.getByUuid(any())).thenThrow(new EntityNotFoundException());

        mvc.perform(get("/api/houses/" + uuid))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(EntityNotFoundException.class));
    }

    @Test
    public void getAllShouldReturnExpectedPageHouseDtoAndStatus200() throws Exception {
        List<HouseDto> listHouseDto = HouseTestBuilder.builder().build().getListHouseDto();
        Page<HouseDto> houseDtoPage = new PageImpl<>(listHouseDto, PageRequest.of(OFFSET, LIMIT), listHouseDto.size());

        given(houseService.getAll(OFFSET, LIMIT)).willReturn(houseDtoPage);

        mvc.perform(get("/api/houses?page=" + OFFSET + "&size=" + LIMIT)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllResidentsShouldReturnExpectedListPersonDtoAndStatus200() throws Exception {
        List<PersonDto> expected = PersonTestBuilder.builder().build().getListPersonDto();
        UUID uuid = HouseTestBuilder.builder().build().getUuid();

        when(houseService.getAllResidents(uuid)).thenReturn(expected);

        mvc.perform(get("/api/houses/residents/" + uuid))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void createShouldReturnExpectedHouseDtoAndStatus201() throws Exception {
        HouseDto expected = HouseTestBuilder.builder().build().buildHouseDto();
        HouseCreateDto houseCreateDto = HouseTestBuilder.builder().build().buildHouseCreateDto();

        when(houseService.create(any())).thenReturn(expected);

        mvc.perform(post("/api/houses")
                        .content(objectMapper.writeValueAsString(houseCreateDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void updateShouldReturnExpectedHouseDtoAndStatus200() throws Exception {
        HouseDto expected = HouseTestBuilder.builder().build().buildHouseDto();
        HouseUpdateDto houseUpdateDto = HouseTestBuilder.builder().build().buildHouseUpdateDto();

        when(houseService.update(any())).thenReturn(expected);
        when(houseService.getByUuid(any())).thenReturn(expected);

        mvc.perform(put("/api/houses")
                        .content(objectMapper.writeValueAsString(houseUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value(expected.getUuid().toString()))
                .andExpect(jsonPath("$.area").value(expected.getArea()));
    }

    @Test
    public void deleteShouldReturnStatus204() throws Exception {
        HouseDto expected = HouseTestBuilder.builder().build().buildHouseDto();

        when(houseService.getByUuid(any())).thenReturn(expected);

        mvc.perform(delete("/api/houses/" + expected.getUuid()))
                .andExpect(status().isNoContent());
    }
}

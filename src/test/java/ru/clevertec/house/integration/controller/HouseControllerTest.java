package ru.clevertec.house.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.clevertec.house.config.PostgresSqlContainerInitializer;
import ru.clevertec.house.config.auth.TokenProvider;
import ru.clevertec.house.exception.EntityNotFoundException;
import ru.clevertec.house.model.dto.HouseDto;
import ru.clevertec.house.model.dto.PersonDto;
import ru.clevertec.house.model.dto.update.HouseUpdateDto;
import ru.clevertec.house.service.HouseService;
import ru.clevertec.house.util.HouseTestBuilder;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.clevertec.house.constant.Constant.LIMIT;
import static ru.clevertec.house.constant.Constant.OFFSET;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class HouseControllerTest extends PostgresSqlContainerInitializer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HouseService houseService;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TokenProvider tokenProvider;

    @Test
    void getByUuidShouldReturnExpectedHouseDtoAndStatus200() throws Exception {
        UUID uuid = UUID.fromString("e9ae795e-cc7c-48d8-9621-1a441deff256");

        HouseDto expected = houseService.getByUuid(uuid);

        mvc.perform(get("/api/houses/" + uuid))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }


    @Test
    public void getByUuidShouldReturnExceptionAndStatus404() throws Exception {
        UUID uuid = UUID.fromString("e9ae795e-cc7c-48d8-9621-1a441deff956");

        mvc.perform(get("/api/houses/" + uuid))
                .andExpect(status().isNotFound())
                .andExpect(MvcResult::getResolvedException).getClass().equals(EntityNotFoundException.class);
    }

    @Test
    public void getAllShouldReturnExpectedPageHouseDtoAndStatus200() throws Exception {
        mvc.perform(get("/api/houses?page=" + OFFSET + "&size=" + LIMIT)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllResidentsShouldReturnExpectedListPersonDtoAndStatus200() throws Exception {
        UUID uuid = UUID.fromString("835915e7-2402-48a4-8f60-1364c6d99ed2");

        List<PersonDto> expected = houseService.getAllResidents(uuid);

        mvc.perform(get("/api/houses/residents/" + uuid))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void updateShouldReturnExpectedHouseDtoAndStatus200() throws Exception {
        HouseUpdateDto houseUpdateDto = HouseTestBuilder.builder().build().buildHouseUpdateDto();
        houseUpdateDto.setUuid(UUID.fromString("835915e7-2402-48a4-8f60-1364c6d99ed2"));

        HouseDto expected = houseService.update(houseUpdateDto);

        mvc.perform(put("/api/houses")
                        .content(objectMapper.writeValueAsString(houseUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void patchShouldReturnExpectedHouseDtoAndStatus200() throws Exception {
        HouseUpdateDto houseUpdateDto = HouseTestBuilder.builder().build().buildHouseUpdateDto();
        houseUpdateDto.setUuid(UUID.fromString("5e9832cf-3a74-40ae-9dae-c70f47f08804"));

        HouseDto expected = houseService.patch(houseUpdateDto);

        mvc.perform(patch("/api/houses")
                        .content(objectMapper.writeValueAsString(houseUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void deleteShouldReturnStatus204() throws Exception {
        UUID uuid = UUID.fromString("5e9832cf-3a74-40ae-9dae-c70f47f08804");

        houseService.delete(uuid);

        mvc.perform(delete("/api/houses/" + uuid))
                .andExpect(status().isNoContent());
    }
}

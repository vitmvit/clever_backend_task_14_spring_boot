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
import ru.clevertec.house.model.dto.create.PersonCreateDto;
import ru.clevertec.house.model.dto.update.PersonUpdateDto;
import ru.clevertec.house.service.PersonService;
import ru.clevertec.house.util.HouseTestBuilder;
import ru.clevertec.house.util.PersonTestBuilder;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    MockMvc mvc;
    @MockBean
    PersonService personService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getByUuidShouldReturnExpectedHouseDtoAndStatus200() throws Exception {
        PersonDto expected = PersonTestBuilder.builder().build().buildPersonDto();

        when(personService.getByUUID(expected.getUuid())).thenReturn(expected);

        mvc.perform(get("/api/persons/" + expected.getUuid()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value(expected.getUuid().toString()))
                .andExpect(jsonPath("$.name").value(expected.getName()))
                .andExpect(jsonPath("$.surname").value(expected.getSurname()))
                .andExpect(jsonPath("$.sex").value(expected.getSex().toString()));
    }

    @Test
    public void getByUuidShouldReturnExceptionAndStatus404() throws Exception {
        UUID uuid = PersonTestBuilder.builder().build().getUuid();

        when(personService.getByUUID(any())).thenThrow(new EntityNotFoundException());

        mvc.perform(get("/api/persons/" + uuid))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(EntityNotFoundException.class));
    }

    @Test
    public void getAllShouldReturnExpectedPageHouseDto() throws Exception {
        int offset = 0;
        int limit = 3;
        List<PersonDto> listPersonDto = PersonTestBuilder.builder().build().getListPersonDto();
        Page<PersonDto> personDtoPage = new PageImpl<>(listPersonDto, PageRequest.of(offset, limit), listPersonDto.size());

        given(personService.getAll(offset, limit)).willReturn(personDtoPage);

        mvc.perform(get("/api/persons?page=" + offset + "&size=" + limit)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllResidentsShouldReturnExpectedListPersonDtoAndStatus200() throws Exception {
        List<HouseDto> expected = HouseTestBuilder.builder().build().getListHouseDto();
        UUID uuid = HouseTestBuilder.builder().build().getUuid();

        when(personService.getAllHouses(uuid)).thenReturn(expected);

        mvc.perform(get("/api/persons/houses/" + uuid))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void createShouldReturnExpectedHouseDtoAndStatus201() throws Exception {
        PersonDto expected = PersonTestBuilder.builder().build().buildPersonDto();
        PersonCreateDto personCreateDto = PersonTestBuilder.builder().build().buildPersonCreateDto();

        when(personService.create(any())).thenReturn(expected);

        mvc.perform(post("/api/persons")
                        .content(objectMapper.writeValueAsString(personCreateDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void updateShouldReturnExpectedHouseDtoAndStatus200() throws Exception {
        PersonDto expected = PersonTestBuilder.builder().build().buildPersonDto();
        PersonUpdateDto personUpdateDto = PersonTestBuilder.builder().build().buildPersonUpdateDto();

        when(personService.update(any())).thenReturn(expected);
        when(personService.getByUUID(any())).thenReturn(expected);

        mvc.perform(put("/api/persons")
                        .content(objectMapper.writeValueAsString(personUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value(expected.getUuid().toString()))
                .andExpect(jsonPath("$.name").value(expected.getName()))
                .andExpect(jsonPath("$.surname").value(expected.getSurname()));
    }

    @Test
    public void deleteShouldReturnStatus204() throws Exception {
        PersonDto expected = PersonTestBuilder.builder().build().buildPersonDto();

        when(personService.getByUUID(any())).thenReturn(expected);

        mvc.perform(delete("/api/persons/" + expected.getUuid()))
                .andExpect(status().isNoContent());
    }
}

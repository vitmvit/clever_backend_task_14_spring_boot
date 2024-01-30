package ru.clevertec.house.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.house.config.auth.TokenProvider;
import ru.clevertec.house.exception.EntityNotFoundException;
import ru.clevertec.house.model.dto.HouseDto;
import ru.clevertec.house.model.dto.PersonDto;
import ru.clevertec.house.model.dto.create.PersonCreateDto;
import ru.clevertec.house.model.dto.update.PersonUpdateDto;
import ru.clevertec.house.service.PersonService;
import ru.clevertec.house.service.UserService;
import ru.clevertec.house.util.HouseTestBuilder;
import ru.clevertec.house.util.PersonTestBuilder;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.clevertec.house.constant.Constant.LIMIT;
import static ru.clevertec.house.constant.Constant.OFFSET;

@WebMvcTest(PersonController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PersonControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PersonService personService;

    @MockBean
    private UserService userService;

    @MockBean
    private TokenProvider tokenProvider;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getByUuidShouldReturnExpectedHouseDtoAndStatus200() throws Exception {
        PersonDto expected = PersonTestBuilder.builder().build().buildPersonDto();

        when(personService.getByUuid(expected.getUuid())).thenReturn(expected);

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

        when(personService.getByUuid(any())).thenThrow(new EntityNotFoundException());

        mvc.perform(get("/api/persons/" + uuid))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult -> Objects.requireNonNull(mvcResult.getResolvedException()).getClass().equals(EntityNotFoundException.class));
    }

    @Test
    public void getAllShouldReturnExpectedPageHouseDto() throws Exception {
        List<PersonDto> listPersonDto = PersonTestBuilder.builder().build().getListPersonDto();
        Page<PersonDto> personDtoPage = new PageImpl<>(listPersonDto, PageRequest.of(OFFSET, LIMIT), listPersonDto.size());

        given(personService.getAll(OFFSET, LIMIT)).willReturn(personDtoPage);

        mvc.perform(get("/api/persons?page=" + OFFSET + "&size=" + LIMIT)
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
        when(personService.getByUuid(any())).thenReturn(expected);

        mvc.perform(put("/api/persons")
                        .content(objectMapper.writeValueAsString(personUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value(expected.getUuid().toString()))
                .andExpect(jsonPath("$.name").value(expected.getName()))
                .andExpect(jsonPath("$.surname").value(expected.getSurname()));
    }

    @Test
    public void patchShouldReturnExpectedHouseDtoAndStatus200() throws Exception {
        PersonDto expected = PersonTestBuilder.builder().build().buildPersonDto();
        PersonUpdateDto personUpdateDto = PersonTestBuilder.builder().build().buildPersonUpdateDto();

        when(personService.patch(any())).thenReturn(expected);
        when(personService.getByUuid(any())).thenReturn(expected);

        mvc.perform(patch("/api/persons")
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

        when(personService.getByUuid(any())).thenReturn(expected);

        mvc.perform(delete("/api/persons/" + expected.getUuid()))
                .andExpect(status().isNoContent());
    }
}

package ru.clevertec.house.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.house.config.auth.TokenProvider;
import ru.clevertec.house.exception.EmptyListException;
import ru.clevertec.house.model.dto.HouseDto;
import ru.clevertec.house.model.dto.PersonDto;
import ru.clevertec.house.service.HouseService;
import ru.clevertec.house.service.PersonService;
import ru.clevertec.house.service.UserService;
import ru.clevertec.house.util.HouseTestBuilder;
import ru.clevertec.house.util.PersonTestBuilder;

import java.util.List;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.clevertec.house.constant.Constant.FRAGMENT_CITY;
import static ru.clevertec.house.constant.Constant.FRAGMENT_SURNAME;

@WebMvcTest(SearchController.class)
@AutoConfigureMockMvc(addFilters = false)
public class SearchControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PersonService personService;

    @MockBean
    private HouseService houseService;

    @MockBean
    private UserService userService;

    @MockBean
    private TokenProvider tokenProvider;

    @Test
    public void searchPersonBySurnameShouldReturnListPersonDtoAndStatus200() throws Exception {
        List<PersonDto> personDtoList = PersonTestBuilder.builder().build().getListPersonDto();

        when(personService.searchBySurname(FRAGMENT_SURNAME)).thenReturn(personDtoList);

        mvc.perform(get("/api/search/person/" + FRAGMENT_SURNAME))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(personDtoList)));
    }

    @Test
    public void searchPersonBySurnameShouldReturnExceptionAndStatus404() throws Exception {
        when(personService.searchBySurname(any())).thenThrow(new EmptyListException());

        mvc.perform(get("/api/search/person/" + FRAGMENT_SURNAME))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult -> Objects.requireNonNull(mvcResult.getResolvedException()).getClass().equals(EmptyListException.class));
    }

    @Test
    public void searchHouseByCityShouldReturnListHouseDtoAndStatus200() throws Exception {
        List<HouseDto> personDtoList = HouseTestBuilder.builder().build().getListHouseDto();

        when(houseService.searchByCity(FRAGMENT_CITY)).thenReturn(personDtoList);

        mvc.perform(get("/api/search/house/" + FRAGMENT_CITY))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(personDtoList)));
    }

    @Test
    public void searchHouseByCityShouldReturnExceptionAndStatus404() throws Exception {
        when(houseService.searchByCity(any())).thenThrow(new EmptyListException());

        mvc.perform(get("/api/search/house/" + FRAGMENT_CITY))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult -> Objects.requireNonNull(mvcResult.getResolvedException()).getClass().equals(EmptyListException.class));
    }
}

package ru.clevertec.house.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.clevertec.house.converter.HouseConverter;
import ru.clevertec.house.converter.PersonConverter;
import ru.clevertec.house.exception.EntityNotFoundException;
import ru.clevertec.house.model.dto.HouseDto;
import ru.clevertec.house.model.dto.PersonDto;
import ru.clevertec.house.model.dto.create.HouseCreateDto;
import ru.clevertec.house.model.dto.update.HouseUpdateDto;
import ru.clevertec.house.model.entity.House;
import ru.clevertec.house.model.entity.parent.BaseModel;
import ru.clevertec.house.model.entity.parent.UuidModel;
import ru.clevertec.house.repository.HouseRepository;
import ru.clevertec.house.service.impl.HouseServiceImpl;
import ru.clevertec.house.util.HouseTestBuilder;
import ru.clevertec.house.util.Patcher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static ru.clevertec.house.constant.Constant.LIMIT;
import static ru.clevertec.house.constant.Constant.OFFSET;

@ExtendWith(MockitoExtension.class)
public class HouseServiceTest {

    @Mock
    private HouseRepository houseRepository;

    @Mock
    private HouseConverter houseConverter;

    @Mock
    private PersonConverter personConverter;

    @Mock
    private Patcher patcher;

    @InjectMocks
    private HouseServiceImpl houseService;

    @Captor
    private ArgumentCaptor<House> argumentCaptor;


    @Test
    void getByUuidShouldReturnExpectedHouseWhenFound() {
        House expected = HouseTestBuilder.builder().build().buildHouse();
        HouseDto houseDto = HouseTestBuilder.builder().build().buildHouseDto();
        UUID uuid = expected.getUuid();

        when(houseRepository.findHouseByUuid(uuid)).thenReturn(Optional.of(expected));
        when(houseConverter.convert(expected)).thenReturn(houseDto);

        HouseDto actual = houseService.getByUuid(uuid);

        assertThat(actual)
                .hasFieldOrPropertyWithValue(UuidModel.Fields.uuid, expected.getUuid())
                .hasFieldOrPropertyWithValue(House.Fields.area, expected.getArea())
                .hasFieldOrPropertyWithValue(House.Fields.country, expected.getCountry())
                .hasFieldOrPropertyWithValue(House.Fields.city, expected.getCity())
                .hasFieldOrPropertyWithValue(House.Fields.number, expected.getNumber());
    }

    @Test
    void getByUuidShouldThrowEntityNotFoundExceptionWhenHouseNotFound() {
        var exception = assertThrows(Exception.class, () -> houseService.getByUuid(null));
        assertEquals(exception.getClass(), EntityNotFoundException.class);
    }

    @Test
    void getAllShouldReturnExpectedListHouses() {
        var page = mock(Page.class);
        when(houseRepository.findAll(any(PageRequest.class))).thenReturn(page);

        houseService.getAll(OFFSET, LIMIT);

        verify(houseRepository).findAll(any(PageRequest.class));
        verifyNoMoreInteractions(houseRepository);
    }

    @Test
    void getAllShouldReturnEmptyPageWhenEmptyPageHouses() {
        when(houseRepository.findAll(PageRequest.of(OFFSET, LIMIT))).thenReturn(Page.empty());

        Page<HouseDto> actualList = houseService.getAll(OFFSET, LIMIT);

        assertEquals(0, actualList.getTotalElements());
        verify(houseRepository, times(1)).findAll(PageRequest.of(OFFSET, LIMIT));
    }

    @Test
    void getAllResidentsShouldReturnExpectedListPersonDto() {
        UUID uuid = HouseTestBuilder.builder().build().getUuid();
        House house = HouseTestBuilder.builder().build().buildHouse();

        when(houseRepository.findHouseByUuid(uuid)).thenReturn(Optional.of(house));

        List<PersonDto> result = houseService.getAllResidents(uuid);

        assertEquals(2, result.size());
        verify(houseRepository).findHouseByUuid(uuid);
    }

    @Test
    void createShouldInvokeRepositoryWithoutHouseId() {
        House houseToSave = HouseTestBuilder.builder().withId(null).build().buildHouse();
        House expected = HouseTestBuilder.builder().build().buildHouse();
        HouseCreateDto dto = HouseTestBuilder.builder().build().buildHouseCreateDto();

        doReturn(expected).when(houseRepository).save(houseToSave);
        when(houseConverter.convert(dto)).thenReturn(houseToSave);

        houseService.create(dto);

        verify(houseRepository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).hasFieldOrPropertyWithValue(BaseModel.Fields.id, null);
    }


    @Test
    void updateShouldCallsMergeAndSaveWhenHouseFound() {
        UUID uuid = HouseTestBuilder.builder().build().getUuid();
        HouseUpdateDto dto = HouseTestBuilder.builder().build().buildHouseUpdateDto();
        House house = new House();

        when(houseRepository.findHouseByUuid(uuid)).thenReturn(Optional.of(house));
        houseService.update(dto);

        verify(houseRepository, times(1)).findHouseByUuid(uuid);
        verify(houseConverter, times(1)).merge(argumentCaptor.capture(), eq(dto));
        assertSame(house, argumentCaptor.getValue());
        verify(houseRepository, times(1)).save(house);
    }

    @Test
    void updateShouldThrowEntityNotFoundExceptionWhenHouseNotFound() {
        UUID uuid = HouseTestBuilder.builder().build().getUuid();
        HouseUpdateDto dto = HouseTestBuilder.builder().build().buildHouseUpdateDto();

        when(houseRepository.findHouseByUuid(uuid)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> houseService.update(dto));
        verify(houseRepository, times(1)).findHouseByUuid(uuid);
    }

    @Test
    void patchShouldCallsMergeAndSaveWhenHouseFound() {
        UUID uuid = HouseTestBuilder.builder().build().getUuid();
        HouseUpdateDto dto = HouseTestBuilder.builder().build().buildHouseUpdateDto();
        House house = new House();

        when(houseRepository.findHouseByUuid(uuid)).thenReturn(Optional.of(house));
        houseService.patch(dto);

        verify(houseRepository, times(1)).findHouseByUuid(uuid);
        verify(houseConverter, times(1)).convert(argumentCaptor.capture());
        assertSame(house, argumentCaptor.getValue());
        verify(houseRepository, times(1)).save(house);
    }

    @Test
    void patchShouldThrowEntityNotFoundExceptionWhenHouseNotFound() {
        UUID uuid = HouseTestBuilder.builder().build().getUuid();
        HouseUpdateDto dto = HouseTestBuilder.builder().build().buildHouseUpdateDto();

        when(houseRepository.findHouseByUuid(uuid)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> houseService.patch(dto));
        verify(houseRepository, times(1)).findHouseByUuid(uuid);
    }

    @Test
    void delete() {
        UUID uuid = HouseTestBuilder.builder().build().getUuid();
        houseService.delete(uuid);
        verify(houseRepository).deleteHouseByUuid(uuid);
    }
}

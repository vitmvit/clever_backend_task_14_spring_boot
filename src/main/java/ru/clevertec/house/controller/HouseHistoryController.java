package ru.clevertec.house.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.house.model.dto.HouseDto;
import ru.clevertec.house.model.dto.PersonDto;
import ru.clevertec.house.service.HouseHistoryService;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/history")
public class HouseHistoryController {

    private final HouseHistoryService houseHistoryService;


//- GET для получения всех House где проживал Person

//    uuid person
//    uuid -> id или весь person
//    in house_history all house with type tenant -> List<Long>
//    и получаем список houses
//- GET для получения всех House которыми когда-либо владел Person
//3) getAllHousesByPerson()
//4) getAllHousesOwnedByPerson()

    // GET для получения всех Person когда-либо проживавших в доме
    @GetMapping("residing/{uuid}")
    List<PersonDto> getAllPersonResidingInHouse(@PathVariable("uuid") UUID houseUuid) {
        return houseHistoryService.getAllPersonResidingInHouse(houseUuid);
    }

    // GET для получения всех Person когда-либо владевших домом
    @GetMapping("owned/{uuid}")
    List<PersonDto> getAllPersonOwnedHouse(@PathVariable("uuid") UUID houseUuid) {
        return houseHistoryService.getAllPersonOwnedHouse(houseUuid);
    }

    // GET для получения всех House где проживал Person
    @GetMapping("resided/{uuid}")
    List<HouseDto> getAllHousesByPerson(@PathVariable("uuid") UUID personUuid) {
        return houseHistoryService.getAllHousesByPerson(personUuid);
    }

    // GET для получения всех House которыми когда-либо владел Person
    @GetMapping("belonged/{uuid}")
    List<HouseDto> getAllHousesOwnedByPerson(@PathVariable("uuid") UUID personUuid) {
        return houseHistoryService.getAllHousesOwnedByPerson(personUuid);
    }
}

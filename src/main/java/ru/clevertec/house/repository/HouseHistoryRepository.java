package ru.clevertec.house.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.house.constant.Type;
import ru.clevertec.house.model.entity.HouseHistory;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public interface HouseHistoryRepository extends JpaRepository<HouseHistory, Long> {

    List<HouseHistory> findAllByHouseIdAndType(Long houseId, Type type);

    /**
     * Метод перекрыт по причине создания записи на уровне триггера в бд
     */
    default HouseHistory save(HouseHistory entity) {
        throw new RuntimeException("Not implemented");
    }

    default Set<Long> findAllPersonIdByHouseIdAndType(Long houseId, Type type) {
        var houseHistoryList = findAllByHouseIdAndType(houseId, type);
        return houseHistoryList.stream().map(HouseHistory::getPersonId).collect(Collectors.toSet());
    }

    default Set<Long> findAllHouseIdByPersonIdAndType(Long personId, Type type) {
        var houseHistoryList = findAllByHouseIdAndType(personId, type);
        return houseHistoryList.stream().map(HouseHistory::getHouseId).collect(Collectors.toSet());
    }
}

package ru.clevertec.house.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.house.constant.Type;
import ru.clevertec.house.model.entity.HouseHistory;

import java.util.List;

@Repository
public interface HouseHistoryRepository extends JpaRepository<HouseHistory, Long> {

    List<Long> findPersonIdByHouseIdAndType(Long id, Type type);

    /**
     * Метод перекрыт по причине создания записи на уровне триггера в бд
     */
    default HouseHistory save(HouseHistory entity) {
        throw new RuntimeException("Not implemented");
    }
}

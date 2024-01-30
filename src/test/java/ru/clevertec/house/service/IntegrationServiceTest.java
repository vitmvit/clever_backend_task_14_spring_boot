//package ru.clevertec.house.service;
//
////Добавляем Integration тесты, чтобы кэш работал в многопоточной среде.
////        Делаем экзекутор на 6 потоков и параллельно вызываем сервисный слой (GET\POST\PUT\DELETE) и проверяем, что результат соответствует ожиданиям.
////        Используем H2 или *testcontainers
//
//import org.assertj.core.api.AssertionsForClassTypes;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import ru.clevertec.house.config.PostgresSqlContainerInitializer;
//import ru.clevertec.house.config.auth.TokenProvider;
//import ru.clevertec.house.model.dto.HouseDto;
//import ru.clevertec.house.model.dto.create.HouseCreateDto;
//import ru.clevertec.house.model.dto.parent.UuidDto;
//import ru.clevertec.house.model.dto.update.HouseUpdateDto;
//import ru.clevertec.house.repository.HouseRepository;
//import ru.clevertec.house.util.DelayGenerator;
//import ru.clevertec.house.util.HouseTestBuilder;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//import java.util.concurrent.*;
//import java.util.concurrent.atomic.AtomicReference;
//
//import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class IntegrationServiceTest extends PostgresSqlContainerInitializer {
//
//    private static final int THREAD_POOL = 1;
//
//    private final ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL);
//    private final DelayGenerator delayGenerator = DelayGenerator.getRandomDelayGenerator(
//            1, 1, TimeUnit.MILLISECONDS
//    );
//
//    @MockBean
//    private TokenProvider tokenProvider;
//
//    @Autowired
//    private HouseRepository houseRepository;
//
//    @Autowired
//    private HouseService houseService;
//
//    @Test
//    public void test() throws InterruptedException, ExecutionException {
//
//        UUID uuid = HouseTestBuilder.builder().build().buildHouse().getUuid();
//        HouseCreateDto houseRequestForCreate = HouseTestBuilder.builder().build().buildHouseCreateDto();
//        HouseUpdateDto houseRequestForUpdate = HouseTestBuilder.builder().build().buildHouseUpdateDto();
//        UUID uuidOfUpdatedHouse = houseRequestForUpdate.getUuid();
//
//        HouseDto expectedCreateHouseResponse = HouseTestBuilder.builder().build().buildHouseDto();
//        HouseDto expectedUpdateHouseResponse = HouseTestBuilder.builder().build().buildHouseDto();
//        HouseDto expectedGetHouseResponse = HouseTestBuilder.builder().build().buildHouseDto();
//
//
//        // Создаем список задач
//        // Создаем пул потоков с 6 потоками
//        ExecutorService executor = Executors.newFixedThreadPool(6);
//
//        // Создаем список задач
//        List<Callable<HouseDto>> tasks = new ArrayList<>();
//        tasks.add(() -> houseService.create(houseRequestForCreate));
//        tasks.add(() -> houseService.update(houseRequestForUpdate));
//        tasks.add(() -> houseService.getByUuid(uuid));
////        tasks.add(() -> houseService.delete(uuid));
//
//
//        // Запускаем задачи и получаем список Future, представляющих результаты методов
//        List<Future<HouseDto>> results = executor.invokeAll(tasks);
//
//        // Создаем AtomicReference для хранения результата
//        AtomicReference<HouseDto> methodResult1 = new AtomicReference<>();
//        AtomicReference<HouseDto> methodResult2 = new AtomicReference<>();
//        AtomicReference<HouseDto> methodResult3 = new AtomicReference<>();
//
//
//        // Обрабатываем результаты методов
//        methodResult1.set(results.get(0).get());
//        methodResult2.set(results.get(1).get());
//        methodResult3.set(results.get(2).get());
//
//
//        AssertionsForClassTypes.assertThat(methodResult1.get())
//                .hasFieldOrPropertyWithValue(HouseDto.Fields.country, expectedCreateHouseResponse.getCountry())
//                .hasFieldOrPropertyWithValue(HouseDto.Fields.area, expectedCreateHouseResponse.getArea())
//                .hasFieldOrPropertyWithValue(HouseDto.Fields.city, expectedCreateHouseResponse.getCity())
//                .hasFieldOrPropertyWithValue(HouseDto.Fields.street, expectedCreateHouseResponse.getStreet())
//                .hasFieldOrPropertyWithValue(HouseDto.Fields.number, expectedCreateHouseResponse.getNumber());
//
//        AssertionsForClassTypes.assertThat(methodResult2.get())
//                .hasFieldOrPropertyWithValue(UuidDto.Fields.uuid, expectedUpdateHouseResponse.getUuid())
//                .hasFieldOrPropertyWithValue(HouseDto.Fields.country, expectedUpdateHouseResponse.getCountry())
//                .hasFieldOrPropertyWithValue(HouseDto.Fields.area, expectedUpdateHouseResponse.getArea())
//                .hasFieldOrPropertyWithValue(HouseDto.Fields.city, expectedUpdateHouseResponse.getCity())
//                .hasFieldOrPropertyWithValue(HouseDto.Fields.street, expectedUpdateHouseResponse.getStreet())
//                .hasFieldOrPropertyWithValue(HouseDto.Fields.number, expectedUpdateHouseResponse.getNumber());
//
//        AssertionsForClassTypes.assertThat(methodResult3.get())
//                .hasFieldOrPropertyWithValue(UuidDto.Fields.uuid, expectedGetHouseResponse.getUuid())
//                .hasFieldOrPropertyWithValue(HouseDto.Fields.country, expectedGetHouseResponse.getCountry())
//                .hasFieldOrPropertyWithValue(HouseDto.Fields.area, expectedGetHouseResponse.getArea())
//                .hasFieldOrPropertyWithValue(HouseDto.Fields.city, expectedGetHouseResponse.getCity())
//                .hasFieldOrPropertyWithValue(HouseDto.Fields.street, expectedGetHouseResponse.getStreet())
//                .hasFieldOrPropertyWithValue(HouseDto.Fields.number, expectedGetHouseResponse.getNumber());
//
//        executor.shutdown();
//    }
//
//        @Test
//        public void shouldAssertAllHouseResponses () throws InterruptedException {
//
//            // given
//            UUID uuid = HouseTestBuilder.builder().build().buildHouse().getUuid();
//            HouseCreateDto houseRequestForCreate = HouseTestBuilder.builder().build().buildHouseCreateDto();
//            HouseUpdateDto houseRequestForUpdate = HouseTestBuilder.builder().build().buildHouseUpdateDto();
//            UUID uuidOfUpdatedHouse = houseRequestForUpdate.getUuid();
//
//            HouseDto expectedCreateHouseResponse = HouseTestBuilder.builder().build().buildHouseDto();
//            HouseDto expectedUpdateHouseResponse = HouseTestBuilder.builder().build().buildHouseDto();
//            HouseDto expectedGetHouseResponse = HouseTestBuilder.builder().build().buildHouseDto();
//
//
//            AtomicReference<HouseDto> createHouseResponse = new AtomicReference<>();
//            AtomicReference<HouseDto> updateHouseResponse = new AtomicReference<>();
//            AtomicReference<HouseDto> getHouseResponse = new AtomicReference<>();
//            AtomicReference<HouseDto> getUpdatedHouseResponse = new AtomicReference<>();
//
//            // when
//            List<Callable<Void>> tasks = new ArrayList<>();
//            tasks.add(() -> {
//                delayGenerator.delay();
//                createHouseResponse.set(houseService.create(houseRequestForCreate));
//                return null;
//            });
//            tasks.add(() -> {
//                delayGenerator.delay();
//                updateHouseResponse.set(houseService.update(houseRequestForUpdate));
//                return null;
//            });
//            tasks.add(() -> {
//                getHouseResponse.set(houseService.getByUuid(uuid));
//                return null;
//            });
//            tasks.add(() -> {
//                delayGenerator.delay();
//                getUpdatedHouseResponse.set(houseService.getByUuid(uuidOfUpdatedHouse));
//                return null;
//            });
//            tasks.add(() -> {
//                delayGenerator.delay();
//                houseService.delete(uuid);
//                return null;
//            });
//
//            List<Future<Void>> futures = executor.invokeAll(tasks);
//
//            HouseDto actualCreateHouseResponse = createHouseResponse.get();
//            HouseDto actualUpdateHouseResponse = updateHouseResponse.get();
//            HouseDto actualGetFirstHouseResponse = getHouseResponse.get();
//            HouseDto actualGetUpdatedHouseResponse = getUpdatedHouseResponse.get();
//
//            assertThat(actualCreateHouseResponse)
//                    .hasFieldOrPropertyWithValue(HouseDto.Fields.country, expectedCreateHouseResponse.getCountry())
//                    .hasFieldOrPropertyWithValue(HouseDto.Fields.area, expectedCreateHouseResponse.getArea())
//                    .hasFieldOrPropertyWithValue(HouseDto.Fields.city, expectedCreateHouseResponse.getCity())
//                    .hasFieldOrPropertyWithValue(HouseDto.Fields.street, expectedCreateHouseResponse.getStreet())
//                    .hasFieldOrPropertyWithValue(HouseDto.Fields.number, expectedCreateHouseResponse.getNumber());
//
//            assertThat(actualUpdateHouseResponse)
//                    .hasFieldOrPropertyWithValue(UuidDto.Fields.uuid, expectedUpdateHouseResponse.getUuid())
//                    .hasFieldOrPropertyWithValue(HouseDto.Fields.country, expectedUpdateHouseResponse.getCountry())
//                    .hasFieldOrPropertyWithValue(HouseDto.Fields.area, expectedUpdateHouseResponse.getArea())
//                    .hasFieldOrPropertyWithValue(HouseDto.Fields.city, expectedUpdateHouseResponse.getCity())
//                    .hasFieldOrPropertyWithValue(HouseDto.Fields.street, expectedUpdateHouseResponse.getStreet())
//                    .hasFieldOrPropertyWithValue(HouseDto.Fields.number, expectedUpdateHouseResponse.getNumber());
//
//            assertThat(actualGetFirstHouseResponse)
//                    .hasFieldOrPropertyWithValue(UuidDto.Fields.uuid, expectedGetHouseResponse.getUuid())
//                    .hasFieldOrPropertyWithValue(HouseDto.Fields.country, expectedGetHouseResponse.getCountry())
//                    .hasFieldOrPropertyWithValue(HouseDto.Fields.area, expectedGetHouseResponse.getArea())
//                    .hasFieldOrPropertyWithValue(HouseDto.Fields.city, expectedGetHouseResponse.getCity())
//                    .hasFieldOrPropertyWithValue(HouseDto.Fields.street, expectedGetHouseResponse.getStreet())
//                    .hasFieldOrPropertyWithValue(HouseDto.Fields.number, expectedGetHouseResponse.getNumber());
//
//            assertThat(actualGetUpdatedHouseResponse)
//                    .hasFieldOrPropertyWithValue(UuidDto.Fields.uuid, expectedUpdateHouseResponse.getUuid())
//                    .hasFieldOrPropertyWithValue(HouseDto.Fields.country, expectedUpdateHouseResponse.getCountry())
//                    .hasFieldOrPropertyWithValue(HouseDto.Fields.area, expectedUpdateHouseResponse.getArea())
//                    .hasFieldOrPropertyWithValue(HouseDto.Fields.city, expectedUpdateHouseResponse.getCity())
//                    .hasFieldOrPropertyWithValue(HouseDto.Fields.street, expectedUpdateHouseResponse.getStreet())
//                    .hasFieldOrPropertyWithValue(HouseDto.Fields.number, expectedUpdateHouseResponse.getNumber());
//        }
//    }

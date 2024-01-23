# clever_backend_task_14_spring_boot

## Задача

Реализация на Spring boot 3.2.*

1. Создать Web приложение учёта домов и жильцов
2. 3 сущности: House, Person, HouseHistory
3. Система должна предоставлять REST API для выполнения следующих операций:

- CRUD для House
    - В GET запросах не выводить информацию о Person
- CRUD для Person
    - В GET запросах не выводить информацию о House
- GET для всех Person проживающих в House
- GET для всех House, владельцем которых является Person
- Для GET операций использовать pagination (default size: 15)
- GET для получения всех Person когда-либо проживавших в доме
- GET для получения всех Person когда-либо владевших домом
- GET для получения всех House где проживал Person
- GET для получения всех House которыми когда-либо владел Person

### House:

1. У House обязаны быть поля id, uuid, area, country, city, street, number, create_date
2. House может иметь множество жильцов (0-n)
3. У House может быть множество владельцев (0-n)
4. create_date устанавливается один раз при создании

### Person:

1. У Person обязаны быть id, uuid, name, surname, sex, passport_series, passport_number, create_date, update_date
2. Person обязан жить только в одном доме и не может быть бездомным
3. Person не обязан владеть хоть одним домом и может владеть множеством домов
4. Сочетание passport_series и passport_number уникально
5. sex должен быть [Male, Female]
6. Все связи обеспечить через id
7. Не возвращать id пользователям сервисов, для этого предназначено поле uuid
8. create_date устанавливается один раз при создании
9. update_date устанавливается при создании и изменяется каждый раз, когда меняется информация о Person. При этом, если
   запрос не изменяет информации, поле не должно обновиться

### HouseHistory:

У HouseHistory обязаны быть (id, house_id, person_id, date, type)

1. type [OWNER, TENANT]
    - Создать свой тип данных в БД
    - Хранить как enum в коде
2. При смене места жительства добавляем запись в HouseHistory [type = TENANT], с текущей датой
3. При смене владельца, добавляем запись в HouseHistory [type = OWNER], с текущей датой
4.
    * Реализовать через триггер в БД
5.
    * Если используется миграция, дописать новый changeset, а не исправлять существующие.

Добавляем кэш из задания по рефлексии на сервисный слой House и Person.

1. Добавляем Integration тесты, чтобы кэш работал в многопоточной среде.
2. Делаем экзекутор на 6 потоков и параллельно вызываем сервисный слой (GET\POST\PUT\DELETE) и проверяем, что результат
   соответствует ожиданиям.
3. Используем H2 или *testcontainers

* Добавляем swagger (OPEN API)

** Добавляем starter:

1. ** Реализовываем мультипроект
2. ** Реализовываем свой cache-starter (из задания по рефлексии)
3. ** Добавляем таску с build в mavenLocal
4. ** Добавляем стартер в основное приложение, через mavelLocal
5. ** Удаляем все классы из основного приложения

### Примечание:

1. Ограничения и нормализацию сделать на своё усмотрение
2. Поля представлены для хранения в базе данных. В коде могут отличаться

### Обязательно:

3. Конфигурационный файл: application.yml
4. Скрипты для создания таблиц должны лежать в classpath:db/
5. create_date, update_date - возвращать в формате ISO-8601 (https://en.wikipedia.org/wiki/ISO_8601). Пример:
   2018-08-29T06:12:15.156.
6. Добавить 5 домов и 10 жильцов. Один дом без жильцов и как минимум в 1 доме больше 1 владельца
7. Использовать репозиторий с JDBC Template для одного метода.

### Дополнительно:

1. *Добавить миграцию
2. *Полнотекстовый поиск (любое текстовое поле) для House
3. *Полнотекстовый поиск (любое текстовое поле) для Person
5. **PATCH для Person и House

### Самостоятельно:

1. Изучить отношения: OneToOne, OneToMany, ManyToOne, ManyToMany;
2. !!! Изучить Hibernate Persistence Context;
3. !!! Изучить кеширование в hibernate;

### Требования к приложению

1. Версия JDK: 17 – используйте Streams, java.time.* и т. д., где это возможно.
2. Корень пакетов приложений: ru.clevertec.house.
3. Можно использовать любой широко используемый пул соединений.
4. Для доступа к данным следует использовать шаблон Spring JDBC.
5. Используйте транзакции там, где это необходимо.
6. Соглашение Java Code является обязательным (исключение: размер поля – 120 символов).
7. Инструмент сборки: Gradle, последняя версия.
8. Веб-сервер: Apache Tomcat.
9. Контейнер приложения: Spring IoC. Spring Framework, последняя версия.
10. База данных: PostgreSQL, последняя версия.
11. Тестирование: JUnit 5.+, Mockito.
12. Сервисный уровень должен быть покрыт юнит-тестами не менее чем на 80%.
13. Уровень репозитория следует тестировать с помощью интеграционных тестов со встроенной в память базой данных или
    тестовыми контейнерами.
14. В качестве картографа используйте Mapstruct.
16. Используйте ломбок.

### Общие требования

1. Код должен быть чистым и не должен содержать никаких конструкций, предназначенных для разработчиков.
2. Приложение должно быть спроектировано и написано с учетом принципов OOD и SOLID.
3. Код должен содержать ценные комментарии, где это необходимо.
4. Публичные API должны быть задокументированы (Javadoc).
5. Следует использовать четкую многоуровневую структуру с определением обязанностей каждого прикладного уровня.
6. JSON следует использовать в качестве формата сообщений связи клиент-сервер.
7. Должен быть реализован удобный механизм обработки ошибок/исключений: все ошибки должны быть значимыми на серверной
   стороне.
   Пример: обработка ошибки 404:
   HTTP Status: 404
   response body

```http
{
 “errorMessage”: “Requested resource not found (uuid = f4fe3df1-22cd-49ce-a54d-86f55a7f372e)”,
 “errorCode”: 40401
 }

```

где “errorCode” — ваш собственный код (он может основываться на статусе http и запрошенном ресурсе — человеке или доме)

9. Абстракцию следует использовать везде, чтобы избежать дублирования кода.
10. Должно быть реализовано несколько конфигураций (минимум две — dev и prod).

__

## Реализация:

### HouseController

#### GET getAll(Integer offset, Integer limit):

Default offset = 0
Default limit = 15

Request:

```http request
http://localhost:8080/api/houses
```

Response:

```json
{
  "content": [
    {
      "uuid": "6d316b83-126e-4090-bc81-4125a68923c0",
      "area": "AreaOne",
      "country": "Country",
      "city": "City",
      "street": "StreetOne",
      "number": 19,
      "createDate": "2024-01-19T13:16:13.992"
    },
    {
      "uuid": "71d9d979-48d9-420c-9454-912225476fef",
      "area": "AreaTwo",
      "country": "Country",
      "city": "City",
      "street": "StreetTwo",
      "number": 56,
      "createDate": "2024-01-19T13:17:12.830"
    },
    {
      "uuid": "f291c4bb-58a0-4800-bfc8-104217051d24",
      "area": "AreaThree",
      "country": "Country",
      "city": "City",
      "street": "StreetThree",
      "number": 43,
      "createDate": "2024-01-19T13:17:47.442"
    },
    {
      "uuid": "7c204d2c-b694-4dc4-a73a-9fa99b02973f",
      "area": "AreaTwo",
      "country": "Country",
      "city": "City",
      "street": "StreetOne",
      "number": 98,
      "createDate": "2024-01-19T13:18:17.079"
    },
    {
      "uuid": "b6baf98c-ea5a-48ec-a4e5-645998c1cceb",
      "area": "AreaOne",
      "country": "Country",
      "city": "City",
      "street": "StreetOne",
      "number": 13,
      "createDate": "2024-01-19T13:18:39.911"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 15,
    "sort": {
      "empty": true,
      "unsorted": true,
      "sorted": false
    },
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "totalPages": 1,
  "totalElements": 5,
  "last": true,
  "size": 15,
  "number": 0,
  "sort": {
    "empty": true,
    "unsorted": true,
    "sorted": false
  },
  "numberOfElements": 5,
  "first": true,
  "empty": false
}
```

Request:

```http request
http://localhost:8080/api/houses?offset=1&limit=2
```

Response:

```json
{
  "content": [
    {
      "uuid": "f291c4bb-58a0-4800-bfc8-104217051d24",
      "area": "AreaThree",
      "country": "Country",
      "city": "City",
      "street": "StreetThree",
      "number": 43,
      "createDate": "2024-01-19T13:17:47.442"
    },
    {
      "uuid": "7c204d2c-b694-4dc4-a73a-9fa99b02973f",
      "area": "AreaTwo",
      "country": "Country",
      "city": "City",
      "street": "StreetOne",
      "number": 98,
      "createDate": "2024-01-19T13:18:17.079"
    }
  ],
  "pageable": {
    "pageNumber": 1,
    "pageSize": 2,
    "sort": {
      "empty": true,
      "unsorted": true,
      "sorted": false
    },
    "offset": 2,
    "paged": true,
    "unpaged": false
  },
  "totalPages": 3,
  "totalElements": 5,
  "last": false,
  "size": 2,
  "number": 1,
  "sort": {
    "empty": true,
    "unsorted": true,
    "sorted": false
  },
  "numberOfElements": 2,
  "first": false,
  "empty": false
}
```

#### GET getByUuid(UUID uuid):

Request:

```http request
http://localhost:8080/api/houses/f291c4bb-58a0-4800-bfc8-104217051d24
```

Response:

```json
{
  "uuid": "f291c4bb-58a0-4800-bfc8-104217051d24",
  "area": "AreaThree",
  "country": "Country",
  "city": "City",
  "street": "StreetThree",
  "number": 43,
  "createDate": "2024-01-19T13:17:47.442"
}
```

Not found:

```json
{
  "errorMessage": "Entity not found!",
  "errorCode": 404
}
```

#### GET getAllResidents(UUID uuid):

GET для всех Person проживающих в House

Request:

```http request
http://localhost:8080/api/houses/residents/f291c4bb-58a0-4800-bfc8-104217051d24
```

Response:

```json
[
  {
    "uuid": "7736bed0-5b5c-4fbc-a919-e56f947a59b7",
    "name": "Liam",
    "surname": "Davis",
    "sex": "MALE",
    "passport": {
      "passportSeries": "GH",
      "passportNumber": "7654321"
    },
    "createDate": "2024-01-19T13:26:03.433",
    "updateDate": "2024-01-19T13:26:03.433"
  },
  {
    "uuid": "d21df8c3-2771-4dcb-9d4d-5f7a971b0c07",
    "name": "Emma",
    "surname": "Wilson",
    "sex": "FEMALE",
    "passport": {
      "passportSeries": "IJ",
      "passportNumber": "5432167"
    },
    "createDate": "2024-01-19T13:26:54.076",
    "updateDate": "2024-01-19T13:26:54.076"
  },
  {
    "uuid": "0953550a-4c67-4d3a-b95d-e7366d0c097c",
    "name": "Isabella",
    "surname": "Lee",
    "sex": "FEMALE",
    "passport": {
      "passportSeries": "QR",
      "passportNumber": "4321098"
    },
    "createDate": "2024-01-19T13:30:14.038",
    "updateDate": "2024-01-19T13:30:14.038"
  }
]
```

#### POST create(HouseCreateDto houseCreateDto):

Request:

```http request
http://localhost:8080/api/houses
```

Body:

```json
{
  "area": "AreaOne",
  "country": "Country",
  "city": "City",
  "street": "StreetOne",
  "number": 35
}
```

Response:

```json
{
  "uuid": "034508fc-1585-47b5-b8f2-33a0b53fbb89",
  "area": "AreaOne",
  "country": "Country",
  "city": "City",
  "street": "StreetOne",
  "number": 35,
  "createDate": "2024-01-20T20:30:21.093"
}
```

#### PUT update(HouseUpdateDto houseUpdateDto):

Request:

```http request
http://localhost:8080/api/houses
```

Body:

```json
{
  "uuid": "0953550a-4c67-4d3a-b95d-e7366d0c097c",
  "area": "AreaOne",
  "country": "Country",
  "city": "City",
  "street": "StreetOne",
  "number": 35
}
```

Response:

```json
{
  "uuid": "0953550a-4c67-4d3a-b95d-e7366d0c097c",
  "area": "AreaOne",
  "country": "Country",
  "city": "City",
  "street": "StreetOne",
  "number": 35,
  "createDate": "2024-01-20T21:06:57.158"
}
```

#### PATCH patch(HouseUpdateDto houseUpdateDto):

Request:

```http request
http://localhost:8080/api/houses
```

Body:

```json
{
  "uuid": "71d9d979-48d9-420c-9454-912225476fef",
  "area": "Area",
  "country": "CountryOne",
  "city": "City",
  "street": "StreetOne",
  "number": 35
}
```

Response:

```json
{
  "uuid": "71d9d979-48d9-420c-9454-912225476fef",
  "area": "Area",
  "country": "CountryOne",
  "city": "City",
  "street": "StreetOne",
  "number": 35,
  "createDate": "2024-01-19T13:17:12.830"
}
```

Not found:

```json
{
  "errorMessage": "Entity not found!",
  "errorCode": 404
}
```

#### DELETE delete(UUID uuid):

Request:

```http request
http://localhost:8080/api/houses/0953550a-4c67-4d3a-b95d-e7366d0c097c
```

__

### PersonController

#### GET getAll(Integer offset, Integer limit):

Default offset = 0
Default limit = 15

Request:

```http request
http://localhost:8080/api/persons
```

Response:

```json
{
  "content": [
    {
      "uuid": "33447590-3f82-4ae7-a9f6-4b140475c18f",
      "name": "Sophia",
      "surname": "Johnson",
      "sex": "MALE",
      "passport": {
        "passportSeries": "AB",
        "passportNumber": "1234567"
      },
      "createDate": "2024-01-19T13:23:18.356",
      "updateDate": "2024-01-19T13:23:18.356"
    },
    {
      "uuid": "54b9906a-8747-4af7-b641-2a803849f2d1",
      "name": "Noah",
      "surname": "Smith",
      "sex": "FEMALE",
      "passport": {
        "passportSeries": "CD",
        "passportNumber": "9876543"
      },
      "createDate": "2024-01-19T13:24:19.361",
      "updateDate": "2024-01-19T13:24:19.361"
    },
    {
      "uuid": "6c19889a-bb5a-4125-b168-6a1b9c4dd418",
      "name": "Olivia",
      "surname": "Brown",
      "sex": "FEMALE",
      "passport": {
        "passportSeries": "EF",
        "passportNumber": "2468101"
      },
      "createDate": "2024-01-19T13:25:00.894",
      "updateDate": "2024-01-19T13:25:00.894"
    },
    {
      "uuid": "7736bed0-5b5c-4fbc-a919-e56f947a59b7",
      "name": "Liam",
      "surname": "Davis",
      "sex": "MALE",
      "passport": {
        "passportSeries": "GH",
        "passportNumber": "7654321"
      },
      "createDate": "2024-01-19T13:26:03.433",
      "updateDate": "2024-01-19T13:26:03.433"
    },
    {
      "uuid": "d21df8c3-2771-4dcb-9d4d-5f7a971b0c07",
      "name": "Emma",
      "surname": "Wilson",
      "sex": "FEMALE",
      "passport": {
        "passportSeries": "IJ",
        "passportNumber": "5432167"
      },
      "createDate": "2024-01-19T13:26:54.076",
      "updateDate": "2024-01-19T13:26:54.076"
    },
    {
      "uuid": "74c3452d-ec27-4cb7-9cd5-e177607bf75c",
      "name": "Jackson",
      "surname": "Martinez",
      "sex": "MALE",
      "passport": {
        "passportSeries": "KL",
        "passportNumber": "2109876"
      },
      "createDate": "2024-01-19T13:27:48.176",
      "updateDate": "2024-01-19T13:27:48.176"
    },
    {
      "uuid": "cd7c5f0f-337b-4db1-8bc0-794d0d98685d",
      "name": "Ava",
      "surname": "Anderson",
      "sex": "FEMALE",
      "passport": {
        "passportSeries": "MN",
        "passportNumber": "6789012"
      },
      "createDate": "2024-01-19T13:28:35.280",
      "updateDate": "2024-01-19T13:28:35.280"
    },
    {
      "uuid": "1e390f7a-2181-42cb-af3d-62fe2a263d08",
      "name": "Lucas",
      "surname": "Thompson",
      "sex": "MALE",
      "passport": {
        "passportSeries": "OP",
        "passportNumber": "0987654"
      },
      "createDate": "2024-01-19T13:29:24.912",
      "updateDate": "2024-01-19T13:29:24.912"
    },
    {
      "uuid": "0953550a-4c67-4d3a-b95d-e7366d0c097c",
      "name": "Isabella",
      "surname": "Lee",
      "sex": "FEMALE",
      "passport": {
        "passportSeries": "QR",
        "passportNumber": "4321098"
      },
      "createDate": "2024-01-19T13:30:14.038",
      "updateDate": "2024-01-19T13:30:14.038"
    },
    {
      "uuid": "f8cba101-e82b-4e43-8eeb-3c5fac1e5f45",
      "name": "Ethan",
      "surname": "Harris",
      "sex": "MALE",
      "passport": {
        "passportSeries": "ST",
        "passportNumber": "8765432"
      },
      "createDate": "2024-01-19T13:31:19.378",
      "updateDate": "2024-01-19T13:31:19.378"
    },
    {
      "uuid": "9d653629-7893-409f-8ebe-71d1f61e3c7b",
      "name": "Noah",
      "surname": "Smith",
      "sex": "FEMALE",
      "passport": {
        "passportSeries": "CD",
        "passportNumber": "965099"
      },
      "createDate": "2024-01-21T14:39:08.880",
      "updateDate": "2024-01-21T14:39:08.881"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 15,
    "sort": {
      "empty": true,
      "unsorted": true,
      "sorted": false
    },
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "totalPages": 1,
  "totalElements": 11,
  "last": true,
  "size": 15,
  "number": 0,
  "sort": {
    "empty": true,
    "unsorted": true,
    "sorted": false
  },
  "numberOfElements": 11,
  "first": true,
  "empty": false
}
```

Request:

```http request
http://localhost:8080/api/persons?offset=1&limit=2
```

Response:

```json
{
  "content": [
    {
      "uuid": "6c19889a-bb5a-4125-b168-6a1b9c4dd418",
      "name": "Olivia",
      "surname": "Brown",
      "sex": "FEMALE",
      "passport": {
        "passportSeries": "EF",
        "passportNumber": "2468101"
      },
      "createDate": "2024-01-19T13:25:00.894",
      "updateDate": "2024-01-19T13:25:00.894"
    },
    {
      "uuid": "7736bed0-5b5c-4fbc-a919-e56f947a59b7",
      "name": "Liam",
      "surname": "Davis",
      "sex": "MALE",
      "passport": {
        "passportSeries": "GH",
        "passportNumber": "7654321"
      },
      "createDate": "2024-01-19T13:26:03.433",
      "updateDate": "2024-01-19T13:26:03.433"
    }
  ],
  "pageable": {
    "pageNumber": 1,
    "pageSize": 2,
    "sort": {
      "empty": true,
      "unsorted": true,
      "sorted": false
    },
    "offset": 2,
    "paged": true,
    "unpaged": false
  },
  "totalPages": 6,
  "totalElements": 11,
  "last": false,
  "size": 2,
  "number": 1,
  "sort": {
    "empty": true,
    "unsorted": true,
    "sorted": false
  },
  "numberOfElements": 2,
  "first": false,
  "empty": false
}
```

#### GET getByUuid(UUID uuid):

Request:

```http request
http://localhost:8080/api/persons/54b9906a-8747-4af7-b641-2a803849f2d1
```

Response:

```json
{
  "uuid": "54b9906a-8747-4af7-b641-2a803849f2d1",
  "name": "Noah",
  "surname": "Smith",
  "sex": "FEMALE",
  "passport": {
    "passportSeries": "CD",
    "passportNumber": "9876543"
  },
  "createDate": "2024-01-19T13:24:19.361",
  "updateDate": "2024-01-19T13:24:19.361"
}
```

Not found:

```json
{
  "errorMessage": "Entity not found!",
  "errorCode": 404
}
```

#### GET getAllHouses(UUID uuid):

GET для всех House, владельцем которых является Person

Request:

```http request
http://localhost:8080/api/persons/houses/cd7c5f0f-337b-4db1-8bc0-794d0d98685d
```

Response:

```json
[
  {
    "uuid": "71d9d979-48d9-420c-9454-912225476fef",
    "area": "AreaTwo",
    "country": "Country",
    "city": "City",
    "street": "StreetTwo",
    "number": 56,
    "createDate": "2024-01-19T13:17:12.830"
  },
  {
    "uuid": "7c204d2c-b694-4dc4-a73a-9fa99b02973f",
    "area": "AreaTwo",
    "country": "Country",
    "city": "City",
    "street": "StreetOne",
    "number": 98,
    "createDate": "2024-01-19T13:18:17.079"
  }
]
```

#### POST create(HouseCreateDto houseCreateDto):

Request:

```http request
http://localhost:8080/api/persons
```

Body:

```json
{
  "name": "Noah",
  "surname": "Smith",
  "sex": "FEMALE",
  "passport": {
    "passportSeries": "CD",
    "passportNumber": "9650991"
  },
  "homeUuid": "6d316b83-126e-4090-bc81-4125a68923c0"
}
```

Response:

```json
{
  "uuid": "06d562e4-4bfd-4897-88b7-5d00acf6ac26",
  "name": "Noah",
  "surname": "Smith",
  "sex": "FEMALE",
  "passport": {
    "passportSeries": "CD",
    "passportNumber": "9650991"
  },
  "createDate": "2024-01-21T14:43:29.382",
  "updateDate": "2024-01-21T14:43:29.382"
}
```

#### PUT update(PersonUpdateDto personUpdateDto):

Request:

```http request
http://localhost:8080/api/persons
```

Body:

```json
{
  "uuid": "06d562e4-4bfd-4897-88b7-5d00acf6ac26",
  "name": "Noa",
  "surname": "Smith",
  "sex": "MALE",
  "passport": {
    "passportSeries": "CD",
    "passportNumber": "9650991"
  },
  "homeUuid": "7c204d2c-b694-4dc4-a73a-9fa99b02973f"
}
```

Response:

```json
{
  "uuid": "06d562e4-4bfd-4897-88b7-5d00acf6ac26",
  "name": "Noa",
  "surname": "Smith",
  "sex": "MALE",
  "passport": {
    "passportSeries": "CD",
    "passportNumber": "9650991"
  },
  "createDate": "2024-01-21T14:43:29.382",
  "updateDate": "2024-01-21T14:43:29.382"
}
```

#### PATCH patch(PersonUpdateDto personUpdateDto):

Request:

```http request
http://localhost:8080/api/persons
```

Body:

```json
{
  "uuid": "54b9906a-8747-4af7-b641-2a803849f2d1",
  "name": "Noa",
  "surname": "SMITH1",
  "sex": "FEMALE",
  "passport": {
    "passportSeries": "CD",
    "passportNumber": "9650991"
  },
  "homeUuid": "6d316b83-126e-4090-bc81-4125a68923c0"
}
```

Response:

```json
{
  "uuid": "54b9906a-8747-4af7-b641-2a803849f2d1",
  "name": "Noa",
  "surname": "SMITH1",
  "sex": "FEMALE",
  "passport": {
    "passportSeries": "CD",
    "passportNumber": "9650991"
  },
  "createDate": "2024-01-19T13:24:19.361",
  "updateDate": "2024-01-24T01:03:11.397"
}
```

Not found:

```json
{
  "errorMessage": "Entity not found!",
  "errorCode": 404
}
```

#### DELETE delete(UUID uuid):

Request:

```http request
http://localhost:8080/api/persons/06d562e4-4bfd-4897-88b7-5d00acf6ac26
```

__

### HouseHistoryController

#### GET getAllPersonResidingInHouse(UUID houseUuid):

GET для получения всех Person когда-либо проживавших в доме

Request:

```http request
http://localhost:8080/api/history/residing/71d9d979-48d9-420c-9454-912225476fef
```

Response:

```json
[
  {
    "uuid": "33447590-3f82-4ae7-a9f6-4b140475c18f",
    "name": "Sophia",
    "surname": "Johnson",
    "sex": "MALE",
    "passport": {
      "passportSeries": "AB",
      "passportNumber": "1234567"
    },
    "createDate": "2024-01-19T13:23:18.356",
    "updateDate": "2024-01-19T13:23:18.356"
  },
  {
    "uuid": "54b9906a-8747-4af7-b641-2a803849f2d1",
    "name": "Noah",
    "surname": "Smith",
    "sex": "FEMALE",
    "passport": {
      "passportSeries": "CD",
      "passportNumber": "9876543"
    },
    "createDate": "2024-01-19T13:24:19.361",
    "updateDate": "2024-01-19T13:24:19.361"
  }
]
```

Not found:

```json
{
  "errorMessage": "Entity not found!",
  "errorCode": 404
}
```

#### GET getAllPersonOwnedHouse(UUID houseUuid):

GET для получения всех Person когда-либо владевших домом

Request:

```http request
http://localhost:8080/api/history/owned/6d316b83-126e-4090-bc81-4125a68923c0
```

Response:

```json

[
  {
    "uuid": "7736bed0-5b5c-4fbc-a919-e56f947a59b7",
    "name": "Liam",
    "surname": "Davis",
    "sex": "MALE",
    "passport": {
      "passportSeries": "GH",
      "passportNumber": "7654321"
    },
    "createDate": "2024-01-19T13:26:03.433",
    "updateDate": "2024-01-19T13:26:03.433"
  }
]
```

Not found:

```json
{
  "errorMessage": "Entity not found!",
  "errorCode": 404
}
```

#### GET getAllHousesByPerson(UUID personUuid):

GET для получения всех House где проживал Person

Request:

```http request
http://localhost:8080/api/history/resided/54b9906a-8747-4af7-b641-2a803849f2d1
```

Response:

```json
[
  {
    "uuid": "71d9d979-48d9-420c-9454-912225476fef",
    "area": "AreaTwo",
    "country": "Country",
    "city": "City",
    "street": "StreetTwo",
    "number": 56,
    "createDate": "2024-01-19T13:17:12.830"
  }
]
```

Not found:

```json
{
  "errorMessage": "Entity not found!",
  "errorCode": 404
}
```

#### GET getAllHousesOwnedByPerson(UUID personUuid):

GET для получения всех House которыми когда-либо владел Person

Request:

```http request
http://localhost:8080/api/history/belonged/33447590-3f82-4ae7-a9f6-4b140475c18f
```

Response:

```json
[
  {
    "uuid": "6d316b83-126e-4090-bc81-4125a68923c0",
    "area": "AreaOne",
    "country": "Country",
    "city": "City",
    "street": "StreetOne",
    "number": 19,
    "createDate": "2024-01-19T13:16:13.992"
  }
]
```

Not found:

```json
{
  "errorMessage": "Entity not found!",
  "errorCode": 404
}
```

___

### SearchController

#### GET searchPersonBySurname(String surname)

Request:

```http request
http://localhost:8080/api/search/person/S
```

Response:

```json
[
  {
    "uuid": "54b9906a-8747-4af7-b641-2a803849f2d1",
    "name": "Noah",
    "surname": "Smith",
    "sex": "FEMALE",
    "passport": {
      "passportSeries": "CD",
      "passportNumber": "9876543"
    },
    "createDate": "2024-01-19T13:24:19.361",
    "updateDate": "2024-01-19T13:24:19.361"
  }
]
```

Empty list:

```json
{
  "errorMessage": "List is empty!",
  "errorCode": 404
}
```

#### GET searchHouseByCity(String city)

Request:

```http request
http://localhost:8080/api/search/house/i
```

Response:

```json
[
  {
    "uuid": "6d316b83-126e-4090-bc81-4125a68923c0",
    "area": "AreaOne",
    "country": "Country",
    "city": "City",
    "street": "StreetOne",
    "number": 19,
    "createDate": "2024-01-19T13:16:13.992"
  },
  {
    "uuid": "71d9d979-48d9-420c-9454-912225476fef",
    "area": "AreaTwo",
    "country": "Country",
    "city": "City",
    "street": "StreetTwo",
    "number": 56,
    "createDate": "2024-01-19T13:17:12.830"
  },
  {
    "uuid": "f291c4bb-58a0-4800-bfc8-104217051d24",
    "area": "AreaThree",
    "country": "Country",
    "city": "City",
    "street": "StreetThree",
    "number": 43,
    "createDate": "2024-01-19T13:17:47.442"
  },
  {
    "uuid": "7c204d2c-b694-4dc4-a73a-9fa99b02973f",
    "area": "AreaTwo",
    "country": "Country",
    "city": "City",
    "street": "StreetOne",
    "number": 98,
    "createDate": "2024-01-19T13:18:17.079"
  },
  {
    "uuid": "b6baf98c-ea5a-48ec-a4e5-645998c1cceb",
    "area": "AreaOne",
    "country": "Country",
    "city": "City",
    "street": "StreetOne",
    "number": 13,
    "createDate": "2024-01-19T13:18:39.911"
  }
]
```

Empty list:

```json
{
  "errorMessage": "List is empty!",
  "errorCode": 404
}

### Swagger

http://localhost:8080/api/doc/swagger-ui/index.html#/



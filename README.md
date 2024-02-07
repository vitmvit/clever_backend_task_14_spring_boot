# clever_backend_task_15_spring_tests

## Задача

Добавить в свой проект оправданный spring aop

Hаписать свой стартер по отлову exception’ов в своём приложении

___

## Инструкции по запуску приложения:

Перед запуском приложения необходимо создать базу данных в PostgreSQL с названием "clever_lab_task_14".
При необходимости можно изменить параметры в application.yml (src/main/resources/application.yml).
Запустить класс Application.

## Swagger

http://localhost:8080/api/doc/swagger-ui/index.html#/

## Реализация:

#### Реализация стартера находится по ссылке:

https://github.com/vitmvit/exception-error-handler-spring-boot-starter/pull/1

#### AOP:

- [HouseProxyService](src/main/java/ru/clevertec/house/service/proxy/HouseProxyService.java)
- [PersonProxyService](src/main/java/ru/clevertec/house/service/proxy/PersonProxyService.java)

#### Аутентификация и авторизация:

Реализована аутентификация и авторизация с помощью Spring-Boot и JWT.

Для создания нового пользователя мы отправляем POST на /api/auth/signup конечную точку запрос с телом, содержащим логин,
пароль и одну из доступных ролей.

Доступные роли:

```text
ADMIN, USER
```

### AuthController

#### POST запрос на создание нового пользователя:

Request:

```http request
http://localhost:8080/api/auth/signup
```

```json
{
  "login": "User",
  "password": "user",
  "role": "USER"
}
```

Response:

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJVc2VyIiwidXNlcm5hbWUiOiJVc2VyIiwiZXhwIjoxNzA2NTUzODMyfQ.fKt7m_e-Thx-JtgjbVnR7RF_9ifqyxjVogTYD2SwCfg"
}
```

Если пользователь существует:

```json
{
  "errorMessage": "Username already exists",
  "errorCode": 404
}
```

#### POST запрос на получение токена аутентификации:

Request:

```http request
http://localhost:8080/api/auth/signin
```

```json
{
  "login": "User",
  "password": "user"
}
```

Response:

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJVc2VyIiwidXNlcm5hbWUiOiJVc2VyIiwiZXhwIjoxNzA2NTUzODMyfQ.fKt7m_e-Thx-JtgjbVnR7RF_9ifqyxjVogTYD2SwCfg"
}
```

Если пользователь не найден:

```json
{
  "errorMessage": "UserDetailsService returned null, which is an interface contract violation",
  "errorCode": 404
}
```

Далее полученный токен необходимо передавать в заголовках запросах по Person и House следующим образом:

| First Header  | Second Header |
| ------------- | ------------- |
| Authorization  | Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJVc2VyIiwidXNlcm5hbWUiOiJVc2VyIiwiZXhwIjoxNzA2NTU0MTEyfQ.Hxfd_kMyME2KlTtuopg0XL2FcHxzdExhn1_RXD37hqU  |

Если токен не действителен, то получим 403 код (отсутсвует доступ).

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
  "uuid": "6d316b83-126e-4090-bc81-4125a68923c0",
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
  "uuid": "6d316b83-126e-4090-bc81-4125a68923c0",
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
  "uuid": "6d316b83-126e-4090-bc81-4125a68923c0",
  "area": "Area",
  "country": "CountryTwo"
}
```

Response:

```json
{
  "uuid": "6d316b83-126e-4090-bc81-4125a68923c0",
  "area": "Area",
  "country": "CountryTwo",
  "city": "City",
  "street": "StreetOne",
  "number": 0,
  "createDate": "2024-01-19T13:16:13.992"
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
  "name": "Noag"
}
```

Response:

```json
{
  "uuid": "54b9906a-8747-4af7-b641-2a803849f2d1",
  "name": "Noag",
  "surname": "SMITi",
  "sex": "FEMALE",
  "passport": {
    "passportSeries": "CD",
    "passportNumber": "9650991"
  },
  "createDate": "2024-01-19T13:24:19.361",
  "updateDate": "2024-01-24T01:44:50.530"
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
```



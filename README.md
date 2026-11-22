# ASTON Restaurant API

## Инструкция по запуску
1. Склонируйте репозиторий.
2. Убедитесь, что у вас установлена Java 17+.
3. Запустите приложение через `Main.java` или командой `mvn spring-boot:run`.
4. Приложение будет доступно по адресу: `http://localhost:8083`.

## Эндпоинты
- `POST /cities` — добавить город (JSON: `{"name":"Москва"}`).
- `POST /restaurants` — добавить ресторан (JSON: `{"name":"Вкусно", "city": {"id": 1}}`).
- `GET /restaurants/city/{cityId}` — получить рестораны города с сортировкой по рейтингу.
- `POST /restaurants/{restaurantId}/votes?rating=5&comment=Отлично` — добавить отзыв и пересчитать средний рейтинг.

## Стек технологий
- Spring Boot 3, Spring Web, Spring Data JPA, Spring JDBC, H2 Database.
- Тестирование: JUnit 5, Mockito.
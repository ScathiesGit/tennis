# Проект “табло теннисного матча”

Автор проекта: [zhukovsd](https://github.com/zhukovsd)

## 

- Создать клиент-серверное приложение с веб-интерфейсом
- Получить практический опыт работы с ORM Hibernate, Spring, Spring Boot

## Использованные технологии и инструменты

- Spring Boot
- Spring Web
- Hibernate
- Junit
- Mockito
- Liquibase
- H2
- PostgreSQL

## Функционал приложения

Работа с матчами:

- Создание нового матча
- Просмотр законченных матчей, поиск матчей по именам игроков
- Подсчёт очков в текущем матче

Для упрощения, допустим что каждый матч играется по следующим правилам:
- Матч играется до двух сетов (best of 3)
- При счёте 6/6 в сете, играется тай-брейк до 7 очков

## Интерфейс приложения

### Главная страница

Адрес - `/`.

- Ссылки, ведущие на страницы нового матча и списка завершенных матчей

### Страница нового матча

Адрес - `/new-match`.

Интерфейс:
- HTML форма с полями “Имя игрока 1”, “Имя игрока 2” и кнопкой “начать”
- Нажатие кнопки “начать” приводить к POST запросу по адресу `/new-match`

### Страница счёта матча - `/match-score`

Адрес - `/match-score?uuid=$match_id`. GET параметр `uuid` содержит UUID матча.

Интерфейс:
- Таблица с именами игроков, текущим счётом
- Формы и кнопки для действий - "игрок 1 выиграл текущее очко", "игрок 2 выиграл текущее очко"
- Нажатие кнопок приводит к POST запросу по адресу `/match-score?uuid=$match_id`, в полях отправленной формы содержится айди выигравшего очко игрока

### Страница сыгранных матчей - `/matches`

Адрес - `/matches?page=$page_number&filter_by_player_name=$player_name`. GET параметры:
- `page` - номер страницы. Если параметр не задан, подразумевается первая страница
- `filter_by_player_name` - имя игрока, матчи которого ищем. Если параметр не задан, отображаются все матчи

Постранично отображает список сыгранных матчей. Позволяет искать матчи игрока по его имени.

Интерфейс:
- Форма с фильтром по имени игрока. Поле ввода для имени и кнопка "искать". По нажатию формируется GET запрос вида `/matches?filter_by_player_name=${NAME}`
- Список найденных матчей
- Переключатель страниц, если матчей найдено больше, чем влезает на одну страницу

## База данных

#### Таблица `Players` - игроки

| Имя колонки | Тип     | Комментарий                   |
|-------------|---------|-------------------------------|
| ID          | Int     | Первичный ключ, автоинкремент |
| Name        | Varchar | Имя игрока                    |

### Таблица `Matches` - завершенные матчи


| Имя колонки | Тип | Комментарий                                     |
|-------------|-----|-------------------------------------------------|
| ID          | Int | Первичный ключ, автоинкремент                   |
| Player1     | Int | Айди первого игрока, внешний ключ на Players.ID |
| Player2     | Int | Айди второго игрока, внешний ключ на Players.ID |
| Winner      | Int | Айди победителя, внешний ключ на Players.ID     |

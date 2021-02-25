[![Build Status](https://travis-ci.org/alex-skorikov/Music_area.svg?branch=master)](https://travis-ci.org/alex-skorikov/Music_area)
[![codecov](https://codecov.io/gh/alex-skorikov/Music_area/branch/master/graph/badge.svg)](https://codecov.io/gh/alex-skorikov/Music_area)
# Music_area
Музыкальная прощадка
Создать 4 сущности:
User, Address, Role, MusicType.
Таблицы и связи между таблицами:
Role : User(1:M);
User : Address(1:1);
User : MusicType (M:M).
Таблицы Role и MusicType заполнены: (USER, MANDATOR, ADMIN) и (RAP, ROCK …).
Реализовать DAO для каждой из сущностей, в которых должны находится CRUD операции
(создать, выбрать все сущности,
выбор сущности по id, редактировать сущность, удалить сущность).
Для User реализовать:
операцию получения всех связанных с ним сущностей;
операцию добавления нового User и всех связанных с ним сущностей;
операцию поиска User по заданному параметру (Address, Role, MusicType).
Для Role реализовать:
операцию получения всех связанных с ним сущностей;

Не использовать spring и hibernate . Добавить web с возможностью входа под этими ролями. 
Разместить на Heroku.com

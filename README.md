# Реактивный веб-сервис

Необходимо реализовать веб-сервис для просмотра каталога товаров. В сервисе можно
регистрировать новых пользователей и добавлять товары. Пользователи при регистрации
указывают, в какой валюте они хотят видеть товары (доллары, евро, рубли).

Указания:

* Веб-сервис должен быть полностью "реактивным" (все взаимодействие должно быть
асинхронным и не блокирующим);

* В запросах можно не проверять авторизацию пользователей, а просто указывать id
пользователя;

* Данные можно хранить в mongo и использовать "реактивный" mongo driver; в качестве
http-сервиса можно использовать rxnetty-http;

# Smart Home Technologies 
Проект, создаваемый для практической отработки навыков работы с Spring Cloud и Apache Kafka.
Разделён на два основных модуля – telemetry и commerece.
### Telemetry
Модуль хаба умного дома. Принимает данные с датчиков, сценарии работы пользователя, а послее соотносит их и активирует сценарий при необходимости.

Стек: Java, Spring Boot, Spring Cloud, Apache Kafka, gRPC, Hibernate, PostgreSQL, Hibernate Eureka Client, Cloud Config Client
### Commerece
Модуль, хранящий в себе логику работы интернет-магазина для умного дома. Содержит модули витрины, корзины, склада, заказов, доставки, оплаты.

Стек: Java, Spring Boot, Spring Cloud, REST API, Hibernate, PostgreSQL, Hibernate Eureka Client, Cloud Config Client, OpenFeign

### Infra
Модуль, содержащий настройки облачного взаимодействия модулей проекта.

Стек: Java, Spring Cloud, Eureka, Spring Cloud Config, Gateway
## Setup
### Telemetry
Необходимо: Java 21, запущенные infra/discovery-server, infra/config-server, PostgreSQL на порту 5432, kafka на порту 9092
### Commerece
Необходимо: Java 21, запущенные infra/discovery-server, infra/config-server, PostgreSQL на порту 5433
### Gateway
Необходимо: Java 21, запущенные infra/discovery-server, infra/config-server
Для коректной работы сначала запустить весь Commerece

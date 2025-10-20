# 🏦 Bank REST API

**Bank REST API** — это проект на **Spring Boot**, реализующий систему управления банковскими картами с использованием **JWT-аутентификации**, **PostgreSQL**, **Liquibase** и **OpenAPI (Swagger UI)**.

---

## 🚀 Стек технологий

- ☕ **Java 17**  
- 🌱 **Spring Boot 3**  
- 🔐 **Spring Security + JWT**  
- 🗄️ **Spring Data JPA + PostgreSQL**  
- 🧩 **Liquibase** — миграции базы данных  
- 📘 **OpenAPI (Swagger UI)** — документация REST API  
- 🐳 **Docker & Docker Compose** — контейнеризация  

---

## 📦 Запуск через Docker

### 1️⃣ Сборка и запуск проекта

```bash
docker compose up --build
```

💡 Эта команда:
- Соберёт проект с помощью Maven внутри контейнера;  
- Поднимет базу данных **PostgreSQL**;  
- Запустит **Spring Boot** приложение на порту `8080`.

---

Если хочешь запустить приложение **в среде разработки (локально)**, а базу — в контейнере:  

1. В `Dockerfile` закомментируй первую часть и **раскомментируй** вторую опцию.  
2. Выполни команды:

```bash
mvn clean package
docker compose up -d
mvn spring-boot:run
```

---

### 2️⃣ Проверка работы

| Сервис | URL |
|--------|-----|
| 🌐 Приложение (API) | [http://localhost:8080](http://localhost:8080) |
| 📄 Swagger UI | [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) |
| 🐘 PostgreSQL | `localhost:5432` (user: `postgres`, password: `postgres`) |

---

### 🧪 Тестовые данные

База данных создаётся и заполняется тестовыми данными при запуске.  

Используй следующие учетные данные для входа и получения JWT-токена (например, через Postman):

| Роль | Username          | Password |
|------|-------------------|----------|
| 👑 ADMIN | `admin`           | `password` |
| 👤 USER | `user2` / `user3` | `password` |

---

## ⚙️ Запуск без Docker

Если у тебя установлены **Java 17**, **Maven** и **PostgreSQL**, можно запустить проект напрямую.

### 1️⃣ Создай базу данных

```sql
CREATE DATABASE bankdb;
```

### 2️⃣ Укажи настройки в `src/main/resources/application.yml`

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/bankdb
    username: postgres
    password: postgres
```

### 3️⃣ Собери и запусти проект

```bash
mvn clean package
java -jar target/bank-rest-0.0.1-SNAPSHOT.jar
```

или просто:

```bash
mvn spring-boot:run
```

После запуска:

- Приложение доступно на: [http://localhost:8080](http://localhost:8080)  
- Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## 🌍 Переменные окружения (для Docker)

| Переменная | Значение |
|-------------|-----------|
| `SPRING_DATASOURCE_URL` | `jdbc:postgresql://db:5432/bankdb` |
| `SPRING_DATASOURCE_USERNAME` | `postgres` |
| `SPRING_DATASOURCE_PASSWORD` | `postgres` |
| `JWT_SECRET` | `super_secret_key_which_should_be_long_and_random_123456789` |

---

💬 **Автор:** *Твой проект Bank REST API — готов к использованию и тестированию!* 🚀  

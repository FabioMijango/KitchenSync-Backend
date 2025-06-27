# 🍽️ API Backend - Sistema de Gestión de Restaurante

Este proyecto corresponde al backend de un sistema de gestión para restaurantes. Permite administrar órdenes, platillos, mesas y usuarios mediante una API REST protegida con JWT.

---

## 🔐 Autenticación

La API utiliza JWT (JSON Web Tokens) para proteger los endpoints. Los tokens deben enviarse en el header `Authorization` como:

```
Authorization: Bearer <token>
```

### ▶️ `POST /auth/login`
Inicia sesión y devuelve el token JWT.

**Request body**
```json
{
  "email": "usuario@example.com",
  "password": "123456"
}
```

**Response**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR..."
}
```

---

## 🍽️ Platillos (`/dishes`)

### ✅ `GET /dishes`
Obtiene todos los platillos disponibles.

### ➕ `POST /dishes`
Crea un nuevo platillo.

**Body**
```json
{
  "name": "Sopa de tortilla",
  "description": "Deliciosa sopa mexicana",
  "price": 4.99,
  "category": "SOUP",
  "available": true
}
```

### 📝 `PUT /dishes/{id}`
Actualiza un platillo existente.

### ❌ `DELETE /dishes/{id}`
Elimina un platillo.

---

## 📋 Órdenes (`/orders`)

### ✅ `GET /orders`
Lista todas las órdenes del sistema.

### ➕ `POST /orders`
Crea una nueva orden.

**Body**
```json
{
  "tableId": 1,
  "userId": 2
}
```

### 📝 `PUT /orders/{id}`
Actualiza el estado de una orden.

### ❌ `DELETE /orders/{id}`
Elimina una orden.

---

## 🧾 Platillos por orden (`/order-dishes`)

### ✅ `GET /order-dishes/{orderId}`
Obtiene los platillos asociados a una orden.

### ➕ `POST /order-dishes`
Asocia platillos a una orden.

**Body**
```json
{
  "orderId": 5,
  "dishId": 2,
  "quantity": 3
}
```

### 📝 `PUT /order-dishes/{id}`
Actualiza cantidad o estado de un platillo dentro de una orden.

### ❌ `DELETE /order-dishes/{id}`
Elimina un platillo de una orden.

---

## 🍽️ Mesas (`/kitchen/tables`)

### ✅ `GET /kitchen/tables`
Obtiene todas las mesas registradas.

### ➕ `POST /kitchen/tables`
Crea una nueva mesa.

**Body**
```json
{
  "number": 7
}
```

---

## 🛠️ Tecnologías

- Java 17
- Spring Boot 3
- Spring Security + JWT
- Maven
- PostgreSQL (configurable)
- RESTful API

---

## 📌 Notas

- Todos los endpoints, salvo `/auth/login`, requieren autenticación.
- Las respuestas de error siguen el formato estándar de Spring.
- Se recomienda incluir encabezados `Content-Type: application/json`.

---

## ✍️ Autoría

Este backend fue desarrollado como parte de un sistema de gestión para restaurantes. 
ATT. LOS 4 CAPAS 

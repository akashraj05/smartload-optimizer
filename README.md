# 🚚 SmartLoad Optimization API

A high-performance, stateless service that selects the optimal combination of shipment orders for a truck, maximizing revenue while respecting weight, volume, and compatibility constraints.

---

## ✨ Features

* 🚀 **Optimal Load Selection** using Bitmask Dynamic Programming (n ≤ 22)
* ⚖️ Respects **weight & volume constraints**
* ☣️ Handles **hazmat compatibility**
* 🗓️ Validates **time window constraints**
* ⚡ **< 300ms response time** for up to 22 orders
* 🧠 Stateless, in-memory computation (no DB)
* 🐳 Fully Dockerized (Java 21)

---

## 🧰 Tech Stack

* Java 21 (LTS)
* Spring Boot 3.2+
* Maven
* Docker (multi-stage build)
* JUnit 5 + MockMvc

---

## 📦 Project Structure

```
smartload-optimizer/
├── controller/        # REST APIs
├── service/           # Optimization logic
├── model/             # DTOs
├── validation/        # Request validation
├── exception/         # Global exception handling
├── util/              # Utility classes
├── test/              # Unit & integration tests
├── Dockerfile
├── docker-compose.yml
└── README.md
```

---

## 🚀 How to Run

### ✅ Option 1: Run with Docker (Recommended)

```bash
git clone <your-repo-url>
cd smartload-optimizer

docker compose up --build
```

👉 Service will start at:

```
http://localhost:8080
```

---

### ✅ Option 2: Run Locally

#### Prerequisites

* Java 21
* Maven 3.9+

```bash
mvn clean install
mvn spring-boot:run
```

---

## 🧪 Health Check

```bash
curl http://localhost:8080/actuator/health
```

✅ Expected:

```json
{
  "status": "UP"
}
```

---

## 📡 API Endpoints

### 🔹 Optimize Load

**POST** `/api/v1/load-optimizer/optimize`

---

### 📥 Sample Request

```json
{
  "truck": {
    "id": "truck-123",
    "maxWeightLbs": 44000,
    "maxVolumeCuft": 3000
  },
  "orders": [
    {
      "id": "ord-001",
      "payoutCents": 250000,
      "weightLbs": 18000,
      "volumeCuft": 1200,
      "origin": "Los Angeles, CA",
      "destination": "Dallas, TX",
      "pickupDate": "2025-12-05",
      "deliveryDate": "2025-12-09",
      "hazmat": false
    }
  ]
}
```

---

### 📤 Sample Response

```json
{
  "truckId": "truck-123",
  "selectedOrderIds": ["ord-001", "ord-002"],
  "totalPayoutCents": 430000,
  "totalWeightLbs": 30000,
  "totalVolumeCuft": 2100,
  "utilizationWeightPercent": 68.18,
  "utilizationVolumePercent": 70.0
}
```

---

### 🔧 cURL Example

```bash
curl -X POST http://localhost:8080/api/v1/load-optimizer/optimize \
  -H "Content-Type: application/json" \
  -d @sample-request.json
```

---

## ⚙️ Constraints & Assumptions

* Max **22 orders** per request
* Same origin → destination lane required
* No mixing of **hazmat and non-hazmat**
* Valid time window: `pickup_date ≤ delivery_date`
* Money handled in **integer cents (long)**

---

## ⚡ Algorithm

* Uses **Bitmask Dynamic Programming**
* Time Complexity: `O(n * 2^n)`
* Optimized using:

    * Primitive arrays (no object overhead)
    * Early pruning (weight/volume)
    * Incremental DP computation

---

## 🧪 Testing

Run all tests:

```bash
mvn test
```

Includes:

* ✅ Unit tests (OptimizerService)
* ✅ Integration tests (MockMvc)
* ✅ Edge cases (empty, invalid input)

---

## 🐳 Docker Details

* Multi-stage build (small image)
* Java 21 (Eclipse Temurin)
* Exposes port **8080**

---

## ❗ Error Handling

| Status | Scenario        |
| ------ | --------------- |
| 200    | Success         |
| 400    | Invalid input   |
| 413    | Too many orders |
| 500    | Internal error  |

---

## 🚀 Future Improvements

* Pareto optimal solutions (multi-objective)
* Route optimization (VRP)
* Caching with Caffeine
* Parallel processing for large inputs

---

## 👨‍💻 Author

Akash

---
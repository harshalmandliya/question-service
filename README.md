# ❓ Question Service Microservice

## 📘 Overview

The **Question Service** is a core component of the QuizApp microservice architecture.  
It manages the question bank — adding, retrieving, and providing questions based on categories or quiz requirements.

This service is used by the Quiz Service through Feign Client communication to generate and evaluate quizzes.

---

## ⚙️ Tech Stack

- **Java 21**
- **Spring Boot**
- **Spring Web**
- **Maven** (Build Tool)
- **RESTful API Architecture**

---

## 🏗️ Project Structure

```
question-service/
├── src/main/java/com/example/question/
│   ├── Question.java
│   ├── QuestionWrapper.java
│   ├── Response.java
│   ├── controller/
│   │   └── QuestionController.java
│   └── service/
│       └── QuestionService.java
│
├── src/main/resources/
│   ├── static/
│   ├── templates/
│   ├── application.properties
│
└── src/test/java/
```

---

## 🚀 Endpoints

### 1️⃣ Get All Questions

- **Endpoint:** `GET /questions/allQuestions`
- **Description:** Retrieves all questions from the database.
- **Response Example:**
    ```json
    [
      {
        "id": 1,
        "questionTitle": "What is the capital of France?",
        "option1": "Berlin",
        "option2": "Madrid",
        "option3": "Paris",
        "option4": "Rome",
        "correctAnswer": "Paris",
        "category": "Geography"
      }
    ]
    ```

---

### 2️⃣ Get Questions by Category

- **Endpoint:** `GET /questions/category/{category}`
- **Description:** Fetches all questions that belong to a specific category.
- **Example:**  
  `GET /questions/category/Science`

---

### 3️⃣ Add a Question

- **Endpoint:** `POST /questions/add`
- **Description:** Adds a new question to the database.
- **Request Body Example:**
    ```json
    {
      "questionTitle": "Which planet is known as the Red Planet?",
      "option1": "Earth",
      "option2": "Mars",
      "option3": "Venus",
      "option4": "Jupiter",
      "correctAnswer": "Mars",
      "category": "Science"
    }
    ```
- **Response:**  
  "Question added successfully!"

---

### 4️⃣ Generate Questions for a Quiz

- **Endpoint:** `GET /questions/generate`
- **Description:** Returns a list of random question IDs from a given category.
- **Example:**  
  `GET /questions/generate?categoryName=Science&numQuestions=5`
- **Response Example:**
    ```json
    [1, 5, 7, 10, 14]
    ```

---

### 5️⃣ Get Questions by ID List

- **Endpoint:** `POST /questions/getQuestions`
- **Description:** Returns question details for a list of question IDs (used by Quiz Service).
- **Request Body Example:**
    ```json
    [1, 5, 7]
    ```
- **Response Example:**
    ```json
    [
      {
        "id": 1,
        "questionTitle": "What is the capital of India?",
        "option1": "Delhi",
        "option2": "Mumbai",
        "option3": "Kolkata",
        "option4": "Chennai"
      }
    ]
    ```

---

### 6️⃣ Calculate Score

- **Endpoint:** `POST /questions/getScore`
- **Description:** Evaluates submitted answers and returns the total score.
- **Request Body Example:**
    ```json
    [
      {
        "questionId": 1,
        "response": "Paris"
      },
      {
        "questionId": 2,
        "response": "Mars"
      }
    ]
    ```
- **Response:**
    ```
    2
    ```
    *(Indicating 2 correct answers)*

---

## 🧩 Key Classes

| Class              | Description                                              |
|--------------------|----------------------------------------------------------|
| `QuestionController` | Exposes REST endpoints for managing questions           |
| `QuestionService`    | Handles core logic for CRUD operations and quiz question generation |
| `Question`           | Entity/model class for a question                      |
| `QuestionWrapper`    | Used to send limited question details (hides correct answers) |
| `Response`           | Represents user answers for score evaluation           |

---

## 🧠 How to Run

1. **Clone the repository:**
    ```bash
    git clone https://github.com/your-repo/question-service.git
    ```

2. **Navigate into the project:**
    ```bash
    cd question-service
    ```

3. **Build and start the service:**
    ```bash
    mvn spring-boot:run
    ```

4. **The service will start on:**
    ```
    http://localhost:8080/
    ```

---

## 🔗 Integration

This service works together with the **Quiz Service**:

- **Quiz Service** → Fetches question IDs & details via Feign.
- **Question Service** → Provides questions and validates answers.

**Inter-service communication:**  
Uses Spring Cloud OpenFeign.

**Example Feign endpoint in Quiz Service:**
```java
@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {
    @GetMapping("questions/generate")
    ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String categoryName, @RequestParam int numQuestions);
}
```
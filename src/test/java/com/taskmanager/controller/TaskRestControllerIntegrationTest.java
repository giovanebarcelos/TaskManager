package com.taskmanager.controller;

import com.taskmanager.model.Task;
import com.taskmanager.model.Task.TaskPriority;
import com.taskmanager.model.Task.TaskStatus;
import com.taskmanager.repository.TaskRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class TaskRestControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        taskRepository.deleteAll();
    }

    @Test
    void testGetAllTasks_EmptyList() {
        given()
            .when()
                .get("/api/tasks")
            .then()
                .statusCode(200)
                .body("$", hasSize(0));
    }

    @Test
    void testCreateTask_Success() {
        String taskJson = """
            {
                "title": "Test Task",
                "description": "Test Description",
                "priority": "HIGH"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(taskJson)
            .when()
                .post("/api/tasks")
            .then()
                .statusCode(201)
                .body("title", equalTo("Test Task"))
                .body("description", equalTo("Test Description"))
                .body("priority", equalTo("HIGH"))
                .body("status", equalTo("PENDING"))
                .body("id", notNullValue());
    }

    @Test
    void testCreateTask_ValidationError_TitleTooShort() {
        String taskJson = """
            {
                "title": "Ab",
                "description": "Test Description",
                "priority": "HIGH"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(taskJson)
            .when()
                .post("/api/tasks")
            .then()
                .statusCode(400)
                .body("status", equalTo(400));
    }

    @Test
    void testCreateTask_ValidationError_TitleBlank() {
        String taskJson = """
            {
                "title": "",
                "description": "Test Description",
                "priority": "HIGH"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(taskJson)
            .when()
                .post("/api/tasks")
            .then()
                .statusCode(400);
    }

    @Test
    void testGetTaskById_Success() {
        Task task = new Task();
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setPriority(TaskPriority.MEDIUM);
        task.setStatus(TaskStatus.PENDING);
        Task savedTask = taskRepository.save(task);

        given()
            .when()
                .get("/api/tasks/{id}", savedTask.getId())
            .then()
                .statusCode(200)
                .body("id", equalTo(savedTask.getId().intValue()))
                .body("title", equalTo("Test Task"))
                .body("description", equalTo("Test Description"));
    }

    @Test
    void testGetTaskById_NotFound() {
        given()
            .when()
                .get("/api/tasks/999")
            .then()
                .statusCode(404)
                .body("status", equalTo(404))
                .body("message", containsString("não encontrada"));
    }

    @Test
    void testUpdateTask_Success() {
        Task task = new Task();
        task.setTitle("Original Title");
        task.setDescription("Original Description");
        task.setPriority(TaskPriority.LOW);
        task.setStatus(TaskStatus.PENDING);
        Task savedTask = taskRepository.save(task);

        String updateJson = """
            {
                "title": "Updated Title",
                "description": "Updated Description",
                "priority": "HIGH",
                "status": "IN_PROGRESS"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(updateJson)
            .when()
                .put("/api/tasks/{id}", savedTask.getId())
            .then()
                .statusCode(200)
                .body("title", equalTo("Updated Title"))
                .body("description", equalTo("Updated Description"))
                .body("priority", equalTo("HIGH"))
                .body("status", equalTo("IN_PROGRESS"));
    }

    @Test
    void testUpdateTask_NotFound() {
        String updateJson = """
            {
                "title": "Updated Title",
                "description": "Updated Description",
                "priority": "HIGH"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(updateJson)
            .when()
                .put("/api/tasks/999")
            .then()
                .statusCode(404);
    }

    @Test
    void testCompleteTask_Success() {
        Task task = new Task();
        task.setTitle("Task to Complete");
        task.setDescription("Description");
        task.setPriority(TaskPriority.MEDIUM);
        task.setStatus(TaskStatus.PENDING);
        Task savedTask = taskRepository.save(task);

        given()
            .when()
                .patch("/api/tasks/{id}/complete", savedTask.getId())
            .then()
                .statusCode(204);

        // Verify task is completed
        given()
            .when()
                .get("/api/tasks/{id}", savedTask.getId())
            .then()
                .statusCode(200)
                .body("status", equalTo("COMPLETED"))
                .body("completedAt", notNullValue());
    }

    @Test
    void testCancelTask_Success() {
        Task task = new Task();
        task.setTitle("Task to Cancel");
        task.setDescription("Description");
        task.setPriority(TaskPriority.MEDIUM);
        task.setStatus(TaskStatus.PENDING);
        Task savedTask = taskRepository.save(task);

        given()
            .when()
                .patch("/api/tasks/{id}/cancel", savedTask.getId())
            .then()
                .statusCode(204);

        // Verify task is cancelled
        given()
            .when()
                .get("/api/tasks/{id}", savedTask.getId())
            .then()
                .statusCode(200)
                .body("status", equalTo("CANCELLED"));
    }

    @Test
    void testDeleteTask_Success() {
        Task task = new Task();
        task.setTitle("Task to Delete");
        task.setDescription("Description");
        task.setPriority(TaskPriority.MEDIUM);
        task.setStatus(TaskStatus.PENDING);
        Task savedTask = taskRepository.save(task);

        given()
            .when()
                .delete("/api/tasks/{id}", savedTask.getId())
            .then()
                .statusCode(204);

        // Verify task is deleted
        given()
            .when()
                .get("/api/tasks/{id}", savedTask.getId())
            .then()
                .statusCode(404);
    }

    @Test
    void testGetTasksByStatus_Success() {
        Task task1 = new Task();
        task1.setTitle("Pending Task 1");
        task1.setPriority(TaskPriority.MEDIUM);
        task1.setStatus(TaskStatus.PENDING);
        taskRepository.save(task1);

        Task task2 = new Task();
        task2.setTitle("Completed Task");
        task2.setPriority(TaskPriority.HIGH);
        task2.setStatus(TaskStatus.COMPLETED);
        taskRepository.save(task2);

        Task task3 = new Task();
        task3.setTitle("Pending Task 2");
        task3.setPriority(TaskPriority.LOW);
        task3.setStatus(TaskStatus.PENDING);
        taskRepository.save(task3);

        given()
            .when()
                .get("/api/tasks/status/PENDING")
            .then()
                .statusCode(200)
                .body("$", hasSize(2))
                .body("title", hasItems("Pending Task 1", "Pending Task 2"));
    }

    @Test
    void testGetTasksByPriority_Success() {
        Task task1 = new Task();
        task1.setTitle("High Priority Task 1");
        task1.setPriority(TaskPriority.HIGH);
        task1.setStatus(TaskStatus.PENDING);
        taskRepository.save(task1);

        Task task2 = new Task();
        task2.setTitle("Medium Priority Task");
        task2.setPriority(TaskPriority.MEDIUM);
        task2.setStatus(TaskStatus.PENDING);
        taskRepository.save(task2);

        Task task3 = new Task();
        task3.setTitle("High Priority Task 2");
        task3.setPriority(TaskPriority.HIGH);
        task3.setStatus(TaskStatus.PENDING);
        taskRepository.save(task3);

        given()
            .when()
                .get("/api/tasks/priority/HIGH")
            .then()
                .statusCode(200)
                .body("$", hasSize(2))
                .body("title", hasItems("High Priority Task 1", "High Priority Task 2"));
    }

    @Test
    void testGetPendingTasks_Success() {
        Task task1 = new Task();
        task1.setTitle("Pending Task");
        task1.setPriority(TaskPriority.MEDIUM);
        task1.setStatus(TaskStatus.PENDING);
        taskRepository.save(task1);

        Task task2 = new Task();
        task2.setTitle("Completed Task");
        task2.setPriority(TaskPriority.HIGH);
        task2.setStatus(TaskStatus.COMPLETED);
        taskRepository.save(task2);

        given()
            .when()
                .get("/api/tasks/pending")
            .then()
                .statusCode(200)
                .body("$", hasSize(1))
                .body("[0].title", equalTo("Pending Task"))
                .body("[0].status", equalTo("PENDING"));
    }

    @Test
    void testGetCompletedTasks_Success() {
        Task task1 = new Task();
        task1.setTitle("Pending Task");
        task1.setPriority(TaskPriority.MEDIUM);
        task1.setStatus(TaskStatus.PENDING);
        taskRepository.save(task1);

        Task task2 = new Task();
        task2.setTitle("Completed Task");
        task2.setPriority(TaskPriority.HIGH);
        task2.setStatus(TaskStatus.COMPLETED);
        taskRepository.save(task2);

        given()
            .when()
                .get("/api/tasks/completed")
            .then()
                .statusCode(200)
                .body("$", hasSize(1))
                .body("[0].title", equalTo("Completed Task"))
                .body("[0].status", equalTo("COMPLETED"));
    }

    @Test
    void testCountTasks_Success() {
        Task task1 = new Task();
        task1.setTitle("Task 1");
        task1.setPriority(TaskPriority.MEDIUM);
        taskRepository.save(task1);

        Task task2 = new Task();
        task2.setTitle("Task 2");
        task2.setPriority(TaskPriority.HIGH);
        taskRepository.save(task2);

        given()
            .when()
                .get("/api/tasks/count")
            .then()
                .statusCode(200)
                .body(equalTo("2"));
    }

    @Test
    void testCountTasksByStatus_Success() {
        Task task1 = new Task();
        task1.setTitle("Pending Task 1");
        task1.setPriority(TaskPriority.MEDIUM);
        task1.setStatus(TaskStatus.PENDING);
        taskRepository.save(task1);

        Task task2 = new Task();
        task2.setTitle("Pending Task 2");
        task2.setPriority(TaskPriority.HIGH);
        task2.setStatus(TaskStatus.PENDING);
        taskRepository.save(task2);

        Task task3 = new Task();
        task3.setTitle("Completed Task");
        task3.setPriority(TaskPriority.LOW);
        task3.setStatus(TaskStatus.COMPLETED);
        taskRepository.save(task3);

        given()
            .when()
                .get("/api/tasks/count/status/PENDING")
            .then()
                .statusCode(200)
                .body(equalTo("2"));
    }
}

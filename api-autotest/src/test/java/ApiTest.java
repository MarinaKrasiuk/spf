import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;

import models.Priority;
import models.Status;
import models.Task;
import com.fasterxml.jackson.core.type.TypeReference;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.stream.IntStream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class ApiTest {
    private static final Logger log = LogManager.getLogger(ApiTest.class);
    static List<Task> sampleTasks;

    @BeforeClass
    static void setup() throws IOException {
        String env = System.getProperty("env", "dev");
        log.info("Currently running env {}", env);
        String base = System.getProperty("baseUrl", "http://localhost:8080");
        RestAssured.baseURI = base;

        File file = Paths.get("src", "main", "resources", env + "tasks.json").toFile();
        if (!file.exists()) {
            throw new IllegalStateException("❌ " + env + "tasks.json not found at: " + file.getAbsolutePath());
        }

        ObjectMapper mapper = new ObjectMapper();
        sampleTasks = mapper.readValue(file, new TypeReference<List<Task>>() {
        });
    }


    @Test
    void getAllTasks() {
        List<Task> actualTasks = given()
                .when().get("/tasks")
                .then()
                .statusCode(200)
                .body("$", notNullValue())
                .extract()
                .body()
                .as(new TypeRef<List<Task>>() {
                });

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(actualTasks.size(), sampleTasks.size(), "Task list size mismatch");
        log.info("Get all tasks: {}", actualTasks.size());
        IntStream.range(0, Math.min(sampleTasks.size(), actualTasks.size()))
                .forEach(i -> compareTaskFields(softAssert, sampleTasks.get(i), actualTasks.get(i), i));

        softAssert.assertAll();
    }

    @Test
    void createTask() {
        Task newTask = new Task();
        newTask.setTitle("Independent Task");
        newTask.setDescription("Created independently");
        newTask.setStatus(Status.TODO);
        newTask.setPriority(Priority.LOW);
        newTask.setAssigneeEmail("independent@test.com");
        log.info("Created task is : {}", newTask.toString());
        Task createdTask = given()
                .header("Content-Type", "application/json")
                .body(newTask)
                .when().post("/tasks")
                .then()
                .statusCode(200)
                .extract().as(Task.class);
        sampleTasks.add(createdTask);
        SoftAssert softAssert = new SoftAssert();
        compareTaskFields(softAssert, newTask, createdTask, -1);
        softAssert.assertAll();
    }

    @Test
    void updateTask() {
        Task newTask = new Task();
        newTask.setTitle("Task To Update");
        newTask.setDescription("Before update");
        newTask.setStatus(Status.TODO);
        newTask.setPriority(Priority.MEDIUM);
        newTask.setAssigneeEmail("update@test.com");

        Task createdTask = given()
                .header("Content-Type", "application/json")
                .body(newTask)
                .when().post("/tasks")
                .then()
                .statusCode(200)
                .extract().as(Task.class);
        sampleTasks.add(createdTask);
        createdTask.setTitle("Updated Title");
        createdTask.setDescription("Updated Description");

        Task updatedTask = given()
                .header("Content-Type", "application/json")
                .body(createdTask)
                .when().put("/tasks/" + createdTask.getId())
                .then()
                .statusCode(200)
                .extract().as(Task.class);
        for (int i = 0; i < sampleTasks.size(); i++) {
            if (sampleTasks.get(i).getId().equals(updatedTask.getId())) {
                sampleTasks.set(i, updatedTask);
                break;
            }
        }

        SoftAssert softAssert = new SoftAssert();
        compareTaskFields(softAssert, createdTask, updatedTask, -1);
        softAssert.assertAll();
    }

    private void compareTaskFields(SoftAssert softAssert, Task expected, Task actual, int index) {
        String idx = index >= 0 ? String.valueOf(index) : "independent";
        log.info("Expected task : {}", expected.toString());
        log.info("Actual task : {}", actual.toString());
        if (expected.getId() != null) {
            softAssert.assertEquals(actual.getId(), expected.getId(), "Task id mismatch at index " + idx);
        }
        softAssert.assertEquals(actual.getTitle(), expected.getTitle(), "Task title mismatch at index " + idx);
        softAssert.assertEquals(actual.getDescription(), expected.getDescription(), "Task description mismatch at index " + idx);
        softAssert.assertEquals(actual.getStatus(), expected.getStatus(), "Task status mismatch at index " + idx);
        softAssert.assertEquals(actual.getPriority(), expected.getPriority(), "Task priority mismatch at index " + idx);
        softAssert.assertEquals(actual.getAssigneeEmail(), expected.getAssigneeEmail(), "Task assigneeEmail mismatch at index " + idx);
    }
}

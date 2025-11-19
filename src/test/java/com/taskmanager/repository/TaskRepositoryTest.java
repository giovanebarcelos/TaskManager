package com.taskmanager.repository;

import com.taskmanager.model.Task;
import com.taskmanager.model.Task.TaskPriority;
import com.taskmanager.model.Task.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    private Task testTask;

    @BeforeEach
    void setUp() {
        taskRepository.deleteAll();
        
        testTask = new Task();
        testTask.setTitle("Test Task");
        testTask.setDescription("Test Description");
        testTask.setStatus(TaskStatus.PENDING);
        testTask.setPriority(TaskPriority.MEDIUM);
    }

    @Test
    void testSaveTask_Success() {
        Task savedTask = taskRepository.save(testTask);

        assertNotNull(savedTask);
        assertNotNull(savedTask.getId());
        assertEquals("Test Task", savedTask.getTitle());
    }

    @Test
    void testFindById_Success() {
        Task savedTask = taskRepository.save(testTask);

        Optional<Task> foundTask = taskRepository.findById(savedTask.getId());

        assertTrue(foundTask.isPresent());
        assertEquals(savedTask.getId(), foundTask.get().getId());
        assertEquals("Test Task", foundTask.get().getTitle());
    }

    @Test
    void testFindById_NotFound() {
        Optional<Task> foundTask = taskRepository.findById(999L);

        assertFalse(foundTask.isPresent());
    }

    @Test
    void testFindByStatus_Success() {
        Task task1 = new Task();
        task1.setTitle("Pending Task 1");
        task1.setPriority(TaskPriority.HIGH);
        task1.setStatus(TaskStatus.PENDING);
        taskRepository.save(task1);

        Task task2 = new Task();
        task2.setTitle("Completed Task");
        task2.setPriority(TaskPriority.MEDIUM);
        task2.setStatus(TaskStatus.COMPLETED);
        taskRepository.save(task2);

        Task task3 = new Task();
        task3.setTitle("Pending Task 2");
        task3.setPriority(TaskPriority.LOW);
        task3.setStatus(TaskStatus.PENDING);
        taskRepository.save(task3);

        List<Task> pendingTasks = taskRepository.findByStatus(TaskStatus.PENDING);

        assertEquals(2, pendingTasks.size());
        assertTrue(pendingTasks.stream().allMatch(t -> t.getStatus() == TaskStatus.PENDING));
    }

    @Test
    void testFindByPriority_Success() {
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
        task3.setStatus(TaskStatus.IN_PROGRESS);
        taskRepository.save(task3);

        List<Task> highPriorityTasks = taskRepository.findByPriority(TaskPriority.HIGH);

        assertEquals(2, highPriorityTasks.size());
        assertTrue(highPriorityTasks.stream().allMatch(t -> t.getPriority() == TaskPriority.HIGH));
    }

    @Test
    void testFindByOrderByCreatedAtDesc_Success() {
        Task task1 = new Task();
        task1.setTitle("First Task");
        task1.setPriority(TaskPriority.MEDIUM);
        taskRepository.save(task1);

        Task task2 = new Task();
        task2.setTitle("Second Task");
        task2.setPriority(TaskPriority.HIGH);
        taskRepository.save(task2);

        Task task3 = new Task();
        task3.setTitle("Third Task");
        task3.setPriority(TaskPriority.LOW);
        taskRepository.save(task3);

        List<Task> tasks = taskRepository.findByOrderByCreatedAtDesc();

        assertEquals(3, tasks.size());
        // As tarefas devem estar em ordem decrescente de criação
        assertEquals("Third Task", tasks.get(0).getTitle());
    }

    @Test
    void testFindByStatusOrderByCreatedAtDesc_Success() {
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

        List<Task> pendingTasks = taskRepository.findByStatusOrderByCreatedAtDesc(TaskStatus.PENDING);

        assertEquals(2, pendingTasks.size());
        assertEquals("Pending Task 2", pendingTasks.get(0).getTitle());
    }

    @Test
    void testFindByPriorityOrderByCreatedAtDesc_Success() {
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
        task3.setStatus(TaskStatus.IN_PROGRESS);
        taskRepository.save(task3);

        List<Task> highPriorityTasks = taskRepository.findByPriorityOrderByCreatedAtDesc(TaskPriority.HIGH);

        assertEquals(2, highPriorityTasks.size());
        assertEquals("High Priority Task 2", highPriorityTasks.get(0).getTitle());
    }

    @Test
    void testCountByStatus_Success() {
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

        long pendingCount = taskRepository.countByStatus(TaskStatus.PENDING);
        long completedCount = taskRepository.countByStatus(TaskStatus.COMPLETED);

        assertEquals(2, pendingCount);
        assertEquals(1, completedCount);
    }

    @Test
    void testDeleteTask_Success() {
        Task savedTask = taskRepository.save(testTask);
        Long taskId = savedTask.getId();

        taskRepository.delete(savedTask);

        Optional<Task> deletedTask = taskRepository.findById(taskId);
        assertFalse(deletedTask.isPresent());
    }

    @Test
    void testUpdateTask_Success() {
        Task savedTask = taskRepository.save(testTask);

        savedTask.setTitle("Updated Title");
        savedTask.setStatus(TaskStatus.COMPLETED);
        Task updatedTask = taskRepository.save(savedTask);

        assertEquals("Updated Title", updatedTask.getTitle());
        assertEquals(TaskStatus.COMPLETED, updatedTask.getStatus());
    }
}

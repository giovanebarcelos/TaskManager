package com.taskmanager.model;

import com.taskmanager.model.Task.TaskPriority;
import com.taskmanager.model.Task.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    private Task task;

    @BeforeEach
    void setUp() {
        task = new Task();
    }

    @Test
    void testTaskCreation() {
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setPriority(TaskPriority.HIGH);
        task.setStatus(TaskStatus.PENDING);

        assertEquals("Test Task", task.getTitle());
        assertEquals("Test Description", task.getDescription());
        assertEquals(TaskPriority.HIGH, task.getPriority());
        assertEquals(TaskStatus.PENDING, task.getStatus());
    }

    @Test
    void testCompleteTask() {
        task.setTitle("Task to Complete");
        task.setStatus(TaskStatus.PENDING);

        task.complete();

        assertEquals(TaskStatus.COMPLETED, task.getStatus());
        assertNotNull(task.getCompletedAt());
    }

    @Test
    void testCancelTask() {
        task.setTitle("Task to Cancel");
        task.setStatus(TaskStatus.PENDING);

        task.cancel();

        assertEquals(TaskStatus.CANCELLED, task.getStatus());
    }

    @Test
    void testTaskStatusDisplayName() {
        assertEquals("Pendente", TaskStatus.PENDING.getDisplayName());
        assertEquals("Em Progresso", TaskStatus.IN_PROGRESS.getDisplayName());
        assertEquals("Concluída", TaskStatus.COMPLETED.getDisplayName());
        assertEquals("Cancelada", TaskStatus.CANCELLED.getDisplayName());
    }

    @Test
    void testTaskPriorityDisplayName() {
        assertEquals("Baixa", TaskPriority.LOW.getDisplayName());
        assertEquals("Média", TaskPriority.MEDIUM.getDisplayName());
        assertEquals("Alta", TaskPriority.HIGH.getDisplayName());
        assertEquals("Urgente", TaskPriority.URGENT.getDisplayName());
    }

    @Test
    void testAllArgsConstructor() {
        LocalDateTime now = LocalDateTime.now();
        Task fullTask = new Task(1L, "Title", "Description", 
                                 TaskStatus.IN_PROGRESS, TaskPriority.URGENT, 
                                 now, null);

        assertEquals(1L, fullTask.getId());
        assertEquals("Title", fullTask.getTitle());
        assertEquals("Description", fullTask.getDescription());
        assertEquals(TaskStatus.IN_PROGRESS, fullTask.getStatus());
        assertEquals(TaskPriority.URGENT, fullTask.getPriority());
        assertEquals(now, fullTask.getCreatedAt());
        assertNull(fullTask.getCompletedAt());
    }

    @Test
    void testNoArgsConstructor() {
        Task emptyTask = new Task();
        assertNotNull(emptyTask);
    }

    @Test
    void testSettersAndGetters() {
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime completedAt = LocalDateTime.now().plusDays(1);

        task.setId(100L);
        task.setTitle("New Title");
        task.setDescription("New Description");
        task.setStatus(TaskStatus.COMPLETED);
        task.setPriority(TaskPriority.LOW);
        task.setCreatedAt(createdAt);
        task.setCompletedAt(completedAt);

        assertEquals(100L, task.getId());
        assertEquals("New Title", task.getTitle());
        assertEquals("New Description", task.getDescription());
        assertEquals(TaskStatus.COMPLETED, task.getStatus());
        assertEquals(TaskPriority.LOW, task.getPriority());
        assertEquals(createdAt, task.getCreatedAt());
        assertEquals(completedAt, task.getCompletedAt());
    }
}

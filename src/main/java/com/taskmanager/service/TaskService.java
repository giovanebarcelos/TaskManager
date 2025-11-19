package com.taskmanager.service;

import com.taskmanager.exception.ResourceNotFoundException;
import com.taskmanager.model.Task;
import com.taskmanager.model.Task.TaskPriority;
import com.taskmanager.model.Task.TaskStatus;
import com.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional
    public Task createTask(Task task) {
        if (task.getStatus() == null) {
            task.setStatus(TaskStatus.PENDING);
        }
        if (task.getPriority() == null) {
            task.setPriority(TaskPriority.MEDIUM);
        }
        return taskRepository.save(task);
    }

    @Transactional
    public Task updateTask(Long id, Task updatedTask) {
        Task task = findTaskById(id);
        
        if (updatedTask.getTitle() != null && !updatedTask.getTitle().trim().isEmpty()) {
            task.setTitle(updatedTask.getTitle());
        }
        if (updatedTask.getDescription() != null) {
            task.setDescription(updatedTask.getDescription());
        }
        if (updatedTask.getStatus() != null) {
            task.setStatus(updatedTask.getStatus());
        }
        if (updatedTask.getPriority() != null) {
            task.setPriority(updatedTask.getPriority());
        }
        
        return taskRepository.save(task);
    }

    @Transactional
    public void completeTask(Long id) {
        Task task = findTaskById(id);
        task.complete();
        taskRepository.save(task);
    }

    @Transactional
    public void cancelTask(Long id) {
        Task task = findTaskById(id);
        task.cancel();
        taskRepository.save(task);
    }

    @Transactional
    public void deleteTask(Long id) {
        Task task = findTaskById(id);
        taskRepository.delete(task);
    }

    public Task findTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada com ID: " + id));
    }

    public List<Task> getAllTasks() {
        return taskRepository.findByOrderByCreatedAtDesc();
    }

    public List<Task> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatusOrderByCreatedAtDesc(status);
    }

    public List<Task> getTasksByPriority(TaskPriority priority) {
        return taskRepository.findByPriorityOrderByCreatedAtDesc(priority);
    }

    public List<Task> getPendingTasks() {
        return getTasksByStatus(TaskStatus.PENDING);
    }

    public List<Task> getCompletedTasks() {
        return getTasksByStatus(TaskStatus.COMPLETED);
    }

    public long countTasks() {
        return taskRepository.count();
    }

    public long countTasksByStatus(TaskStatus status) {
        return taskRepository.countByStatus(status);
    }
}

package com.taskmanager.controller;

import com.taskmanager.model.Task;
import com.taskmanager.model.Task.TaskPriority;
import com.taskmanager.model.Task.TaskStatus;
import com.taskmanager.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/tasks")
public class TaskWebController {

    private final TaskService taskService;

    public TaskWebController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public String listTasks(@RequestParam(required = false) String filter, Model model) {
        if (filter != null) {
            switch (filter) {
                case "pending":
                    model.addAttribute("tasks", taskService.getPendingTasks());
                    break;
                case "completed":
                    model.addAttribute("tasks", taskService.getCompletedTasks());
                    break;
                default:
                    model.addAttribute("tasks", taskService.getAllTasks());
            }
        } else {
            model.addAttribute("tasks", taskService.getAllTasks());
        }
        model.addAttribute("statuses", TaskStatus.values());
        model.addAttribute("priorities", TaskPriority.values());
        return "tasks/list";
    }

    @GetMapping("/new")
    public String newTaskForm(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("priorities", TaskPriority.values());
        return "tasks/form";
    }

    @PostMapping
    public String createTask(@Valid @ModelAttribute Task task, 
                           BindingResult result, 
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "tasks/form";
        }
        taskService.createTask(task);
        redirectAttributes.addFlashAttribute("success", "Tarefa criada com sucesso!");
        return "redirect:/tasks";
    }

    @GetMapping("/{id}/edit")
    public String editTaskForm(@PathVariable Long id, Model model) {
        model.addAttribute("task", taskService.findTaskById(id));
        model.addAttribute("statuses", TaskStatus.values());
        model.addAttribute("priorities", TaskPriority.values());
        return "tasks/edit";
    }

    @PostMapping("/{id}")
    public String updateTask(@PathVariable Long id, 
                           @Valid @ModelAttribute Task task,
                           BindingResult result,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "tasks/edit";
        }
        taskService.updateTask(id, task);
        redirectAttributes.addFlashAttribute("success", "Tarefa atualizada com sucesso!");
        return "redirect:/tasks";
    }

    @PostMapping("/{id}/complete")
    public String completeTask(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        taskService.completeTask(id);
        redirectAttributes.addFlashAttribute("success", "Tarefa concluída!");
        return "redirect:/tasks";
    }

    @PostMapping("/{id}/cancel")
    public String cancelTask(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        taskService.cancelTask(id);
        redirectAttributes.addFlashAttribute("success", "Tarefa cancelada!");
        return "redirect:/tasks";
    }

    @PostMapping("/{id}/delete")
    public String deleteTask(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        taskService.deleteTask(id);
        redirectAttributes.addFlashAttribute("success", "Tarefa excluída!");
        return "redirect:/tasks";
    }

    @GetMapping("/")
    public String redirectToTasks() {
        return "redirect:/tasks";
    }
}

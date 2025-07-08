package com.soft.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soft.dto.TaskDto;
import com.soft.dto.UpdateTaskForm;
import com.soft.entity.Task;
import com.soft.service.TaskService;

@CrossOrigin(origins = "*") 
@RestController
@RequestMapping("/api")
public class TaskRestController {
	@Autowired
	private TaskService taskService;

	@PostMapping("/tasks")
	public ResponseEntity<String> createTask(@RequestBody TaskDto taskDto) {
		boolean status = taskService.createTask(taskDto);
		if (status) {
			return new ResponseEntity<String>("Task created successfully...", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("An error occurred while processing your request...",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/alltasks")
	public ResponseEntity<List<TaskDto>> getAllPlans() {
		List<TaskDto> allTasks = taskService.getAllTasks();
		return new ResponseEntity<List<TaskDto>>(allTasks, HttpStatus.OK);
	}

	
	@PutMapping("/tasks/{id}")
	public ResponseEntity<?> updateTask(@RequestBody UpdateTaskForm taskForm, @PathVariable("id") Long id) {
		try {
			Task updateTask = taskService.updateTask(taskForm, id);
			return new ResponseEntity<>(updateTask, HttpStatus.OK);
		} catch (RuntimeException ex) {
			return new ResponseEntity<>("Task not found with ID :"+id, HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/tasks/{id}")
	public ResponseEntity<String> deleteTask(@PathVariable Long id) {
		try {
			taskService.deleteTask(id);
	        return new ResponseEntity<>("Task deleted successfully...", HttpStatus.OK);
		} catch (RuntimeException e) {
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
    }

}

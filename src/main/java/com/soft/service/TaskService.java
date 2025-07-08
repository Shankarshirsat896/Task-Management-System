package com.soft.service;

import java.util.List;

import com.soft.dto.TaskDto;
import com.soft.dto.UpdateTaskForm;
import com.soft.entity.Task;

public interface TaskService {
	
	// create Tasks
	public boolean createTask(TaskDto taskDto);
	
	// get All the Task
	public List<TaskDto> getAllTasks();
	
	// update Task
	public Task updateTask(UpdateTaskForm taskForm, Long taskId);
	
	// delete Task
	public void deleteTask(Long taskId);

}

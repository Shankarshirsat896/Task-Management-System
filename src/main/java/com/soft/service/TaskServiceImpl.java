package com.soft.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soft.dto.TaskDto;
import com.soft.dto.UpdateTaskForm;
import com.soft.entity.Task;
import com.soft.repo.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService {
	
	@Autowired
	private TaskRepository taskRepository;

	@Override
	public boolean createTask(TaskDto taskDto) {
		Task taskEntity=new Task();		
		BeanUtils.copyProperties(taskDto, taskEntity);
		Task saveTask = taskRepository.save(taskEntity);
		return saveTask.getTaskId()!=null;
	}

	@Override
	public List<TaskDto> getAllTasks() {
		List<Task> entities = taskRepository.findAll();
		List<TaskDto> dtos=new ArrayList<>();
		entities.forEach(entity->{
			TaskDto dto=new TaskDto();
			BeanUtils.copyProperties(entity, dto);
			dtos.add(dto);
		});
		return dtos;
	}

	@Override
	public Task updateTask(UpdateTaskForm taskForm, Long taskId) {
		return taskRepository.findById(taskId).map(existingTask -> {
			existingTask.setTaskTitle(taskForm.getTaskTitle());
			existingTask.setTaskDescription(taskForm.getTaskDescription());
			return taskRepository.save(existingTask);
		}).orElseThrow(() -> new RuntimeException("Task not found with ID: " + taskId));
	}

	@Override
	public void deleteTask(Long taskId) {
		if (taskRepository.existsById(taskId)) {
			taskRepository.deleteById(taskId);
		} else {
			throw new RuntimeException("Task not found with ID: " + taskId);
		}
	}
	

}

package com.soft.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soft.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}

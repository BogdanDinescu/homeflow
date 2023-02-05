package com.fmi.homeflow.repository;

import com.fmi.homeflow.model.family.FamilyEntity;
import com.fmi.homeflow.model.task.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, UUID> {
    List<TaskEntity> findTaskByFamily(FamilyEntity familyEntity);
}

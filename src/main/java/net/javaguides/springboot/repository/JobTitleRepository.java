package net.javaguides.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.javaguides.springboot.model.JobTitle;

public interface JobTitleRepository extends JpaRepository<JobTitle, Long> {
}


package net.javaguides.springboot.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.DTO.jobtitle.JobTitleRequestDTO;
import net.javaguides.springboot.DTO.jobtitle.JobTitleResponseDTO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import net.javaguides.springboot.service.JobTitleService;

//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/jobTitles")
@RequiredArgsConstructor
public class JobTitleController {
    private final JobTitleService jobTitleService;

    @GetMapping
    public List<JobTitleResponseDTO> getAllJobTitles() {
        return jobTitleService.getAllJobTitles();
    }

    @PostMapping
    public JobTitleResponseDTO createJobTitle(
            @RequestBody JobTitleRequestDTO dto) {
        return jobTitleService.createJobTitle(dto);
    }

    @GetMapping("/{id}")
    public JobTitleResponseDTO getJobTitleById(
            @PathVariable Long id) {
        return jobTitleService.getJobTitleById(id);
    }

    @PutMapping("/{id}")
    public JobTitleResponseDTO updateJobTitle(
            @PathVariable Long id,
            @RequestBody JobTitleRequestDTO dto) {
        return jobTitleService.updateJobTitle(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteJobTitle(@PathVariable Long id) {
        jobTitleService.deleteJobTitle(id);
    }
}


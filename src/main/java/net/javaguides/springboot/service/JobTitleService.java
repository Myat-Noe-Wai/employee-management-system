package net.javaguides.springboot.service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.DTO.jobtitle.JobTitleRequestDTO;
import net.javaguides.springboot.DTO.jobtitle.JobTitleResponseDTO;
import org.springframework.stereotype.Service;

import net.javaguides.springboot.model.JobTitle;
import net.javaguides.springboot.repository.JobTitleRepository;

@Service
@RequiredArgsConstructor
public class JobTitleService {
    private final JobTitleRepository jobTitleRepository;

    public List<JobTitleResponseDTO> getAllJobTitles() {
        return jobTitleRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public JobTitleResponseDTO createJobTitle(JobTitleRequestDTO dto) {
        JobTitle jobTitle = mapToEntity(dto);
        JobTitle saved = jobTitleRepository.save(jobTitle);
        return mapToResponse(saved);
    }

    public JobTitleResponseDTO getJobTitleById(Long id) {
        JobTitle jobTitle = jobTitleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("JobTitle not found with id: " + id));
        return mapToResponse(jobTitle);
    }

    public void deleteJobTitle(Long id) {
        jobTitleRepository.deleteById(id);
    }

    public JobTitleResponseDTO updateJobTitle(Long id, JobTitleRequestDTO dto) {
        JobTitle jobTitle = jobTitleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("JobTitle not found with id: " + id));

        jobTitle.setName(dto.getName());
        jobTitle.setDescription(dto.getDescription());

        JobTitle updated = jobTitleRepository.save(jobTitle);
        return mapToResponse(updated);
    }

    // -------- Mapping --------

    private JobTitle mapToEntity(JobTitleRequestDTO dto) {
        JobTitle jobTitle = new JobTitle();
        jobTitle.setName(dto.getName());
        jobTitle.setDescription(dto.getDescription());
        return jobTitle;
    }

    private JobTitleResponseDTO mapToResponse(JobTitle jobTitle) {
        return new JobTitleResponseDTO(
                jobTitle.getId(),
                jobTitle.getName(),
                jobTitle.getDescription()
        );
    }
}


package net.javaguides.springboot.DTO.jobtitle;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JobTitleResponseDTO {
    private Long id;
    private String name;
    private String description;
}

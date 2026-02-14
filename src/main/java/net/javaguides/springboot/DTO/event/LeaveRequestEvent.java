package net.javaguides.springboot.DTO.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LeaveRequestEvent {
    private String email;
    private String subject;
    private String message;
}


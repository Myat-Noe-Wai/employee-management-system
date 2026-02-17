package net.javaguides.springboot.listeners;

import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.DTO.event.LeaveRequestEvent;
import net.javaguides.springboot.service.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailEventListener {

    private final EmailService emailService;

    @EventListener
    public void handleLeaveEvent(LeaveRequestEvent event){
        if(event.getAttachment() != null){
            emailService.sendPayslipWithAttachment(
                    event.getEmail(),
                    event.getSubject(),
                    event.getMessage(),
                    event.getAttachment()
            );
        }else{
            emailService.sendHtmlEmail(
                    event.getEmail(),
                    event.getSubject(),
                    event.getMessage()
            );
        }
    }
}


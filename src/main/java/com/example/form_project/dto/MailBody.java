package com.example.form_project.dto;

import lombok.Builder;

@Builder
public record MailBody(String to,
                       String subject,
                       String text) {
}

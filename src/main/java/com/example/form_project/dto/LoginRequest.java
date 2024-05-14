package com.example.form_project.dto;

import lombok.Data;


public record LoginRequest(
        String email,
        String password
){

}

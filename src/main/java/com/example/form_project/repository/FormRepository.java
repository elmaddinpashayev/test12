package com.example.form_project.repository;

import com.example.form_project.model.Form;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormRepository extends JpaRepository<Form,Integer> {

}

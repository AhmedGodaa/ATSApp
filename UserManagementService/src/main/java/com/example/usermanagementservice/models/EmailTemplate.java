package com.example.usermanagementservice.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EmailTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name is mandatory for email template.")
    private String name;
    @NotBlank(message = "Content is mandatory for email template.")
    @Column(columnDefinition = "TEXT")
    private String content;

    public EmailTemplate(String name, String content) {
        this.name = name;
        this.content = content;
    }
}
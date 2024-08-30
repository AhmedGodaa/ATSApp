package com.example.usermanagementservice.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Workspace {
    private String id;
    private String name;
    private String description;
    private String ownerId;
}

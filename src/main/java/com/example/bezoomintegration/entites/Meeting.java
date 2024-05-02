package com.example.bezoomintegration.entites;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(length = 1000) // Increase the length as needed
    private String join_url;
    private String topic;
    @Column(length = 1000) // Increase the length as needed
    private String start_url;
    @JsonProperty("host_email")  // Map the JSON field name to the entity field
    private String hostEmail;

}

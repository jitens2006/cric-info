package com.poc.cric.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
public class Player {

    @Id
    @NotNull(message = "Primary Key is required")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @Schema(name = "Player ID", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
    private int id;

    @Schema(name = "Player Name", example = "Full Name: Virat Kohli", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Schema(name = "Country", example = "Full Country Name: India", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Country cannot be blank")
    private String country;
    
    @Schema(name = "runs", example = "6789", requiredMode = Schema.RequiredMode.REQUIRED)
    private int runs;

    @Schema(name = "wickets", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
    private int wickets;

}

package com.cts.fia.investigation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class DecisionDTO {

	@NotBlank(message = "Decision cannot be empty")
	private String decision;
	
	@NotBlank(message = "Comments cannot be empty")
	private String comments;
}

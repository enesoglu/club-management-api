package com.cms.clubmanagementapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateTermRequest {

    @NotBlank(message = "Term name cannot be blank.")
    private String name;

}

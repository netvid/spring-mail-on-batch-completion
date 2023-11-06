package com.example.application.api;

import com.example.application.dto.JobInfoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.example.application.constants.ApiConstants.BASE_URL;
import static com.example.application.constants.ApiConstants.CURRENT_VERSION;

@RequestMapping(BASE_URL + "/device" + CURRENT_VERSION)
public interface DeviceApi {
    @Operation(summary = "Run a job by its bean name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "The job has launched success"),
            @ApiResponse(responseCode = "500",description = "An internal error has occurred during the job execution")
    })
    @PostMapping("/{beanName}")
    ResponseEntity<JobInfoDTO> run(@PathVariable String beanName);
}

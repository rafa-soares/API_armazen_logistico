package com.wms.appointment.entrypoint.handler;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {
    private int status;
    private String error;
    private String message;
    private String path;
}
package com.wms.receiving.entrypoint.handler;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {
    private int status;
    private String error;
    private String message;
    private String path;
}

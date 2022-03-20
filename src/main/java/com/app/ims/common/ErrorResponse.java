package com.app.ims.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private String message;
    private String suggestion;
    private List<String> ErrorDetails;
    private String path;
    private Instant timestamp = Instant.now();

    /**
     * Constructs a {@code ErrorResponse} with the specified message and timestamp
     * cause.
     * @param message the detail message.
     * @param timestamp root cause
     */
    public ErrorResponse(String message, Instant timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    /**
     * Constructs a {@code ErrorResponse} with the specified message, error details and timestamp
     * cause.
     * @param message the detail message.
     * @param errorDetails root cause
     * @param timestamp error timestamp
     */
    public ErrorResponse(String message, List<String> errorDetails, Instant timestamp) {
        this.message = message;
        ErrorDetails = errorDetails;
        this.timestamp = timestamp;
    }

    public ErrorResponse(String message, String suggestion, List<String> errorDetails) {
        this.message = message;
        this.suggestion = suggestion;
        ErrorDetails = errorDetails;
        setPath();
    }

    public void setPath() {
        String requestURI = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest().getRequestURI();
        this.setPath(requestURI);
    }
}

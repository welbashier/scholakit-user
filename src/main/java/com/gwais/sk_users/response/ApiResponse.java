package com.gwais.sk_users.response;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponse<T> {
	
    private HttpStatus status;
    private String message;
    private ApiMetaData meta;
    private T data;

    public ApiResponse() {
    }

    public ApiResponse(HttpStatus status, T data, ApiMetaData meta) {
        this.status = status;
        this.data = data;
        this.meta = meta;
    }

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public ApiMetaData getMeta() {
		return meta;
	}

	public void setMeta(ApiMetaData meta) {
		this.meta = meta;
	}
	
	public ResponseEntity<ApiResponse<T>> buildResponse(T data, Instant startTime, Instant endTime) {
		
        LocalDateTime requestDate = LocalDateTime.now();
        int recordsCount = getRecordsCount(data);
        long elapsedTime = Duration.between(startTime, endTime).toMillis();

        ApiMetaData meta = new ApiMetaData(requestDate, elapsedTime, recordsCount);

        ApiResponse<T> composedResponse = new ApiResponse<>(HttpStatus.OK, data, meta);

        return ResponseEntity.ok(composedResponse);
    }
	
	public ResponseEntity<ApiResponse<T>> buildResponse(T data, Instant startTime, Instant endTime, 
			String clientIp, String serverIp) {
		
        LocalDateTime requestDate = LocalDateTime.now();
        int recordsCount = getRecordsCount(data);
        long elapsedTime = Duration.between(startTime, endTime).toMillis();

        ApiMetaData meta = new ApiMetaData(requestDate, elapsedTime, recordsCount, clientIp, serverIp);

        ApiResponse<T> composedResponse = new ApiResponse<>(HttpStatus.OK, data, meta);

        return ResponseEntity.ok(composedResponse);
    }

	private int getRecordsCount(T data) {
		int recordsCount;
        if (data instanceof List) {
            recordsCount = ((List<?>) data).size();
        } else {
            recordsCount = (data != null) ? 1 : 0;
        }
		return recordsCount;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
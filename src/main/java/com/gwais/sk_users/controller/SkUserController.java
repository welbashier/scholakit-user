package com.gwais.sk_users.controller;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gwais.sk_users.model.SmUser;
import com.gwais.sk_users.response.ApiResponse;
import com.gwais.sk_users.service.SkUserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/users") // browse to: http://localhost:<port-number-from-config-file>/users
public class SkUserController {
	
	@Autowired
	SkUserService userService;

	SmUser record;
	List<SmUser> records;
	ResponseEntity<ApiResponse<SmUser>> response;
	Instant startTime;
	Instant endTime;
	String message;
    String clientIp;
    String serverIp;
	
	
	@GetMapping("")
	public ResponseEntity<ApiResponse<List<SmUser>>> readAll(HttpServletRequest request) {
		
        // Get the client and server IP addresses
        clientIp = request.getRemoteAddr();
        serverIp = request.getLocalAddr();
        
		startTime = Instant.now();
		records = userService.findAll();
		endTime = Instant.now();

		return new ApiResponse<List<SmUser>>()
				.buildResponse(records, startTime, endTime, clientIp, serverIp);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<SmUser>> readOneById(
			@PathVariable("id") long id, 
			HttpServletRequest request) {

		startTime = Instant.now();
		record = userService.findOneById(id);
		endTime = Instant.now();

		return new ApiResponse<SmUser>().buildResponse(record, startTime, endTime);
	}
	
	@GetMapping("/new")
	public ResponseEntity<ApiResponse<SmUser>> getAnEmptyObject(HttpServletRequest request) {

		startTime = Instant.now();
		record = new SmUser();
		endTime = Instant.now();

		return new ApiResponse<SmUser>().buildResponse(record, startTime, endTime);
	}
	
	@PostMapping("/add")
	public ResponseEntity<ApiResponse<SmUser>> creatOne(
			@RequestBody SmUser newUser, 
			HttpServletRequest request) {
		
		startTime = Instant.now();
		Long newId = newUser.getUserId();
		// if ID is provided (for new record) return an error
		if (newId != null) {
			endTime = Instant.now();
			response = new ApiResponse<SmUser>().buildResponse(record, startTime, endTime);
			message = "Adding ID for a new record is not allowed!";
			response.getBody().setStatus(HttpStatus.BAD_REQUEST);
			response.getBody().setMessage(message);
			
		} else {
			// All is OK and ready, go save or get error
			response = saveToDatabaseAndCheckForErrors(newUser, newUser.getUserId(), 'I');
		}

		return response;
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<SmUser>> modifyOne(
			@RequestBody SmUser changedUser,
			@PathVariable(name = "id", required = true) Long id) {

		Long changedId = changedUser.getUserId();
		startTime = Instant.now();
		
		if (changedId == null || !changedId.equals(id)) {
			// error!
			endTime = Instant.now();
			record = null;
			if (changedId == null) {
				message = "Missing ID in the request body!";
			} else {
				message = "Parameter ID doesn't match ID in the request body!";
			}
			response = new ApiResponse<SmUser>().buildResponse(record, startTime, endTime);
			response.getBody().setStatus(HttpStatus.BAD_REQUEST);
			response.getBody().setMessage(message);
			
		} else {
			// All is OK and ready, go save or get error
			response = saveToDatabaseAndCheckForErrors(changedUser, changedId, 'U');
		}
		
		return response;
	}
	

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<SmUser>> deleteOneById(
			@PathVariable("id") long id, 
			HttpServletRequest request) {

		startTime = Instant.now();
		Long deletedId = userService.deleteOneById(id);
		endTime = Instant.now();

		response = new ApiResponse<SmUser>().buildResponse(record, startTime, endTime);
		message = "Record #" + deletedId + " was deleted successfully";
		response.getBody().setMessage(message);
		
		return response;
	}

	private ResponseEntity<ApiResponse<SmUser>> 
				saveToDatabaseAndCheckForErrors(SmUser changedUser, Long changedId, char operation) {
		
		ResponseEntity<ApiResponse<SmUser>> databaseResponse;
		
		try {
			
			if (operation == 'U') {
				record = userService.modifyOne(changedUser);
			} else if (operation == 'I') {
				record = userService.insertOne(changedUser);
			}
			endTime = Instant.now();
			message = "Record #" + record.getUserId() + " was saved successfully";
			databaseResponse = new ApiResponse<SmUser>().buildResponse(record, startTime, endTime);
			databaseResponse.getBody().setMessage(message);
			
		} catch (Exception e) {
			// error!
			record = null;
			endTime = Instant.now();
			message = "Record #" + changedId + " was not saved, because: " + e;
			databaseResponse = new ApiResponse<SmUser>().buildResponse(record, startTime, endTime);
			databaseResponse.getBody().setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			databaseResponse.getBody().setMessage(message);
		}
		
		return databaseResponse;
	}
}
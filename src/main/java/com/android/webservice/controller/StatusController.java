package com.android.webservice.controller;

import java.util.List;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.android.webservice.exception.ResourceNotFoundException;
import com.android.webservice.model.Status;
import com.android.webservice.repository.StatusRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
public class StatusController {

	
	@Autowired
	private StatusRepository statusRepository;
	
	@GetMapping("/status")
	public List<Status> getStatus(){
		return statusRepository.findAll();
	}
	
	@PostMapping("/status")
	public Status createStatus(@Valid @RequestBody Status status) {
		return statusRepository.save(status);
	}
	
	@PutMapping("/status/{statusId}")
	public Status updateStatus(@PathVariable Long statusId,
			@Valid @RequestBody Status statusRequest) {
				
		
		return statusRepository.findById(statusId)
				.map(status ->{
					status.setTitle(statusRequest.getTitle());
					status.setDescription(statusRequest.getDescription());
					return statusRepository.save(status);
				}).orElseThrow(() -> new ResourceNotFoundException("Status not Found with ID" + statusId));
	}
	
	@DeleteMapping("/status/{statusId}")
	public ResponseEntity<?> deleteStatus(@PathVariable Long statusId){
		return statusRepository.findById(statusId)
				.map(status -> {
					statusRepository.delete(status);
					return ResponseEntity.ok().build();
				}).orElseThrow(() -> new ResourceNotFoundException("Status not Found with ID" + statusId));
	}
}

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
import com.android.webservice.model.Comment;
import com.android.webservice.repository.CommentRepository;
import com.android.webservice.repository.StatusRepository;

@RestController
public class CommentController {

	@Autowired
	private StatusRepository statusRepository;
	@Autowired
	private CommentRepository commentRepository;
	
	
	@GetMapping("/status/{statusId}/comment")
	public List<Comment> getCommentByStatusId(@PathVariable Long statusId){
		return commentRepository.findByStatusId(statusId);
	}
	
	@PostMapping("/status/{statusId}/comment")
	public Comment addComment(@PathVariable Long statusId,
			@Valid @RequestBody Comment comment) {
		
		return statusRepository.findById(statusId)
				.map(status -> {
					comment.setStatus(status);
					return commentRepository.save(comment);
				}).orElseThrow(() -> new ResourceNotFoundException("Status not Found " + statusId));
	}
	
	@PutMapping("/status/{statusId}/comment/{commentId}")
	public Comment updateComment(@PathVariable Long statusId, @PathVariable Long commentId,
			@Valid @RequestBody Comment commentRequest) {
		if(!statusRepository.existsById(statusId)) {
			throw new ResourceNotFoundException("Status not Found " + statusId);
		}
		return commentRepository.findById(commentId)
				.map(comment -> {
					comment.setText(commentRequest.getText());
					return commentRepository.save(comment);
				}).orElseThrow(() -> new ResourceNotFoundException("Comment not Found " + commentId));
	}
	
	@DeleteMapping("/status/{statusId}/comment/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable Long statusId, @PathVariable Long commentId){
		if(!statusRepository.existsById(statusId)) {
			throw new ResourceNotFoundException("Status not Found " + statusId);
		}
		
		return commentRepository.findById(commentId)
				.map(comment -> {
					commentRepository.delete(comment);
					System.out.println("Delete Successfully!");
					return ResponseEntity.ok().build();
				}).orElseThrow(() -> new ResourceNotFoundException("Comment not Found " + commentId));
				
	}
}

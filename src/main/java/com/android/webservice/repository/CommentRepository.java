package com.android.webservice.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.android.webservice.model.Comment;

@Repository
@Service
@Transactional
public interface CommentRepository extends JpaRepository<Comment, Long>{
	
	List<Comment> findByStatusId(Long statusId);
}

package com.android.webservice.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "status")
@Getter
@Setter
public class Status extends AuditModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	
	@NotBlank
	@Size(min = 3, max = 100)
	private String title;
	
	@Column(columnDefinition = "text")
	private String description;
	
	@OneToMany(mappedBy = "status", cascade = CascadeType.ALL)
	private List<Comment> comments;
}

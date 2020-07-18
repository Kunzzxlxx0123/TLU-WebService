package com.android.webservice.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.android.webservice.model.Status;

@Repository
@Service
@Transactional
public interface StatusRepository extends JpaRepository<Status, Long>{

}

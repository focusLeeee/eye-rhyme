package com.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.models.MovieTicket;


@Repository
public interface MovieTicketRepository extends CrudRepository<MovieTicket, Integer> {
	
}

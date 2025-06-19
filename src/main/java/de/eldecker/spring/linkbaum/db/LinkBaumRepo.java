package de.eldecker.spring.linkbaum.db;

import org.springframework.data.repository.CrudRepository;

import de.eldecker.spring.linkbaum.db.model.LinkBaum;

public interface LinkBaumRepo extends CrudRepository<LinkBaum, String>{

}

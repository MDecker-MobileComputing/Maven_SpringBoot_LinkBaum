package de.eldecker.spring.linkbaum.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.eldecker.spring.linkbaum.db.model.LinkBaum;


/**
 * Repo-Bean für Zugriff auf Valkey/Redis-Datenbank. 
 */
@Repository
public interface LinkBaumRepo extends CrudRepository<LinkBaum, String> {

}

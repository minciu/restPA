package jav.rest.repositories;


import jav.rest.baza.Game;
import org.springframework.data.repository.CrudRepository;
public interface GameRepository extends CrudRepository<Game, Long> {

}

package jav.rest.repositories;

import jav.rest.baza.Game;
import jav.rest.baza.Player;
import jav.rest.baza.PlayerGame;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlayerGameRepository extends CrudRepository<PlayerGame, Long> {
	 @Query("select pg.player from PlayerGame pg where pg.game.id = :gameId")
	    List<Player> findPlayersByGame(@Param("gameId") Long gameId);

	    @Query("select pg.game from PlayerGame pg where pg.player.id = :playerId")
	    List<Game> findGamesByPlayer(@Param("playerId") Long playerId);
}

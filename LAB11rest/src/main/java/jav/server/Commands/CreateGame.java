package jav.server.Commands;

import jav.server.Gomoku.Player;
import jav.server.Gomoku.Game;
import jav.server.Gomoku.GameManagement;
/**
 *
 * @author user
 */
public class CreateGame extends AbstractCommand{
    private final GameManagement gameManagement = GameManagement.getInstance();
    

    public CreateGame(String command) {
        super(command);
    }

    @Override
    public String execute(Object... arguments) {
        Player player = (Player) arguments[0];
        player.setToken('x');
        Game game = new Game();
        game.addPlayer(player);
        player.setGame(game);
        gameManagement.addGame(game);
        return "Game created. Your token is 'x'. Wait an opponent";
    }
}
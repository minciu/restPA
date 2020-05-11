package jav.server.Commands;

import jav.server.Gomoku.Player;
import jav.server.Gomoku.Game;
import jav.server.Gomoku.GameManagement;
/**
 *
 * @author user
 */
public class JoinGame extends AbstractCommand{
    private final GameManagement gameManagement = GameManagement.getInstance();
    

    public JoinGame(String command, String arguments) {
        super(command, arguments);
    }

    @Override
    public String execute(Object... arguments) {
        if (arguments.length != 2) {
            return "Format: join-game id";
        }
        Player player = (Player) arguments[0];
        int id = Integer.parseInt((String) arguments[1]);

        if (gameManagement.getGames().size() - 1 >= id && gameManagement.getGames().get(id).isAvailable()) {
            Game game = gameManagement.getGames().get(id);
            player.setGame(game);
            player.setToken('o');
            game.addPlayer(player);
            return "You joined the game " + id + ". Your token is 'o'. Your opponent is " + game.getPlayers().get(0).getName();
        }

        return "Game unavailable";
    }
}

package jav.server.Commands;

import jav.server.Gomoku.GameManagement;
/**
 *
 * @author user
 */
public class ShowGames extends AbstractCommand{
    private final GameManagement gameManager = GameManagement.getInstance();

    public ShowGames(String command) {
        super(command);
    }

    @Override
    public String execute(Object... arguments) {
        return gameManager.getAvailableGames();
    }
}
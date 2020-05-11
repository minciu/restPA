package jav.server.Gomoku;

import java.util.*;
/**
 *
 * @author user
 */
public class GameManagement {
    private static GameManagement gameManagement;
    private List<Game> games;

    private GameManagement() {
        this.games = new ArrayList<>();
    }

    public synchronized static GameManagement getInstance() {
        if (getGameManagement() == null) {
            setGameManagement(new GameManagement());
        }

        return getGameManagement();
    }

    public synchronized void addGame(Game game) {
        getGames().add(game);
    }

    /**
     * Get ids of the available games.
     */
    public synchronized String getAvailableGames() {
        StringBuilder result = new StringBuilder();
        result.append("Available games: ");
        int id = 0;
        for (Game game : getGames()) {
            if (game.isAvailable()) {
                result.append(id).append(' ');
            }
            ++id;
        }
        return result.toString();
    }

    /**
     * @return the gameManagement
     */
    public static GameManagement getGameManagement() {
        return gameManagement;
    }

    /**
     * @param aGameManagement the gameManagement to set
     */
    public static void setGameManagement(GameManagement aGameManagement) {
        gameManagement = aGameManagement;
    }

    /**
     * @return the games
     */
    public List<Game> getGames() {
        return games;
    }

    /**
     * @param games the games to set
     */
    public void setGames(List<Game> games) {
        this.games = games;
    }
}

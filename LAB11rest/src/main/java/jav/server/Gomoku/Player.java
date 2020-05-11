package jav.server.Gomoku;

import java.util.*;
/**
 *
 * @author user
 */
public class Player {
    private String name;
    private Game game;
    private char token;

    public boolean wonTheGame(int x, int y) {
        return this.getGame().checkWon(getToken(), x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return getToken() == player.getToken();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getToken());
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the game
     */
    public Game getGame() {
        return game;
    }

    /**
     * @param game the game to set
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * @return the token
     */
    public char getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(char token) {
        this.token = token;
    }
}

package jav.server.Gomoku;

import java.io.*;
import java.util.*;

/**
 *
 * @author user
 */
public class Game {
    private final int CHAIN_SIZE = 5;

    /**
     * The token of the player who
     * has the current turn.
     */
    private char currentTurn;

    private Board board;
    private List<Player> players;

    /**
     * A representation of the game which consists
     * of a list of moves made by players.
     */
    private List<Map<String, String>> moves;

    public Game() {
        this.currentTurn = 'x';
        this.board = new Board();
        this.players = new ArrayList<>();
        this.moves = new ArrayList<>();
    }

    public synchronized boolean isAvailable() {
        return getPlayers().size() < 2;
    }

    /**
     * Add a player to the game.
     * In order to start, the game needs
     * exactly two players.
     */
    public boolean addPlayer(Player player) {
        try {
            if (getPlayers().size() > 2) {
                throw new TooMuchPlayersException();
            }
            getPlayers().add(player);
            return true;
        } catch (TooMuchPlayersException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void updateTurn() {
        if (this.getCurrentTurn() == 'x') {
            this.setCurrentTurn('o');
        } else {
            this.setCurrentTurn('x');
        }
    }

    public synchronized void addMove(Player player, String move) {
        Map<String, String> currentMove = new HashMap<>();
        String token = String.valueOf(player.getToken());
        currentMove.put("token", token);
        currentMove.put("move", move);
        this.getMoves().add(currentMove);
    }

    public synchronized Map<String, String> getLastMove() {
        return this.getMoves().get(this.getMoves().size() - 1);
    }

    private final int[] xDirections = {1, 0, 1, 1};
    private final int[] yDirections = {0, 1, 1, -1};

    public boolean checkWon(char token, int x, int y) {
        for (int direction = 0; direction < 4; ++direction) {
            int currentChainSize = 1;

            for (int i = 1; i < CHAIN_SIZE; ++i) {
                int currentX = x + xDirections[direction] * i;
                int currentY = y + yDirections[direction] * i;

                if (this.getBoard().getPiece(currentX, currentY) == token) {
                    ++currentChainSize;
                } else {
                    break;
                }
            }

            for (int i = 1; i < CHAIN_SIZE; ++i) {
                int currentX = x - xDirections[direction] * i;
                int currentY = y - yDirections[direction] * i;

                if (this.getBoard().getPiece(currentX, currentY) == token) {
                    ++currentChainSize;
                } else {
                    break;
                }
            }

            if (currentChainSize >= CHAIN_SIZE) {
                return true;
            }
        }
        return false;
    }

   

    public class TooMuchPlayersException extends Exception {
    public TooMuchPlayersException() {
        super("Too much players");
    }
}

    
     
     
    public char getCurrentTurn() {
        return currentTurn;
    }

    /**
     * @param currentTurn the currentTurn to set
     */
    public void setCurrentTurn(char currentTurn) {
        this.currentTurn = currentTurn;
    }

    /**
     * @return the board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * @param board the board to set
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * @return the players
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * @param players the players to set
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    /**
     * @return the moves
     */
    public List<Map<String, String>> getMoves() {
        return moves;
    }

    /**
     * @param moves the moves to set
     */
    public void setMoves(List<Map<String, String>> moves) {
        this.moves = moves;
    }
    
}
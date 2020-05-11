package jav.server;

import jav.server.Gomoku.Player;
import jav.server.Gomoku.Game;
import jav.server.Gomoku.Board;
import jav.server.Commands.AbstractCommand;
import jav.server.Commands.SubmitMove;
import jav.server.Commands.Shell;

import java.io.*;
import java.net.*;

class ClientThread extends Thread {

    private final Shell shell;
    private Socket socket = null;
    private final Player player;

    public ClientThread(Shell shell,Socket socket) {
        this.shell = shell;
        this.socket = socket;
        this.player = new Player();
    }

    public void run() {
        try {
            
            // Get the request from the input stream: client → server
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            
            boolean flag=true;
            while (flag==true) {
                String request = in.readLine();
                
                // Send the response to the oputput stream: server → client

                String raspuns;
                if (request.equals("stop")) {
                    raspuns = "Server stopped ";
                } else {
                    
                    System.out.println("Client has request:" + request);
                }
                String[] commandArguments = request.split(" ", 2);
                AbstractCommand command = shell.getCommand(commandArguments[0]);
                raspuns = executeCommand(shell, command, commandArguments);
                //response="exit";
                out.println(raspuns);
                out.flush();
                
                if (raspuns.equals("Goodbye")) {
                    flag = false;
                } else if (raspuns.contains("stop")) {
                    System.exit(0);
                } else if (raspuns.contains("created") || raspuns.contains("joined")) {
                    gameMode(in, out);
                }
                
                            
            }
        } catch (IOException e) {
            System.err.println("Communication error... " + e);
        } finally {
            try {
                socket.close(); // or use try-with-resources
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

    private String executeCommand(Shell shell, AbstractCommand command, String[] commandArguments) {
        if (command == null) {
            return "Invalid command. Type 'show' to see the available commands";
        } else if (command.getCommand().equals("show")) {
            return command.execute(shell);
        } else if (command.getCommand().equals("set-name")) {
            return executeSetName(command, commandArguments);
        } else if (command.getCommand().equals("create-game")) {
            return command.execute(player);
        } else if (command.getCommand().equals("join-game")) {
            return executeJoinGame(command, commandArguments);
        }
        return command.execute(); // show-games
    }
    
    private String executeSetName(AbstractCommand command, String[] commandArguments) {
        if (commandArguments.length > 1) {
            return command.execute(player, commandArguments[1]);
        }
        return command.execute();
    }

    private String executeJoinGame(AbstractCommand command, String[] commandArguments) {
        if (commandArguments.length > 1) {
            return command.execute(player, commandArguments[1]);
        }
        return command.execute();
    }

    private void gameMode(BufferedReader in, PrintWriter out) throws IOException {
        waitOtherPlayer();
        String response;
        if (player.getToken() == 'x') {
            response = "Your opponent is " + player.getGame().getPlayers().get(1).getName() + ". It's your turn to make a move";
            out.println(response);
            out.flush();
        }

        String request;
        boolean playing = true;
        while (playing) {
            if (player.getGame().getCurrentTurn() == player.getToken()) {
                boolean validMove = false;
                while (!validMove) {
                    request = in.readLine();

                    response = submitMove(request);
                    out.println(response);
                    out.flush();
                    if (!response.contains("Invalid")) {
                        if (response.contains("won")) {
                            playing = false;
                            
                        }
                        validMove = true;
                    }
                }
                player.getGame().updateTurn();
            }

            if (player.getGame().getCurrentTurn() != player.getToken()) {
                response = getLastMoveFromOpponent(playing);
                if (playing) {
                    out.println(response);
                    out.flush();
                    if (response.contains("lost")) {
                        playing = false;
                    }
                }
            }
        }
    }

    /**
     * Wait an opponent to connect in order to start the game
     * using a wait-notify approach
     */
    private void waitOtherPlayer() {
        synchronized (player.getGame()) {
            player.getGame().notifyAll();
            while (player.getGame().getPlayers().size() < 2) {
                try {
                    player.getGame().wait();
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    private String submitMove(String move) {
        SubmitMove submitMoveCommand = new SubmitMove("submit-move", "x, y");
        String[] moveArguments = move.split(" ");
        if (moveArguments.length == 2) {
            return submitMoveCommand.execute(player, moveArguments[0], moveArguments[1]);
        }
        return submitMoveCommand.execute();
    }

    /**
     * Wait-notify approach in order to get the opponent
     * last move.
     */
    private String getLastMoveFromOpponent(boolean playing) {
        String move = "";
        synchronized (player.getGame()) {
            player.getGame().notifyAll();
            if (!playing) {
                return "";
            }
            // if the last move was submitted by the current player
            while (player.getGame().getMoves().size() == 0 ||
                    player.getGame().getLastMove().get("token").equals(String.valueOf(player.getToken()))) {
                try {
                    player.getGame().wait();
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }

            if (player.getGame().getLastMove().get("move").equals("won the game")) {
                move = move.concat("You lost.");
                move = move.concat("Your opponent move: ");
                move = move.concat(player.getGame().getMoves().get(player.getGame().getMoves().size() - 2).get("move"));
            } else {
                move = move.concat("Your opponent move: ");
                move = move.concat(player.getGame().getLastMove().get("move"));
            }
        }
        return move;
    }
    
    
    
}
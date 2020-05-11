package jav.server;

import jav.server.Commands.*;
import java.io.*;
import java.net.*;

public class GameServer {
    // Define the port on which the server is listening

    public static final int PORT = 8100;

    public GameServer() throws IOException {
        Shell shell = getShell();
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            int i = 0;
            while (true) {
                System.out.println("Waiting for a client ...");
                Socket socket = serverSocket.accept();

                // Execute the client's request in a new thread
                new ClientThread(shell,socket).start();

            }
        } catch (IOException e) {
            System.err.println("Ooops... " + e);
        } finally {
            serverSocket.close();
        }
    }

    private static Shell getShell() {
        Shell shell = new Shell();

        shell.addCommand(new SetName("set-name", "name"));
        shell.addCommand(new CreateGame("create-game"));
        shell.addCommand(new JoinGame("join-game", "id"));
        shell.addCommand(new ShowCommands("show"));
        shell.addCommand(new ShowGames("show-games"));

        return shell;
    }
}

package jav.server.Commands;

import jav.server.Gomoku.Player;

/**
 *
 * @author user
 */
public class SetName extends AbstractCommand{
    public SetName(String command, String arguments) {
        super(command, arguments);
    }

    @Override
    public String execute(Object... arguments) {
        if (arguments.length != 2) {
            return "Format: set-name name";
        }
        Player player = (Player) arguments[0];
        player.setName((String) arguments[1]);
        return "Your name is: " + arguments[1];
    }
}

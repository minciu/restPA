package jav.server.Commands;

import java.util.*;
/**
 *
 * @author user
 */
public class Shell {
    private List<AbstractCommand> commands = new ArrayList<>();

    public AbstractCommand getCommand(String commandName) {
        for (AbstractCommand command : commands) {
            if (command.getCommand().equals(commandName)) {
                return command;
            }
        }
        return null;
    }

    public void addCommand(AbstractCommand command) {
        commands.add(command);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (AbstractCommand command : commands) {
            stringBuilder.append(command.getCommand());
            if (command.getArguments() != null) {
                stringBuilder.append(" ").append(command.getArguments());
            }
            stringBuilder.append("/");
        }

        return stringBuilder.toString();
    }
}

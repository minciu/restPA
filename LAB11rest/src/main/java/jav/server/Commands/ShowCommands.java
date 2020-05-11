package jav.server.Commands;

public class ShowCommands extends AbstractCommand{
    public ShowCommands(String command) {
        super(command);
    }

    @Override
    public String execute(Object... arguments) {
        return "Commands: " + arguments[0];
    }
}

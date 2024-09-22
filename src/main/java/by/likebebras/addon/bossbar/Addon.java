package by.likebebras.addon.bossbar;

import by.likebebras.addon.bossbar.commands.*;
import lombok.Getter;
import org.by1337.bairx.BAirDropX;
import org.by1337.bairx.addon.JavaAddon;
import org.by1337.bairx.command.CommandRegistry;
import org.by1337.bairx.event.Event;
import org.by1337.blib.command.Command;

public final class Addon extends JavaAddon {

    @Getter
    private static Addon instance;

    private final Manager manager = new Manager();

    @Override
    public void onLoad() {

        CommandRegistry.registerCommand(
                new Command<Event>("[BOSSBAR]")
                        .addSubCommand(new PlayersCommand())
                        .addSubCommand(new CreateCommand())
                        .addSubCommand(new DeleteCommand())
                        .addSubCommand(new UpdateCommand())
                        .addSubCommand(new EditCommand())
        );
    }

    @Override
    public void onEnable() {
        instance = this;
    }

    public static Manager getManager(){
        return instance.manager;
    }

    @Override
    public void onDisable() {
        manager.clear();
    }
}

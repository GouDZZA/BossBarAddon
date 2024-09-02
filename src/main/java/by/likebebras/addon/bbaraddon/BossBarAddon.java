package by.likebebras.addon.bbaraddon;

import by.likebebras.addon.bbaraddon.cmds.*;
import org.by1337.bairx.addon.JavaAddon;
import org.by1337.bairx.command.CommandRegistry;
import org.by1337.blib.command.Command;

public final class BossBarAddon extends JavaAddon {

    public final BossBarManager manager = new BossBarManager();

    @Override
    public void onLoad() {
        CommandRegistry.registerCommand(
                new Command("[BOSSBAR]")
                        .addSubCommand(new AddCmd(manager))
                        .addSubCommand(new EditCmd(manager))
                        .addSubCommand(new CreateCmd(manager))
                        .addSubCommand(new DeleteCmd(manager))
                        .addSubCommand(new RadiusCmd(manager))
                        .addSubCommand(new RemoveCmd(manager))
        );
    }
    @Override
    public void onEnable() {

    }
    @Override
    public void onDisable() {
        manager.clear();
    }
}

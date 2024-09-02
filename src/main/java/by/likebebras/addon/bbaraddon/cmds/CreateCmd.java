package by.likebebras.addon.bbaraddon.cmds;

import by.likebebras.addon.bbaraddon.BossBarManager;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.KeyedBossBar;
import org.by1337.bairx.event.Event;
import org.by1337.blib.command.Command;
import org.by1337.blib.command.argument.ArgumentString;

public class CreateCmd extends Command<Event> {

    public CreateCmd(BossBarManager manager) {
        super("[CREATE]");
        argument(new ArgumentString<>("id"));
        executor((e, args) -> {
            String id = (String) args.getOrThrow("id", "в комманде не указан ID (arg №1) для боссбара");

            KeyedBossBar bar = manager.createBossBar(id, "", BarColor.RED, BarStyle.SOLID);

            bar.setVisible(true);
        });
    }
}

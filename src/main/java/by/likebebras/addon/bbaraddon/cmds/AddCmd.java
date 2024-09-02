package by.likebebras.addon.bbaraddon.cmds;

import by.likebebras.addon.bbaraddon.BossBarManager;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.entity.Player;
import org.by1337.bairx.BAirDropX;
import org.by1337.bairx.event.Event;
import org.by1337.blib.command.Command;
import org.by1337.blib.command.argument.ArgumentString;

import java.util.logging.Logger;

public class AddCmd extends Command<Event> {

    private final Logger log = Bukkit.getLogger();

    public AddCmd(BossBarManager manager) {
        super("[ADD]");
        argument(new ArgumentString<>("id"));
        argument(new ArgumentString<>("name"));
        executor((e, args) -> {
            String id = (String) args.getOrThrow("id", "в комманде не указан ID (arg №1) для боссбара");
            String args2 = (String) args.getOrThrow("name", "в комманде не указан (arg №2) для боссбара");
            KeyedBossBar bar = manager.getBossBar(id);

            if (bar == null) return;

            if (args2.equalsIgnoreCase("[ALL]")) Bukkit.getOnlinePlayers().forEach(bar::addPlayer);
            else {
                Player p = Bukkit.getPlayer(args2);

                if (p == null) return;

                bar.addPlayer(p);
            }
        });
    }
}

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

public class RemoveCmd extends Command<Event> {

    public RemoveCmd(BossBarManager manager) {
        super("[REMOVE]");
        argument(new ArgumentString<>("id"));
        argument(new ArgumentString<>("nick"));
        executor((e, args) -> {
            String id = (String) args.getOrThrow("id", "в комманде не указан ID (arg №1) для боссбара");
            String arg2 = (String) args.getOrThrow("nick", "в комманде не указан ник игрока  (arg №2) для боссбара");

            KeyedBossBar bar = manager.getBossBar(id);

            if (bar == null) return;

            if (arg2.equalsIgnoreCase("[ALL]")) {
                Bukkit.getOnlinePlayers()
                        .stream()
                        .filter(bar.getPlayers()::contains)
                        .forEach(bar::removePlayer);
            }
            else {
                Player p = Bukkit.getPlayer(arg2);

                if (p == null) return;

                bar.removePlayer(p);
            }
        });
    }
}

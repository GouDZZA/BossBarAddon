package by.likebebras.addon.bbaraddon.cmds;

import by.likebebras.addon.bbaraddon.BossBarManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.entity.Player;
import org.by1337.bairx.event.Event;
import org.by1337.bairx.util.Validate;
import org.by1337.blib.command.Command;
import org.by1337.blib.command.argument.ArgumentDouble;
import org.by1337.blib.command.argument.ArgumentString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class RadiusCmd extends Command<Event> {

    private Logger log = Bukkit.getLogger();

    public RadiusCmd(BossBarManager manager) {
        super("[RADIUS]");

        argument(new ArgumentString<>("id"));
        argument(new ArgumentDouble<>("radius"));
        executor((e, args) -> {
            if (e != null) {
                String id = (String) args.getOrThrow("id", "в комманде не указан ID (arg №1) для боссбара");
                double rad = (Double) args.getOrThrow("radius", "В комманде не указан радиус, либо указан неверно (args №2)");

                KeyedBossBar bar = manager.getBossBar(id);

                if (bar == null) return;

                Location loc = Validate.notNull(e.getAirDrop().getLocation(), "Аир не заспавниля (увы)");

                Set<Player> players = loc.
                        getWorld().getNearbyEntities(loc, rad, rad, rad)
                        .stream()
                        .filter(en -> en instanceof Player)
                        .map(en -> (Player) en)
                        .collect(Collectors.toSet());

                bar.getPlayers().forEach(p -> {
                    if (!players.contains(p) || !p.isOnline()) bar.removePlayer(p);
                });

                players.forEach(p -> {
                    if (!bar.getPlayers().contains(p)) bar.addPlayer(p);
                });
            } else log.warning("Комманда не испольняеться от лица ивента!");
        });
    }
}

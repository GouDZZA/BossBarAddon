package by.likebebras.addon.bbaraddon.cmds;

import by.likebebras.addon.bbaraddon.BossBarManager;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.KeyedBossBar;
import org.by1337.bairx.event.Event;
import org.by1337.blib.command.Command;
import org.by1337.blib.command.argument.ArgumentString;
import org.by1337.blib.command.argument.ArgumentStrings;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class EditCmd extends Command<Event> {

    private Logger log = Bukkit.getLogger();

    public EditCmd(BossBarManager manager) {
        super("[EDIT]");
        argument(new ArgumentString<>("id"));
        argument(new ArgumentStrings<>("actions"));

        executor((e, args) -> {
            String id = (String) args.getOrThrow("id", "в комманде не указан ID (arg №1) для боссбара");
            String actions = (String) args.getOrThrow("actions", "в комманде не указано значение для действия (arg №2) боссбара");

            KeyedBossBar bar = manager.getBossBar(id);
            for (String action : actions.split(" \\[AND] ")) {
                action(action, bar);
            }
        });
    }

    public void action(String action, KeyedBossBar bar){ // [VALUE] 69 80
        String value = action.replace(action.split(" ")[0].toUpperCase() + " ", "");

        switch (action.split(" ")[0].toUpperCase()){
            case "[VALUE]":
                try {
                    List<Double> d = new ArrayList<>();
                    for (String s : value.split(" ")) {
                        d.add(Double.parseDouble(s));
                    }

                    if (d.size() == 1) {
                        double vDouble = d.get(0);

                        if (vDouble > 1) vDouble = 1;

                        bar.setProgress(vDouble);

                    } else if (!d.isEmpty()){
                        double vDouble1 = d.get(0);
                        double vDouble2 = d.get(1);

                        if (vDouble1/vDouble2 > 1) bar.setProgress(1);
                        else if (vDouble1/vDouble2 < 0) bar.setProgress(0);
                        else {
                            bar.setProgress(vDouble1 / vDouble2);
                        }
                    }
                    break;
                } catch (NumberFormatException ex) {
                    log.warning("provided not a double at args №3");
                    break;
                }
            case "[COLOR]":

                try {
                    BarColor color = BarColor.valueOf(value.toUpperCase());

                    bar.setColor(color);
                    break;

                } catch (IllegalArgumentException ex) {
                    log.warning("provided not a color at args №3");
                    break;
                }

            case "[STYLE]":

                try {
                    BarStyle style = BarStyle.valueOf(value.toUpperCase());

                    bar.setStyle(style);
                    break;

                } catch (IllegalArgumentException ex) {
                    log.warning("provided not a style at args №3");
                    break;
                }

            case "[TEXT]":

                bar.setTitle(value);
                break;

            default:
                log.warning("wrong action " + "\"" + action + "\"");
                break;
        }
    }
}

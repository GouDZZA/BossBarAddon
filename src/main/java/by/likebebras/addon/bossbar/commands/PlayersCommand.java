package by.likebebras.addon.bossbar.commands;

import by.likebebras.addon.bossbar.Addon;
import by.likebebras.addon.bossbar.WrappedBossbar;
import org.bukkit.Bukkit;
import org.by1337.bairx.BAirDropX;
import org.by1337.bairx.event.Event;
import org.by1337.blib.command.Command;
import org.by1337.blib.command.argument.ArgumentString;
import org.by1337.blib.command.argument.ArgumentStrings;

public class PlayersCommand extends Command<Event> {
    public PlayersCommand() {
        super("[PLAYERS]");
        argument(new ArgumentString<>("ID"));
        argument(new ArgumentStrings<>("ACTION"));
        executor((event, args) -> {

            Object id = args.get("ID");
            Object actionO = args.get("ACTION");

            String eventName = event.getAirDrop().getId().getName();

            if (id == null){
                BAirDropX.getMessage().warning("Не указан айди боссбара в ивенте: \"" + eventName + "\"");

                return;
            }

            WrappedBossbar bar = Addon.getManager().getBossBar((String) id);

            if (actionO == null) {
                BAirDropX.getMessage().warning("Действия в ивенте: " + eventName + "не указаны!");

            } else action((String) actionO, eventName, bar);
        });
    }

    private void action(String action, String eventName, WrappedBossbar bar) {
        final String actionName = action.split(" ")[0];
        final String value = action.replace(actionName + " ", "");
        final boolean isPlayer = Bukkit.getPlayerExact(value) != null;


        if (actionName == null) return;

        switch (actionName.toLowerCase()){
            case "[add]", "[a]" -> {
                if (!isPlayer){
                    if (value.equalsIgnoreCase("[all]"))
                        bar.setAllPlayers(!bar.isAllPlayers());

                } else {
                    bar.addPlayer(Bukkit.getPlayerExact(value));
                }

            }
            case "[remove]", "[r]" -> {

                if (!isPlayer){
                    if (value.equalsIgnoreCase("[all]")) {
                        bar.setAllPlayers(false);

                        bar.removeAllPlayers();
                    }

                } else {
                    bar.removePlayer(Bukkit.getPlayerExact(value));
                }
            }
        }
    }
}

package by.likebebras.addon.bossbar.commands;

import by.likebebras.addon.bossbar.Addon;
import by.likebebras.addon.bossbar.WrappedBossbar;
import org.by1337.bairx.BAirDropX;
import org.by1337.bairx.event.Event;
import org.by1337.blib.command.Command;
import org.by1337.blib.command.argument.ArgumentString;

public class DeleteCommand extends Command<Event> {
    public DeleteCommand() {
        super("[DELETE]");
        argument(new ArgumentString<>("ID"));
        executor((event, args) -> {
            Object idObject = args.get("ID");

            String eventName = event.getAirDrop().getId().getName();

            if (idObject == null){

                BAirDropX.getMessage().warning("Не указан айди аира в комманде аира: " + eventName);

                return;
            }

            String id = (String) idObject;

            if (id.equalsIgnoreCase("[ALL]")){

                Addon.getManager().clear();

                return;
            } else {
                WrappedBossbar bossBar = Addon.getManager().getBossBar(id);

                if (bossBar == null) {

                    BAirDropX.getMessage().warning("Указан неверный айди аира в комманде ивента: " + eventName);

                } else Addon.getManager().deleteBossbar(bossBar);
            }
        });
    }
}

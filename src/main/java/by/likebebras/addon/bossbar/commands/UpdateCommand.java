package by.likebebras.addon.bossbar.commands;

import by.likebebras.addon.bossbar.Addon;
import by.likebebras.addon.bossbar.WrappedBossbar;
import org.by1337.bairx.BAirDropX;
import org.by1337.bairx.event.Event;
import org.by1337.blib.command.Command;
import org.by1337.blib.command.argument.ArgumentString;

public class UpdateCommand extends Command<Event> {
    public UpdateCommand() {
        super("[UPDATE]");
        argument(new ArgumentString<>("ID"));
        executor((event, args) -> {
            Object id = args.get("ID");

            String eventName = event.getAirDrop().getId().getName();

            if (id == null){
                BAirDropX.getMessage().warning("Не указан айди боссбара в ивенте: \"" + eventName + "\"");

                return;
            }

            WrappedBossbar bar = Addon.getManager().getBossBar((String) id);

            if (bar != null) {

                bar.update();

            } else {
                BAirDropX.getMessage().warning("Указан неверный айди боссбара в ивенте: \"" + eventName + "\"");
            }
        });
    }
}

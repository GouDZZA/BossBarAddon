package by.likebebras.addon.bossbar.commands;

import by.likebebras.addon.bossbar.Addon;
import org.by1337.bairx.BAirDropX;
import org.by1337.bairx.event.Event;
import org.by1337.blib.command.Command;
import org.by1337.blib.command.argument.ArgumentString;
import org.by1337.blib.lang.Lang;

public class CreateCommand extends Command<Event> {
    public CreateCommand() {
        super("[CREATE]");
        argument(new ArgumentString<>("ID"));
        executor((event, args) -> {
            Object idObj = args.get("ID");

            String eventName = event.getAirDrop().getId().getName();

            if (idObj == null){
                BAirDropX.getMessage().warning("Не указан айди аира для ивента: " + eventName);

                return;
            }

            String id = (String) idObj;

            if (id.equalsIgnoreCase("[ALL]")){
                BAirDropX.getMessage().warning("Айди аира не может быть \"[ALL]\". Ивент: " + eventName);

                return;
            }

            Addon.getManager().createBar(id, event);
        });
    }
}

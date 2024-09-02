package by.likebebras.addon.bbaraddon.cmds;

import by.likebebras.addon.bbaraddon.BossBarManager;
import org.by1337.bairx.event.Event;
import org.by1337.blib.command.Command;
import org.by1337.blib.command.argument.ArgumentString;

public class DeleteCmd extends Command<Event> {

    public DeleteCmd(BossBarManager manager) {
        super("[DELETE]");
        argument(new ArgumentString<>("id"));
        executor((e, args) -> {
            String id = (String) args.getOrThrow("id", "в комманде не указан ID (arg №1) для боссбара");
            if (id.equalsIgnoreCase("[ALL]")) {
                manager.clear();
            }
            else {
                manager.deleteBossBar(id);
            }
        });
    }
}

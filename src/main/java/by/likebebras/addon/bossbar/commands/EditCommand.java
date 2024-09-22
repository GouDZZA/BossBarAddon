package by.likebebras.addon.bossbar.commands;

import by.likebebras.addon.bossbar.Addon;
import by.likebebras.addon.bossbar.WrappedBossbar;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.by1337.bairx.BAirDropX;
import org.by1337.bairx.event.Event;
import org.by1337.blib.command.Command;
import org.by1337.blib.command.argument.ArgumentString;
import org.by1337.blib.command.argument.ArgumentStrings;

public class EditCommand extends Command<Event> {

//    public static final Pattern PROGRESS_PATTERN = Pattern.compile("\\{[a-z_]{3,20}} \\{[a-z_]{3,20}}");

    public EditCommand() {
        super("[EDIT]");
        argument(new ArgumentString<>("ID"));
        argument(new ArgumentStrings<>("ACTIONS"));

        executor((event, args) -> {
            Object id = args.get("ID");
            Object actions = args.get("ACTIONS");

            String eventName = event.getAirDrop().getId().getName();

            if (id == null){
                BAirDropX.getMessage().warning("Не указан айди боссбара в ивенте: \"" + eventName + "\"");

                return;
            }

            WrappedBossbar bar = Addon.getManager().getBossBar((String) id);

            if (actions == null) {
                BAirDropX.getMessage().warning("Действия в ивенте: " + eventName + "не указаны!");
            } else {

                if (bar != null)
                    for (String action : ((String)actions).split(" \\[AND] ")) edit(action, eventName, bar);
                else
                    BAirDropX.getMessage().warning("Указан неверный айди боссбара в ивенте: \"" + eventName + "\"");
            }
        });

    }
    private void edit(String action, String eventName, WrappedBossbar bar){
        final String actionName = action.split(" ")[0];
        final String actionValue = action.replace(actionName + " ", "");
        final String[] actionValueArr = actionValue.split(" ");

        if (actionName == null) return;

        switch (actionName.toLowerCase()){
            case "[progress]", "[prog]" -> {
                if (actionValueArr.length != 2) return;

                String type = actionValueArr[0];
                String progress = actionValueArr[1];

                double valueIfStatic = 0;

                if (progress != null) {

                    try {

                        valueIfStatic = Double.parseDouble(progress);

                    } catch (NumberFormatException ignored) {}

                    WrappedBossbar.ProgressType type0 = WrappedBossbar.ProgressType.fromStringOrElse(type, WrappedBossbar.ProgressType.STATIC); // well

                    bar.setProgress(type0, valueIfStatic);
                } else {

                    WrappedBossbar.ProgressType type0 = WrappedBossbar.ProgressType.fromStringOrElse(type, WrappedBossbar.ProgressType.STATIC); // well

                    bar.setProgress(type0, valueIfStatic);
                }
            }
            case "[color]", "[c]" -> {
                if (actionValueArr.length != 1) return;

                String type = actionValueArr[0];

                try {
                    BarColor barColor = BarColor.valueOf(type);

                    bar.setColor(barColor);

                } catch (IllegalArgumentException ignored) {

                    BAirDropX.getMessage().warning("Указан неверный цвет боссбара для аира: " + eventName);

                }
            }
            case "[style]", "[s]" -> {
                if (actionValueArr.length != 1) return;

                String type = actionValueArr[0];

                try {
                    BarStyle barStyle = BarStyle.valueOf(type);

                    bar.setStyle(barStyle);

                } catch (IllegalArgumentException ignored) {

                    BAirDropX.getMessage().warning("Указан неверный стиль боссбара для аира: " + eventName);

                }
            }
            case "[text]", "[t]" -> {
                if (actionValue.isBlank()) return;

                bar.setTitle(actionValue);
            }
            case "[radius]", "[r]" -> {

                try {

                    bar.setRadius(Double.parseDouble(actionValueArr[0]));

                } catch (NumberFormatException ignored) {

                    BAirDropX.getMessage().warning("Указан радиус боссбара для аира: " + eventName);
                }
            }
        }
    }
}

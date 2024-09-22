package by.likebebras.addon.bossbar;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.entity.Player;
import org.by1337.bairx.BAirDropX;
import org.by1337.bairx.event.Event;
import org.by1337.blib.lang.Lang;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public final class WrappedBossbar {

    private final NamespacedKey  _key;
    private final KeyedBossBar   _bar;
    private final String          _id; @Getter // _air is annotated
    private final Event          _air;

    @Setter
    private ProgressType progressType = ProgressType.STATIC;


    @Getter private String          title;
//    @Getter private String progressString;


    @Setter @Getter private double     radius = -1;
    @Setter @Getter private double staticValue = 1;

    @Setter @Getter
    private boolean allPlayers;


    public WrappedBossbar(String id, Event event) {
        _key = new NamespacedKey(BAirDropX.getInstance(), id);
        _bar = Bukkit.createBossBar(_key, "", BarColor.RED, BarStyle.SOLID);
        _id = id;
        _air = event;
    }

    public void update(){
        if (allPlayers){

            _bar.getPlayers()
                    .stream()
                    .filter(p -> !p.isOnline())
                    .collect(Collectors.toSet())
                    .forEach(this::removePlayer);

            Bukkit.getOnlinePlayers().forEach(this::addPlayer);

        }
        else if (radius > 0){

            Location loc = _air.getAirDrop().getLocation();

            if (loc.getWorld() == null) return;

            Set<Player> playersInRadi = loc.getWorld()
                    .getNearbyEntities(loc, radius, radius, radius)
                    .stream().filter(p -> p instanceof Player).map(p -> (Player)p)
                    .collect(Collectors.toSet());

            _bar.getPlayers().forEach(p -> {
                if (!playersInRadi.contains(p) || !p.isOnline()) removePlayer(p);
            });

            playersInRadi.forEach(p -> {
                if (!_bar.getPlayers().contains(p)) addPlayer(p);
            });
        }

        if (title != null && !title.isEmpty()){
            setTitle(_air.replace(title));
        }

        switch (progressType){
            case TIME_TO_OPEN  -> setProgress(_air.replace("{time_to_open}") , _air.replace("{time_to_open_const}")) ;
            case TIME_TO_START -> setProgress(_air.replace("{time_to_start}"), _air.replace("{time_to_start_const}"));
            case TIME_TO_END   -> setProgress(_air.replace("{time_to_end}")  , _air.replace("{time_to_end_const}"))  ;
            case STATIC -> setProgress(staticValue);
        }
    }


    public void addPlayer(@Nullable Player p){
        if (p != null)
            _bar.addPlayer(p);
    }

    public void removePlayer(@Nullable Player p){
        if (p != null)
            _bar.removePlayer(p);
    }
    public void removeAllPlayers(){
        new HashSet<>(_bar.getPlayers()).forEach(this::removePlayer);
    }

    private void setProgress(String... progressStrings){

        try {
            double firstValue = Double.parseDouble(progressStrings[0]);
            double constValue = Double.parseDouble(progressStrings[1]);

            setProgress(firstValue, constValue);

        } catch (NumberFormatException ignored) {
            BAirDropX.getMessage().warning(_id + ": число неверно! (bossbar)");
        }
    }

    private void setProgress(double value){
          if (value > 1) value = 1;
        else if (value < 0) value = 0;

        _bar.setProgress(value);
    }

    private void setProgress(double first, double second){
        setProgress(first/second);
    }

    public void setProgress(@NotNull ProgressType type, double valueIfStatic){
        if (type == ProgressType.STATIC){
            staticValue = valueIfStatic;
        }

        progressType = type;
    }

    public void removeFlag(BarFlag flag){
        _bar.removeFlag(flag);
    }

    public void addFlag(BarFlag flag){
        _bar.addFlag(flag);
    }

    public void setTitle(String title){
        _bar.setTitle(title);
    }

    public void setStyle(BarStyle style){
        _bar.setStyle(style);
    }

    public void setColor(BarColor color){
        _bar.setColor(color);
    }

    public String getId(){
        return _id;
    }

    public void delete(){
        _bar.removeAll();

        Bukkit.removeBossBar(_key);
    }

    public enum ProgressType{
        TIME_TO_OPEN,
        TIME_TO_START,
        TIME_TO_END,
        STATIC;

        @NotNull
        public static ProgressType fromStringOrElse(String string, ProgressType throwElse){
            return switch (string.toLowerCase()){
                case "time_to_open" -> TIME_TO_OPEN;
                case "time_to_start" -> TIME_TO_START;
                case "time_to_end" -> TIME_TO_END;
                default -> throwElse;
            };
        }
    }
}

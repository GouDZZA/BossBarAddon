package by.likebebras.addon.bbaraddon;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.KeyedBossBar;
import org.by1337.bairx.BAirDropX;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class BossBarManager {
    private final Map<String, KeyedBossBar> barMap = new HashMap<>();

    public void addBossBar(String id, KeyedBossBar bossBar){
        barMap.put(id, bossBar);
    }

    public void deleteBossBar(String id){
        KeyedBossBar bar = barMap.remove(id);
        bar.removeAll();

        Bukkit.removeBossBar(bar.getKey());

    }

    public @NotNull KeyedBossBar createBossBar(String id, String title, BarColor color, BarStyle style){
        KeyedBossBar bar = Bukkit.createBossBar(new NamespacedKey(BAirDropX.getInstance(), id), title, color, style);

        addBossBar(id, bar);

        return Bukkit.createBossBar(new NamespacedKey(BAirDropX.getInstance(), id), title, color, style);
    }

    public @Nullable KeyedBossBar getBossBar(String id){
        return barMap.get(id);
    }

    public void clear(){
        new HashSet<>(barMap.keySet()).forEach(this::deleteBossBar);
    }
}

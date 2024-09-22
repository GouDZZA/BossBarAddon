package by.likebebras.addon.bossbar;

import lombok.NoArgsConstructor;
import org.by1337.bairx.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
public class Manager {

    private final Set<WrappedBossbar> bossbars = new HashSet<>();

    public void createBar(String id, Event event){
        bossbars.add(new WrappedBossbar(id, event));
    }

    @Nullable
    public WrappedBossbar getBossBar(String id){
        for (WrappedBossbar bossbar : bossbars)
            if (bossbar.getId().equals(id)) return bossbar;

        return null;
    }

    public void deleteBossbar(String id){
        WrappedBossbar bar = getBossBar(id);

        if (bar == null) return;

        bossbars.remove(bar);
        bar.delete();
    }

    public void deleteBossbar(@NotNull WrappedBossbar bar){
        bossbars.remove(bar);
        bar.delete();
    }

    public void clear(){
        new HashSet<>(bossbars).forEach(WrappedBossbar::delete);
    }
}

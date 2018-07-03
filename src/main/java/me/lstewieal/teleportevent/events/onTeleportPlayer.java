package me.lstewieal.teleportevent.events;

import com.mumfrey.liteloader.util.render.Icon;
import net.eq2online.macros.event.MacroEvent;
import net.eq2online.macros.scripting.api.IMacroEventProvider;

class onTeleportPlayer extends MacroEvent {
    onTeleportPlayer(IMacroEventProvider provider, String name, boolean permissible, String permissionGroup, Icon icon) {
        super(provider, name, permissible, permissionGroup, icon);
    }
}

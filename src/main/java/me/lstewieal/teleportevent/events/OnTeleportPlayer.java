package me.lstewieal.teleportevent.events;

import net.eq2online.macros.compatibility.IconTiled;
import net.eq2online.macros.event.MacroEvent;
import net.eq2online.macros.res.ResourceLocations;
import net.eq2online.macros.scripting.api.IMacroEventProvider;

class OnTeleportPlayer extends MacroEvent {
    OnTeleportPlayer(IMacroEventProvider provider) {
        super(provider, "onPlayerTeleported", true, "player", new IconTiled(ResourceLocations.EXT, 9, 216, 0, 24, 24, 256, 256));
    }
}

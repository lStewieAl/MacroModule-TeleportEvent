package me.lstewieal.teleportevent.events;

import com.mumfrey.liteloader.util.render.Icon;
import me.lstewieal.teleportevent.ModuleInfo;
import net.eq2online.macros.compatibility.IconTiled;
import net.eq2online.macros.res.ResourceLocations;
import net.eq2online.macros.scripting.api.*;
import net.eq2online.macros.scripting.parser.ScriptContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

import java.util.ArrayList;
import java.util.List;

@APIVersion(ModuleInfo.API_VERSION)
public class EventProviderTeleport implements IMacroEventDispatcher, IMacroEventProvider {
    private ArrayList<String> help = new ArrayList<>();
    private IMacroEvent event;
    private double lastTickXPos = 8.50;
    private double lastTickYPos = 65.00;;
    private double lastTickZPos = 8.50;


    public EventProviderTeleport(IMacroEventProvider provider, String name, boolean permissible, String permissionGroup, Icon icon) {
        new onTeleportPlayer(this, name, permissible, permissionGroup, icon);
    }

    public EventProviderTeleport() {
        this.event = new onTeleportPlayer(this, "onPlayerTeleported", true, null, new IconTiled(ResourceLocations.EXT, 9, 216, 0, 24, 24, 256, 256));
    }



    @Override
    public void onTick(IMacroEventManager manager, Minecraft minecraft) {
        EntityPlayerSP player = minecraft.player;
        if(player == null) return;

        // check the last tick positions aren't the default values
        if((this.lastTickXPos != 8.50 || this.lastTickYPos != 65.00 || this.lastTickZPos != 8.50) && ((Math.abs(player.posX - this.lastTickXPos) > 5) || (Math.abs(player.posY - this.lastTickYPos) > 5) || (Math.abs(player.posZ - this.lastTickZPos) > 5))) {
            manager.sendEvent("onPlayerTeleported", 10);
        }
        this.lastTickXPos = player.posX;
        this.lastTickYPos = player.posY;
        this.lastTickZPos = player.posZ;
    }

    @Override
    public IMacroEventDispatcher getDispatcher() {
        return this;
    }

    @Override
    public void registerEvents(IMacroEventManager manager) {
      manager.registerEvent(this.event);
    }

    @Override
    public List<String> getHelp(IMacroEvent macroEvent) {
        return this.help;
    }

    @Override
    public void onInit() {
      help.add("§f<" + event.getName() + ">");
      help.add("This event is raised if the player moves 5 blocks in one direction");
      help.add("within a tick.");
      ScriptContext.MAIN.getCore().registerEventProvider(this);
    }
}


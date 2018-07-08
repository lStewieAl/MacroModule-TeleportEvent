package me.lstewieal.teleportevent.events;

import me.lstewieal.teleportevent.ModuleInfo;
import me.lstewieal.teleportevent.events.providers.OnTeleportPlayerProvider;
import net.eq2online.macros.scripting.api.*;
import net.eq2online.macros.scripting.parser.ScriptContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

@APIVersion(ModuleInfo.API_VERSION)
public class EventProviderTeleport implements IMacroEventDispatcher, IMacroEventProvider {
    private ArrayList<String> help = new ArrayList<>();
    private IMacroEvent event;
    private OnTeleportPlayerProvider provider;

    private static final Vec3d defaultPos = new Vec3d(8.50, 65.00, 8.50);
    private Vec3d lastTickPos =  new Vec3d(8.50, 65.00, 8.50);

    private static final int DISTANCE_THRESHOLD = 5;

    /*
    *  setup event provider
    * */
    public EventProviderTeleport() {
        this.event = new OnTeleportPlayer(this);
    }



    @Override
    public void onTick(IMacroEventManager manager, Minecraft minecraft) {
        EntityPlayerSP player = minecraft.player;
        if(player == null) return;
        Vec3d currentPos = new Vec3d(player.posX, player.posY, player.posZ);

        // euclidean distance between current position and last tick pos.
        double distanceTraveled = lastTickPos.distanceTo(currentPos);

        // check the last tick positions aren't the default values
        if(!lastTickPos.equals(defaultPos) && distanceTraveled > DISTANCE_THRESHOLD) {
            /* give distance to variable provider and fire event! */
            provider.setDistanceFloat((float) distanceTraveled);
            manager.sendEvent("onPlayerTeleported", 10, Double.toString(distanceTraveled));
        }
        lastTickPos = new Vec3d(player.posX, player.posY, player.posZ);
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
      setupHelp();

      /* register event provider (to add event to in-game events list) */
      ScriptContext.MAIN.getCore().registerEventProvider(this);

      /* setup variable provider (for %ENTITYDIST% etc.) */
      this.provider = new OnTeleportPlayerProvider();
      ScriptContext.MAIN.getCore().getScriptActionProvider().registerVariableProvider(this.provider);
    }

    private void setupHelp() {
        help.add("§f<" + event.getName() + ">");
        help.add("This event is raised if the player moves " + DISTANCE_THRESHOLD + " blocks within a tick.");
        help.add("You can access the teleported distance using the §CTELEPORTDIST§r");
        help.add("and §CTELEPORTDISTF§r global variables");
        help.add("§k:::§r §4§lMade By lStewieAl§r §k:::");
    }
}


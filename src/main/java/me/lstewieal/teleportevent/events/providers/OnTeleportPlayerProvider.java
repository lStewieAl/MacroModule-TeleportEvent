package me.lstewieal.teleportevent.events.providers;

import net.eq2online.macros.scripting.api.IMacroEventVariableProvider;

import java.util.HashSet;
import java.util.Set;

public class OnTeleportPlayerProvider implements IMacroEventVariableProvider {
    private float distanceFloat = 0;
    @Override
    public void initInstance(String[] instanceVariables) {
        try {
            this.distanceFloat = Float.parseFloat(instanceVariables[0]);
        } catch (NumberFormatException e) {
            this.distanceFloat = 0;
        }

    }

    @Override
    public void updateVariables(boolean clock) {}

    @Override
    public Object getVariable(String variableName) {
        if (variableName.equals("TELEPORTDIST")) {
            return (int) this.distanceFloat;
        } else if (variableName.equals("TELEPORTDISTF")) {
            return this.distanceFloat;
        }
        return null;
    }

    @Override
    public Set<String> getVariables() {
        Set<String> variables = new HashSet();
        variables.add("TELEPORTDIST");
        variables.add("TELEPORTDISTF");
        return variables;
    }

    @Override
    public void onInit() {

    }

    public void setDistanceFloat(float distance) {
        this.distanceFloat = distance;
    }
}

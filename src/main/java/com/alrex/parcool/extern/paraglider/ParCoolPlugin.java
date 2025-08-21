package com.alrex.parcool.extern.paraglider;

import net.minecraft.resources.ResourceLocation;

/*@ParagliderPlugin
public class ParCoolPlugin implements MovementPlugin {
    @Override
    public void registerNewStates(PlayerStateRegister register) {
        ParCoolPlayerStates.ENTRIES.forEach(it -> register.register(it.stateID(), it.staminaDelta()));
    }

    @Override
    public void registerStateConnections(PlayerStateConnectionRegister register) {
        ParCoolPlayerStates.ENTRIES.forEach(it -> {
            for (ResourceLocation parent : it.parentID()) {
                register.connect(parent, it.stateID(), it.condition(), it.priority());
            }
        });
    }
}*/
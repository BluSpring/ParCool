package com.alrex.parcool.client.fabric;

import net.minecraft.client.Camera;

public class CameraAngles {
    private final Camera camera;
    private final double renderPartialTicks;

    private float yaw;
    private float pitch;
    private float roll;

    public CameraAngles(Camera camera, double renderPartialTicks, float yaw, float pitch, float roll) {
        this.camera = camera;
        this.renderPartialTicks = renderPartialTicks;
        this.yaw = yaw;
        this.pitch = pitch;
        this.roll = roll;
    }

    public Camera getCamera() {
        return camera;
    }

    public double getPartialTick() {
        return renderPartialTicks;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getRoll() {
        return roll;
    }

    public void setRoll(float roll) {
        this.roll = roll;
    }
}

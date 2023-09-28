package com.Apothic0n.Inversia.api;

public class InversiaMath {
    public static float invLerp(float value, float scale, float min, float max) {
        return (value - min) / (max - min)*scale;
    }
}

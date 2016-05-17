package sceneobjects;

import utils.RgbColor;
import utils.Vec3;


public class Light {

    public static final int POINT = 0;
    public static final int AREA = 1;

    public int lightType;
    private Vec3 position;
    private RgbColor color;
    private RgbColor ambient;

    public Light (int lightType, Vec3 position, RgbColor color, RgbColor ambient) {
        this.lightType = lightType;
        this.position = position;
        this.color = color;
        this.ambient = ambient;
    }

    public RgbColor getColor() {
        return color;
    }

    public Vec3 getPosition () {
        return position;
    }

    public RgbColor getAmbient () {return ambient;}
}

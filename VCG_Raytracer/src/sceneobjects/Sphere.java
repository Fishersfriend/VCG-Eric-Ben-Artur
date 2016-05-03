package sceneobjects;

import utils.Vec3;

/**
 * Created by PraktikumCG on 03.05.2016.
 */
public class Sphere extends Shape {
    private float radius;

    public Sphere (float radius, Vec3 position) {
        this.radius = radius;
        super.position = position;
    }
}

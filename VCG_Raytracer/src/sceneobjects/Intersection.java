package sceneobjects;

import raytracer.Ray;
import sceneobjects.Shape;
import sceneobjects.Sphere;
import utils.Vec3;
import raytracer.*;

/**
 * Created by PraktikumCG on 03.05.2016.
 */
public class Intersection {
    private Vec3 intersectionPoint;
    private Vec3 normal;
    private Ray inRay;
    private Ray outRay;
    private Shape shape;
    private float distance;
    private boolean incoming;
    private boolean hit;

    public Intersection (Ray ray, Shape shape, Vec3 intersectionPoint) {
        this.shape = shape;
        this.inRay = ray;
        this.intersectionPoint= intersectionPoint;
        //this.intersectionPoint = ray.startPoint.add(ray.direction.multScalar(ray.t));
        //System.out.println(intersectionPoint);
    }

    public Vec3 getIntersec() {
        return intersectionPoint;
    }

    public Ray calculateReflectionRay() {
        return new Ray();
    }

    public Ray calculateRefractionRay () {
        return new Ray();
    }

    public boolean isOutOfDistance () {
        return true;
    }
}

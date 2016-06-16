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

    //erstellen des IntersectionObjekts mit übergebener Shape
    public Intersection (Ray ray, Shape shape, Vec3 intersectionPoint) {
        this.shape = shape;
        this.inRay = ray;
        this.intersectionPoint = intersectionPoint;
    }

    //Funktion getIntersec
    //[Rückgabe des IntersectionPoints]
    public Vec3 getIntersec() {
        return intersectionPoint;
    }

    //Funktion ReflectionRay
    //[Berechnung des ReflexionsStrahl]
    public Ray calculateReflectionRay() {
        return new Ray();
    }

    //Funktion RefraktionRay
    //[Berechnung des RefraktionsStrahl]
    public Ray calculateRefractionRay () {
        return new Ray();
    }

    public boolean isOutOfDistance () {
        return true;
    }
}

package sceneobjects;

import raytracer.Ray;
import sceneobjects.Shape;
import sceneobjects.Sphere;
import utils.Vec3;
import raytracer.*;

import java.util.ArrayList;

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
    public Vec3 inverseDirection;

    //erstellen des IntersectionObjekts mit übergebener Shape
    public Intersection (Ray ray, Shape shape, Vec3 intersectionPoint, Vec3 normal) {
        this.shape = shape;
        this.inRay = ray;
        this.intersectionPoint = intersectionPoint;
        this.normal = normal;
        this.inverseDirection = inRay.direction.negate();
        //System.out.print(normal);
    }

    //Funktion getIntersec
    //[Rückgabe des IntersectionPoints]
    public Vec3 getIntersec() {
        return intersectionPoint;
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

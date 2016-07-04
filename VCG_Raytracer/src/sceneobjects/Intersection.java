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
    public Ray calculateRefractionRay (float index, Ray incRay) {
        float n_dot_i = normal.scalar(incRay.direction);
        float eta_r = index;
        float angles = ((eta_r * n_dot_i) - (float) Math.sqrt(1-eta_r*eta_r*(1-n_dot_i*n_dot_i)));
        Vec3 transmission = normal.multScalar(angles).sub(incRay.direction.multScalar(eta_r));

        Ray transmissionRay = new Ray();
        transmissionRay.direction = transmission.normalize();
        transmissionRay.startPoint = intersectionPoint.add(transmissionRay.direction.multScalar(0.001f)).normalize();

        return transmissionRay;
    }

    public Ray sendRay () {

        return new Ray(intersectionPoint.add(inRay.direction.multScalar(0.0004f)), inRay.direction);
    }

    public boolean isOutOfDistance () {
        return true;
    }
}

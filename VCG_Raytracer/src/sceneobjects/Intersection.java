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
    public Intersection (Ray ray, Shape shape, Vec3 intersectionPoint, Vec3 normal) {
        this.shape = shape;
        this.inRay = ray;
        this.intersectionPoint = intersectionPoint;
        this.normal = normal;
        Vec3 inverseDirection = inRay.direction.negate();
        //System.out.print(normal);
    }

    //Funktion getIntersec
    //[Rückgabe des IntersectionPoints]
    public Vec3 getIntersec() {
        return intersectionPoint;
    }

    //Funktion ReflectionRay
    //[Berechnung des ReflexionsStrahl]
    public Ray calculateReflectionRay()
    {
        Vec3 Ref;

        //Ref = (normal.multScalar(normal.scalar(inRay.direction)).multScalar(2)).sub(inRay.direction);

        Ref = normal.multScalar(normal.scalar(inRay.direction));
        Ref = Ref.multScalar(2).sub(inRay.direction);

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

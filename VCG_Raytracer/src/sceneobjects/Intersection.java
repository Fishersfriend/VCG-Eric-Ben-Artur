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
    public Vec3 normal;
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
    public Ray calculateRefractionRay (String material, Ray incRay, boolean debug) {

        float n1 = 0f;
        float n2 = 0f;

        if(material.equals("Glass")){
            n1 = 1.0f;
            n2 = 1.5f;
        }

        Vec3 n = normal;
        Vec3 i = incRay.direction.negate();
        float eta = (n1/n2);
        float ca = n.scalar(i);
        float cb = 1-eta*eta*(1-ca*ca);

        if(cb < 0.0f){
            cb = 0;
        }else{
            cb = (float) Math.sqrt(cb);
        }

        Vec3 t = n.multScalar(ca).sub(i).multScalar(eta).sub(n.multScalar(cb));

        if(debug){
            System.out.println();
            System.out.println("cos(a)                : " + ca);
            System.out.println("cos(b)                : " + cb);
            System.out.println("N cos(a)              : " + n.multScalar(ca));
            System.out.println("N cos(a) - I          : " + n.multScalar(ca).sub(i));
            System.out.println("(N cos(a) - I)*eta    : " + n.multScalar(ca).sub(i).multScalar(eta));
            System.out.println("N cos(b)              : " + n.multScalar(cb));
            System.out.println("T                     : " + n.multScalar(ca).sub(i).multScalar(eta).sub(n.multScalar(cb)));
        }

        Ray transmissionRay = new Ray();
        transmissionRay.direction = t.normalize();
        transmissionRay.startPoint = intersectionPoint.add(transmissionRay.direction.multScalar(0.001f));

        return transmissionRay;
    }

    public Ray sendRay () {

        return new Ray(intersectionPoint.add(inRay.direction.multScalar(0.0004f)), inRay.direction);
    }

    public boolean isOutOfDistance () {
        return true;
    }

    public Vec3 getNormal () {
        return normal;
    }
}

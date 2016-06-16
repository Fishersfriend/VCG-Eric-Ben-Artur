package sceneobjects;

import raytracer.Ray;
import utils.Matrix4;
import utils.RgbColor;
import utils.Vec3;

public class Plane extends Shape {


    public Vec3 normal;
    public Vec3 intersectionPoint;

    //Konstruktor
    public Plane (Vec3 position, Vec3 normal, Material material) {
        super(position, material);
        this.normal = normal.normalize();

        matrixTransformation = new Matrix4().scale(1).translate(position);
        inverse = matrixTransformation.invert();
    }

    @Override


    //Intersection-Berechung mit Strahl
    public Vec3 intersect (Ray ray) {

        //Werte des Rays übergeben und in HilfsVariablen speichern
        transfStart = ray.startPoint;
        transfDirect = ray.direction;

        //Hilfsvariablen mit Inverse transformieren
        transfStart = this.inverse.multVec3(transfStart,true);
        transfDirect = this.inverse.multVec3(transfDirect,false).normalize();

        float pnp0 = normal.scalar(transfStart);

        //Wenn Schnittpunkt, dann Entfernung und InstersectionPoint berechnen
        if(normal.scalar(transfDirect) < 0){
            //Entfernung Berechnen
            ray.t = -((pnp0)/(normal.scalar(transfDirect)));

            //Berechnung IntersectionPoints und transformieren in Globales Koordinatensystem
            this.intersectionPoint = transfStart.add(transfDirect.multScalar(ray.t));
            this.intersectionPoint = this.matrixTransformation.multVec3(intersectionPoint, true);
            return intersectionPoint;

        }

        else
        {
            ray.t = -1;
            return null;
        }
    }

    //Funktion um Normale auszugeben
    public Vec3 getNormal (Ray ray, Vec3 intersectionPoint) {
        return normal;
    }
}

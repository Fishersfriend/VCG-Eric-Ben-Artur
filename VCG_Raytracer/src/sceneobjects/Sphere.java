package sceneobjects;

import raytracer.Ray;
import utils.Matrix4;
import utils.RgbColor;
import utils.Vec3;
import java.lang.Math;

/**
 * Created by PraktikumCG on 03.05.2016.
 */
public class Sphere extends Shape {


    private float rQuad;
    public Vec3 intersectionPoint;

    //Konstruktor
    public Sphere (float radius, Vec3 position, Material material) {

        super(position, material);

        //Radius quadrieren für spätere berechnung
        rQuad = radius*radius;
        //TransformationsMatrix erstellen
        matrixTransformation = new Matrix4().scale(radius).translate(position);
        //Inverse Erstellen
        inverse = matrixTransformation.invert();
        //System.out.print(""+inverse.getValueAt(0,0));
    }

    //Berechnung und Rückgabe der Normalen
    public Vec3 getNormal (Ray ray, Vec3 intersecPoint) {
        return intersecPoint.sub(this.position).normalize();
    }


    @Override


    //Intersection-Berechung mit Strahl

    public Vec3 intersect(Ray ray) {

        //Werte des Rays übergeben und in HilfsVariablen speichern
        transfStart = ray.startPoint;
        transfDirect = ray.direction;

        //Hilfsvariablen mit Inverse transformieren
        transfStart = this.inverse.multVec3(transfStart,true);
        transfDirect = this.inverse.multVec3(transfDirect,false).normalize();


        //Berechnung der Determinanten und der bis zu 2 t werte
        float b = 2 * transfStart.scalar(transfDirect);
        float c = transfStart.scalar(transfStart) - rQuad;
        float d = b*b - 4*c;
        float temp = (float) Math.sqrt(d);
        float t0 = (float) 0.5 * (-b - temp);
        float t1 = (float) 0.5 * (-b + temp);

        //Wenn Schnittpunkt, dann Entfernung und InstersectionPoint berechnen
        //[Vergleichen der t Werte und aussortieren]
        //[IntersectionPoint berechnung und transformation]
        //[Bei keinem Schnittpunkt Return 0 Vektor]
        if (d > 0) {

            if (t0 > 0 && t1 > 0)
            {
                ray.t = Math.min(t0, t1);
                intersectionPoint = transfStart.add(transfDirect.multScalar(ray.t));
                intersectionPoint = this.matrixTransformation.multVec3(intersectionPoint,true);
                return intersectionPoint;
            }
            else if (t0 < 0 && t1 > 0)
            {
                ray.t = t1;
                intersectionPoint = transfStart.add(transfDirect.multScalar(ray.t));
                intersectionPoint = this.matrixTransformation.multVec3(intersectionPoint,true);
                return intersectionPoint;
            }
            else if (t0 > 0 && t1 < 0)
            {
                ray.t = t0;
                intersectionPoint = transfStart.add(transfDirect.multScalar(ray.t));
                intersectionPoint = this.matrixTransformation.multVec3(intersectionPoint,true);
                return intersectionPoint;
            }
            else
            {
                ray.t = -1;
                return null;
            }
        } else {
            ray.t = -1;
            return null;
        }
    }

}
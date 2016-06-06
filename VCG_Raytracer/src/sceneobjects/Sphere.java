package sceneobjects;

import raytracer.Ray;
import utils.Matrix4;
import utils.RgbColor;
import utils.Vec3;
import java.lang.Math;

/**
 * Created by PraktikumCG on 03.05.2016.
 */
public class Sphere extends Shape {                                                                                     //Klasse Sphere erweitert Shape

    Matrix4 matrixTransformation;
    Matrix4 inverse;
                                                                                      //Material
    private float radius;                                                                                               //Radius
    private float rQuad;
    public Vec3 intersectionPoint;

    public Sphere (float radius, Vec3 position, Material material) {                                                    //Konstruktor Sphere

        super(position, material);

        this.radius = radius;

        rQuad = radius*radius;
        matrixTransformation = new Matrix4();
        matrixTransformation.translate(position);
        matrixTransformation.scale(radius);
        inverse = new Matrix4();
        inverse = matrixTransformation.invert();

    }

    public Vec3 getNormal (Ray ray,Vec3 intersectionPoint) {                                                                                   //Methode getNormal
        //intersectionPoint = ray.startPoint.add(ray.direction.multScalar(ray.t));
        //this.intersectionPoint = this.matrixTransformation.multVec3(this.intersectionPoint,true);
        return intersectionPoint.sub(this.position).normalize();
    }

    @Override
    public Vec3 intersect(Ray ray) {                                                                                 //Methode intersect

        ray.startPoint = this.inverse.multVec3(ray.startPoint,true);

        double b = 2 * ray.startPoint.scalar(ray.direction);                                                            //Variablen für DeterminantenTest

        double c = ray.startPoint.x * ray.startPoint.x + ray.startPoint.y * ray.startPoint.y + ray.startPoint.z * ray.startPoint.z - rQuad;

        double d = b*b - 4*c;

        double t0 = (float) (0.5 * (-b - Math.sqrt(b*b - 4*c)));

        double t1 = (float) (0.5 * (-b + Math.sqrt(b*b - 4*c)));
        //ray.startPoint = this.matrixTransformation.multVec3(ray.startPoint,true);
        ray.startPoint = this.matrixTransformation.multVec3(ray.startPoint, true);
        if (d > 0) {                                                                                                    //Prüfen ob Sphere wichtig für die Szene
            if (t0 > 0 && t1 > 0) {
                ray.t = (float) Math.min(t0, t1);
                this.intersectionPoint = ray.startPoint.add(ray.direction.multScalar(ray.t));
                //this.intersectionPoint = this.matrixTransformation.multVec3(this.intersectionPoint,true);
                return intersectionPoint;
            } else if (t0 < 0 && t1 > 0) {
                ray.t = (float) t1;
                this.intersectionPoint = ray.startPoint.add(ray.direction.multScalar(ray.t));
                //this.intersectionPoint = this.matrixTransformation.multVec3(this.intersectionPoint,true);
                return intersectionPoint;
            } else if (t0 > 0 && t1 < 0) {
                ray.t = (float) t0;
                this.intersectionPoint = ray.startPoint.add(ray.direction.multScalar(ray.t));
                //this.intersectionPoint = this.matrixTransformation.multVec3(this.intersectionPoint,true);
                return intersectionPoint;
            } else
            {
                return new Vec3(0,0,0);
            }
        } else {
            return new Vec3(0, 0, 0);
        }
    }
}

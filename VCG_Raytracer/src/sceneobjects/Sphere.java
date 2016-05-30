package sceneobjects;

import raytracer.Ray;
import utils.RgbColor;
import utils.Vec3;
import java.lang.Math;

/**
 * Created by PraktikumCG on 03.05.2016.
 */
public class Sphere extends Shape {                                                                                     //Klasse Sphere erweitert Shape

    public Material material;                                                                                           //Material
    private float radius;                                                                                               //Radius

    public Sphere (float radius, Vec3 position, Material material) {                                                    //Konstruktor Sphere
        this.radius = radius;
        super.position = position;
        this.material = material;
    }

    public Vec3 getNormal (Ray ray) {                                                                                   //Methode getNormal
        Vec3 intersecPoint = ray.startPoint.add(ray.direction.multScalar(ray.t));

        return intersecPoint.sub(this.position).normalize();
    }

    @Override
    public boolean intersect(Ray ray) {                                                                                 //Methode intersect
        ray.startPoint = ray.startPoint.sub(this.position);

        double b = 2 * ray.startPoint.scalar(ray.direction);                                                            //Variablen für DeterminantenTest

        double c = ray.startPoint.x * ray.startPoint.x + ray.startPoint.y * ray.startPoint.y + ray.startPoint.z * ray.startPoint.z - radius * radius;

        double d = b*b - 4*c;

        double t0 = (float) (0.5 * (-b - Math.sqrt(b*b - 4*c)));

        double t1 = (float) (0.5 * (-b + Math.sqrt(b*b - 4*c)));
        ray.startPoint = ray.startPoint.add(this.position);

        if (d > 0) {                                                                                                    //Prüfen ob Sphere wichtig für die Szene
            if (t0 > 0 && t1 > 0) {
                ray.t = (float) Math.min(t0, t1);
                return true;
            } else if (t0 < 0 && t1 > 0) {
                ray.t = (float) t1;
                return true;
            } else if (t0 > 0 && t1 < 0) {
                ray.t = (float) t0;
                return true;
            } else { return false;}
        } else
            return false;
    }
}

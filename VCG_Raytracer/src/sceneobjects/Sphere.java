package sceneobjects;

import raytracer.Ray;
import utils.RgbColor;
import utils.Vec3;
import java.lang.Math;

/**
 * Created by PraktikumCG on 03.05.2016.
 */
public class Sphere extends Shape {

    public Material material;
    private float radius;

    public Sphere (float radius, Vec3 position, Material material) {
        this.radius = radius;
        super.position = position;
        this.material = material;
    }

    public Vec3 getNormal (Ray ray) {
        Vec3 intersecPoint = ray.startPoint.add(ray.direction.multScalar(ray.t));

        return intersecPoint.sub(this.position).normalize();
    }

    @Override
    public boolean intersect(Ray ray) {

        double b = 2 * ray.startPoint.scalar(ray.direction);

        double c = ray.startPoint.x * ray.startPoint.x + ray.startPoint.y * ray.startPoint.y + ray.startPoint.z * ray.startPoint.z - radius * radius;

        double d = b*b - 4*c;

        double t0 = (float) (0.5 * (-b - Math.sqrt(b*b - 4*c)));

        double t1 = (float) (0.5 * (-b + Math.sqrt(b*b - 4*c)));

        if (d > 0) {
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

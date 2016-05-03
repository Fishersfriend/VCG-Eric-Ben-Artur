package sceneobjects;

import raytracer.Ray;
import utils.RgbColor;
import utils.Vec3;

/**
 * Created by PraktikumCG on 03.05.2016.
 */
public class Sphere extends Shape {
    private float radius;
    private RgbColor color;

    public Sphere (float radius, Vec3 position, RgbColor color) {
        this.radius = radius;
        super.position = position;
        this.color = color;
    }

    public RgbColor getColor() {
        return color;
    }

    public Vec3 calculateNormal (Vec3 interSectionPoint) {
        Vec3 normal = interSectionPoint.sub(super.position);
        return normal.normalize();
    }

    @Override
    public void intersect (Ray ray) {
        float b = 2 * ray.startPoint.scalar(ray.direction);
        float c = ray.startPoint.scalar(ray.startPoint) - radius * radius;
        float discriminant = b * b - 4 * c;
        float t0 = (float) (-b - Math.sqrt(discriminant))/2;
        float t1 = (float) (-b + Math.sqrt(discriminant))/2;

        /* hier weiter machen
        if((discriminant >= 0) && (t0 > 0 && t1 > 0)){
            if(t0 < t1)
            Vec3 interSectionPoint = ray.startPoint.add(ray.getDirection().multScalar(t0));
            Intersection intersection = new Intersection(ray, this, )
        }

        */

    }
}

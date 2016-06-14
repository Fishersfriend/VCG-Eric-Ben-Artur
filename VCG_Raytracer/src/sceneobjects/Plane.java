package sceneobjects;

import raytracer.Ray;
import utils.Matrix4;
import utils.RgbColor;
import utils.Vec3;

public class Plane extends Shape {


    public Vec3 normal;
    public Vec3 intersectionPoint;

    public Plane (Vec3 position, Vec3 normal, Material material) {
        super(position, material);
        this.normal = normal.normalize();


    }

    @Override
    public Vec3 intersect (Ray ray) {
        float pnp0 = normal.scalar(ray.startPoint);

        //t = -((pnp0 + position.length())/(normal.scalar(ray.direction)));

        if(normal.scalar(ray.direction) < 0){
            ray.t = -((pnp0 + position.length())/(normal.scalar(ray.direction)));
            this.intersectionPoint = ray.startPoint.add(ray.direction.multScalar(ray.t));
            return intersectionPoint;

        }

        else
        {
            ray.t = -1;
            return null;
        }
    }

    public Vec3 getNormal (Ray ray, Vec3 intersectionPoint) {
        return normal;
    }


}

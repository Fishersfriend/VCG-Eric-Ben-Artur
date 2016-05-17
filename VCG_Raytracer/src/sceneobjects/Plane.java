package sceneobjects;

import raytracer.Ray;
import utils.Vec3;

public class Plane extends Shape {
    public Vec3 normal;

    public Plane (Vec3 position, Vec3 normal) {
        super.position = position;
        this.normal = normal.normalize();
    }

    @Override
    public boolean intersect (Ray ray) {
        float determ = normal.scalar(ray.direction);

        if (determ == 0) {return false;}
        else if (determ > 0) {ray.t = - (normal.scalar(ray.startPoint) + position.length()) / determ; return true;}
        else if (determ < 0) {ray.t = - (normal.scalar(ray.startPoint) + position.length()) / determ; return true;}

        return false;
    }


}

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
        float pnp0 = normal.scalar(ray.startPoint);

        //t = -((pnp0 + position.length())/(normal.scalar(ray.direction)));

        if(normal.scalar(ray.direction) > 0){
          ray.t = -((pnp0 + position.length())/(normal.scalar(ray.direction)));
        }else{
          return false;
        }

        //System.out.println(t);

        return true;
    }


}

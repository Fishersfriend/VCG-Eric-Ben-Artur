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

        matrixTransformation = new Matrix4().scale(1).translate(position);
        inverse = matrixTransformation.invert();
    }

    @Override
    public Vec3 intersect (Ray ray) {
        transfStart = ray.startPoint;
        transfDirect = ray.direction;

        transfStart = this.inverse.multVec3(transfStart,true);
        transfDirect = this.inverse.multVec3(transfDirect,false).normalize();

        float pnp0 = normal.scalar(transfStart);

        if(normal.scalar(transfDirect) < 0){
            ray.t = -((pnp0)/(normal.scalar(transfDirect)));
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

    /*
    public Vec3 intersect (Ray ray) {
        transfStart = ray.startPoint;
        transfDirect = ray.direction;

        transfStart = this.inverse.multVec3(transfStart,true);
        transfDirect = this.inverse.multVec3(transfDirect,false).normalize();

        float pnp0 = normal.scalar(transf);

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
    */

    public Vec3 getNormal (Ray ray, Vec3 intersectionPoint) {
        return normal;
    }
}

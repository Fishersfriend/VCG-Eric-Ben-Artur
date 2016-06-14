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

    public Sphere (float radius, Vec3 position, Material material) {

        super(position, material);

        rQuad = radius*radius;
        matrixTransformation = new Matrix4().scale(radius).translate(position);

        inverse = matrixTransformation.invert();
        System.out.print(""+inverse.getValueAt(0,0));
    }

    public Vec3 getNormal (Ray ray, Vec3 intersecPoint) {
        return intersecPoint.sub(this.position).normalize();
    }

    @Override
    public Vec3 intersect(Ray ray) {
        transfStart = ray.startPoint;
        transfDirect = ray.direction;

        transfStart = this.inverse.multVec3(transfStart,true);
        transfDirect = this.inverse.multVec3(transfDirect,false).normalize();

        float b = 2 * transfStart.scalar(transfDirect);

        float c = transfStart.scalar(transfStart) - rQuad;

        float d = b*b - 4*c;

        float temp = (float) Math.sqrt(d);

        float t0 = (float) 0.5 * (-b - temp);

        float t1 = (float) 0.5 * (-b + temp);


        if (d > 0) {
            if (t0 > 0 && t1 > 0) {
                ray.t = Math.min(t0, t1);
                intersectionPoint = transfStart.add(transfDirect.multScalar(ray.t));
                intersectionPoint = this.matrixTransformation.multVec3(intersectionPoint,true);
                return intersectionPoint;
            } else if (t0 < 0 && t1 > 0) {
                ray.t = t1;
                intersectionPoint = transfStart.add(transfDirect.multScalar(ray.t));
                intersectionPoint = this.matrixTransformation.multVec3(intersectionPoint,true);
                return intersectionPoint;
            } else if (t0 > 0 && t1 < 0) {
                ray.t = t0;
                intersectionPoint = transfStart.add(transfDirect.multScalar(ray.t));
                intersectionPoint = this.matrixTransformation.multVec3(intersectionPoint,true);
                return intersectionPoint;
            } else
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
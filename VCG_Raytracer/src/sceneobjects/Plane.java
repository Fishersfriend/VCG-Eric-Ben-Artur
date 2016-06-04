package sceneobjects;

import raytracer.Ray;
import utils.Matrix4;
import utils.RgbColor;
import utils.Vec3;

public class Plane extends Shape {                                                                                      //Klasse plane

    Matrix4 matrixTransformation;
    Vec3 intersectionPoint;
    public Vec3 normal;                                                                                                //NormalenVektor

    public Plane (Vec3 position, Vec3 normal, Material material) {                                                         //Konstruktor Plane
        super(position, material);                                                                                 //Position und Normale übergeben
        this.normal = normal.normalize();
        matrixTransformation = new Matrix4();
        matrixTransformation.translate(position);
    }

    @Override
    public Vec3 intersect (Ray ray) {                                                                                //Methode intersect
        float pnp0 = normal.scalar(ray.startPoint);

        //t = -((pnp0 + position.length())/(normal.scalar(ray.direction)));

        if(normal.scalar(ray.direction) < 0){                                                                           //Prüfen ob Plane wichtig für Szene
          ray.t = -((pnp0 + position.length())/(normal.scalar(ray.direction)));
        }else{
          ray.t = -1;
          return new Vec3(0,0,0);
        }

        //System.out.println(t);
        this.intersectionPoint = ray.startPoint.add(ray.direction.multScalar(ray.t));
        this.intersectionPoint = this.matrixTransformation.multVec3(this.intersectionPoint,true);
        return intersectionPoint;
    }

    public Vec3 getNormal (Ray ray) {                                                                                   //Methode getNormal
        return normal;
    }


}

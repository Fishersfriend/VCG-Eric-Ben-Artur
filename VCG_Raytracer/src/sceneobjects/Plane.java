package sceneobjects;

import raytracer.Ray;
import utils.RgbColor;
import utils.Vec3;

public class Plane extends Shape {                                                                                      //Klasse plane

    public Vec3 normal;                                                                                                //NormalenVektor

    public Plane (Vec3 position, Vec3 normal, Material material) {                                                         //Konstruktor Plane
        super(position, material);                                                                                 //Position und Normale übergeben
        this.normal = normal.normalize();
    }

    @Override
    public boolean intersect (Ray ray) {                                                                                //Methode intersect
        float pnp0 = normal.scalar(ray.startPoint);

        //t = -((pnp0 + position.length())/(normal.scalar(ray.direction)));

        if(normal.scalar(ray.direction) < 0){                                                                           //Prüfen ob Plane wichtig für Szene
          ray.t = -((pnp0 + position.length())/(normal.scalar(ray.direction)));
        }else{
          ray.t = -1;
          return false;
        }

        //System.out.println(t);

        return true;
    }

    public Vec3 getNormal (Ray ray) {                                                                                   //Methode getNormal
        return normal;
    }


}

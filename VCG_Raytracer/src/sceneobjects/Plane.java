package sceneobjects;

import raytracer.Ray;
import utils.RgbColor;
import utils.Vec3;

public class Plane extends Shape {                                                                                      //Klasse plane

    public Vec3 normal;                                                                                                 //NormalenVektor
    public RgbColor color;

    public Plane (Vec3 position, Vec3 normal, RgbColor color) {                                                         //Konstruktor Plane
        super.position = position;                                                                                      //Position und Normale übergeben
        this.normal = normal.normalize();
        this.color = color;
    }

    @Override
    public boolean intersect (Ray ray) {                                                                                //Methode intersect
        float pnp0 = normal.scalar(ray.startPoint);

        //t = -((pnp0 + position.length())/(normal.scalar(ray.direction)));

        if(normal.scalar(ray.direction) > 0){                                                                           //Prüfen ob Plane wichtig für Szene
          ray.t = -((pnp0 + position.length())/(normal.scalar(ray.direction)));
        }else{
          return false;
        }

        //System.out.println(t);

        return true;
    }


}

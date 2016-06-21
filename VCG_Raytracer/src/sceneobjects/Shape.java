package sceneobjects;

import raytracer.Ray;
import utils.Matrix4;
import utils.Vec3;

/**
 * Created by PraktikumCG on 03.05.2016.
 */
public abstract class Shape extends SceneObject {

    //Variablen, die von Kindern von Shape gebraucht werden
    public Material material;



    Shape(Vec3 position, Material material){
        super(position);
        this.material = material;
    }

    public Vec3 intersect (Ray ray) {



        return new Vec3(0,0,0);
    }

    public Vec3 getNormal (Ray ray, Vec3 intersectionPoint) {

        return new Vec3(0,0,0);
    }

}

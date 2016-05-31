package sceneobjects;

import raytracer.Ray;
import utils.Vec3;

/**
 * Created by PraktikumCG on 03.05.2016.
 */
public abstract class Shape extends SceneObject {

    public Material material;

    Shape(Vec3 position, Material material){
        super(position);
        this.material = material;
    }

    public boolean intersect (Ray ray) {



        return false;
    }

    public Vec3 getNormal (Ray ray) {                                                                                   //Methode getNormal

        return new Vec3(0,0,0);
    }

}

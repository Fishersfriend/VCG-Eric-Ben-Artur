import java.util.ArrayList;
import sceneobjects.*;
import utils.RgbColor;
import utils.Vec3;

public class Scene {
    //private ArrayList<Shape> shapeList = new ArrayList<>();
    //private ArrayList<Light> lightList = new ArrayList<>();

    public Scene(){

    }

    public void createSphere(){
        Sphere sphere = new Sphere(5f, new Vec3(0,0,-5f), new RgbColor(0, 1f, 0));
    }

    public void createPlane(){

    }

    public void createPointLight(){

    }

    public void createPerspCamera(){

    }
}

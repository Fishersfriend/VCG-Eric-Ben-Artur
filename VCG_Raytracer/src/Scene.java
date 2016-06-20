/*
package raytracer;

import ui.Window;
import utils.*;
import sceneobjects.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.lang.Math.*;

public class Scene {
    private ArrayList<Shape> shapeList = new ArrayList<>();
    private ArrayList<Light> lightList = new ArrayList<>();


    public Scene(){

    }

    public void createMaterial(){
        Material phong = new Material(new RgbColor(0.1f, 0.1f, 0.1f), new RgbColor(0.5f, 0.5f, 0.5f), new RgbColor(0.1f, 0.1f, 0.1f), 6);
        Material phong2 = new Material(new RgbColor(0.1f, 0.0f, 0.0f), new RgbColor(0f, 0.0f, 1.0f), new RgbColor(0.0f, 0.0f, 0.3f), 50);
    }

    public void createSphere(){
        Sphere sphere1 = new Sphere(1f, new Vec3 (2, -3, -3f), phong2);
        Sphere sphere2 = new Sphere(1f, new Vec3(-3f, -3, 0), phong2);
    }

    public void createPlane(){
        Plane topPlane = new Plane      (new Vec3(0f, 4f, 0f), new Vec3(0, -1, 0), phong);
        Plane bottomPlane = new Plane   (new Vec3(0f, -4f, 0f), new Vec3(0, 1, 0), phong);
        Plane leftPlane = new Plane     (new Vec3(-5f, 0f, 0f), new Vec3(1, 0, 0), phong);
        Plane rightPlane = new Plane    (new Vec3(5f, 0f, 0f), new Vec3(-1, 0, 0), phong);
        Plane backPlane = new Plane     (new Vec3(0f, 0f, 10f), new Vec3(0, 0, -1), phong);
    }

    public void createPointLight(){
        Light light0    = new Light(0,new Vec3(0, 3.9f, -5f), new RgbColor  (1f, 1f, 1f),       new RgbColor(0.0f, 0.0f, 0.0f));
        //Light light1  = new Light(0,new Vec3(-10, -4, -3), new RgbColor   (0.1f, 1f, 0.8f),   new RgbColor(0.0f, 0.0f, 0.0f));
        //Light light2  = new Light(0,new Vec3(10, -4, -3), new RgbColor    (1f, 0.1f, 0.8f),   new RgbColor(0.0f, 0.0f, 0.0f));
    }

    public void createPerspCamera(){
        Camera camera = new Camera(mRenderWindow, new Vec3(0, 0f, -30f), new Vec3(0, 0, 0), new Vec3(0, 1, 0), 0.5f, 1f);
    }
//scene.createMaterial ();
//scene.createSphere ();
//scene.createPlane ();
//scene.createPointLight ();
//scene.createPerspCamera();
}
*/
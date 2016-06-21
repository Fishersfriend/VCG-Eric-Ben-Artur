/*
package utils;

import sceneobjects.*;

import java.util.ArrayList;


public class Scene {

    public ArrayList<Shape> shapeList = new ArrayList<>();
    public ArrayList<Light> lightList = new ArrayList<>();
    public ArrayList<Material> materialList = new ArrayList<>();

    public Camera createCamera(){
        //Kamera erstellen (mRenderWindow, Position, LookAt, ViewerUpVektor, viewAngle, focalLength)
        Camera camera = new Camera(mRenderWindow, new Vec3(0, 0f, -30f), new Vec3(0, 0, 0), new Vec3(0, 1, 0), 0.5f, 1f); //Licht erstellen (Lichtart, Position, Farbe, Ambient-Farbe)
        return camera;
    }

    public void createMaterial(){
        //Material erstellen (Ambient, Diffuse, Specular, Shininess)
        Material phong = new Material(new RgbColor(0.1f, 0.1f, 0.1f), new RgbColor(0.5f, 0.5f, 0.5f), new RgbColor(0.1f, 0.1f, 0.1f), 6);
        Material phong2 = new Material(new RgbColor(0.1f, 0.0f, 0.0f), new RgbColor(0f, 0.0f, 1.0f), new RgbColor(0.0f, 0.0f, 0.3f), 50);
        materialList.add(0,phong);
        materialList.add(1,phong2);
    }

    public void createShapes(){
        //Kugel erstellen (Radius, Position, Material)
        Sphere sphere1 = new Sphere(1f, new Vec3 (2, -3, -3f), materialList.get(1));
        Sphere sphere2 = new Sphere(1f, new Vec3(-3f, -3, 0), materialList.get(1));
        Sphere sphere3 = new Sphere(1f, new Vec3(-3f, -3, 0), materialList.get(0));

        //Ebene erstellen (Postiton, Normale, Material)
        Plane topPlane = new Plane(new Vec3(0f, 4f, 0f), new Vec3(0, -1, 0), materialList.get(0));
        Plane bottomPlane = new Plane(new Vec3(0f, -4f, 0f), new Vec3(0, 1, 0), materialList.get(0));
        Plane leftPlane = new Plane(new Vec3(-5f, 0f, 0f), new Vec3(1, 0, 0), materialList.get(0));
        Plane rightPlane = new Plane(new Vec3(5f, 0f, 0f), new Vec3(-1, 0, 0), materialList.get(0));
        Plane backPlane = new Plane(new Vec3(0f, 0f, 10f), new Vec3(0, 0, -1), materialList.get(0));
        shapeList.add(0, leftPlane);
        shapeList.add(1, rightPlane);
        shapeList.add(2, topPlane);
        shapeList.add(3, bottomPlane);
        shapeList.add(4, backPlane);
        shapeList.add(5, sphere1);
        shapeList.add(6, sphere2);
        shapeList.add(7, sphere3);


    }

    public void createLight(){
        //Licht erstellen (Lichtart, Position, Farbe, Ambient-Farbe)
        Light light0 = new Light(0, new Vec3(0, 3.9f, -5f), new RgbColor(1f, 1f, 1f), new RgbColor(0.0f, 0.0f, 0.0f));
        //Light light1 = new Light(0, new Vec3(-10, -4, -3), new RgbColor(0.1f, 1f, 0.8f), new RgbColor(0.0f, 0.0f, 0.0f));
        //Light light2 = new Light(0, new Vec3(10, -4, -3), new RgbColor(1f, 0.1f, 0.8f), new RgbColor(0.0f, 0.0f, 0.0f));
        lightList.add(0, light0);
        //lightList.add(1, light1);
        //lightList.add(2, light2);
    }
}
*/
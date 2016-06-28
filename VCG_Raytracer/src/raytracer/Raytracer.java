/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    1. Send primary ray
    2. intersection test with all shapes
    3. if hit:
    3a: send secondary ray to the light source
    3b: 2
        3b.i: if hit:
            - Shape is in the shade
            - Pixel color = ambient value
        3b.ii: in NO hit:
            - calculate local illumination
    4. if NO hit:
        - set background color

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


package raytracer;

import sceneobjects.Shape;
import ui.Window;
import utils.*;
import sceneobjects.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.lang.Math.*;

public class Raytracer {

    //erstellen der Shape-Array-Liste, die alle Objekte beinhaltet
    private ArrayList<Shape> shapeList = new ArrayList<>();
    //erstellen der Light-Array-Liste, die alle Lichter beinhaltet
    private ArrayList<Light> lightList = new ArrayList<>();
    // erstellen der Material-Array-Liste, die alle Materialien beinhaltet
    ArrayList<Material> materialList = new ArrayList<>();

    private BufferedImage mBufferedImage;
    public Window mRenderWindow;

    //Hintergrundfarbe
    private RgbColor mBackgroundColor = new RgbColor(0, 0, 0);
    public final int maxRecursions = 3;
    public int currentRecursions = 0;

    float minIntersec = 0;
    int intersecShape = -1;
    Vec3 intersectionPoint = null;
    Vec3 intersectionPointShade = null;
    Intersection intersec = null;
    int shadeCount = 0;

    //Konstruktor
    public Raytracer(Window renderWindow)
    {
        mBufferedImage = renderWindow.getBufferedImage();
        mRenderWindow = renderWindow;

    }


    //Funktion renderScene (von Main aufgerufen)
    public void renderScene() {
        Log.print(this, "Start rendering");

        // Kamera, Materialien, Shapes und Lights werden erstellt
        Camera camera = this.createCamera();
        this.createMaterial();
        this.createShapes();
        this.createLight();


        for(int h = 0; h < mRenderWindow.getHeight(); h++){
            for(int w = 0; w < mRenderWindow.getWidth(); w++){

                Ray primaryRay = new Ray(camera.getPosition());
                primaryRay.setDirection(camera.getPosition().add(camera.windowToViewplane(w, h)));

                Shape shape = intersectLoop(primaryRay);
                shadeCount = calculateShadow(shadeCount, intersec, intersectionPoint, intersecShape);

                if(intersec != null){
                    if(shadeCount == 0){
                        mRenderWindow.setPixel(mBufferedImage, shape.material.shade(shape.getNormal(intersec.getIntersec()), primaryRay.startPoint, lightList, intersec.getIntersec()), new Vec2(w, h));
                    }else{
                        RgbColor shade = shape.material.shade(shape.getNormal(intersec.getIntersec()), primaryRay.startPoint, lightList, intersec.getIntersec());

                        for(int i = 0; i < shadeCount; i++){
                            shade.sub(0.1f, 0.1f, 0.1f);
                        }

                        mRenderWindow.setPixel(mBufferedImage, shade, new Vec2(w, h));
                    }
                }
                else{
                    mRenderWindow.setPixel(mBufferedImage, mBackgroundColor, new Vec2(w, h));
                }
                currentRecursions = 0;
            }
        }

        //Speichern des Bildes
        IO.saveImageToPng(mBufferedImage, "raytracing.png");
    }



    public Camera createCamera(){
        //Kamera erstellen (mRenderWindow, Position, LookAt, ViewerUpVektor, viewAngle, focalLength)
        Camera camera = new Camera(mRenderWindow, new Vec3(0, 0f, -30f), new Vec3(0, 0, 0), new Vec3(0, 1, 0), 0.5f, 1f); //Licht erstellen (Lichtart, Position, Farbe, Ambient-Farbe)
        return camera;
    }

    public void createMaterial(){
        //Material erstellen (Ambient, Diffuse, Specular, Shininess)
        Material phong = new Material(new RgbColor(0.1f, 0.1f, 0.1f), new RgbColor(0.8f, 0.8f, 0.8f), new RgbColor(0.1f, 0.1f, 0.1f), 6, 0, 0, this);
        Material phongLeft = new Material(new RgbColor(0.1f, 0.1f, 0.1f), new RgbColor(0f, 0f, 0.8f), new RgbColor(0f, 0f, 0f), 0, 0, 0, this);
        Material phongRight = new Material(new RgbColor(0.1f, 0.1f, 0.1f), new RgbColor(0.8f, 0f, 0f), new RgbColor(0f, 0f, 0f), 0, 0, 0, this);
        Material phongSphere = new Material(new RgbColor(0.1f, 0.1f, 0.1f), new RgbColor(0f, 0f, 0.5f), new RgbColor(0.5f, 0.5f, 0.5f), 5, 1, 1f, this);
        //Materialien zur Liste hinzufügen
        materialList.add(0,phong);
        materialList.add(1,phongSphere);
        materialList.add(2,phongLeft);
        materialList.add(3,phongRight);

    }

    public void createShapes(){
        //Kugel erstellen (Radius, Position, Material)
        Sphere sphere1 = new Sphere(2f, new Vec3 (2, 0, -20f), materialList.get(1));
        Sphere sphere2 = new Sphere(1f, new Vec3(-2.5f, -3, -1), materialList.get(1));
        //Ebene erstellen (Postiton, Normale, Material)
        Plane topPlane = new Plane(new Vec3(0f, 4f, 0f), new Vec3(0, -1, 0), materialList.get(0));
        Plane bottomPlane = new Plane(new Vec3(0f, -4f, 0f), new Vec3(0, 1, 0), materialList.get(0));
        Plane rightPlane = new Plane(new Vec3(-5f, 0f, 0f), new Vec3(1, 0, 0), materialList.get(3));
        Plane leftPlane = new Plane(new Vec3(5f, 0f, 0f), new Vec3(-1, 0, 0), materialList.get(2));
        Plane backPlane = new Plane(new Vec3(0f, 0f, 10f), new Vec3(0, 0, -1), materialList.get(0));
        //Shapes zur Liste hinzufügen
        shapeList.add(0, leftPlane);
        shapeList.add(1, rightPlane);
        shapeList.add(2, topPlane);
        shapeList.add(3, bottomPlane);
        shapeList.add(4, backPlane);
        shapeList.add(5, sphere1);
        shapeList.add(6, sphere2);
    }

    public void createLight(){
        //Licht erstellen (Lichtart, Position, Farbe, Ambient-Farbe)
        Light light0 = new Light(0, new Vec3(0f, 0f, -6), new RgbColor(0.8f, 0.8f, 0.8f), new RgbColor(0.0f, 0.0f, 0.0f));
       // Light light1 = new Light(0, new Vec3(-3f, 3.9f, -5f), new RgbColor(0.8f, 0.8f, 0.8f), new RgbColor(0.0f, 0.0f, 0.0f));
        //Light light2 = new Light(0, new Vec3(10, -4, -3), new RgbColor(1f, 0.1f, 0.8f), new RgbColor(0.0f, 0.0f, 0.0f));
        //Ligts zur Liste hinzugen
        lightList.add(0, light0);
      //  lightList.add(1, light1);
        //lightList.add(2, light2);
    }

    public int calculateShadow(int shadeCount, Intersection intersec, Vec3 intersecP, int intersecShape){
        for (int lightIndex = 0; lightIndex < lightList.size(); lightIndex++) {
            Ray shadowRay = new Ray(intersec.getIntersec());
            shadowRay.setDirection(lightList.get(lightIndex).getPosition());
            float shadowRayLength = shadowRay.endPoint.sub(shadowRay.startPoint).length();

            for(int shapeIndex = 0; shapeIndex < shapeList.size(); shapeIndex++){
                if(shapeIndex != intersecShape){
                    intersecP = shapeList.get(shapeIndex).intersect(shadowRay);
                    if((shadowRay.t != -1) && (shadowRay.t < shadowRayLength)){
                        shadeCount++;
                    }
                }
            }
        }

        return shadeCount;
    }

    public Shape intersectLoop(Ray ray) {
        minIntersec = 0;
        intersecShape = 0;
        intersectionPoint = null;
        intersec = null;
        shadeCount = 0;

        for(int shapeIndex = 0; shapeIndex < shapeList.size(); shapeIndex++){
            intersectionPoint = shapeList.get(shapeIndex).intersect(ray);

            if((ray.t != -1) && (intersec == null)){
                minIntersec = ray.t;
                intersecShape = shapeIndex;
                intersec = new Intersection(ray, shapeList.get(shapeIndex), intersectionPoint, shapeList.get(shapeIndex).getNormal(intersectionPoint) );
                shapeList.get(intersecShape).intersection = new Intersection(ray, shapeList.get(shapeIndex), intersectionPoint, shapeList.get(shapeIndex).getNormal(intersectionPoint) );
            }else if((ray.t != -1) && (ray.t < minIntersec) && (intersec != null)){
                minIntersec = ray.t;
                intersecShape = shapeIndex;
                intersec = new Intersection(ray, shapeList.get(shapeIndex), intersectionPoint, shapeList.get(shapeIndex).getNormal(intersectionPoint));
                shapeList.get(intersecShape).intersection = new Intersection(ray, shapeList.get(shapeIndex), intersectionPoint, shapeList.get(shapeIndex).getNormal(intersectionPoint));
            }
        }

        return shapeList.get(intersecShape);
    }
}

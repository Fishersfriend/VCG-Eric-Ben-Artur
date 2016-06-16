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

import ui.Window;
import utils.*;
import sceneobjects.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.lang.Math.*;

public class Raytracer {

    //erstellen der Shape-Array-Liste, die alle Objekte beinhaltet
    private ArrayList<Shape> shapeList = new ArrayList<>();
    //erstellen der Light-Array-Liste, die alle Lichter beinhaltet
    private ArrayList<Light> lightList = new ArrayList<>();

    private BufferedImage mBufferedImage;
    private Window mRenderWindow;

    //Hintergrundfarbe
    private RgbColor mBackgroundColor = new RgbColor(0, 0, 0);
    private int maxRecursions;

    //Konstruktor
    public Raytracer(Window renderWindow)
    {
        mBufferedImage = renderWindow.getBufferedImage();
        mRenderWindow = renderWindow;
    }

    //Funktion renderScene (von Main aufgerufen)
    public void renderScene() {
        Log.print(this, "Start rendering");

        //Kamera erstellen (mRenderWindow, Position, LookAt, ViewerUpVektor, viewAngle, focalLength)
        Camera camera = new Camera(mRenderWindow, new Vec3(0, 0f, -15f), new Vec3(0, 0, 0), new Vec3(0, 1, 0), 0.5f, 1f);

        //Material erstellen (Ambient, Diffuse, Specular, Shininess)
        Material phong = new Material(new RgbColor(0.1f, 0.1f, 0.1f), new RgbColor(0.5f, 0.5f, 0.5f), new RgbColor(0.1f, 0.1f, 0.1f), 6);
        Material phong2 = new Material(new RgbColor(0.1f, 0.0f, 0.0f), new RgbColor(0f, 0.0f, 1.0f), new RgbColor(0.0f, 0.0f, 0.3f), 50);

        //Kugel erstellen (Radius, Position, Material)
        Sphere sphere1 = new Sphere(1f, new Vec3 (2, 1, -5f), phong2);
        //Sphere sphere2 = new Sphere(1f, new Vec3(4f, 2f, 0), phong2);

        //Ebene erstellen (Postiton, Normale, Material)
        Plane topPlane = new Plane(new Vec3(0f, -4f, 0f), new Vec3(0, 1, 0), phong);
        Plane bottomPlane = new Plane(new Vec3(0f, 4f, 0f), new Vec3(0, -1, 0), phong);
        Plane leftPlane = new Plane(new Vec3(-5f, 0f, 0f), new Vec3(1, 0, 0), phong);
        Plane rightPlane = new Plane(new Vec3(5f, 0f, 0f), new Vec3(-1, 0, 0), phong);
        Plane backPlane = new Plane(new Vec3(0f, 0f, 10f), new Vec3(0, 0, -1), phong);

        //Licht erstellen (Lichtart, Position, Farbe, Ambient-Farbe)
        Light light0 = new Light(0, new Vec3(0, 0.1f, -5), new RgbColor(1f, 1f, 1f), new RgbColor(0.0f, 0.0f, 0.0f));
        //Light light1 = new Light(0, new Vec3(-10, -4, -3), new RgbColor(0.1f, 1f, 0.8f), new RgbColor(0.0f, 0.0f, 0.0f));
        //Light light2 = new Light(0, new Vec3(10, -4, -3), new RgbColor(1f, 0.1f, 0.8f), new RgbColor(0.0f, 0.0f, 0.0f));

        //Lichterliste einordnung
        lightList.add(0, light0);
        //lightList.add(1, light1);
        //lightList.add(2, light2);

        //Objekteliste einordnung
        shapeList.add(0, leftPlane);
        shapeList.add(1, rightPlane);
        shapeList.add(2, topPlane);
        shapeList.add(3, bottomPlane);
        shapeList.add(4, backPlane);
        shapeList.add(5, sphere1);
        //shapeList.add(6, sphere2);

        int intersecShape = 0;
        Intersection intersec = null;
        float oldIntersec = 10000;
        Vec3 intersectionPoint;

        //Renderschleife, Jeden Pixel abfahren
        for(int h = 0; h < mRenderWindow.getHeight(); h++){
            for(int w = 0; w < mRenderWindow.getWidth(); w++){

                //erstellen eines Strahles an Kameraposition
                Ray ray = new Ray(camera.getPosition());
                //Ray-Richtung setzen
                ray.setDirection(camera.getPosition().add(camera.windowToViewplane(w, h)));


                //Kollisionsabfrage mit allen Objekten
                for(int index = 0; index < shapeList.size(); index++){

                    //IntersectionPoint von abgefragtem Objekt berechnen
                    intersectionPoint = shapeList.get(index).intersect(ray);


                    //Abgleichen der Distanz der Schnittpunkte von unterschiedlichen Objekten und nur nähesten beachten
                    if((ray.t != -1) && (ray.t < oldIntersec)){
                        oldIntersec = ray.t;
                        intersecShape = index;
                        intersec = new Intersection(ray, shapeList.get(index), intersectionPoint);
                    }

                }

                //Nähestes Objekt IntersecShape erstellen
                Shape shape = shapeList.get(intersecShape);

                //wenn Objekt, dann Farbe/Material des Objektes an Pixel setzen, ansonsten Hintergrundfarbe
                if(intersec != null)
                {
                    mRenderWindow.setPixel(mBufferedImage, shape.material.shade(shape.getNormal(ray, intersec.getIntersec()), ray.startPoint, lightList, intersec.getIntersec()), new Vec2(w, h));
                }
                else
                {
                    mRenderWindow.setPixel(mBufferedImage, mBackgroundColor, new Vec2(w, h));
                }

                //Alles zurücksetzen, für nächsten durchlauf
                intersecShape = 0;
                intersec = null;
                oldIntersec = 10000;
            }
        }

        //Speichern des Bildes
        IO.saveImageToPng(mBufferedImage, "raytracing.png");
    }





    private RgbColor sendPrimaryRay(){

        // Dummy return value
        return new RgbColor(0, 0, 0);
    }

    private RgbColor traceRay(){

        // Dummy return value
        return new RgbColor(0, 0, 0);
    }

    private RgbColor shade(){

        // Dummy return value
        return new RgbColor(0, 0, 0);
    }

    private RgbColor traceIllumination(){

        // Dummy return value
        return new RgbColor(0, 0, 0);
    }
}

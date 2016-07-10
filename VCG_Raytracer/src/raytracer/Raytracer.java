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
    //Einstellung AntiAliasing
    public static boolean ANTI_ALIASING = true;
    int aliasingDepth = 4;

    //erstellen der Shape-Array-Liste, die alle Objekte beinhaltet
    private ArrayList<Shape> shapeList = new ArrayList<>();

    //erstellen der Light-Array-Liste, die alle Lichter beinhaltet
    private ArrayList<Light> lightList = new ArrayList<>();

    // erstellen der Material-Array-Liste, die alle Materialien beinhaltet
    ArrayList<Material> materialList = new ArrayList<>();

    private BufferedImage mBufferedImage;
    public Window mRenderWindow;

    //Hintergrundfarbe
    private RgbColor mBackgroundColor = new RgbColor(1, 0, 0);

    //RekursionsEinstellungen und Counter
    public final int maxRecursions = 10;
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
    public void renderScene()
    {

        Log.print(this, "Start rendering");

        //Kamera, Materialien, Objekte und Lights werden erstellt
        Camera camera = this.createCamera();
        this.createMaterial();
        this.createShapes();
        this.createLight();


        //Renderschleife für jeden Pixel
        for(int h = 0; h < mRenderWindow.getHeight(); h++)
        {
            for(int w = 0; w < mRenderWindow.getWidth(); w++)
            {


                //Erstellen PrimaryRay
                Ray primaryRay = new Ray();
                Shape shape = null;

                //Falls Alias aktiv
                if(ANTI_ALIASING)
                {
                    //Erstellen AliasStrahl
                    Ray[] aliasRay = new Ray[aliasingDepth];
                    Shape[] shapeArr = new Shape[aliasingDepth];

                    //Aufrufen Funktion calculateAnitAliasing in Raytracer
                    aliasRay = calculateAntiAliasing(w, h, camera);
                    Ray[] refracRay = new Ray[aliasingDepth];

                    for(int i = 0; i < aliasingDepth; i++)
                    {
                        refracRay[i] = null;
                    }

                    RgbColor[] shadeArr = new RgbColor[aliasingDepth];

                    for(int i = 0; i < aliasingDepth; i++)
                    {
                        shapeArr[i] = intersectLoop(aliasRay[i]);

                        if(shapeArr[i].material.transparent > 0)
                        {
                            refracRay[i] = intersec.calculateRefractionRay(shapeArr[i].material.getTransmissionType(), aliasRay[i], false);
                            shapeArr[i] = intersectLoop(refracRay[i]);

                            if(intersec != null)
                            {
                                intersec.normal = intersec.normal.negate();
                                refracRay[i] = intersec.calculateRefractionRay(shapeArr[i].material.getInverseTransmissionType(), refracRay[i], false);
                                shapeArr[i] = intersectLoop(refracRay[i]);
                            }
                        }

                        shadeCount = calculateShadow(shadeCount, intersec, intersectionPoint, intersecShape);

                        if(intersec != null)
                        {
                            if(shadeCount == 0)
                            {
                                shadeArr[i] = shapeArr[i].material.shade(shapeArr[i].getNormal(intersec.getIntersec()), aliasRay[i].startPoint, lightList, intersec.getIntersec());

                            }
                            else
                            {
                                shadeArr[i] = shapeArr[i].material.shade(shapeArr[i].getNormal(intersec.getIntersec()), aliasRay[i].startPoint, lightList, intersec.getIntersec());
                                for(int shadeIndex = 0; shadeIndex < shadeCount; shadeIndex++)
                                {
                                    shadeArr[i].sub(0.1f, 0.1f, 0.1f);
                                }
                            }
                        }
                        else
                        {
                            mRenderWindow.setPixel(mBufferedImage, mBackgroundColor, new Vec2(w, h));
                        }

                        currentRecursions = 0;
                    }

                    mRenderWindow.setPixel(mBufferedImage, RgbColor.calculateAverage(shadeArr), new Vec2(w, h));
                }

                //Falls kein Alias aktiv ist
                else
                {
                    //erstellen des Primary Rays von Kameraposition und setzen der Richtung über setDirection
                    primaryRay = new Ray(camera.getPosition());
                    primaryRay.setDirection(camera.getPosition().add(camera.windowToViewplane(w, h)));

                    //IntersectionTest und Variable shape = getroffenes Objekt
                    shape = intersectLoop(primaryRay);

                    //Falls Material refraktiv
                    //[ erstellen RefractionsStrahl und Berechnung des Strahles     ]
                    //[ Schnittest mit allen Objekten                               ]
                    //[ Bei Treffer Brechung invertieren und Schnittest mit Objekten]

                    if(shape.material.transparent > 0)
                    {
                        Ray refracRay = null;

                        refracRay = intersec.calculateRefractionRay(shape.material.getTransmissionType(), primaryRay, false);
                        shape = intersectLoop(refracRay);

                        if(intersec != null)
                        {
                            intersec.normal = intersec.normal.negate();
                            refracRay = intersec.calculateRefractionRay(shape.material.getInverseTransmissionType(), refracRay, false);
                            shape = intersectLoop(refracRay);
                        }
                    }

                    //Schattenzähler (für überlappende Schatten) = Funktion calculateShadow
                    shadeCount = calculateShadow(shadeCount, intersec, intersectionPoint, intersecShape);

                    //Pixel mit Farbe ausstatten
                    //[ Falls Objekt getroffen Farbe des Objekts bzw Reflektion/Refraktion an dieser Stelle sonst HintergrundFarbe ]
                    if(intersec != null)
                    {
                        // Wenn nicht im Schatten Pixel=Objekt
                        if(shadeCount == 0)
                        {
                                mRenderWindow.setPixel(mBufferedImage, shape.material.shade(shape.getNormal(intersec.getIntersec()), primaryRay.startPoint, lightList, intersec.getIntersec()), new Vec2(w, h));
                        }
                        //ansonsten Verdunkeln um SchattenCounter
                        else
                        {
                            RgbColor shade = shape.material.shade(shape.getNormal(intersec.getIntersec()), primaryRay.startPoint, lightList, intersec.getIntersec());
                            for(int i = 0; i < shadeCount; i++)
                            {
                                shade.sub(0.1f, 0.1f, 0.1f);
                            }
                            mRenderWindow.setPixel(mBufferedImage, shade, new Vec2(w, h));
                        }
                    }
                    //Ansonsten HintergrundFarbe
                    else
                    {
                        mRenderWindow.setPixel(mBufferedImage, mBackgroundColor, new Vec2(w, h));
                    }

                    currentRecursions = 0;
                }

            }
        }
        //Speichern des Bildes
        IO.saveImageToPng(mBufferedImage, "raytracing.png");
    }



    public Camera createCamera()
    {
        //Kamera erstellen (mRenderWindow, Position, LookAt, ViewerUpVektor, viewAngle, focalLength)
        Camera camera = new Camera(mRenderWindow, new Vec3(0, 0f, -19f), new Vec3(0, 0, 0), new Vec3(0, 1, 0), 0.5f, 1f);
        return camera;
    }

    public void createMaterial()
    {
        //Material erstellen (Ambient, Diffuse, Specular, Shininess, Refraktion, Reflection, Raytracer, RefraktionMaterial)
        Material phong = new Material(new RgbColor(0.1f, 0.1f, 0.1f), new RgbColor(0.8f, 0.8f, 0.8f), new RgbColor(0.1f, 0.1f, 0.1f), 6, 0, 0, this);
        Material phongLeft = new Material(new RgbColor(0.1f, 0.1f, 0.1f), new RgbColor(1f, 0.1f, 0.1f), new RgbColor(0f, 0f, 0f), 0, 0, 0, this);
        Material phongRight = new Material(new RgbColor(0.1f, 0.1f, 0.1f), new RgbColor(0.1f, 0.1f, 1f), new RgbColor(0f, 0f, 0f), 0, 0, 0, this);
        Material phongBack = new Material(new RgbColor(0.1f, 0.1f, 0.1f), new RgbColor(0.9f, 0.9f, 0.9f), new RgbColor(0f, 0f, 0f), 0, 0, 0, this);
        Material phongSphere = new Material(new RgbColor(0.1f, 0.1f, 0.1f), new RgbColor(0.8f, 0.8f, 0.8f), new RgbColor(1f, 1f, 1f), 10, 0f, 1f, this, "Water");
        Material phongSphere2 = new Material(new RgbColor(0.1f, 0.1f, 0.1f), new RgbColor(0.8f, 0.8f, 0.8f), new RgbColor(1f, 1f, 1f), 10, 1, 0, this, "Diamant");
        //Materialien zur Liste hinzufügen
        materialList.add(0,phong);
        materialList.add(1,phongSphere);
        materialList.add(2,phongLeft);
        materialList.add(3,phongRight);
        materialList.add(4,phongSphere2);
        materialList.add(5,phongBack);
    }

    public void createShapes()
    {
        //Kugel erstellen (Radius, Position, Material)
        Sphere sphere1 = new Sphere(1.6f, new Vec3 (2.5f, -2.4f, 5f), materialList.get(1));
        Sphere sphere2 = new Sphere(1.6f, new Vec3(-2f, -2.4f, 0f), materialList.get(4));
        //Ebene erstellen (Postiton, Normale, Material)
        Plane topPlane = new Plane(new Vec3(0f, 4f, 0f), new Vec3(0, -1, 0), materialList.get(0));
        Plane bottomPlane = new Plane(new Vec3(0f, -4f, 0f), new Vec3(0, 1, 0), materialList.get(0));
        Plane rightPlane = new Plane(new Vec3(-5.5f, 0f, 0f), new Vec3(1, 0, 0), materialList.get(3));
        Plane leftPlane = new Plane(new Vec3(5.5f, 0f, 0f), new Vec3(-1, 0, 0), materialList.get(2));
        Plane backPlane = new Plane(new Vec3(0f, 0f, 10f), new Vec3(0, 0, -1), materialList.get(5));
        Plane frontPlane = new Plane(new Vec3(0f, 0f, -5f), new Vec3(0, 0, 1), materialList.get(5));
        //Shapes zur Liste hinzufügen
        shapeList.add(0, leftPlane);
        shapeList.add(1, rightPlane);
        shapeList.add(2, topPlane);
        shapeList.add(3, bottomPlane);
        shapeList.add(4, backPlane);
        shapeList.add(5, sphere1);
        shapeList.add(6, sphere2);
        shapeList.add(7, frontPlane);
    }

    public void createLight()
    {
        //Licht erstellen (Lichtart, Position, Farbe, Ambient-Farbe)
        Light light0 = new Light(0, new Vec3(0f, 2.2f, 2.5f), new RgbColor(1f, 1f, 1f), new RgbColor(1.0f, 1.0f, 1.0f));
        //Light light1 = new Light(0, new Vec3(3f, 3.9f, -5f), new RgbColor(0.4f, 0.4f, 0.4f), new RgbColor(0.0f, 0.0f, 0.0f));
        //Light light2 = new Light(0, new Vec3(10, -4, -3), new RgbColor(1f, 0.1f, 0.8f), new RgbColor(0.0f, 0.0f, 0.0f));

        //Ligts zur Liste hinzugen
        lightList.add(0, light0);
        //lightList.add(1, light1);
        //lightList.add(2, light2);
    }

    public int calculateShadow(int shadeCount, Intersection intersec, Vec3 intersecP, int intersecShape)
    {
        //Berechnung der Schatten für alle Lichtquellen in der LichtListe
        for(int lightIndex = 0; lightIndex < lightList.size(); lightIndex++)
        {
            //erstellen Schattenstrahl und in Richtung des Lichtes
            Ray shadowRay = new Ray(intersec.getIntersec());
            shadowRay.setDirection(lightList.get(lightIndex).getPosition());

            if (intersecShape == -5)
            {
                shadowRay.startPoint = shadowRay.startPoint.add(shadowRay.direction.multScalar(0.01f));
            }
            float shadowRayLength = shadowRay.endPoint.sub(shadowRay.startPoint).length();

            //Schnitttest ShadowRay mit allen Objekten
            for(int shapeIndex = 0; shapeIndex < shapeList.size(); shapeIndex++)
            {
                if(shapeIndex != intersecShape)
                {
                    intersecP = shapeList.get(shapeIndex).intersect(shadowRay);

                    if((shadowRay.t != -1) && (shadowRay.t < shadowRayLength))
                    {
                        shadeCount++;
                    }
                }
            }
        }
        return shadeCount;
    }

    public Shape intersectLoop(Ray ray)
    {
        //Schnittest mit allen Objekten
        minIntersec = 0;
        intersecShape = 0;
        intersectionPoint = null;
        intersec = null;
        shadeCount = 0;

        //Schleife für alle Objekte in ShapeList
        for(int shapeIndex = 0; shapeIndex < shapeList.size(); shapeIndex++)
        {
            //Berechnung des Schnittpunktes mit dem Objekt durch Funktion im Objekt und Rückgabe des Schnittpunktes
            intersectionPoint = shapeList.get(shapeIndex).intersect(ray);

            //Test welches Objekt am weitesten vorne ist
            //ray.t nicht Standardwert und null
            if((ray.t != -1) && (intersec == null))
            {
                minIntersec = ray.t;
                intersecShape = shapeIndex;
                intersec = new Intersection(ray, shapeList.get(shapeIndex), intersectionPoint, shapeList.get(shapeIndex).getNormal(intersectionPoint) );
                shapeList.get(intersecShape).intersection = new Intersection(ray, shapeList.get(shapeIndex), intersectionPoint, shapeList.get(shapeIndex).getNormal(intersectionPoint) );
            }
            //ray.t nicht Standardwert, kleiner als letzer minimalIntersect und Intersect nicht Null
            else if((ray.t != -1) && (ray.t < minIntersec) && (intersec != null))
            {
                minIntersec = ray.t;
                intersecShape = shapeIndex;
                intersec = new Intersection(ray, shapeList.get(shapeIndex), intersectionPoint, shapeList.get(shapeIndex).getNormal(intersectionPoint));
                shapeList.get(intersecShape).intersection = new Intersection(ray, shapeList.get(shapeIndex), intersectionPoint, shapeList.get(shapeIndex).getNormal(intersectionPoint));
            }
        }
        return shapeList.get(intersecShape);
    }

    public Ray[] calculateAntiAliasing(float currXpixel, float currYpixel, Camera cam)
    {
        Ray[] aliasRay = new Ray[aliasingDepth];

        //erstellen AliasStrahle

        for(int i = 0; i < aliasingDepth; i++)
        {
          aliasRay[i] = new Ray(cam.getPosition());
        }
        //Rays offset geben
        aliasRay[0].setDirection(cam.getPosition().add(cam.windowToViewplane(currXpixel-0.250f, currYpixel-0.250f)));
        aliasRay[1].setDirection(cam.getPosition().add(cam.windowToViewplane(currXpixel-0.250f, currYpixel+0.250f)));
        aliasRay[2].setDirection(cam.getPosition().add(cam.windowToViewplane(currXpixel+0.250f, currYpixel-0.250f)));
        aliasRay[3].setDirection(cam.getPosition().add(cam.windowToViewplane(currXpixel+0.250f, currYpixel+0.250f)));

        return aliasRay;
    }
}

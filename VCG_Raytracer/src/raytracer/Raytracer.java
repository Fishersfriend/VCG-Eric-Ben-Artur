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

public class Raytracer {                                                                                                //Klasse Raytracer

    private ArrayList<Shape> shapeList = new ArrayList<>();
    private ArrayList<Light> lightList = new ArrayList<>();                                                             //array für alle Lichter

    private BufferedImage mBufferedImage;
    private Window mRenderWindow;
    private RgbColor mBackgroundColor;                                                                                  //Hintergrundfarbe
    private int maxRecursions;                                                                                          //Rekursionen

    public Raytracer(Window renderWindow){                                                                              //Konstruktor
        mBufferedImage = renderWindow.getBufferedImage();
        mRenderWindow = renderWindow;
    }

    public void renderScene() {                                                                                          //Methode renderScene (aufgerufen von Main)
        Log.print(this, "Start rendering");

        RgbColor background = new RgbColor(0, 0, 0);                                                                      //setzen der Hintergrundfarbe

        //Kamera erstellen
        Camera camera = new Camera(mRenderWindow, new Vec3(0, 0,-20f), new Vec3(0, 0, 0),                              //Kamera erstellen (Position,LookAt,UserUp,
                new Vec3(0, 1, 0), 2f, 1f);                                                                             //                  ViewAngle,focalLength)

        Material phong = new Material(new RgbColor(0.5f, 0.5f, 0.5f), new RgbColor(0.5f, 0.5f, 0.5f), new RgbColor(0.5f, 0.5f, 0.5f), 6);

        Ray ray = new Ray(camera.getCameraPosition());                                                                  //Ray erstellen (startPosition)
        ray.setDirection(camera.windowToViewplane(399, 299));                                                           //setdirection(endPoint)aufrufen
        ray.normalize();                                                                                                //normalze aufrufen

        Sphere sphere = new Sphere(1f, new Vec3(-1f, -0f, 40), phong);                                                     //Kugel erstellen (radius, position Material
        Plane topPlane = new Plane(new Vec3(0f, -4f, 0f), new Vec3(0, 1, 0), phong);
        Plane bottomPlane = new Plane(new Vec3(0f, 4f, 0f), new Vec3(0, -1, 0), phong);                                          //Plane Erstellen (Position,Normal,Farbe)
        Plane leftPlane = new Plane(new Vec3(-5f, 0f, 0f), new Vec3(1, 0, 0), phong);
        Plane rightPlane = new Plane(new Vec3(5f, 0f, 0f), new Vec3(-1, 0, 0), phong);
        Plane backPlane = new Plane(new Vec3(0f, 0f, 150f), new Vec3(0, 0f, -1), phong);

        Light light0 = new Light(0, new Vec3(10, 4, -3), new RgbColor(1f, 0.8f, 0.1f), new RgbColor(0.1f, 0.1f, 0.1f));   //Licht Erstellen (type,position,color,ambient)
        Light light1 = new Light(0, new Vec3(-10, -4, -3), new RgbColor(0.1f, 1f, 0.8f), new RgbColor(0.1f, 0.1f, 0.1f));

        lightList.add(0, light0);
        lightList.add(1, light1);

        shapeList.add(0, leftPlane);
        shapeList.add(1, rightPlane);
        shapeList.add(2, topPlane);
        shapeList.add(3, bottomPlane);
        shapeList.add(4, backPlane);
        shapeList.add(5, sphere);

        int intersecShape = 0;
        Intersection intersec = null;
        float oldIntersec = 100000000;

        for(int h = 0; h < mRenderWindow.getHeight(); h++){                                                             //Schleife für Höhe
            for(int w = 0; w < mRenderWindow.getWidth(); w++){

                ray.setDirection(camera.windowToViewplane(w, h));                                                       //Strahl durch jeden Pixel
                ray.normalize();

                for(int index = 0; index < shapeList.size(); index++){
                    shapeList.get(index).intersect(ray);

                    if((ray.t != -1) && (ray.t < oldIntersec)){
                        oldIntersec = ray.t;
                        intersecShape = index;
                        intersec = new Intersection(ray, shapeList.get(index));

                        if(h == 320 && w == 600){
                          System.out.print(index + "(" + w + "/" + h + ")" + ": ");
                          System.out.println(intersec.getIntersec());

                        }

                        if(h == 400 && w == 600){
                          System.out.print(index + "(" + w + "/" + h + ")" + ": ");
                          System.out.println(intersec.getIntersec());

                        }
                    }

                }

                Shape shape = shapeList.get(intersecShape);

                if(intersec != null){

                  mRenderWindow.setPixel(mBufferedImage, shape.material.shade(shape.getNormal(ray), ray.startPoint, lightList, intersec), new Vec2(w, h));

                }else{
                  mRenderWindow.setPixel(mBufferedImage, background, new Vec2(w, h));
                }

                intersecShape = 0;
                intersec = null;
                oldIntersec = 100000000;
            }
        }

                                                                                       //LichtListe

        /*
        for(int h = 0; h < mRenderWindow.getHeight(); h++){                                                             //Schleife für Höhe
            for(int w = 0; w < mRenderWindow.getWidth(); w++){                                                          //schleife für Breite

                ray.setDirection(camera.windowToViewplane(w, h));                                                       //Strahl durch jeden Pixel
                ray.normalize();

               // mRenderWindow.setPixel(mBufferedImage, new RgbColor(ray.direction.z, ray.direction.z, ray.direction.z), new Vec2(w, h));

                if (sphere.intersect(ray)) {                                                                            //Wenn Kugel getroffen
                    Intersection intersec = new Intersection(ray, sphere);                                              //Intersection Objekt erstellen
                    mRenderWindow.setPixel(mBufferedImage, sphere.material.shade(sphere.getNormal(ray), ray.startPoint, lightList, intersec), new Vec2(w, h));
                }
                else if (bottomPlane.intersect(ray) || leftPlane.intersect(ray)) {                                      //wenn Plane getroffen

                    float t0 = 0;
                    float t1 = 0;

                    //Intersection intersec = new Intersection(ray, bottomPlane);


                    if (bottomPlane.intersect(ray) ){
                        t0 = ray.t;
                    }

                    if(leftPlane.intersect(ray) ){
                        t1 = ray.t;
                    }


                    if(bottomPlane.intersect(ray) && !leftPlane.intersect(ray) || bottomPlane.intersect(ray) && t0>t1){
                        mRenderWindow.setPixel(mBufferedImage, bottomPlane.color , new Vec2(w, h));
                    }
                    else if(leftPlane.intersect(ray) && !bottomPlane.intersect(ray) || leftPlane.intersect(ray) && t0<t1  ) {
                      mRenderWindow.setPixel(mBufferedImage, leftPlane.color, new Vec2(w, h));
                    }


                }
                else {
                    mRenderWindow.setPixel(mBufferedImage, background, new Vec2(w, h));
                }

            }

            mRenderWindow.setPixel(mBufferedImage, background, new Vec2(w, h));
        }

        IO.saveImageToPng(mBufferedImage, "raytracing.png");                                                            //Speichere Bild
        */

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

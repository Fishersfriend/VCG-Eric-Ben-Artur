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

    //private ArrayList<Shape> shapeList = new ArrayList<>();
    private ArrayList<Light> lightList = new ArrayList<>();

    private BufferedImage mBufferedImage;
    private Window mRenderWindow;
    private RgbColor mBackgroundColor;
    private int maxRecursions;

    public Raytracer(Window renderWindow){
        mBufferedImage = renderWindow.getBufferedImage();
        mRenderWindow = renderWindow;
    }

    public void renderScene(){
        Log.print(this, "Start rendering");

        float r = 1;
        float g = 1;
        float b = 1;

        RgbColor background = new RgbColor(0,0,0);

		Camera camera = new Camera(mRenderWindow, new Vec3(0, 0, -5f), new Vec3(0, 0, 10), new Vec3(0, 1, 0), (float) Math.PI-0.1f, 1);
        Ray ray = new Ray(camera.getCameraPosition());
        ray.setDirection(camera.windowToViewplane(399, 299));
        ray.normalize();
        Sphere sphere = new Sphere(4f, new Vec3(0,0,0), new Material(new RgbColor(0.5f,0.5f,0.5f), new RgbColor(0.5f,0.5f,0.5f), new RgbColor(0.5f,0.5f,0.5f), 6));  //radius,pos,ka,ks,kd,n
        Light light0 = new Light(0, new Vec3(10,4,-3), new RgbColor(1f, 0.8f, 0.1f), new RgbColor(0.1f, 0.1f, 0.1f));   //type,pos,color,ambient
        Light light1 = new Light(0, new Vec3(-10,-4,-3), new RgbColor(0.1f, 1f, 0.8f), new RgbColor(0.1f, 0.1f, 0.1f));

        lightList.add(0, light0); lightList.add(1, light1);

        for(int h = 0; h < mRenderWindow.getHeight(); h++){
            for(int w = 0; w < mRenderWindow.getWidth(); w++){

                ray.setDirection(camera.windowToViewplane(w, h));
                ray.normalize();

               // mRenderWindow.setPixel(mBufferedImage, new RgbColor(ray.direction.z, ray.direction.z, ray.direction.z), new Vec2(w, h));

                if (sphere.intersect(ray)) {
                    Intersection intersec = new Intersection(ray, sphere);

                    mRenderWindow.setPixel(mBufferedImage, sphere.material.shade(sphere.getNormal(ray), ray.startPoint, lightList, intersec), new Vec2(w, h));
                } else {
                    mRenderWindow.setPixel(mBufferedImage, background, new Vec2(w, h));
                }

            }
        }

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

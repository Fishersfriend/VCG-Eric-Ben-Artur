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
    //private ArrayList<Light> lightList = new ArrayList<>();

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

        float r = 0;
        float g = 0;
        float b = 1;
		
		Ray ray = new Ray(mRenderWindow.getWidth(), mRenderWindow.getHeight());
		Camera camera = new Camera(new Vec3(0, 0, 1));
		ray.setStartPoint(camera.getCameraPosition());	

        for(int h = 0; h < mRenderWindow.getHeight(); h++){
            for(int w = 0; w < mRenderWindow.getWidth(); w++) {
                ray.normalizePixel(w, h);
                    if(w < 400 && h < 300){
                        r = ray.getDirection().x+1;
                        g = ray.getDirection().y+1;

                    }
                    if(h >= 300){
                        r = ray.getDirection().x+1;
                        g = Math.abs(ray.getDirection().y-1);
                    }
                    if(w >=400){
                        r = Math.abs(ray.getDirection().x-1);
                    }

                mRenderWindow.setPixel(mBufferedImage, new RgbColor(r, g, b) , new Vec2(w, h));
				
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

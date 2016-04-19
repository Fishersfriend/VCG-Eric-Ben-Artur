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

import java.awt.image.BufferedImage;

public class Raytracer {

    private BufferedImage mBufferedImage;
    private Window mRenderWindow;

    public Raytracer(Window renderWindow){
        mBufferedImage = renderWindow.getBufferedImage();
        mRenderWindow = renderWindow;
    }

    public void renderScene(){
        Log.print(this, "Start rendering");

        float r = 0;
        float g = 0;
        float b = 0;

        for(int i = 0; i < mRenderWindow.getWidth(); i++){
            for(int j = 0; j < mRenderWindow.getHeight(); j++) {

                mRenderWindow.setPixel(mBufferedImage, new RgbColor(r, g, b), new Vec2(i, j));
                g += 0.0008;
                r += 0.000002;
            }

            g = 0;
        }

        IO.saveImageToPng(mBufferedImage, "raytracing.png");
    }
}

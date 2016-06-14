package sceneobjects;

import utils.RgbColor;
import utils.Vec3;
import sceneobjects.*;
import java.lang.Math;
import java.util.ArrayList;

public class Material {

    private RgbColor ka, kd, ks;
    private float n;


    public Material(RgbColor ambient, RgbColor diffuse, RgbColor specular, float shininess) {
        ka = ambient; kd = diffuse; ks = specular; n = shininess;
    }

    public RgbColor shade(Vec3 normal, Vec3 cameraPos, ArrayList<Light> lightList, Vec3 intersecPoint) {

        float red = 0, green = 0, blue = 0;
        int i;

        for (i = 0; i < lightList.size(); i++){

            Vec3 r = normal.multScalar(2 * normal.scalar(lightList.get(i).getPosition())).sub(lightList.get(i).getPosition()).normalize();
            Vec3 l = lightList.get(i).getPosition().sub(intersecPoint).normalize();
            Vec3 v = cameraPos.sub(intersecPoint).normalize();
            double spec = Math.pow(v.scalar(r), n);
            float temp = normal.scalar(l);


            red += (float) (lightList.get(i).getAmbient().red() * ka.red() + lightList.get(i).getColor().red() * (kd.red() * temp + ks.red() * spec));

            green += (float) (lightList.get(i).getAmbient().green() * ka.green() + lightList.get(i).getColor().green() * (kd.green() * temp + ks.green() * spec));

            blue += (float) (lightList.get(i).getAmbient().blue() * ka.blue() + lightList.get(i).getColor().blue() * (kd.blue() * temp + ks.blue() * spec));


        }

        return new RgbColor(red,green,blue);
    }
}

package sceneobjects;

import utils.RgbColor;
import utils.Vec3;
import sceneobjects.*;
import java.lang.Math;
import java.util.ArrayList;

public class Material {                                                                                                 //Klasse Material

    private RgbColor ka, kd, ks;                                                                                        //Variblen Ambient,Diffus,Spekular,Shininess
    private double n;


    public Material(RgbColor ambient, RgbColor diffuse, RgbColor specular, double shininess) {                          //Übergabe der Variablen

        ka = ambient; kd = diffuse; ks = specular; n = shininess;
    }

    public RgbColor shade(Vec3 normal, Vec3 cameraPos, ArrayList<Light> lightList, Intersection intersec) {             //Phong Beleuchtungs-Berechnung

        float red = 0, green = 0, blue = 0;
        int i;

        for (i = 0; i < lightList.size(); i++){

            Vec3 r = normal.multScalar(2 * normal.scalar(lightList.get(i).getPosition())).sub(lightList.get(i).getPosition()).normalize();
            Vec3 l = lightList.get(i).getPosition().sub(intersec.getIntersec()).normalize();
            Vec3 v = cameraPos.sub(intersec.getIntersec()).normalize();

            red += (float) (lightList.get(i).getAmbient().red() * ka.red() + lightList.get(i).getColor().red() * (kd.red() * normal.scalar(l) + ks.red() * Math.pow(v.scalar(r), n)));

            green += (float) (lightList.get(i).getAmbient().green() * ka.green() + lightList.get(i).getColor().green() * (kd.green() * normal.scalar(l) + ks.green() * Math.pow(v.scalar(r), n)));

            blue += (float) (lightList.get(i).getAmbient().blue() * ka.blue() + lightList.get(i).getColor().blue() * (kd.blue() * normal.scalar(l) + ks.blue() * Math.pow(v.scalar(r), n)));

        }

        return new RgbColor(red,green,blue);
    }
}

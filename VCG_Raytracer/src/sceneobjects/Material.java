package sceneobjects;

import utils.RgbColor;
import utils.Vec3;
import sceneobjects.*;
import java.lang.Math;
import java.util.ArrayList;

public class Material {

    private RgbColor ka, kd, ks;
    private float n;
    public float transparent;
    public float reflection;

    //Konstruktor
    public Material(RgbColor ambient, RgbColor diffuse, RgbColor specular, float shininess, float transparent, float reflection) {
        ka = ambient; kd = diffuse; ks = specular; n = shininess;
        this.transparent = transparent;
        this.reflection = reflection;
    }

    //Funktion Shade
    //[Setzen der Übergabe-werte und Berechnung von Ambiente, Diffus und Spekular für jedes Licht in LichtListe]
    public RgbColor shade(Vec3 normal, Vec3 cameraPos, ArrayList<Light> lightList, Vec3 intersecPoint) {

        float red = 0, green = 0, blue = 0;
        double spec;
        int i;

        for (i = 0; i < lightList.size(); i++){

            //Berechnung Lightvektor und normalizierung
            Vec3 l = lightList.get(i).getPosition().sub(intersecPoint).normalize();
            //Berechnung ViewerVektor und normalizierung
            Vec3 v = cameraPos.sub(intersecPoint).normalize();
            //Berechnung R- Vektor und normalizierung
            Vec3 r =  normal.multScalar((normal.scalar(l)) * 2).sub(l).normalize();

            float vnScalar = v.scalar(r);
            if (vnScalar > 0)
                spec = Math.pow(vnScalar, n);
            else
                spec = 0;
            float temp = normal.scalar(l);

            //Berechung für rgb mit errechneten Vektoren
            red += (float) (lightList.get(i).getAmbient().red() * ka.red() + lightList.get(i).getColor().red() * (kd.red() * temp + ks.red() * spec));

            green += (float) (lightList.get(i).getAmbient().green() * ka.green() + lightList.get(i).getColor().green() * (kd.green() * temp + ks.green() * spec));

            blue += (float) (lightList.get(i).getAmbient().blue() * ka.blue() + lightList.get(i).getColor().blue() * (kd.blue() * temp + ks.blue() * spec));

        }

        return new RgbColor(red,green,blue);
    }
}

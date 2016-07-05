package sceneobjects;

import raytracer.Ray;
import raytracer.Raytracer;
import utils.RgbColor;
import utils.Vec3;
import sceneobjects.*;
import java.lang.Math;
import java.util.ArrayList;

public class Material {

    private RgbColor ka, kd, ks;
    private float n;
    public float transparent;
    public String transmissionType;
    private float reflection;
    private Raytracer raytracer;
    private float phongCoeff;
    private int shadeCount = 0;

    //Konstruktor
    public Material(RgbColor ambient, RgbColor diffuse, RgbColor specular, float shininess, float transparent, float reflection, Raytracer raytracer) {
        ka = ambient; kd = diffuse; ks = specular; n = shininess;
        this.transparent = transparent;
        this.reflection = reflection;
        this.raytracer = raytracer;
        if (reflection > 1) {
            this.reflection = 1;
        } else if (reflection < 0) {
            this.reflection = 0;
        }
        if (transparent > 1) {
            this.transparent = 1;
        } else if (transparent < 0) {
            this.transparent = 0;
        }
        phongCoeff = 1 - this.reflection;
    }

    public Material(RgbColor ambient, RgbColor diffuse, RgbColor specular, float shininess, float transparent, float reflection, Raytracer raytracer, String transmType) {
        ka = ambient; kd = diffuse; ks = specular; n = shininess;
        this.transparent = transparent;
        this.reflection = reflection;
        this.raytracer = raytracer;
        if (reflection > 1) {
            this.reflection = 1;
        } else if (reflection < 0) {
            this.reflection = 0;
        }
        if (transparent > 1) {
            this.transparent = 1;
        } else if (transparent < 0) {
            this.transparent = 0;
        }
        phongCoeff = 1 - this.reflection;
        transmissionType = transmType;
    }

    //Funktion Shade
    //[Setzen der Übergabe-werte und Berechnung von Ambiente, Diffus und Spekular für jedes Licht in LichtListe]
    public RgbColor shade(Vec3 normal, Vec3 cameraPos, ArrayList<Light> lightList, Vec3 intersecPoint) {





        float red = 0, green = 0, blue = 0;
        double spec;
        int i;
        RgbColor reflecColor = new RgbColor(0,0,0);

        for (i = 0; i < lightList.size(); i++){

            if (reflection < 1) {
                //Berechnung Lightvektor und normalizierung
                Vec3 l = lightList.get(i).getPosition().sub(intersecPoint).normalize();
                //Berechnung ViewerVektor und normalizierung
                Vec3 v = cameraPos.sub(intersecPoint).normalize();
                //Berechnung R- Vektor und normalizierung
                Vec3 r = normal.multScalar((normal.scalar(l)) * 2).sub(l).normalize();

                float vnScalar = v.scalar(r);
                if (vnScalar > 0)
                    spec = Math.pow(vnScalar, n);
                else
                    spec = 0;
                float temp = normal.scalar(l);
                if (temp > 1)
                    temp = 1;
                else if (temp < 0)
                    temp = 0;

                //Berechung für rgb mit errechneten Vektoren
                red += (float) (lightList.get(i).getAmbient().red() * ka.red() + lightList.get(i).getColor().red() * (kd.red() * temp + ks.red() * spec));

                green += (float) (lightList.get(i).getAmbient().green() * ka.green() + lightList.get(i).getColor().green() * (kd.green() * temp + ks.green() * spec));

                blue += (float) (lightList.get(i).getAmbient().blue() * ka.blue() + lightList.get(i).getColor().blue() * (kd.blue() * temp + ks.blue() * spec));

            }

        }

        if(reflection != 0 && raytracer.maxRecursions > raytracer.currentRecursions)
        {
            raytracer.currentRecursions++;
            Vec3 v = cameraPos.sub(intersecPoint).normalize();
            Vec3 r =  normal.multScalar((normal.scalar(v)) * 2).sub(v);
            if (v.length() > 1.001f) {
                System.out.print(normal+"\n");
            }

            Ray reflecRay = new Ray (intersecPoint.add(r.multScalar(0.1f)), r);

            Shape shape = raytracer.intersectLoop(reflecRay);

            if (shape != null) {
                reflecColor = shape.material.shade(shape.getNormal(shape.intersection.getIntersec()), cameraPos, lightList, shape.intersection.getIntersec());
            }
            shadeCount = 0;
            shadeCount = raytracer.calculateShadow(shadeCount, shape.intersection, shape.intersection.getIntersec(), -1);
            System.out.print(shadeCount+"\n");
            for(int j = 0; j < shadeCount; j++){
                reflecColor.sub(0.1f, 0.1f, 0.1f);
            }

        }


        return new RgbColor(red*phongCoeff + reflecColor.red()*reflection, green*phongCoeff + reflecColor.green()*reflection, blue*phongCoeff + reflecColor.blue()*reflection);
    }

    public float getTransmissionType(){
        if(transmissionType != null){
            if(transmissionType.equals("Glass")){
                return (4/7);
            }else{
                return (4/7);
            }
        }

        return (4/7);
    }
}

// ************************************************************ //
//                      Hochschule Duesseldorf                  //
//                                                              //
//                     Vertiefung Computergrafik                //
// ************************************************************ //


/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    1. Documentation:    Did you comment your code shortly but clearly?
    2. Structure:        Did you clean up your code and put everything into the right bucket?
    3. Performance:      Are all loops and everything inside really necessary?
    4. Theory:           Are you going the right way?

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

 <<< YOUR TEAM NAME >>>

     Master of Documentation:
     Master of Structure:
     Master of Performance:
     Master of Theory:

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

import ui.Window;
import raytracer.*;
import utils.Log;
import utils.Vec3;
import sceneobjects.*;

// Main application class. This is the routine called by the JVM to run the program.
public class Main {                                                                                                     //Klasse Main

    static int IMAGE_WIDTH = 800;                                                                                       //Weite und Breite des Bildes
    static int IMAGE_HEIGHT = 600;

    // Initial method. This is where the show begins.
    public static void main(String[] args){
        long tStart = System.currentTimeMillis();                                                                       //start Timer

        Window renderWindow = new Window(IMAGE_WIDTH, IMAGE_HEIGHT);                                                    //Fenster erstellen
		

        draw(renderWindow);                                                                                             //draw Methode aufrufen

        renderWindow.setTimeToLabel(String.valueOf(stopTime(tStart)));                                                  //stop Timer
    }

    private static void draw(Window renderWindow){                                                                      //Methode draw
        raytraceScene(renderWindow);                                                                                    //raytraceScene Methode aufrufen
    }

    private static void raytraceScene(Window renderWindow){                                                             //Methode raytraceScene
        Raytracer raytracer = new Raytracer(renderWindow);                                                              //erstellen neues Raytracer

        raytracer.renderScene();                                                                                        //raytracer.renderScene Methode aufrufen
    }

    private static double stopTime(long tStart){                                                                        //Timer
        long tEnd = System.currentTimeMillis();
        long tDelta = tEnd - tStart;
        return tDelta / 1000.0;
    }
}
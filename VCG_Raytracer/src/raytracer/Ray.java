package raytracer;

import utils.*;

public class Ray {																										//Klasse Raytracer

	public Vec3 startPoint;																								//Variablen
	public Vec3 endPoint;
	public Vec3 direction;
	public float t;
	public float distance;

	public Ray() {

	}

    public Ray(Vec3 startPoint){
		this.startPoint = startPoint;																					//startpoint übergabe
    }


	public void setStartPoint(Vec3 startPoint){																			//Methode setStartPoint (wird in aufgerufen)
		this.startPoint = startPoint;
	}

	public void setDirection(Vec3 endPoint){																			//Methode setDirection
		this.endPoint = endPoint;
		this.direction = endPoint.sub(startPoint);																		//errechnen der Richtung (end-start)
	}

	public void normalize(){																							//Methode normalize
		direction = direction.normalize();																				//Normalisiert den Richtungsvektor
		//System.out.println(direction);
	}
}

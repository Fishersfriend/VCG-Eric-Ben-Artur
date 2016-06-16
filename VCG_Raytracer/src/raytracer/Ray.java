package raytracer;

import utils.*;

public class Ray {

	//erstellend er TransformationsMatrix
	public Matrix4 matrixTransformation;

	public Vec3 startPoint;
	public Vec3 endPoint;
	public Vec3 direction;
	public float t;

	public Ray() {

	}

	//Übergabe des Kamerastartpunktes
	public Ray(Vec3 startPoint){
		this.startPoint = startPoint;
	}

	//Berechnung der Richtung des Strahles
	//[Endpoint auf SichtEbene - StartVektor]
	public void setDirection(Vec3 endPoint){
		this.endPoint = endPoint;
		this.direction = endPoint.sub(startPoint).normalize();
	}

}

package raytracer;

import utils.*;

public class Ray
{
	public Vec3 startPoint;
	public Vec3 endPoint;
	public Vec3 direction;
	public float t;

	public Ray()
	{
	}

	//Übergabe des Kamerastartpunktes
	public Ray(Vec3 startPoint)
	{
		this.startPoint = startPoint;
	}

	//Überga des Kamerastartpunkt und Richtung
	public Ray(Vec3 startPoint, Vec3 direction)
	{
		this.startPoint = startPoint;
		this.direction = direction;
	}

	//Berechnung der Richtung des Strahles
	//[Endpoint auf SichtEbene - StartVektor]
	public void setDirection(Vec3 endPoint){
		this.endPoint = endPoint;
		this.direction = endPoint.sub(startPoint).normalize();
	}

}

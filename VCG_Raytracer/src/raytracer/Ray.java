package raytracer;

import utils.*;

public class Ray {

	public Matrix4 matrixTransformation;

	public Vec3 startPoint;
	public Vec3 endPoint;
	public Vec3 direction;
	public float t;

	public Ray() {

	}

	public Ray(Vec3 startPoint){
		this.startPoint = startPoint;
	}


	public void setDirection(Vec3 endPoint){
		this.endPoint = endPoint;
		this.direction = endPoint.sub(startPoint).normalize();
	}

}

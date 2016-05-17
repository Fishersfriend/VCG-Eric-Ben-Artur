package raytracer;

import utils.*;

public class Ray {

	public Vec3 startPoint;
	public Vec3 endPoint;
	public Vec3 direction;
	public float t;
	public float distance;

	public Ray() {

	}

    public Ray(Vec3 startPoint){
		this.startPoint = startPoint;
    }


	public void setStartPoint(Vec3 startPoint){
		this.startPoint = startPoint;
	}

	public void setDirection(Vec3 endPoint){
		this.endPoint = endPoint;
		this.direction = endPoint.sub(startPoint);
	}

	public void normalize(){
		direction = direction.normalize();
		//System.out.println(direction);
	}
}

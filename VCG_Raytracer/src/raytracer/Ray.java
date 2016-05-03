package raytracer;

import utils.*;

public class Ray {
	
	public Vec3 startPoint;
	public Vec3 endPoint;
	public Vec3 direction;
	public float distance;

	private float windowWidth;
	private float windowHeight;
	
	public float xNormPixel;
	public float yNormPixel;

	public Ray() {

	}
	
    public Ray(float windowWidth, float windowHeight){
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
    }

	
	public void setStartPoint(Vec3 startPoint){
		this.startPoint = startPoint;
	}
	
	public void normalizePixel(float xPixel, float yPixel){
		xNormPixel = ((2*xPixel+1)/windowWidth)-1;
		yNormPixel = ((2*yPixel+1)/windowHeight)-1;
		
		endPoint = new Vec3(xNormPixel, yNormPixel, 0);
		
		direction = endPoint.sub(startPoint);
	}
	
	public Vec3 getDirection(){
		return direction;
	}
}

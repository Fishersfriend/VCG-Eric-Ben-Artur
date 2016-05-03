package sceneobjects;

import raytracer.*;
import utils.*;
import ui.*;
import java.lang.Math;

public class Camera extends SceneObject {
	private Vec3 cameraPosition;
	private Vec3 lookAt;
	private Vec3 userUpVector;

	private Vec3 viewVector;
	private Vec3 sideVector;
	private Vec3 upVector;

	private float windowHeight;
	private float windowWidth;

	private float viewAngle;
	private float focalLength;
	private float ratio;

	public Camera(Vec3 cameraPosition, Vec3 lookAt, Vec3 userUpVector, float viewAngle, float focalLength, float ratio){

		viewVector = lookAt.sub(cameraPosition);
		viewVector = viewVector.normalize();
		userUpVector = userUpVector.normalize();
		sideVector = viewVector.cross(userUpVector);
		upVector = sideVector.cross(viewVector);

		windowHeight = 2 * (float) Math.tan(viewAngle/2);
		System.out.println(ratio);

		this.viewAngle = viewAngle;
		this.focalLength = focalLength;
		this.ratio = ratio;
		this.lookAt = lookAt;
		this.userUpVector = userUpVector;
		this.cameraPosition = cameraPosition;
	}

	public Vec3 calculateDestinationPoint(){

		return new Vec3(0,0,0);
	}

	public Vec3 getCameraPosition(){
		return cameraPosition;
	}



}

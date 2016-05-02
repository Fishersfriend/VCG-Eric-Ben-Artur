package sceneobjects;

import raytracer.*;
import utils.*;
import ui.*;

public class Camera extends SceneObject {
	private Vec3 cameraPosition;
	private Vec3 lookAt;
	private Vec3 upVector;
	private float viewAngle;
	private float focalLength;
	
	public Camera(Vec3 cameraPosition){
		this.cameraPosition = cameraPosition;
	}
	
	public Vec3 calculateDestinationPoint(){
		
		return new Vec3(0,0,0);
	}
	
	public Vec3 getCameraPosition(){
		return cameraPosition;
	}
	
	
	
}
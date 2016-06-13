package sceneobjects;

import raytracer.*;
import utils.*;
import ui.*;
import java.lang.Math;

public class Camera extends SceneObject {

	Matrix4 matrixTransformation;
	private Vec3 lookAt;
	private Vec3 userUpVector;

	private Vec3 viewVector;
	private Vec3 sideVector;
	private Vec3 upVector;

	private float viewplaneHeight;
	private float viewplaneWidth;

	private float viewAngle;
	private float focalLength;
	private float ratio;

	private Window renderWindow;
	private float viewplaneX;
	private float viewplaneY;

	public Camera(Window renderWindow, Vec3 cameraPosition, Vec3 lookAt, Vec3 userUpVector, float viewAngle, float focalLength){

		super(cameraPosition);

		viewVector = lookAt.sub(cameraPosition);
		viewVector = viewVector.normalize();

		userUpVector = userUpVector.normalize();

		sideVector = viewVector.cross(userUpVector);
		sideVector = sideVector.normalize();

		upVector = sideVector.cross(viewVector);
		upVector = upVector.normalize();


		this.renderWindow = renderWindow;
		this.ratio = (float) renderWindow.getWidth()/(float) renderWindow.getHeight();

		viewplaneHeight = 2 * (float) Math.tan(viewAngle/2);
		viewplaneWidth = viewplaneHeight * this.ratio;

		this.viewAngle = viewAngle;
		this.focalLength = focalLength;
		this.lookAt = lookAt;
		this.userUpVector = userUpVector;

		//matrixTransformation = new Matrix4();
		//matrixTransformation.translate(cameraPosition);
	}

	public Vec3 windowToViewplane(int xPixel, int yPixel){

		float xViewplane = (float) (2 * (xPixel + 0.5f)) / (renderWindow.getWidth() - 1) - 1;
		float yViewplane = (float) (2 * (yPixel + 0.5f)) / (renderWindow.getHeight() - 1) - 1;

		xViewplane = 0.5f * viewplaneWidth * xViewplane;
		yViewplane = 0.5f * viewplaneHeight * yViewplane;

		Vec3 sidePos 	= sideVector.multScalar(xViewplane);
		Vec3 upPos		= upVector.multScalar(yViewplane);

		Vec3 viewplanePixel = viewVector.add(sidePos).add(upPos);

		return viewplanePixel;
	}

	public Vec3 calculateDestinationPoint(){

		return new Vec3(0,0,0);
	}

	public Vec3 getPosition(){
		return position;
	}
}

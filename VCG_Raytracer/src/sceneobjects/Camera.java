package sceneobjects;

import raytracer.*;
import utils.*;
import ui.*;
import java.lang.Math;

public class Camera extends SceneObject {																				//Klasse Camera erweitert SceneObject

	Matrix4 matrixTransformation;

	private Vec3 cameraPosition;																						//Variablen
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

		viewVector = lookAt.sub(cameraPosition);
		viewVector = viewVector.normalize();
		userUpVector = userUpVector.normalize();
		sideVector = viewVector.cross(userUpVector);
		upVector = sideVector.cross(viewVector);

        this.renderWindow = renderWindow;
        this.ratio = (float) renderWindow.getWidth()/(float) renderWindow.getHeight();

		viewplaneHeight = 2 * (float) Math.tan(viewAngle/2);
		viewplaneWidth = viewplaneHeight * this.ratio;

		this.viewAngle = viewAngle;
		this.focalLength = focalLength;
		this.lookAt = lookAt;
		this.userUpVector = userUpVector;
		this.cameraPosition = cameraPosition;
		matrixTransformation = new Matrix4();
		matrixTransformation.translate(cameraPosition);
	}

    public Vec3 windowToViewplane(float windowX, float windowY){														//Tranformation Fenster zu Viewplane
        viewplaneX = ((2*windowX+1f)/(float) renderWindow.getWidth()) -1.0f;
        viewplaneY = ((2*windowY+1f)/(float) renderWindow.getHeight()) -1.0f;

        viewplaneX = viewplaneX*(0.5f*(viewplaneWidth));
        viewplaneY = viewplaneY*(0.5f*(viewplaneHeight));

        return new Vec3(viewplaneX, viewplaneY, focalLength);
    }

	public Vec3 calculateDestinationPoint(){

		return new Vec3(0,0,0);
	}

	public Vec3 getCameraPosition(){
		return cameraPosition;
	}



}

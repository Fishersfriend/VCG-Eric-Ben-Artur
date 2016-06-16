package sceneobjects;

import raytracer.*;
import utils.*;
import ui.*;

public abstract class SceneObject {

	//Variablen, die von allen Kindern von SceneObject gebraucht werden
	Vec3 position;
	Matrix4 matrixTransformation;
	Matrix4 inverse;
	Vec3 transfStart;
	Vec3 transfDirect;

	SceneObject ()
	{

	}

	SceneObject(Vec3 position){
		this.position = position;
	}
}

package sceneobjects;

import raytracer.*;
import utils.*;
import ui.*;

public abstract class SceneObject {

	Vec3 position;

	SceneObject(Vec3 position){
		this.position = position;
	}
}

package brogine;

import brogine.utils.Loader;

public abstract class Main {

	public static void main(String[] args) {
		DisplayManager.createDisplay();
		
		Scene scene = new Scene();
		
		while (!DisplayManager.isCloseRequested()) {
			scene.render();
		}
		
		scene.clean();
		Loader.clean();
		DisplayManager.close();
	}
	
}

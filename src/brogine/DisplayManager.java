package brogine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public abstract class DisplayManager {

	public static final String TITLE = "Brogine test";
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 800;
	public static final int FPS = 60;
	
	public static void createDisplay() {
		PixelFormat pixelFormat = new PixelFormat(8, 8, 8, 8);
        ContextAttribs contextAttributes = new ContextAttribs(3, 2)
            .withProfileCore(true)
            .withForwardCompatible(true);

        try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setVSyncEnabled(true);
			Display.setResizable(true);
			Display.setTitle(TITLE);
			Display.setFullscreen(true);
			Display.create(pixelFormat, contextAttributes);
		} catch (LWJGLException e) {
			close();
			System.err.println("Unable to create display");
			e.printStackTrace();
			System.exit(-1);
		}
		
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_BLEND);
		
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glClearColor(0.97f, 0.97f, 0.97f, 1);
	}
	
	public static boolean isCloseRequested() {
		return Display.isCloseRequested();
	}
	
	public static void update() {
		Display.sync(FPS);
		Display.update();
	}
	
	public static void close() {
		Display.destroy();
		System.exit(0);
	}
	
}

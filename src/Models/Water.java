package Models;

import com.jogamp.opengl.GL2;
import ulits.Colour;

/**
 * @author Jack Hosking
 * @studentID 16932920
 */

public class Water {

    private final static Colour BLUE_800 = new Colour(0.25882f, 0.64706f, 0.96078f, 0.8f);
    private final static Colour LIGHT_BLUE_800 = new Colour(0.16078f, 0.71373f, 0.96471f, 0.8f);
    private final static Colour BLUE_900 = new Colour(0.05098f, 0.27843f, 0.63137f, 0.0f);

    // defining heights as will be reused with the bouncing of fishes (fish wont collide with wave water)
    // if wave water height changes, then the water height will decrease to allow for the bigger waves
    // while leaving enough space for the buttons
    public final static float WAVE_WATER_HEIGHT = 0.75f;

    //amount of waves
    private final int WAVE_POINTS = 80;
    private float waterLevel;
    private float[] stillWave, targetWave, currentWave;

    public Water() {
        super();
        stillWave = new float[WAVE_POINTS];
        targetWave = new float[WAVE_POINTS];
        currentWave = new float[WAVE_POINTS];

        // create wave points using sin waves
        for (int i = 0; i < WAVE_POINTS; i++) {
            //current wave and still wave.
            currentWave[i] = stillWave[i] = (WAVE_WATER_HEIGHT) + (float) ((Math.sin((WAVE_POINTS) * (1.0f - (i * 0.02f)))) / 48) + 0.01f;

            //target height of the wave
            targetWave[i] = WAVE_WATER_HEIGHT;
        }
    }

    public void draw(GL2 gl) {

        gl.glBegin(GL2.GL_POLYGON);

        // top left
        Colour.setColourRGBA(BLUE_800, gl);
        gl.glVertex2f(-1.0f, WAVE_WATER_HEIGHT);


        //top right
        Colour.setColourRGBA(LIGHT_BLUE_800, gl);
        gl.glVertex2f(1.0f, WAVE_WATER_HEIGHT);

        //bottom right
        Colour.setColourRGBA(BLUE_900, gl);
        gl.glVertex2f(1.0f, -1.0f);

        //bottom left
        Colour.setColourRGBA(BLUE_900, gl);
        gl.glVertex2f(-1.0f, -1.0f);
        gl.glEnd();
    }

    public void animate(GL2 gl) {
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
        gl.glBegin(GL2.GL_POLYGON);

        //change
        Colour.setColourRGBA(LIGHT_BLUE_800, gl);
        gl.glVertex2d(-1.0f, WAVE_WATER_HEIGHT);
        gl.glVertex2d(1.0f, WAVE_WATER_HEIGHT);

        for (int i = 0; i < WAVE_POINTS; i++) {
            if (Float.compare(Math.abs(currentWave[i] - targetWave[i]), 0.0005f) <= 0) {
                float temp = stillWave[i];   //Switching the still line value, into the target wave.
                stillWave[i] = targetWave[i];
                targetWave[i] = temp;
            }

            // decrease or increase wave height (high value will increase the speed and decrease smooth)
            if (Float.compare(currentWave[i], targetWave[i]) >= 0) {
                waterLevel = -0.0005f;
            } else if (Float.compare(currentWave[i], targetWave[i]) <= 0) {
                waterLevel = 0.0005f;
            }
            currentWave[i] += waterLevel;

            // X value is affecting the growth of the wave, and the Y value is affecting the height of wave.
            gl.glVertex2d(1.0f - (i * 0.2f), currentWave[i]);
        }

        gl.glEnd();
    }
}

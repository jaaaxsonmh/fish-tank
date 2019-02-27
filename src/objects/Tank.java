package objects;

import Models.Sand.SandBase;
import com.jogamp.opengl.GL2;
import Models.Water;

public class Tank {

    public void draw(GL2 gl) {
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);


        Water water = new Water();
        water.draw(gl);

        SandBase base = new SandBase();
        base.draw(gl);

    }
}

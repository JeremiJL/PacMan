package jeremi.pacman;

import java.awt.*;

public class Constants {

    //Array represents possible pairs of width and height that can be set as window dimensions
    public final static int[][] POSSIBLE_DIMENSIONS = {{480,700},{560,800},{640,900},{720,1000}};

    //Currently set dimensions
    public static int FRAME_WIDTH = POSSIBLE_DIMENSIONS[0][0];
    public static int FRAME_HEIGHT = POSSIBLE_DIMENSIONS[0][1];

    private static String fontName = " ";

    public static String keySetChosen = "wasd";

    public static int[] arrowsCodes = {38,37,40,39};
    public static int[] wasdCodes = {87,65,83,68};

    public enum KEY_CODES {

        UP(wasdCodes[0]),LEFT(wasdCodes[1]),DOWN(wasdCodes[2]),RIGHT(wasdCodes[3]);

        public int codeValue;

        KEY_CODES(int val){
            this.codeValue = val;
        }


    }

    //Container that pairs scalar of size with components
    public enum COMPONENT_TYPE {

        SPACE(0.2,0.1),
        LABEL(0.2,0.2),
        BUTTON(0.25,0.1);

        public double widthScalar;
        public double heightScalar;

        COMPONENT_TYPE(double widthScalar, double heightScalar){
            this.widthScalar = widthScalar;
            this.heightScalar = heightScalar;
        }

    }

    //Container that pairs scalar of size with font types
    public enum FONT_TYPE {

        LARGE_FONT(0.11,true),
        MEDIUM_FONT(0.042,false),
        SMALL_FONT(0.035,false),
        TINY_FONT(0.025,false);

        public double sizeScalar;
        public boolean bold;

        FONT_TYPE(double sizeScalar, boolean bold) {
            this.sizeScalar = sizeScalar;
            this.bold = bold;
        }
    }

    //Refresh frame actual width, height
    public static void refresh(int mode){
        FRAME_WIDTH = POSSIBLE_DIMENSIONS[mode][0];
        FRAME_HEIGHT = POSSIBLE_DIMENSIONS[mode][1];

    }

    //returns the dimensions of components - scaled window dimensions by components parameters
    public static Dimension getDimension(COMPONENT_TYPE component){
        double widthScalar = component.widthScalar;
        double heightScalar = component.heightScalar;
        return new Dimension((int)(FRAME_WIDTH*widthScalar),(int)(FRAME_HEIGHT*heightScalar));
    }

    //returns the font with scaled size
    public static Font getFont(FONT_TYPE type){
        int size = (int)(FRAME_WIDTH*type.sizeScalar);

        if (type.bold)
            return new Font(fontName, Font.BOLD,size);
        else
            return new Font(fontName, Font.PLAIN,size);
    }


}

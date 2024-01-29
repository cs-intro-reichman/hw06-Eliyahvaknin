import java.awt.Color;


public class Runigram {

    public static void main(String[] args) {
       
        //// Hide / change / add to the testing code below, as needed.
       
        // Tests the reading and printing of an image:  
        Color[][] tinypic = read("tinypic.ppm");
        Color [][] gray= grayScaled(tinypic);
        print(blend(tinypic,gray,0.13));
        print(tinypic);
       
    }

    /** Reads the image data from the given file,
     *  and returns a 2D  array containing the image data.
     * . */
    public static Color[][] read(String fileName) {
        In in = new In(fileName);
        // Reads the file header, ignoring the first and the third lines.
        in.readString();
        int numCols = in.readInt();// Reads the number of columns in the file
        int numRows = in.readInt();
        in.readInt();
        Color[][] image = new Color[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                int r = in.readInt();
                int g = in.readInt();
                int b = in.readInt();
                image[i][j] = new Color(r, g, b);
            }
        }
        return image;
    }
    /**
     *  Prints the pixels in the given image
     * @param c
     */ 
    private static void print(Color c) {
        System.out.print("(");
        System.out.printf("%3s,", c.getRed());     
        System.out.printf("%3s,", c.getGreen()); 
        System.out.printf("%3s",  c.getBlue());  
        System.out.print(")  ");
    }


    private static void print(Color[][] image) {
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[i].length; j++) {
                System.out.printf("%3s,","("+image[i][j].getRed());   // Prints the red component
                System.out.printf("%3s,", +image[i][j].getGreen()); // The green
                System.out.printf("%3s", +image[i][j].getBlue()+")");  // 
            }
            System.out.println();
        }
    }
   
    /**
     * Returns an image which is the horizontally flipped version of the given image.
     * 
     * @param image the image to flip the horizontally
     * 
     * @return an image which is horizontally 
     */
    public static Color[][] flippedHorizontally(Color[][] image) {  
        Color[][] flippedHor = new Color[image.length][image[0].length];
        for (int i = 0; i < image.length; i++) {
             for (int j = image[i].length - 1; j >= 0; j--) {
                flippedHor[i][image[i].length - 1 - j] = image[i][j];
        }
    }
     print(flippedHor);
     return flippedHor;
}
    /**
     * Returns an image which is the vertically flipped version of the give image.
     * 
     * @param image
     * @return
     * @throws
     *
     */
    public static Color[][] flippedVertically(Color[][] image){
        Color[][] flippedVer = new Color[image.length][image[0].length];

        for (int i = image.length-1; i >= 0; i--) {
            for (int j = 0; j < image[i].length; j++) {
                flippedVer[image.length - 1 - i][j] = image[i][j];
            }
        }
            print(flippedVer);
        return flippedVer;
    }
   
    /**
     * Computes the luminance of the RGB values
     */
    public static Color luminance(Color pixel) {
        int r = pixel.getRed();
        int g = pixel.getGreen();
        int b = pixel.getBlue();
        int lum = (int) (0.299 * r + 0.587 * g + 0.114 * b);
        return new Color(lum, lum, lum);
        }
   
    /**
     * Returns an image which is the grayscaled version of the given.
     */
    public static Color[][] grayScaled(Color[][] image) {
        Color[][] grayColors= new Color[image.length][image[0].length];
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[i].length; j++) {
                grayColors[i][j]=luminance(image[i][j]);
            }
        }
        return grayColors;
    }  
   
    /**
     * Returns an image which is the scaled version of the given image  with the given lumin.
     */
    public static Color[][] scaled(Color[][] image, int width, int height) {
        Color[][] scaled = new Color[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int ratioX = (i * image.length) / height;
                int ratioY = (j * image[0].length) / width;
                scaled[i][j] = image[ratioX][ratioY];
            }
        }
        return scaled;
    }
   
    /**
     * Computes and returns a blended version of the given
     */
    public static Color blend(Color c1, Color c2, double alpha) {
        int r = (int) (alpha * c1.getRed() + (1 - alpha) * c2.getRed());
        int g = (int) (alpha * c1.getGreen() + (1 - alpha) * c2.getGreen());
        int b = (int) (alpha * c1.getBlue() + (1 - alpha) * c2.getBlue());
        return new Color(r, g, b);
    }
   
    /**
     *  Calculates the color between the two given 
     * @param image1
     * @param image2
     * @param alpha
     * @return 
   
     */
    public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
        Color[][] marge=new Color[image1.length][image1[0].length];
        for (int i = 0; i < image1.length; i++) {
            for (int j = 0; j < image1[1].length; j++) {
                marge[i][j]=blend(image1[i][j], image2[i][j], alpha);
            }
        }
        return marge;
    }

    /**
     * Displays a morphing sequence of the two given images..
     */
    public static void morph(Color[][] source, Color[][] target, int n) {
        target=scaled(target, source.length, source[0].length);
        Color[][] morph =new Color[source.length][source[0].length];
        for (int i = 0; i < n; i++) {
            morph=blend(source, target, (double) i / (n - 1));
        Runigram.display(morph);
        StdDraw.pause(500);
        }
    }
   
    /** Creates a canvas for the given image.
     * * @param image - the image to create a canvas for.
     */
    public static void setCanvas(Color[][] image) {
        StdDraw.setTitle("Runigram 2023");
        int height = image.length;
        int width = image[0].length;
        StdDraw.setCanvasSize(height, width);
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);
        StdDraw.enableDoubleBuffering();
    }

    /** Displays the given image on the current canvas.
     * * @param image - the image to display
     */
    public static void display(Color[][] image) {
        int height = image.length;
        int width = image[0].length;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                StdDraw.setPenColor( image[i][j].getRed(),
                                     image[i][j].getGreen(),
                                     image[i][j].getBlue() );
                StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
            }
        }
        StdDraw.show();
    }
}


	
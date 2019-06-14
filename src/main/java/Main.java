import nu.pattern.OpenCV;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;


public class Main {


    public static void main(String[] args) {
        OpenCV.loadLocally();

        new Constant().run();
        new Constant().run("Welcome OpenCV" + Core.VERSION);


        new MyAPI().testFillFlood(new Constant().pathResources + new Constant().imgPath,
                0,0, 150,150,150, 50);
    }
}

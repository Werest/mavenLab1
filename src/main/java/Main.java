import nu.pattern.OpenCV;
import org.opencv.core.Core;

public class Main {


    public static void main(String[] args) {

        OpenCV.loadLocally();

        Constant cnt = new Constant();

        cnt.run();
        cnt.run(("Welcome to OpenCV " + Core.VERSION));

        new MyAPI().testPiramid(new Constant().pathResources + new Constant().girlPretty);

    }
}

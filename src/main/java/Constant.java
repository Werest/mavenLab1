import org.apache.log4j.Logger;
import java.util.Locale;


class Constant {
    String pathResources    = "src/main/resources/img/";
    String pathFLab4        = "Lab4/";
    String pathFLab4_1      = "Lab4_1/";
    String testPiramid      = "testPiramid/";

    String imgPath          = "Nature-259.jpg";
    String girlPretty       = "depositphotos_112957276-stock-photo-company-of-young-beautiful-girls.jpg";

    String nomer1           = "nomer1.png";

    private final static Logger LOGGER = Logger.getLogger(Constant.class);
    void run(){
        LOGGER.info("Checking OS");
        LOGGER.info("Your OS is " + getOperatingSystemType());
    }

    void run(String prm){
        LOGGER.info(prm);
    }

    void run(double prm){
        LOGGER.info(prm);
    }


    //enum OS
    public enum OSType {
        Windows, MacOS, Linux, Other
    }

    // cached result of OS detection
    private static OSType detectedOS;

    //getOS
    private static OSType getOperatingSystemType() {
        if (detectedOS == null) {
            String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
            if ((OS.contains("mac")) || (OS.contains("darwin"))) {
                detectedOS = OSType.MacOS;
            } else if (OS.contains("win")) {
                detectedOS = OSType.Windows;
            } else if (OS.contains("nux")) {
                detectedOS = OSType.Linux;
            } else {
                detectedOS = OSType.Other;
            }
        }
        return detectedOS;
    }
}

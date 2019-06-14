import nu.pattern.OpenCV;
import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.photo.Photo;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.*;

import static org.opencv.imgproc.Imgproc.*;
import static org.opencv.imgproc.Imgproc.MORPH_BLACKHAT;


class MyAPI {


    void displayImage(Mat m, String msg) {
        new Constant().run(msg);
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if ( m.channels() > 1 ) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = m.channels()*m.cols()*m.rows();
        byte [] b = new byte[bufferSize];
        m.get(0,0,b); // get all the pixels
        BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);
        //BufferedImage img=ImageIO.read(new File("/HelloOpenCV/lena.png"));
        ImageIcon icon=new ImageIcon(image);
        JFrame frame=new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(image.getWidth(null)+50, image.getHeight(null)+50);
        JLabel lbl=new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    Mat ImageToMat(String path, int numChn){
        Mat srcImage = Imgcodecs.imread(path);
        for(int i = 0; i< srcImage.cols();i++){
            for(int j = 0; j< srcImage.rows();j++){
                double[] c = srcImage.get(j, i);
                c[numChn] = 0;
                srcImage.put(j, i, c[0], c[1], c[2]);
            }
        }

        return srcImage;
    }


    void morfologyTest(String ditPath, String imgPath) {
        try {
            int[] massiv= {3,5,7,9,13,15};
            int[] massiv_morph = {MORPH_RECT, MORPH_ELLIPSE, MORPH_GRADIENT, MORPH_BLACKHAT};
            String path_save;
            String prfName = "erode";
            Mat src = Imgcodecs.imread(ditPath + imgPath, Imgcodecs.IMREAD_COLOR);

            for (int i1 : massiv) {
                for (int j = 0; j < massiv_morph.length; j++) {
                    path_save = ditPath + prfName + "/" + i1 + i1 + massiv_morph[j] + "__" + massiv_morph[j] + imgPath;
                    if (j <= 1) {
                        motgologyTest_2(i1, path_save, src, massiv_morph[j]);
                    } else {
                        morfologyTest_1(i1, path_save, src, massiv_morph[j]);
                    }
                }
            }

        }catch (Exception ex) {
            new Constant().run(Arrays.toString(ex.getStackTrace()));
        }
    }

    private void morfologyTest_1(int size, String path_save, Mat src, int op){

        Mat kernel = new Mat(new Size(size,size), CvType.CV_8UC1);

        Mat dst = src.clone();
        Imgproc.morphologyEx(src, dst, op, kernel, new Point(-1, -1), 5);
        Imgcodecs.imwrite(path_save, dst);
        new Constant().run("Save file: " + path_save);
    }

    private void motgologyTest_2(int size, String path_save, Mat src, int op){
        Mat element = Imgproc.getStructuringElement(op, new Size(size, size));
        Mat dst = src.clone();

        Imgproc.erode(src, dst, element);
        Imgcodecs.imwrite( path_save, dst);
        new Constant().run( "Save file: " + path_save);
    }


    //Lab4
    // В случае если параметры цвета — NULL, выбирать все цветовые параметры с помощью генератора случайных чисел.
    // Выполнить метод для различных начальных координат начиная с самой левой верхней точки.
    void testFillFlood(String pathImg, int x, int y, int red, int green, int blue, int zone){
        try{
            Mat srcImage = Imgcodecs.imread(pathImg);

            if(red > 255 || red < 0 || green > 255 || green < 0 || blue > 255 || blue < 0) {
                new Constant().run("Неверно задан цвет");
            }else if(x>srcImage.width()){
                new Constant().run("Неверно задан параметр начала координат X");
            }else if(y>srcImage.height()) {
                new Constant().run("Неверно задан параметр начала координат Y");
            }else {
                new Constant().run("Всё заполнено правильно - " + "testFillFlood");
                new Constant().run("RED - " + red);
                new Constant().run("GREEN - " + green);
                new Constant().run("BLUE - " + blue);

                testFillFlood_1(srcImage, x, y, red, green, blue, zone);
            }
        }catch (Exception ex){
            new Constant().run(Arrays.toString(ex.getStackTrace()));
        }


    }

    //seedPoint - координаты точки начала анализа параметров цвета заданного изображения;
    // newVal - цвет (rgb-параметры) области «заливки»;
    //loDiff upDiff - диапазон параметров цвета (верхняя и нижняя граница),
    // который следует заменить заданным цветом области «заливки»;
    private void testFillFlood_1(Mat srcImage, int x, int y, int red, int green, int blue, int zone){

        try{
            displayImage(srcImage, "FIRST");
            Imgcodecs.imwrite(new Constant().pathResources+ new Constant().pathFLab4_1 + "1.jpg", srcImage);

            Point seedPoint = new Point(x, y);
            Scalar newVal = new Scalar(red,green,blue);
            Scalar loDiff = new Scalar(zone, zone, zone);
            Scalar upDiff = new Scalar(zone, zone, zone);
            Rect rect = new Rect();
            Mat mask = new Mat();

            Imgproc.floodFill(srcImage, mask, seedPoint, newVal, rect, loDiff, upDiff,
                    Imgproc.FLOODFILL_FIXED_RANGE);
            displayImage(srcImage, "CHANGE");
            Imgcodecs.imwrite(new Constant().pathResources+ new Constant().pathFLab4_1 + "2.jpg", srcImage);
        }catch (Exception ex){
            new Constant().run("testFillFlood_1 " + Arrays.toString(ex.getStackTrace()));
        }

    }

    //Lab4
    void testPiramid(String pathImg){
        Mat srcImage = Imgcodecs.imread(pathImg);
        Mat srcImage1 = Imgcodecs.imread(pathImg);

        try {

            String dirpath = new Constant().pathResources + new Constant().testPiramid;
            /*
            Mat mask = new Mat();
            displayImage(srcImage, "SRC");


            Imgproc.pyrDown(srcImage, mask);
            displayImage(mask, "pyrDown native");
            Imgcodecs.imwrite(dirpath + "Down.jpg", mask);

            Imgproc.pyrUp(mask, mask);
            displayImage(mask, "pyrUp");
            Imgcodecs.imwrite(dirpath + "Up.jpg", mask);

            Core.subtract(srcImage, mask, mask);
            displayImage(mask, "Core");
            Imgcodecs.imwrite(dirpath + "Core.jpg", mask);
            */

            while (true){
                HighGui.imshow( "CHANGE", srcImage);
                char c = (char) HighGui.waitKey(0);
                c = Character.toLowerCase(c);
                if( c == 27 ){
                    break;
                }else if( c == 'i'){
                    Imgproc.pyrUp(srcImage, srcImage);//, new Size( srcImage.cols()*2, srcImage.rows()*2 ) );
                    new Constant().run( "** Zoom In: Image x 2" );
                    Imgcodecs.imwrite(dirpath + "Up.jpg", srcImage);
                }else if( c == 'o'){
                    Imgproc.pyrDown(srcImage, srcImage);//, new Size( srcImage.cols()/2.0, srcImage.rows()/2.0 ) );
                    new Constant().run( "** Zoom Out: Image / 2" );
                    Imgcodecs.imwrite(dirpath + "Down.jpg", srcImage);
                }
            }


            //Calculates the per-element difference between two arrays or array and a scalar.
            //Вычисляет разность элементов между двумя массивами или массивом и скаляром.

            //Down Up Esc
            //o - i - Esc

            HighGui.destroyAllWindows();

            Core.subtract(srcImage1, srcImage, srcImage);
            //HighGui.imshow("Core", mask);
            //displayImage(srcImage, "Core");
            //HighGui.imshow("Core", srcImage);

            Imgcodecs.imwrite(new Constant().pathResources + new Constant().testPiramid + "Core.jpg", srcImage);


            new Constant().run("testPiramid - end");

            HighGui.destroyAllWindows();
            System.exit(0);

        }catch (Exception ex){
            new Constant().run("testPiramid " + Arrays.toString(ex.getStackTrace()));
        }
    }

    //Lab4
    void testSegments(int height_search, int width_search) {
        try {
            String pathImg = new Constant().pathResources + new Constant().nomer1;
            Mat mImg = Imgcodecs.imread(pathImg);
            displayImage(mImg, "SRC");

            String dirPath = new Constant().pathResources + new Constant().pathFLab4;

            // 1
            Mat grayImage = new Mat();
            Imgproc.cvtColor(mImg, grayImage, Imgproc.COLOR_BGR2GRAY);
            Imgcodecs.imwrite(dirPath + "grayImage.jpg", grayImage);
            // 2
            Mat denoisingImage = new Mat();
            Photo.fastNlMeansDenoising(grayImage, denoisingImage);
            Imgcodecs.imwrite(dirPath + "noNoise.jpg", denoisingImage);
            // 3
            Mat histogramEqualizationImage = new Mat();
            Imgproc.equalizeHist(denoisingImage, histogramEqualizationImage);
            Imgcodecs.imwrite(dirPath + "histogramEq.jpg", histogramEqualizationImage);
            // 4
            Mat morphologicalOpeningImage = new Mat();
            Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));
            Imgproc.morphologyEx(histogramEqualizationImage, morphologicalOpeningImage,
                    Imgproc.MORPH_RECT, kernel);
            Imgcodecs.imwrite(dirPath + "morphologicalOpening.jpg", morphologicalOpeningImage);
            // 5
            Mat subtractImage = new Mat();
            Core.subtract(histogramEqualizationImage, morphologicalOpeningImage, subtractImage);
            Imgcodecs.imwrite(dirPath + "subtract.jpg", subtractImage);
            // 6
            Mat thresholdImage = new Mat();
            double threshold = Imgproc.threshold(subtractImage, thresholdImage, 127, 255,
                    Imgproc.THRESH_OTSU);
            Imgcodecs.imwrite(dirPath + "threshold.jpg", thresholdImage);
            thresholdImage.convertTo(thresholdImage, CvType.CV_16SC1);
            // 7
            Mat edgeImage = new Mat();
            thresholdImage.convertTo(thresholdImage, CvType.CV_8U);
            //displayImage(thresholdImage, "tr2");
            Imgproc.Canny(thresholdImage, edgeImage, threshold, threshold * 3, 3, true);
            Imgcodecs.imwrite(dirPath + "edge.jpg", edgeImage);
            // 8
            Mat dilatedImage = new Mat();
            Imgproc.dilate(thresholdImage, dilatedImage, kernel);
            Imgcodecs.imwrite(dirPath + "dilation.jpg", dilatedImage);
            // 9

            ArrayList<MatOfPoint> contours = new ArrayList<>();

            //displayImage(denoisingImage, "gray");
            Imgproc.findContours(dilatedImage, contours, new Mat(), RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
            contours.sort(Collections.reverseOrder(Comparator.comparing(Imgproc::contourArea)));

            /*
            Mat gImg = new Mat(mImg.size(), mImg.type());

            for (int i=0; i<contours.size(); i++){
                Imgproc.drawContours(gImg, contours, -1, new Scalar(255,255,0), 1);
            }
            */


            int rect1 = 0, rect2 = 0;
            for (MatOfPoint contour : contours.subList(0, contours.size())) {
                new Constant().run(Imgproc.contourArea(contour));
                MatOfPoint2f point2f = new MatOfPoint2f();
                MatOfPoint2f approxContour2f = new MatOfPoint2f();
                MatOfPoint approxContour = new MatOfPoint();
                contour.convertTo(point2f, CvType.CV_32FC2);
                double arcLength = Imgproc.arcLength(point2f, true);
                Imgproc.approxPolyDP(point2f, approxContour2f, 0.01 * arcLength, true);
                approxContour2f.convertTo(approxContour, CvType.CV_32S);
                Rect rect = Imgproc.boundingRect(approxContour);
                //double ratio = (double) rect.height / rect.width;


                if (approxContour.total() == 4) {

                    if(rect.width >= width_search && rect.height >= height_search){
                        rect2++;
                    }

                    rect1++;
                }

                /*
                Mat submat = mImg.submat(rect);
                Imgproc.resize(submat, submat, new Size(100, 100 * ratio));
                Imgcodecs.imwrite(dirPath + "result" + contour.hashCode() + ".jpg", submat);
                */

            }//for

            new Constant().run("Обнаружено = " + rect1 + " прямоугольников");
            new Constant().run("Обнаружено с размерами (" + width_search +"х" +height_search + ") и больше = "
                    + rect2 + " прямоугольников");
        }catch (Exception ex){
            new Constant().run(Arrays.toString(ex.getStackTrace()));
        }





    }










}

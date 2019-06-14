import nu.pattern.OpenCV;
import org.junit.jupiter.api.Test;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

class MyAPITest {

    @Test
    void test1() {
        //Лабораторная работа 1 - готово
        OpenCV.loadLocally();

        Constant cnt = new Constant();

        cnt.run();
        cnt.run(("Welcome to OpenCV " + Core.VERSION));



    }

    //Создать общий метод для загрузки файла в матрицу по указанному полному имени файла.
    // Получить любое цветное изображение, например с помощью мобильного устройства.
    // Написать метод, который в качестве параметров принимает номер канала и контент изображения
    // в виде Mat объекта и затем устанавливает все его значения в 0.
    // Вывести на экран исходную картинку и результаты при изменении канала адаптировав приведённый метод showImage()
    // так, что бы картинка оставалась на экране в процессе выполнения тестов.
    // Сохранить преобразованную картинку по заданному пути.

    @Test
    void test2(){
        test1();
        //Лабораторная работа 2 - готово
        String img_path = new Constant().pathResources + "/Nature-259.jpg";
        Mat img1 = Imgcodecs.imread(img_path);
        Mat img2 = Imgcodecs.imread(img_path, CvType.CV_8UC1);

        new MyAPI().displayImage(img1, "img1");
        new MyAPI().displayImage(img2, "img2");

        for(int i=0; i<3; i++){
            Mat img3 = new MyAPI().ImageToMat(img_path, i);
            new MyAPI().displayImage(img3, "img3");
        }

    }


    //1. Получить несколько цветных изображений в разном качестве, по волнующей вас тематике,
    // например с помощью мобильного устройства. Написать метод который выполняет фильтрацию четырьмя способами,
    // в качестве параметров, метод принимает путь к изображению и размер ядра свертки. В тестовом методе необходимо
    // реализовать вывод промежуточных результатов на экран и сохранение в файловой системе. Выполнить метод
    // для различных размеров ядра 3,5,7... Дать качественную оценку результатам.
    //2. Для тестирования морфологических операций, рекомендую получить любое фото с рекламой,
    // так как желательно, что бы на картинке присутствовал текст и графика.
    // Адаптировать метод morfologyTest() в своем приложении, в тестовом пакете.
    // Сделать вывод итогового контента для ядер размером 3х3 5х5 7х7 9х9 13х13 15х15 для каждого из
    // трех указателя формы : MORPH_GRADIENT; MORPH_BLACKHAT; MORPH_RECT; MORPH_ELLIPSE.
    // Включив указатель формы в имя итогового графического контекста, например : gr_ или bl_ или rec_ или ell_.
    @Test
    void test3(){
        //Лабораторная работа 3 - готово
        test1();
        new MyAPI().morfologyTest(new Constant().pathResources, new Constant().imgPath);
    }


    private String pathImg = new Constant().pathResources + new Constant().imgPath;
    @Test
    void test4_1(){
        //Лабораторная работа 4
        test1();

        new MyAPI().testFillFlood(pathImg, 0, 0, 100, 100, 0, 50);

        new Constant().run("Okay");
    }

    @Test
    void test4_2(){
        test1();
        new MyAPI().testPiramid(new Constant().pathResources + new Constant().girlPretty);
        new Constant().run("Okay");
    }

    @Test
    void test4_3(){
        test1();
        new MyAPI().testSegments(30, 40);
        new Constant().run("Okay");
    }
}
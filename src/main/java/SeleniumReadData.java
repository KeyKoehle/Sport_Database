import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SeleniumReadData {

    public static WebElement element = null;
    public static WebElement scroll = null;
    public static String position;
    public static Actions action;
    int homegoals, awaygoals, homegoalsht, awaygoalsht, gamedaysql;
    public String home, away, gameday, jahr, saison, land, liga;
    public java.sql.Date sqldate;
    public static List<WebElement> spieltag;
    public static List<WebElement> datum;
    public static List<WebElement> heimmannschaft;
    public static List<WebElement> endstand;
    public static List<WebElement> auswaertsmannschaft;
    public static List<WebElement> halbzeitstand;
    public static List<WebElement> neu;


    public void readWebsite(String land, String liga, String saison) throws InterruptedException, ParseException, SQLException, IOException {
        System.out.println("Start ReadWebsite");
        this.saison = saison;
        this.land = land;
        this.liga = liga;
        // EInstellungen für den Browser setzen
        System.setProperty("webdriver.chrome.driver", ".\\Driver\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        //Zu Website navigieren
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.navigate().to("https://www.scoreboard.com/de/fussball/" + land + "/" + liga + "-" + saison + "/ergebnisse/");
        Thread.sleep(1000);
        //"Mehr Spiele Anzeigen" Button betätigen, wenn vorhanden
        if (driver.findElements(By.id("onetrust-accept-btn-handler")).size() != 0) {
            driver.findElement(By.id("onetrust-accept-btn-handler")).click();
        }
        int anzahlElemente = 0;
        while (driver.findElements(By.xpath("/html/body/div[4]/div[1]/div/div[1]/div[2]/div[4]/div[2]/div[1]/div/div/a")).size() != 0) {
            element = driver.findElement(By.xpath("/html/body/div[4]/div[1]/div/div[1]/div[2]/div[4]/div[2]/div[1]/div/div/a"));
            scroll = driver.findElement(By.xpath("/html/body/div[5]/div/div[1]/div[1]/div[1]"));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,250)");
            action = new Actions(driver);
            action.moveToElement(element).click().perform();
            WebDriverWait wait = new WebDriverWait(driver, 60);
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.className("event__participant--home"), anzahlElemente));
            anzahlElemente = driver.findElements(By.className("event__participant--home")).size();
        }
        listdata(driver,land);
    }

    public void listdata(WebDriver driver,String land) throws ParseException, SQLException, InterruptedException, IOException {
        System.out.println("Start Listdata");
        neu = driver.findElements(By.className("event__match--oneLine"));
        spieltag = driver.findElements(By.className("event__round"));
        FileWriter writer = new FileWriter("reader.txt");
        String za = "";
        String htString = "";
        String esString = "";
        String bsString = "";
        gameday = spieltag.get(0).getText();
        int x= 0;
        System.out.println(neu.size());
        for(int i = 0; i < neu.size(); i++) {
            if (x + 1 < spieltag.size()) {
                if (neu.get(i).getLocation().getY() > spieltag.get(x).getLocation().getY() && neu.get(i).getLocation().getY() < spieltag.get(x + 1).getLocation().getY()) {
                } else {
                    x++;
                    gameday = spieltag.get(x).getText();
                }
            }
            za = neu.get(i).getText();
            if(gameday.contains("Spieltag")){
                if(!za.contains("(")){
                    htString = "#NOHT";
                }else{
                    htString = "";
                }
                if(za.contains("Beschluss")){
                    bsString = "#BS";
                }else{
                    bsString = "";
                }
                if(za.contains("n.E.")){
                    esString = "#ES";
                }else{
                    esString = "";
                }
                writer.write(gameday+"\n");
                writer.write("$"+htString+esString+bsString+"\n");
                writer.write(za+"\n");
                System.out.println(i);
            }
        }
        readtxt();
    }
    public void readtxt() throws IOException, ParseException {
        FileReader file = new FileReader("reader.txt");
        BufferedReader reader = new BufferedReader(file);
        boolean change = false;
        String jahr;
        String line = "start";
        if (saison.length() == 4) {
            jahr = saison;
        } else {
            jahr = saison.substring(5, 9);
        }
        boolean ht = true;
        boolean es = false;
        boolean bs = false;

        while (line != null) {
            line = reader.readLine();
            System.out.println(line.length()+" " + line);
            if (line.length() == 12) {
                gamedaysql = Integer.parseInt(line.substring(0, 2));
            } else {
                gamedaysql = Integer.parseInt(line.substring(0, 1));
            }
            line = reader.readLine();
            if (line.contains("#ES") || line.contains("#NOHT") || line.contains("#BS")) {
                if (line.contains("#ES")) {
                    es = true;
                } else {
                    es = false;
                }
                if (line.contains("#NOHT")) {
                    ht = false;
                } else {
                    ht = true;
                }
                if (line.contains("#BS")) {
                    bs = true;
                } else {
                    bs = false;
                }
            } else {
                ht = true;
                es = false;
                bs = false;
            }
            line = reader.readLine();
            if (saison.length() > 5) {
                if (line.length() == 12 && change == false) {
                    jahr = saison.substring(0, 4);
                    change = true;
                }
                java.util.Date date2 = new SimpleDateFormat("dd.MM.yyyy").parse(line.substring(0, 6) + jahr);
                sqldate = new java.sql.Date(date2.getTime());

                if (bs == true || es == true) {
                    line = reader.readLine();
                }

                line = reader.readLine();

                home = line.replace("'", "\\'");

                line = reader.readLine();

                homegoals = Integer.parseInt(line);

                line = reader.readLine();
                line = reader.readLine();

                awaygoals = Integer.parseInt(line);

                if (es) {
                    line = reader.readLine();
                    line = reader.readLine();
                    line = reader.readLine();
                    line = reader.readLine();
                    line = reader.readLine();
                }

                line = reader.readLine();
                ;

                away = line.replace("'", "\\'");
                line = reader.readLine();

                if (ht == false || bs == true) {
                } else {
                    homegoalsht = Integer.parseInt(line.substring(1, 2));
                    awaygoalsht = Integer.parseInt(line.substring(5, 6));
                }
            }
            System.out.println("Land: "+land+" || Liga: " +liga+" || Saison: "+saison+" || Heim: " +home+ " || Auswaerts: "+away+" ||  "+homegoals+" - "+awaygoals+" " +"("+homegoalsht+" - "+awaygoalsht+")  "+ sqldate  );
        }
    }
//        spieltag = driver.findElements(By.className("event__round"));
//        datum = driver.findElements(By.className("event__time"));
//        heimmannschaft = driver.findElements(By.className("event__participant--home"));
//        endstand = driver.findElements(By.className("event__scores"));
//        auswaertsmannschaft = driver.findElements(By.className("event__participant--away"));
//        //halbzeitstand = driver.findElements(By.className("event__part"));
//        System.out.println("Spieltag: "+spieltag.size());
//        System.out.println("Datum: "+datum.size());
//        System.out.println("Heimmannschaft: "+heimmannschaft.size());
//        System.out.println("Endstand: "+endstand.size());
//        System.out.println("Auswaertsmannschaft: "+auswaertsmannschaft.size());
//        //System.out.println("halbzeitstand: "+halbzeitstand.size());
//        checkdata(spieltag, datum, heimmannschaft, endstand, auswaertsmannschaft, halbzeitstand,land);

    public void checkdata(List<WebElement> spieltag, List<WebElement> datum, List<WebElement> heimmannschaft,
                          List<WebElement> endstand, List<WebElement> auswaertsmannschaft, List<WebElement> halbzeitstand,String land) throws ParseException, SQLException, FileNotFoundException {
        System.out.println("Start Checkdata");
        jahr = saison.substring(5, 9);
        boolean change = false;
        int x = 0;

        gameday = spieltag.get(0).getText();
        int korrekttur = 0;
        for (int i = 0; i < datum.size(); i++) {
            if (x + 1 < spieltag.size()) {
                if (datum.get(i).getLocation().getY() > spieltag.get(x).getLocation().getY() && datum.get(i).getLocation().getY() < spieltag.get(x + 1).getLocation().getY()) {
                } else {
                    x++;
                    gameday = spieltag.get(x).getText();
                }
            }
            int y = Integer.parseInt(datum.get(i).getText().substring(3, 5));
            if (y == 12 && change == false) {
                jahr = saison.substring(0, 4);
                change = true;
            }
            java.util.Date date2 = new SimpleDateFormat("dd.MM.yyyy").parse(datum.get(i).getText().substring(0, 6) + jahr);
            if(datum.get(i).getText().contains("Beschluss")) {
                korrekttur++;
            }


            sqldate = new java.sql.Date(date2.getTime());
            if (endstand.get(i).getText().replaceAll("\n", "").substring(3, 4).contains("-")) {
                homegoals = Integer.parseInt(endstand.get(i).getText().replaceAll("\n", "").substring(0, 2));
                awaygoals = Integer.parseInt(endstand.get(i).getText().replaceAll("\n", "").substring(5, 6));
            } else {
                if (endstand.get(i).getText().replaceAll("\n", "").length() == 6) {
                    homegoals = Integer.parseInt(endstand.get(i).getText().replaceAll("\n", "").substring(0, 1));
                    awaygoals = Integer.parseInt(endstand.get(i).getText().replaceAll("\n", "").substring(4, 6));
                } else {
                    homegoals = Integer.parseInt(endstand.get(i).getText().replaceAll("\n", "").substring(0, 1));
                    awaygoals = Integer.parseInt(endstand.get(i).getText().replaceAll("\n", "").substring(4, 5));
                }
            }
//            boolean keinhalbzeitstand = false;
//            if(halbzeitstand.size() > 2) {
//                homegoalsht = Integer.parseInt(halbzeitstand.get(i - korrekttur).getText().substring(1, 2));
//                awaygoalsht = Integer.parseInt(halbzeitstand.get(i - korrekttur).getText().substring(5, 6));
//            }
//            else {
//                keinhalbzeitstand = true;
//            }
            home = heimmannschaft.get(i).getText().replace("'", "\\'");;
            away = auswaertsmannschaft.get(i).getText().replace("'", "\\'");;
            gamedaysql = 1000;
            if (gameday.length() == 11 || gameday.length() == 12) {
                if (gameday.length() == 11) {
                    gamedaysql = Integer.parseInt(gameday.substring(0, 1));
                }
                if (gameday.length() == 12) {
                    gamedaysql = Integer.parseInt(gameday.substring(0, 2));
                }
            } else {
                gamedaysql = 1000;
            }
            if (gamedaysql < 500 ) { //keinhalbzeitstand == false
                MySql data = new MySql();
                data.einlesen(sqldate, home, away, homegoals, awaygoals, gamedaysql, saison,  land, liga);
            } else {

            }
        }
    }
}



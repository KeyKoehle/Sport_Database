import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
            boolean ht = false;
            gameday = spieltag.get(0).getText();
            int x= 0;

            for(int i = 0; i < neu.size(); i++) {
                if (x + 1 < spieltag.size()) {
                    if (neu.get(i).getLocation().getY() > spieltag.get(x).getLocation().getY() && neu.get(i).getLocation().getY() < spieltag.get(x + 1).getLocation().getY()) {
                    } else {
                        x++;
                        gameday = spieltag.get(x).getText();
                    }
                }
                ht = true;
                za = neu.get(i).getText();
                if(!za.contains("(") || za.contains("Beschluss")){
                    ht = false;
                }
                if(ht){
                    writer.write(gameday+"#HT# \n");
                    writer.write(za+"\n");
                }
                else {
                    writer.write(gameday+"no Halftime \n");
                    writer.write(za+"\n");
                }
            }
            writer.close();


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
        driver.close();
        driver.quit();
    }

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



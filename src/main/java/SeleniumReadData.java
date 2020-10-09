import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SeleniumReadData {

    public static WebElement element = null;
    public static WebElement scroll = null;
    public static Actions action;
    int homegoals, awaygoals, homegoalsht, awaygoalsht, gamedaysql;
    public String home, away, gameday, jahr, saison, land, liga;
    public java.sql.Date sqldate;
    public static List<WebElement> spieltag;
    public static List<WebElement> neu;


    public void readWebsite(String land, String liga, String saison) throws InterruptedException, ParseException, SQLException, IOException {
        System.out.println("Start ReadWebsite");
        this.saison = saison;
        this.land = land;
        this.liga = liga;
        // EInstellungen für den Browser setzen
        System.setProperty("webdriver.chrome.driver", ".\\Driver\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        //Zu Website navigieren
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.navigate().to("https://www.scoreboard.com/de/fussball/" + land + "/" + liga + "" + saison + "/ergebnisse/");
        Thread.sleep(1000);
        //"Mehr Spiele Anzeigen" Button betätigen, wenn vorhanden
        if (driver.findElements(By.id("onetrust-accept-btn-handler")).size() != 0) {
            driver.findElement(By.id("onetrust-accept-btn-handler")).click();
        }
        int anzahlElemente = 0;
        while (driver.findElements(By.xpath("/html/body/div[4]/div[1]/div/div[1]/div[2]/div[4]/div[2]/div[1]/div/div/a")).size() != 0) {
            element = driver.findElement(By.xpath("/html/body/div[4]/div[1]/div/div[1]/div[2]/div[4]/div[2]/div[1]/div/div/a"));
            scroll = driver.findElement(By.xpath("/html/body/div[5]/div/div[1]/div[1]/div[1]"));
            Thread.sleep(1000);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,250)");
            Thread.sleep(1000);
            action = new Actions(driver);
            action.moveToElement(element).click().perform();
            Thread.sleep(1000);
            WebDriverWait wait = new WebDriverWait(driver, 60);
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.className("event__participant--home"), anzahlElemente));
            anzahlElemente = driver.findElements(By.className("event__participant--home")).size();
            Thread.sleep(1000);
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
        for(int i = 0; i < neu.size(); i++) {
            if (x + 1 < spieltag.size()) {
                if (neu.get(i).getLocation().getY() > spieltag.get(x).getLocation().getY() && neu.get(i).getLocation().getY() < spieltag.get(x + 1).getLocation().getY()) {
                } else {
                    x++;
                    gameday = spieltag.get(x).getText();
                }
            }
            za = neu.get(i).getText();
            if(gameday.contains("Spieltag")) {
                if (!za.contains("(")) {
                    htString = "#NOHT";
                } else {
                    htString = "";
                }
                if (za.contains("Beschluss")) {
                    bsString = "#BS";
                } else {
                    bsString = "";
                }
                if (za.contains("n.E.")) {
                    esString = "#ES";
                } else {
                    esString = "";
                }
                String s = gameday + "\n" + "$" + htString + esString + bsString + "\n" + za+"\n";
                writer.append(s);
                writer.flush();

            }
        }
        writer.close();
        driver.close();
        driver.quit();
        readtxt(land);

    }
    public void readtxt(String land) throws IOException, ParseException, SQLException {
        System.out.println("Start Readtxt");
        FileReader file = new FileReader("reader.txt");
        BufferedReader reader = new BufferedReader(file);
        boolean change = false;
        boolean inkonsitenz = false;
        String line = reader.readLine();
        System.out.println("INI  :"+line);
        MySql data = new MySql();
        System.out.println(saison.length());
        if(saison.length() == 0){
            saison = "2019-2020";
        }
        if (saison.length() == 4) {
            jahr = saison;
        } else {
            jahr = saison.substring(5, 9);
        }
        boolean ht;
        boolean es;
        boolean bs;

        while (line != null) {
            System.out.println("Start  "+line);
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
                away = line.replace("'", "\\'");
                inkonsitenz = false;
                if (ht == false || bs == true) {
                    line = reader.readLine();
                    if(line!=null) {
                        if (line.contains("(") && line != null) {
                            inkonsitenz = true;
                        }
                    }
                    homegoalsht = 100;
                    awaygoalsht = 100;
                } else {
                    line=reader.readLine();
                    if(line.substring(4,5).contains("-")){
                        homegoalsht = Integer.parseInt(line.substring(0,2));
                        awaygoalsht = Integer.parseInt(line.substring(6,7));
                    }
                    else {
                        if(line.length()== 8 && line.substring(3,4).contains("-")){
                            homegoalsht = Integer.parseInt(line.substring(1,2));
                            awaygoalsht = Integer.parseInt(line.substring(5,7));
                        }
                        else {
                            homegoalsht = Integer.parseInt(line.substring(1,2));
                            awaygoalsht = Integer.parseInt(line.substring(5,6));
                        }
                    }
                    line= reader.readLine();
                }
            }
            if(inkonsitenz) {
                line = reader.readLine();
            }
            System.out.println(sqldate+" "+home+" "+away);
            data.einlesen(sqldate,home,away,homegoals,awaygoals,gamedaysql,saison,homegoalsht,awaygoalsht,land,liga);
        }
    }
}



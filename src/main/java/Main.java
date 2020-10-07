import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import javax.sound.sampled.Line;
import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

public class Main {

    public static String p;
    public static WebElement element = null;
    public static WebDriver driver = null;
    public static Actions action;
    public static int i = 0;

    public static void main (String [] args) throws InterruptedException, ParseException, SQLException, IOException {
        Main main = new Main ();
        main.read();

    }

    public void read () throws IOException, InterruptedException, ParseException, SQLException {
        String linereader;
        String land = "null";
        String Saison1 = "2017-2018";
        String Saison2 = "2018-2019";
        String Saison3 = "2019-2020";

        SeleniumReadData data = new SeleniumReadData();
        FileReader myFile = new FileReader("Ligen.txt");
        BufferedReader reader = new BufferedReader(myFile);

        int Saisoncounter = 1;
        linereader = reader.readLine();
        int counter = 1;
        while(linereader != null){
            if(linereader.contains("XXX")){
                land = linereader.substring(3);
                linereader=reader.readLine();
                counter++;
            }else {
                switch(Saisoncounter){
                    case 1:
                        System.out.println(land+" "+linereader+" "+Saison1);
                        data.readWebsite(land,linereader,Saison1);
                        Saisoncounter++;
                        break;
                    case 2:
                        System.out.println(land+" "+linereader+" "+Saison2);
                        data.readWebsite(land,linereader,Saison2);
                        Saisoncounter++;
                        break;
                    case 3:
                        System.out.println(land+" "+linereader+" "+Saison3);
                        data.readWebsite(land,linereader,Saison3);
                        Saisoncounter++;
                        break;
                    default:
                        linereader=reader.readLine();
                        Saisoncounter = 1;
                        break;
                }
            }
        }
    }
}

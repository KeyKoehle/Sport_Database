import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;

public class Main {

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
        String Saison4 = "2017";
        String Saison5 = "2018";
        String Saison6 = "2019";

        SeleniumReadData data = new SeleniumReadData();
        FileReader myFile = new FileReader("Ligen.txt");
        BufferedReader reader = new BufferedReader(myFile);

        int Saisoncounter = 1;
        linereader = reader.readLine();
        boolean corona=false;
        while(linereader != null){
            if(linereader.contains("XXX") || linereader.contains("XX#")){
                if(linereader.contains("XX#")){
                    corona = true;
                }
                if(linereader.contains("#S")){
                    land = linereader.substring(5);
                }else{
                    land = linereader.substring(3);
                }
                linereader=reader.readLine();
            } else {
                switch(Saisoncounter){
                    case 1:
                        if(land.contains("#S")){
                            System.out.println(land+" "+linereader+" "+Saison4);
                            data.readWebsite(land.substring(2),linereader+"-",Saison4);
                            Saisoncounter++;
                        }else {
                            System.out.println(land + " " + linereader + " " + Saison1);
                            data.readWebsite(land, linereader + "-", Saison1);
                            Saisoncounter++;
                        }
                    case 2:
                        if(land.contains("#S")) {
                            System.out.println(land + " " + linereader + " " + Saison5);
                            data.readWebsite(land.substring(2), linereader+"-", Saison5);
                            Saisoncounter++;
                        }else {
                            System.out.println(land + " " + linereader + " " + Saison2);
                            data.readWebsite(land, linereader+"-", Saison2);
                            Saisoncounter++;
                        }
                    case 3:
                        if(land.contains("#S")) {
                            System.out.println(land + " " + linereader + " " + Saison6);
                            if(corona) {
                                data.readWebsite(land.substring(2), linereader+"-", "");
                                Saisoncounter++;
                            }else{
                                data.readWebsite(land, linereader, Saison6);
                                Saisoncounter++;
                            }
                        }else {
                            System.out.println(land + " " + linereader + " " + Saison3);
                            if(corona) {
                                data.readWebsite(land, linereader, "");
                                Saisoncounter++;
                            }else{
                                data.readWebsite(land, linereader+"-", Saison3);
                                Saisoncounter++;
                            }
                        }
                    default:
                        linereader=reader.readLine();
                        System.out.println(linereader);
                        Saisoncounter = 1;
                }
            }
        }
    }
}

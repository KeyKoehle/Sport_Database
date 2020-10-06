import java.sql.*;
import java.util.Date;

public class MySql {
    String url = "jdbc:mysql://localhost:3306/fussballspiele?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    String user = "root";
    String password = "1234";

    public void einlesen(Date Datum, String Verein_Heim, String Verein_Auswaerts, int Tore_Heim, int Tore_Auswaerts, int Spieltag, String Saison,  String Land, String Liga) throws SQLException {
        Connection myConnection = DriverManager.getConnection(url, user, password);
        checktableexist(Land,myConnection);
        PreparedStatement Statement = myConnection.prepareStatement("Select * from "+Land+ " WHERE verein_heim = "+"'"+Verein_Heim+"'"+" AND verein_auswaerts = " +"'"+Verein_Auswaerts+"'"+ " AND datum = " +"'"+ Datum+"'");
        ResultSet result = Statement.executeQuery();
        if(!result.next()) {
            Statement myStatement = myConnection.createStatement();
            String sql = "INSERT INTO " + Land + "(datum, verein_heim, verein_auswaerts, tore_heim, tore_auswaerts, spieltag, saison, land, liga) " +
                    "VALUES ('" + Datum + "','" + Verein_Heim + "','" + Verein_Auswaerts + "','" + Tore_Heim + "','" + Tore_Auswaerts + "','" + Spieltag + "','" + Saison +  "','" + Land + "','" + Liga + "')";
            myStatement.execute(sql);
        }
        else{
        }
    }

    public void checktableexist(String land, Connection con) throws SQLException {
        DatabaseMetaData dbm = con.getMetaData();
        ResultSet tables = dbm.getTables(null, null, land, null);
        if (tables.next()) {
        }
        else {
            PreparedStatement statement = con.prepareStatement(
                    "CREATE TABLE " +land+ "(datum DATE NOT NULL, verein_heim VARCHAR(50) NOT NULL, verein_auswaerts VARCHAR(50) NOT NULL, tore_heim INT NOT NULL, tore_auswaerts INT NOT NULL, spieltag INT, saison VARCHAR(20), land VARCHAR(50), liga VARCHAR(50));");
            statement.execute();
            PreparedStatement statement2 = con.prepareStatement("ALTER TABLE "+land+" ADD CONSTRAINT PK PRIMARY KEY (datum,verein_heim,verein_auswaerts);");
            statement2.execute();
            PreparedStatement statement3 = con.prepareStatement(
                    "CREATE TABLE " +land+"_upcoming"+"(datum DATE NOT NULL, verein_heim VARCHAR(50) NOT NULL, verein_auswaerts VARCHAR(50) NOT NULL, tore_heim INT NOT NULL, tore_auswaerts INT NOT NULL, spieltag INT, saison VARCHAR(20), land VARCHAR(50), liga VARCHAR(50));");
            statement3.execute();
            PreparedStatement statement4 = con.prepareStatement("ALTER TABLE "+land+"_upcoming"+" ADD CONSTRAINT PK PRIMARY KEY (datum,verein_heim,verein_auswaerts);");
            statement4.execute();
        }
    }
}

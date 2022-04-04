package jdbc;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;



public class Database {
    public static void database() throws ClassNotFoundException, SQLException{
        final String MYSQLDRIVER = "com.mysql.cj.jdbc.Driver";
        final String POSTGRESQLDRIVER = "org.postgresql.Driver";
        final String MARIADBDRIVER = "org.mariadb.jdbc.Driver";
        final String URL = "jdbc:mysql://localhost:3306/wild";
        final String USERNAME = "app";
        final String PASSWORD = "12345";
        Class.forName(MYSQLDRIVER);
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from persons");
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO persons (FIRSTNAME, LASTNAME, AGE) VALUES (?,?,?)");
        PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM persons WHERE age > 40");
        PreparedStatement changeStatement = connection.prepareStatement("UPDATE persons SET age = ? WHERE age > 40");
        preparedStatement.setString(1, "Paul");
        preparedStatement.setString(2, "Paulsen");
        preparedStatement.setInt(3, 42);
        preparedStatement.execute();
        preparedStatement.setString(1, "Micha");
        preparedStatement.setString(2, "Michaelsen");
        preparedStatement.setInt(3, 50);
        changeStatement.setInt(1, 100);

        System.out.println("Table before adding new entrys: ");
        while(resultSet.next()){
            System.out.println(resultSet.getString(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3));
        }

        preparedStatement.execute();
        System.out.println("_______________________________________");
        System.out.println("Table after adding new entrys: ");
        ResultSet resultAfterInsertion = statement.executeQuery("select * from persons");
        while(resultAfterInsertion.next()){
            System.out.println(resultAfterInsertion.getString(1) + " " + resultAfterInsertion.getString(2) + " " + resultAfterInsertion.getString(3));
        }


        changeStatement.execute();
        System.out.println("_______________________________________");
        System.out.println("Table after changing 2 entrys: ");
        ResultSet resultAfterChange = statement.executeQuery("select * from persons");
        while(resultAfterChange.next()){
            System.out.println(resultAfterChange.getString(1) + " " + resultAfterChange.getString(2) + " " + resultAfterChange.getString(3));
        }

        deleteStatement.execute();
        System.out.println("_______________________________________");
        System.out.println("Table after deleting 2 entrys: ");
        ResultSet showResult = statement.executeQuery("select * from persons");
        while(showResult.next()){
            System.out.println(showResult.getString(1) + " " + showResult.getString(2) + " " + showResult.getString(3));
        }

        System.out.println("_______________________________________");
        System.out.println("Table with datatypes: ");
        ResultSet resultWithType = statement.executeQuery("select * from persons");
        ResultSetMetaData rsmd = resultWithType.getMetaData();
        while(resultWithType.next()){
            System.out.println("\n" + resultWithType.getString(1) + " Datatype is: " + rsmd.getColumnTypeName(1) +"\n"+ resultWithType.getString(2) + " Datatype is: " + rsmd.getColumnTypeName(2)+ "\n"  + resultWithType.getString(3) + " Datatype is: " + rsmd.getColumnTypeName(3) );
        }

        resultSet.close();
        showResult.close();
        resultAfterChange.close();
        resultAfterInsertion.close();
        statement.close();
        connection.close();
        preparedStatement.close();
        deleteStatement.close();
        changeStatement.close();
        resultWithType.close();
    }
}

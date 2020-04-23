package backend.db;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class Broker {

    public static void setPossiblePemission(Connection connection, long id,boolean isPermanent,String textedReason,String place ,String date1,String date2,String attachment , boolean needRevision ) throws SQLException {

        String SQL = " INSERT INTO [dbo].[Permit] (ID, IS_PERMANENT, REASON ,PLACE, FIRST_DATE , SECOND_DATE , ATTACHMENT, NEED_REVISION, IS_PERMITTED)" +
                "VALUES (?, ?, ?,?, ?, ? , ? ,?,?);" ;

        PreparedStatement stmt = null;

        try {
            stmt = connection.prepareStatement(SQL);
            stmt.setLong(1, id);
            stmt.setBoolean(2, isPermanent);
            stmt.setString(3, textedReason);
            stmt.setString(3, place);
            if(date1==null)
                stmt.setDate(4, null);
            else
                stmt.setDate(4, Date.valueOf(date1));
            if(date1==null)
                stmt.setDate(5, null);
            else
                stmt.setDate(5, Date.valueOf(date1));

            stmt.setString(6, attachment);
            stmt.setBoolean(7, needRevision);
            stmt.setBoolean(8, false);

            stmt.executeUpdate();

        } finally {
            ConnectionManager.close(stmt, connection);
        }

    }

    public static String getPermission( Connection connection , long id ) throws SQLException {
        String SQL = "SELECT *  FROM [dbo].[Permit] with(nolock) WHERE ID = ? ;";

        PreparedStatement stmt = null;
        ResultSet rs = null;
        String allReasons="";

        try {
            stmt = connection.prepareStatement(SQL);
            stmt.setLong(1, id);

            rs = stmt.executeQuery();

            while (rs.next()) {
                if(rs.getBoolean(("IS_PERMITTED")))
                allReasons+= rs.getString("REASON")+","+rs.getDate("FIRST_DATE")+","+rs.getDate("SECOND_DATE") + "|";

            }
            System.out.println("Reason is ------ : " +allReasons);

            return allReasons;

        } finally {
            ConnectionManager.close(rs, stmt, connection);
        }


    }

    public static List<Long> getAllNotPermittedAndReady( int numberOfRecords, Connection connection)
            throws SQLException {
        String SQL = "SELECT TOP 5 ID FROM [dbo].[Permit] with(nolock) WHERE IS_PERMITTED = ? and NEED_REVISION= ? ;";
        List<Long> allReady = new LinkedList<>();
        //List<Long> allNotReady = new LinkedList<>();

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = connection.prepareStatement(SQL);
            //stmt.setInt(1, numberOfRecords);
            stmt.setBoolean(1,false);
            stmt.setBoolean(2,false);

            System.out.println("***** getAllNotPermittedAndReady *****");

            rs = stmt.executeQuery();

            while (rs.next()) {
                allReady.add(rs.getLong("ID"));
            }
        } finally {
            ConnectionManager.close(rs, stmt, connection);
        }
        return allReady;
    }

    public static void setPermitted( Connection connection , long id)throws SQLException {
        String SQL = "UPDATE [dbo].[Permit] SET IS_PERMITTED = ? WHERE ID = ?";

        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(SQL);

            stmt.setBoolean(1, true);
            stmt.setLong(2, id);

            stmt.executeUpdate();

        } finally {
            ConnectionManager.close(stmt);
        }
    }
}

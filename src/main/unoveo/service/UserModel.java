package service;
import model.UserInfo;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserModel {
    public List<UserInfo> getUserList() {
        List<UserInfo> list = new ArrayList<>();
        ConnectionDemo obj = new ConnectionDemo();
        try {

            Connection con = obj.IntializeDatabase();
            Statement st = con.createStatement();
            String query1 = "select * from user ";
            ResultSet rs = st.executeQuery(query1);
            while (rs.next()) {
                UserInfo userdata = new UserInfo();
                userdata.setFirstName(rs.getString(1));
                userdata.setLastName(rs.getString(2));
                userdata.setEmail(rs.getString(3));
                userdata.setPassword(rs.getString(4));
                userdata.setId(rs.getInt(5));
                list.add(userdata);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        System.out.println("userlist " + list.get(1));
        return (list);
    }

    public List<UserInfo> getaUser(String id) {
        ConnectionDemo obj = new ConnectionDemo();
        List<UserInfo> list = new ArrayList<>();
        try {
            Connection con = obj.IntializeDatabase();
            Statement st = con.createStatement();
            String query1 = "select * from user where id = '" + id + "'";
            ResultSet rs = st.executeQuery(query1);
            UserInfo userdata = new UserInfo();
            while (rs.next()) {

                userdata.setFirstName(rs.getString(1));
                userdata.setLastName(rs.getString(2));
                userdata.setEmail(rs.getString(3));
                userdata.setPassword(rs.getString(4));
                userdata.setId(rs.getInt(5));

            }
            System.out.println("userstring------>" + userdata);

            list.add(userdata);
            rs.close();

        } catch (Exception e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }

        return (list);
    }

    public void deleteUser(String id) {

        ConnectionDemo obj = new ConnectionDemo();
        try {
            Connection con = obj.IntializeDatabase();
            Statement st = con.createStatement();
            String query1 = "DELETE from user where id = '" + id + "'";
            ResultSet rs = st.executeQuery(query1);
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void AddUser(UserInfo user) {
        ConnectionDemo obj = new ConnectionDemo();
        try {
            Connection con = obj.IntializeDatabase();
            PreparedStatement st = con.prepareStatement("INSERT INTO USER values(?,?,?,?,?)");
            st.setString(1, user.getFirstName());
            st.setString(2, user.getLastName());
            st.setString(3, user.getEmail());
            st.setString(4, user.getPassword());
            st.setString(5, String.valueOf(user.getId()));
            // Execute the insert command using executeUpdate()
            // to make changes in database
            st.executeUpdate();
            // Close all the connections
            st.close();
//            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public void UpdateUser(UserInfo user,String id) {
        ConnectionDemo obj = new ConnectionDemo();

        try {
            Connection con = obj.IntializeDatabase();
            PreparedStatement st = con.prepareStatement("Update user SET firstName = ?, lastName = ?, email = ?, password = ? where id = ?");
            st.setString(1, user.getFirstName());
            st.setString(2, user.getLastName());
            st.setString(3, user.getEmail());
            st.setString(4, user.getPassword());
            st.setString(5, id);

            st.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void updatePatchUser(String id,UserInfo user) {
        ConnectionDemo obj = new ConnectionDemo();
////        int status=0;
        try {
            Connection con = obj.IntializeDatabase();
            List<UserInfo>result= getaUser(id);
            UserInfo updatedUser=result.get(0);
            System.out.println(updatedUser.getPassword());
////              for(UserInfo updatedUser : result) {

            if(user.getPassword()!=null)
                updatedUser.setPassword(user.getPassword());
            if(user.getEmail()!=null)
                updatedUser.setEmail(user.getEmail());

            if(user.getFirstName()!=null)
                updatedUser.setFirstName(user.getFirstName());

            if(user.getLastName()!=null)
                updatedUser.setLastName(user.getLastName());
////
////              for(int i=0;i<keys.size();i++)
////              {
////                  String ans= keys.get(i);
////                  System.out.println(user.ans);
////              }
////            System.out.println(user.getPassword());

            PreparedStatement st = con.prepareStatement("Update user SET firstName = ?, lastName = ?, email = ?, password = ? where id = '" + id + "'");
            st.setString(1, updatedUser.getFirstName());
            st.setString(2, updatedUser.getLastName());
            st.setString(3, updatedUser.getEmail());
            st.setString(4, updatedUser.getPassword());
////            st.setString(5, updatedUser.getId());
            st.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();     }

    }

    public int loginUser(UserInfo user)
    {
        ConnectionDemo obj = new ConnectionDemo();
        int status=0;
        try {
            Connection con = obj.IntializeDatabase();
            Statement st= con.createStatement();

            String query1 = "select email from user where email= '"+user.getEmail()+"'";
            String query2 = "select password from user where password= '"+user.getPassword()+"'";
            ResultSet rs = st.executeQuery(query1);
            ResultSet rs1 = st.executeQuery(query2);
            if (rs.next() && rs1.next()) {
                status=1;
            }
            else {
                status=0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
return status;

    }


}






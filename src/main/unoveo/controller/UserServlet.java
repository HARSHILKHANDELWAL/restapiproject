package controller;
import java.security.MessageDigest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import jakarta.servlet.http.*;
//import .ConnectionDemo;
import model.UserInfo;
import service.UserModel;
import service.UserModel;
@WebServlet("/UserServlet/*")
public class UserServlet extends HttpServlet {
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getMethod();
        if (!method.equals("PATCH")) {
            super.service(request, response);
        }
        this.doPatch(request, response);


    }
    public void doGet(HttpServletRequest request, HttpServletResponse response)

            throws IOException, ServletException {

        String Info = request.getPathInfo();
//        System.out.println(Info);
        if(Info != null) {
            System.out.println("In Get 1");
            UserModel um = new UserModel();
            GsonBuilder builder = new GsonBuilder();
            builder.serializeNulls();
            Gson gson = builder.excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
            String[] pathInfo = Info.split("/");
            List<UserInfo> result = um.getaUser(pathInfo[1]);
            String jsonEmp = gson.toJson(result);
//            System.out.println("result list------>"+result.get(0));
//            new Gson().toJson(mobilePhone)
//            System.out.println("gson result");
//            System.out.println(jsonEmp);
//            System.out.println("controller list------>"+jsonString.toString()); //bug is ere

            response.setContentType("application/json");
//            response.setHeader("Keep-Alive", "timeout=5, max=1000");
            ;
//            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("duihduishdui");

        } else {

            try {
                System.out.println("In Get 2 all");
                UserModel um = new UserModel();
                List<UserInfo> result = um.getUserList();
                GsonBuilder builder = new GsonBuilder();
                builder.serializeNulls();
                Gson gson = builder.excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
                String jsonEmp = gson.toJson(result);
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(jsonEmp);
            } catch (Exception e) {
                e.printStackTrace(); // Log the exception
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // Set appropriate status code
                response.getWriter().write("Internal Server Error");
            }

        }

    }

        public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
            String Info = request.getPathInfo();
            String[] pathparts = Info.split("/");
            UserModel um = new UserModel();
            um.deleteUser(pathparts[1]);
            List<UserInfo> result = um.getUserList();
            GsonBuilder builder = new GsonBuilder();
            builder.serializeNulls();
            Gson gson = builder.setPrettyPrinting().create();
            String jsonString = gson.toJson(result);
            response.setContentType("application/json");
            response.getWriter().write(jsonString);

        }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String Info = request.getPathInfo();

        MessageDigest md5;
        if ( Info != null) {
            String[] pathInfo = Info.split("/");

            UserModel um = new UserModel();
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String requestBody = sb.toString();
            System.out.println(requestBody);

            ObjectMapper objectMapper = new ObjectMapper();
            UserInfo user = objectMapper.
                    readValue(requestBody, UserInfo.class);

            try {
                md5 = MessageDigest.getInstance("MD5");
                byte[] hash = md5.digest(user.getPassword().getBytes());

                System.out.println(hash.toString());

                StringBuilder hashpassword = new StringBuilder();

                for (int i = 0; i < hash.length; i++) {
                    hashpassword.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
                }
//            System.out.println("hex password-------->" + hashpassword);
                user.setPassword(hashpassword.toString());
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            int status = um.loginUser(user);
            if (status == 1)
                response.getWriter().write("Login Successfull");
            else
                response.getWriter().write("Login Unsuccessfull");
        } else {
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String requestBody = sb.toString();
            System.out.println(requestBody);

            ObjectMapper objectMapper = new ObjectMapper();
            UserInfo user = objectMapper.
                    readValue(requestBody, UserInfo.class);
            System.out.println(user.getFirstName());
            md5 = null;
            try {
                md5 = MessageDigest.getInstance("MD5");
                byte[] hash = md5.digest(user.getPassword().getBytes());

                System.out.println(hash.toString());

                StringBuilder hashpassword = new StringBuilder();

                for (int i = 0; i < hash.length; i++) {
                    hashpassword.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
                }
                System.out.println("hex password-------->" + hashpassword);
                user.setPassword(hashpassword.toString());
                StringBuilder hashpassword1 = new StringBuilder();

                for (byte b : hash) {
//                System.out.printf("%02x", b);
                    hashpassword1.append(b);

                }
                System.out.println("not hex password-------->" + hashpassword1);

            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            UserModel addUser = new UserModel();
            addUser.AddUser(user);

            response.getWriter().println("inserted successfully");
        }
    }
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String Info = request.getPathInfo();
        String[] pathInfo = Info.split("/");
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String requestBody = sb.toString();
//        System.out.println(requestBody);
        ObjectMapper objectMapper = new ObjectMapper();
        UserInfo user = objectMapper.readValue(requestBody, UserInfo.class);
//        System.out.println(pathInfo[1]);
        UserModel updateUser = new UserModel();
        try {
            updateUser.UpdateUser(user, pathInfo[1]);
        } catch (Exception e) {
            System.out.println("error is there");
        }
        response.getWriter().println("Updated(put) successfully");

    }




    public void doPatch(HttpServletRequest request,HttpServletResponse response) throws ServletException,
            IOException {
        String Info=request.getPathInfo();
        String[] pathInfo=Info.split("/");
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String requestBody = sb.toString();
        System.out.println(requestBody);
        ObjectMapper objectMapper = new ObjectMapper();

        UserInfo user = objectMapper.readValue(requestBody, UserInfo.class);
//    System.out.println(request.getReader().);
//        System.out.println(user.getFirstName());
//        System.out.println(user.getLastName());
//        System.out.println(user.getEmail());
//        System.out.println(user.getPassword());
        UserModel updatepatchUser = new UserModel();
////    try {
        updatepatchUser.updatePatchUser(pathInfo[1],user);
////    }
////    catch(Exception e)
////    {
////        System.out.println("error is there");
////    }
        response.getWriter().println("Updated successfully");

//    }


    }

}





































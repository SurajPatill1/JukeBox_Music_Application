import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Users {

    private int userid;
    private String username;
    private String password;
    Statement st=null;
    Connection con=null;
    ResultSet rs=null;
    static int uid;

    public Users() {
    }

    public Users(int userid, String username, String password) {
        this.userid = userid;
        this.username = username;
        this.password = password;
    }


    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int checkUser() throws SQLException {
        ConnectionClass cc=new ConnectionClass();
        ArrayList<Users> listbyuser = new ArrayList<>();
        try {
            con = ConnectionClass.getConnection();
            Scanner sc = new Scanner(System.in);
            int num = 0;
            System.out.println("----------------------------------------------------------------");
            System.out.println("****************** Welcome To Your JukeBox *********************");
            System.out.println("----------------------------------------------------------------");
            do {
                System.out.println("Enter  1.For New User");
                System.out.println("       2.For Existing User");
                int choice = sc.nextInt();
                if (choice == 1) {
                    System.out.println("Enter Your Name");
                    String name = sc.next();
                    System.out.println("Create Your Min. 8 Digit Password");
                    String password = sc.next();
                    st = con.createStatement();
                    if (password.length() >= 8) {
                        rs = st.executeQuery("select * from users where username='" + name + "'");
                        while (rs.next()) {
                            listbyuser.add(new Users(rs.getInt(1), rs.getString(2), rs.getString(3)));
                        }
                        if (listbyuser.size() > 0) {
                            for (Users us : listbyuser) {
                                if (us.getUsername().equals(name)) {
                                    System.out.println("Username Already Exits");
                                    System.out.println("Try with another Username");
                                }

                            }

                        } else {
                            int x = st.executeUpdate("insert into users(username,password) values('" + name + "','" + password + "')");
                            System.out.println();
                            System.out.println("Account Created Successfully");
                            System.out.println("Thanks! for Signing up.");
                            System.out.println();
                            System.out.println("*************** Welcome to Jukebox ***************");
                            return 1;
                        }

                    } else {
                        System.out.println("Password Needs didn't Meet");
                    }

                } else if (choice == 2) {
                    System.out.println("Enter Your Name");
                    String uname = sc.next();
                    System.out.println("Enter the Password");
                    String pass = sc.next();
                    String dbpass = "";
                    String dbname = "";
                    st = con.createStatement();
                    ResultSet rs = st.executeQuery("select * from users where username='" + uname + "'");
                    while (rs.next()) {
                        uid = rs.getInt(1);
                        dbpass = rs.getString(3);
                        dbname = rs.getString(2);
                    }
                    if (dbpass.equals(pass) && dbname.equals(uname)) {
                        System.out.println("*************** Welcome to Jukebox ***************");
                        return 1;
                    } else {
                        System.out.println("Wrong Credentials");
                    }

                } else {
                    System.out.println("Invalid Input");
                }
                System.out.println("Do you want to Continue Enter 1. Or Enter 0 For Quiting");
                Scanner sc1 = new Scanner(System.in);
                int entry = sc1.nextInt();
                if (entry == 1) {
                    checkUser();
                } else if (entry == 0) {
                    break;
                } else {
                    System.out.println("Invalid Input");
                }
                num = sc.nextInt();
            }
            while (num != 0);
            System.out.println("-------* Thanks For Visiting *-------");

        } catch (Exception e) {
            System.out.println("WARNING ⚠️Enter only Mentioned Entries ");
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Users{" +
                "userid=" + userid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Podcast {
    private int podcastid;
    private String podcastname;
    private String podcastepname;
    private int poscastepid;
    private String duration;
    private String eppath;
    private int pod;
    Statement st=null;
    Connection con=null;
    ResultSet rs=null;

    public Podcast() {
    }

    public Podcast(int podcastid, String podcastname, String podcastepname, int poscastepid, String duration, String eppath, int pod) {
        this.podcastid = podcastid;
        this.podcastname = podcastname;
        this.podcastepname = podcastepname;
        this.poscastepid = poscastepid;
        this.duration = duration;
        this.eppath = eppath;
        this.pod = pod;
    }

    public int getPodcastid() {
        return podcastid;
    }

    public void setPodcastid(int podcastid) {
        this.podcastid = podcastid;
    }

    public String getPodcastname() {
        return podcastname;
    }

    public void setPodcastname(String podcastname) {
        this.podcastname = podcastname;
    }

    public String getPodcastepname() {
        return podcastepname;
    }

    public void setPodcastepname(String podcastepname) {
        this.podcastepname = podcastepname;
    }

    public int getPoscastepid() {
        return poscastepid;
    }

    public void setPoscastepid(int poscastepid) {
        this.poscastepid = poscastepid;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getEppath() {
        return eppath;
    }

    public void setEppath(String eppath) {
        this.eppath = eppath;
    }

    public int getPod() {
        return pod;
    }

    public void setPod(int pod) {
        this.pod = pod;
    }

    public void podcastDisplay() throws SQLException {
        try {
            Scanner scan = new Scanner(System.in);
            con = ConnectionClass.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("select * from podcast");
            System.out.println("******************************************************************************************************************************************************************");
            System.out.format("%-15s %-25s %30s %30s %32s", "Podcast ID", "Podcast Name", "Episode Name", "Episode ID", "Duration \n");
            System.out.println("******************************************************************************************************************************************************************");
            while (rs.next()) {
                System.out.format("%-15s %-25s %30s %30s %30s\n", rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5));
            }
            System.out.println();
            System.out.println("Enter the Podcast Episode ID");
            String pid = scan.next();
            ResultSet rs1 = st.executeQuery("select * from podcast where poscastepid='" + pid + "'");
            System.out.println("*********************************************************************************************************");
            System.out.format(" %-10s %10s %30s \n", "Episode ID", "Episode Name", "Episode Path");
            System.out.println("*********************************************************************************************************");
            while (rs1.next()) {
                System.out.format(" %-10s %10s %30s \n", rs1.getInt(1), rs1.getString(3), rs1.getString(6));
            }
            System.out.println("Enter 1.For continue 0.For Quit");
            int num=scan.nextInt();
            switch (num){
                case 1:{
                    podcastDisplay();
                }
                case 0:{
                    break;
                }
                default:{
                    System.out.println("Invalid Input");
                }
            }
        }catch (Exception ex){
            System.out.println("Invalid Input");
            System.out.println("Try Again");
        }
    }
    public void podcastname() throws SQLException {
        try {
            Scanner scan = new Scanner(System.in);
            con = ConnectionClass.getConnection();
            st = con.createStatement();
            System.out.println("--------- Available Podcasts ---------");
            ResultSet rs = st.executeQuery("select distinct(podcastname) from podcast");
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
            System.out.println();
            System.out.println("Enter the Podcast Channel Name");
            String pname = scan.next();
            System.out.println("--------- Available Channels ---------");
            ResultSet rs1 = st.executeQuery("select * from podcast where podcastname='" + pname + "'");
            System.out.println("****************************************************************************************************************************************************");
            System.out.format(" %-10s %20s %40s %55s \n", "Podcast ID", "Podcast Name", "Episode Name", "Episode Path");
            System.out.println("****************************************************************************************************************************************************");
            while (rs1.next()) {
                System.out.format(" %-10s %20s %40s %55s \n", rs1.getInt(1), rs1.getString(2), rs1.getString(3), rs1.getString(6));
            }
            System.out.println("Enter 1.For continue 0.For Quit");
            int num=scan.nextInt();
            switch (num){
                case 1:{
                    podcastname();
                }
                case 0:{
                    break;
                }
            }
        }catch (Exception ex){
            System.out.println("Invalid Input");
            System.out.println("Try Again");
        }
    }
    public void podcastepname() throws SQLException {
        try {
            Scanner scan = new Scanner(System.in);
            con = ConnectionClass.getConnection();
            st = con.createStatement();
            System.out.println("--------- Available Episodes ---------");
            ResultSet rs = st.executeQuery("select distinct(podcastepname) from podcast");
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
            System.out.println();
            System.out.println("Enter the Episode Name");
            String ename = scan.next();
            ResultSet rs1 = st.executeQuery("select * from podcast where podcastepname='" + ename + "'");
            System.out.println("**********************************************************************************************************");
            System.out.format(" %-10s %20s %30s \n", "Podcast ID", "Episode Name", "Episode Path");
            System.out.println("**********************************************************************************************************");
            while (rs1.next()) {
                System.out.format(" %-10s %20s %30s \n", rs1.getInt(1), rs1.getString(3), rs1.getString(6));
            }
            System.out.println("Enter 1.For continue 0.For Quit");
            int num=scan.nextInt();
            switch (num){
                case 1:{
                    podcastepname();
                }
                case 0:{
                    break;
                }

            }
        }catch (Exception ex){
            System.out.println("Invalid Input");
            System.out.println("Try Again");
        }
    }

    @Override
    public String toString() {
        return "Podcast{" +
                "podcastid=" + podcastid +
                ", podcastname='" + podcastname + '\'' +
                ", podcastepname='" + podcastepname + '\'' +
                ", poscastepid=" + poscastepid +
                ", duration='" + duration + '\'' +
                ", eppath='" + eppath + '\'' +
                ", pod=" + pod +
                '}';
    }
}

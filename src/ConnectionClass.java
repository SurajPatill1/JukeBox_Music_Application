import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class ConnectionClass {
    static Connection con = null;
    Statement st = null;
    ResultSet rs = null;

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jukeboxdb", "root", "root");
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return con;
    }


    public static void main(String[] args) throws SQLException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        ConnectionClass cc = new ConnectionClass();
        Users users = new Users();
        Songs s = new Songs();
        Podcast pd = new Podcast();
        Playlist pl = new Playlist();
        //try {
            Scanner scan = new Scanner(System.in);
            int result = users.checkUser();
            int num = 0;
            do {
                if (result == 1) {
                    System.out.println("Enter 1.For All Songs");
                    System.out.println("      2.For Podcast");
                    System.out.println("      3.Search by Album");
                    System.out.println("      4.Search by Artist");
                    System.out.println("      5.Search by Genre");
                    System.out.println("      6.Create Playlist");
                    System.out.println("      7.Display Playlist");
                    System.out.println("      0.For Quit");
                    int choise = scan.nextInt();
                    switch (choise) {
                        case 1:
                            s.songsDisplay();
                            break;
                        case 2:
                            System.out.println("Enter 1.For all Podcast");
                            System.out.println("      2.For Podcast Channel");
                            System.out.println("      3.For All Episodes");
                            int entry = scan.nextInt();
                            if (entry == 1) {
                                pd.podcastDisplay();
                            } else if (entry == 2) {
                                pd.podcastname();
                            } else if (entry == 3) {
                                pd.podcastepname();
                            } else {
                                System.out.println("Invalid Entry");
                            }
                            break;
                        case 3:
                            s.album();
                            System.out.println("Do you want to Continue Enter 1. Or Enter 0 For Quiting");
                            int entry1 = scan.nextInt();
                            if (entry1 == 1)
                                s.album();
                            else if (entry1 == 0) {
                                s.songsDisplay();
                            } else {
                                System.out.println("Invalid Input");
                            }
                            break;
                        case 4:
                            s.artist();
                            break;
                        case 5:
                            s.genre();
                            break;
                        case 6:
                            pl.createPlaylist();
                            break;
                        case 7:
                            pl.displayplaylist();
                            break;
                        case 0:
                            break;
                        default:
                            System.out.println("Invalid Entry");
                    }

                }
                System.out.println("Do you want to Continue Enter 1. Or Enter 0 For Quiting");
                num = scan.nextInt();
                if (num == 1) {

                }
            } while (num != 0);
            System.out.println("-------Thanks For Visiting-------");
//        } catch (Exception ex) {
//            System.out.println("Invalid Input");
//            System.out.println("Try Again");
//        }
    }
}


import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Playlist {
    private int playlistid;
    private int plid;
    private String playlistname;
    private int songid;
    private int pod;
    private int userid;
    Statement st = null;
    Connection con = null;
    ResultSet rs = null;
    Songs s = new Songs();

    public Playlist() {
    }

    public Playlist(int playlistid, int plid, String playlistname, int songid, int pod, int userid) {
        this.playlistid = playlistid;
        this.plid = plid;
        this.playlistname = playlistname;
        this.songid = songid;
        this.pod = pod;
        this.userid = userid;
    }

    public int getPlaylistid() {
        return playlistid;
    }

    public void setPlaylistid(int playlistid) {
        this.playlistid = playlistid;
    }

    public int getPlid() {
        return plid;
    }

    public void setPlid(int plid) {
        this.plid = plid;
    }

    public String getPlaylistname() {
        return playlistname;
    }

    public void setPlaylistname(String playlistname) {
        this.playlistname = playlistname;
    }

    public int getSongid() {
        return songid;
    }

    public void setSongid(int songid) {
        this.songid = songid;
    }

    public int getPod() {
        return pod;
    }

    public void setPod(int pod) {
        this.pod = pod;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void createPlaylist() throws SQLException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        ArrayList<Playlist> listbyplaylist = new ArrayList<>();
        con = ConnectionClass.getConnection();
        st = con.createStatement();
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the Playlist ID");
        String plid=scan.next();
        System.out.println("Enter the Playlist Name");
        String pname=scan.next();
        rs = st.executeQuery("select * from playlist where playlistname='" + pname + "'");
        while (rs.next()) {
            listbyplaylist.add(new Playlist(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6)));
        }
        if (listbyplaylist.size() > 0) {
            for (Playlist us : listbyplaylist) {
                if (us.getPlaylistname().equals(pname)) {
                    System.out.println("Playlist name Already Exits");
                    System.out.println("Try with another name");
                    System.out.println("-------- Or --------");
                    break;
                }

            }

        }
        System.out.println("Enter 1.For Adding article    0.For Quit");
        int entry=scan.nextInt();
        if(entry==1){
            existingplaylist();
        }

}
        public void existingplaylist() throws SQLException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        ArrayList<Songs> listbyplaylist1=new ArrayList<>();
        con = ConnectionClass.getConnection();
        st = con.createStatement();
        Scanner scan=new Scanner(System.in);
        int uid1=Users.uid;
        rs = st.executeQuery("select * from playlist where userid="+uid1);
            System.out.println("****************************************************************************************");
            System.out.format(" %-10s %15s %20s %30s \n", "Playlist ID", "Playlist Name", "Song ID", "Podcast Episode ID");
            System.out.println("****************************************************************************************");
        while (rs.next()) {
            System.out.format(" %-10s %15s %20s %30s \n", rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getInt(5));
        }
            rs = st.executeQuery("select filepath, songs.songid from songs join playlist on songs.songid=playlist.songid");
            while (rs.next()) {
                listbyplaylist1.add(new Songs(rs.getString(1),rs.getInt(2)));
            }
            System.out.println("Enter 1. for Song to play    2. for Podcast to play    3.for Adding items");
            int value = scan.nextInt();
            switch (value) {
                case 1: {
                    System.out.println("Enter the SongID");
                    int sid = scan.nextInt();
                    s.musicplay(listbyplaylist1, sid);
                    break;
                }

                case 2: {
                    System.out.println("Enter the PodcastID");
                    int pid = scan.nextInt();
                    rs = st.executeQuery("select * from podcast where poscastepid='" + pid + "'");
                    System.out.println("******************************************************************************************************************************************");
                    System.out.format(" %-10s %20s %40s %55s \n", "Podcast ID", "Podcast Name", "Episode Name", "Episode Path");
                    System.out.println("*******************************************************************************************************************************************");
                    while (rs.next()) {
                        System.out.format(" %-10s %20s %40s %75s \n", rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(6));
                    }
                }
                case 3: {
                    System.out.println("Enter the Playlist ID to add in Playlist");
                    String plid = scan.next();
                    System.out.println("Enter the Playlist Name");
                    String pname = scan.next();
                    System.out.println("Enter 1.For Adding Songs   2.For Adding Podcast   3.For Adding both Songs & Podcast");
                    int entry = scan.nextInt();
                    if (entry == 1) {
                        System.out.println("--------- Available Songs ---------");
                        rs = st.executeQuery("select * from songs");
                        System.out.println("****************************************************************************************************************");
                        System.out.format(" %-10s %-17s %18s %20s %20s %22s ", "Song ID", "Song Name", "Artist Name", "Album Name", "Genre", "Duration \n");
                        System.out.println("****************************************************************************************************************");
                        while (rs.next()) {
                            System.out.format(" %-10s %-17s %18s %20s %20s %20s \n", rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
                        }
                        System.out.println("Enter the Song ID you want to add");
                        int sid = scan.nextInt();
                        int x = st.executeUpdate("insert into playlist (plid,playlistname,songid,userid) values(" + plid + ",'" + pname + "'," + sid + "," + uid1 + ")");
                        if (x > 0) {
                            System.out.println("-------Added Successfully-------");
                        }
                    } else if (entry == 2) {
                        rs = st.executeQuery("select * from podcast");
                        System.out.println("************************************************************************************************************************************************");
                        System.out.format("%-15s %-25s %30s %30s %32s", "Podcast ID", "Podcast Name", "Episode Name", "Episode ID", "Duration \n");
                        System.out.println("************************************************************************************************************************************************");
                        while (rs.next()) {
                            System.out.format("%-15s %-25s %30s %30s %30s\n", rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5));
                        }
                        System.out.println("Enter the Episode ID you want to add");
                        int pid = scan.nextInt();
                        //int uid1 = Users.uid;
                        int x = st.executeUpdate("insert into playlist (plid,playlistname,pod,userid) values(" + plid + ",'" + pname + "'," + pid + "," + uid1 + ")");
                        if (x > 0) {
                            System.out.println("-------Added Successfully-------");
                        }
                    } else if (entry == 3) {
                        System.out.println("Enter the Song ID you want to add");
                        int sid = scan.nextInt();
                        System.out.println("Enter the Episode ID you want to add");
                        int pid = scan.nextInt();
                        //int uid1 = Users.uid;
                        int x = st.executeUpdate("insert into playlist (plid,playlistname,songid,pod,userid) values(" + plid + ",'" + pname + "'," + sid + "," + pid + "," + uid1 + ")");
                    }
                }
                default:{
                    System.out.println("Invalid Input");
                }
            }
        }

    @Override
    public String toString() {
        return "Playlist{" +
                "playlistid=" + playlistid +
                ", playlistname='" + playlistname + '\'' +
                ", songid=" + songid +
                ", userid=" + userid +
                ", pod=" + pod +
                '}';
    }
}

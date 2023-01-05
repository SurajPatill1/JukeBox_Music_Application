import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
public class Songs {
    private int songid;
    private String songname;
    private String artistname;
    private String albumname;
    private String genre;
    private String duration;
    private String filepath;

    public Songs() {
    }

    public Songs(int songid, String songname, String artistname, String albumname, String genre, String duration, String filepath) {
        this.songid = songid;
        this.songname = songname;
        this.artistname = artistname;
        this.albumname = albumname;
        this.genre = genre;
        this.duration = duration;
        this.filepath = filepath;
    }
    public Songs(String filepath, int songid) {
        this.filepath = filepath;
        this.songid = songid;
    }

    public int getSongid() {
        return songid;
    }

    public void setSongid(int songid) {
        this.songid = songid;
    }

    public String getSongname() {
        return songname;
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }

    public String getArtistname() {
        return artistname;
    }

    public void setArtistname(String artistname) {
        this.artistname = artistname;
    }

    public String getAlbumname() {
        return albumname;
    }

    public void setAlbumname(String albumname) {
        this.albumname = albumname;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    Statement st = null;
    Connection con = null;
    ResultSet rs = null;

    public void songsDisplay() throws SQLException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        ArrayList<Songs> listbysongs=new ArrayList<>();
        try {
            Scanner scan = new Scanner(System.in);
            con = ConnectionClass.getConnection();
            st = con.createStatement();
            System.out.println("--------- Available Songs ---------");
            rs = st.executeQuery("select * from songs");
            System.out.println("****************************************************************************************************************");
            System.out.format(" %-10s %-17s %18s %20s %20s %22s ", "Song ID", "Song Name", "Artist Name", "Album Name", "Genre", "Duration \n");
            System.out.println("****************************************************************************************************************");
            while (rs.next()) {
                System.out.format(" %-10s %-17s %18s %20s %20s %20s \n", rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
            }
            System.out.println();
            System.out.println("Enter the Song ID");
            int sid = scan.nextInt();
            ResultSet rs1 = st.executeQuery("select * from songs where songid='" + sid + "'");
//            System.out.println("******************************************************");
//            System.out.format(" %-10s %10s \n", "Song ID", "Song Name");
//            System.out.println("******************************************************");
            while (rs1.next()) {
//                System.out.format(" %-10s %10s \n", rs1.getInt(1), rs1.getString(2));
               listbysongs.add(new Songs(rs1.getInt(1), rs1.getString(2), rs1.getString(3), rs1.getString(4), rs1.getString(5), rs1.getString(6), rs1.getString(7)));
            }
            System.out.println();
            //System.out.println("Enter the Song ID");
            //int sid1 = scan.nextInt();
            musicplay(listbysongs, sid);
        }catch (Exception ex){
            System.out.println("Invalid Input");
            System.out.println("Try Again");
        }
    }

    public  void genre() throws SQLException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        ArrayList<Songs> listbygenre=new ArrayList<>();
        try {
            Scanner scan = new Scanner(System.in);
            con = ConnectionClass.getConnection();
            st = con.createStatement();
            System.out.println("--------- Available Genre ---------");
            ResultSet rs = st.executeQuery("select distinct(genre) from songs");
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
            System.out.println();
            System.out.println("Enter the Genre Name");
            String gname = scan.next();
            System.out.println("--------- Available Songs with Genre ---------");
            ResultSet rs1 = st.executeQuery("select * from songs where genre='" + gname + "'");
            System.out.println("******************************************************");
            System.out.format(" %-10s %10s %20s \n", "Song ID", "Genre Name", "Song Name");
            System.out.println("******************************************************");
            while (rs1.next()) {
                System.out.format(" %-10s %10s %20s \n", rs1.getInt(1), rs1.getString(5), rs1.getString(2));
                listbygenre.add(new Songs(rs1.getInt(1), rs1.getString(2), rs1.getString(3), rs1.getString(4), rs1.getString(5), rs1.getString(6), rs1.getString(7)));
            }
            System.out.println();
            System.out.println("Enter the Song ID");
            int sid = scan.nextInt();
            musicplay(listbygenre, sid);
        }catch (Exception ex){
            System.out.println("Invalid Input");
            System.out.println("Try Again");
        }
    }

    public void album() throws SQLException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        ArrayList<Songs> listbyalbum=new ArrayList<>();
        try {
            Scanner scan = new Scanner(System.in);
            con = ConnectionClass.getConnection();
            st = con.createStatement();
            System.out.println("--------- Available Albums ---------");
            ResultSet rs = st.executeQuery("select distinct(albumname) from songs");
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
            System.out.println();
            System.out.println("Enter the Album Name");
            String abname = scan.next();
            System.out.println("--------- Available Songs in Albums ---------");
            ResultSet rs1 = st.executeQuery("select * from songs where albumname='" + abname + "'");
            System.out.println("******************************************************");
            System.out.format(" %-10s %10s %20s \n", "Song ID", "Album Name", "Song Name");
            System.out.println("******************************************************");
            while (rs1.next()) {
                System.out.format(" %-10s %10s %20s \n", rs1.getInt(1), rs1.getString(4), rs1.getString(2));
                listbyalbum.add(new Songs(rs1.getInt(1), rs1.getString(2), rs1.getString(3), rs1.getString(4), rs1.getString(5), rs1.getString(6), rs1.getString(7)));
            }
            System.out.println();
            System.out.println("Enter the Song ID");
            int sid = scan.nextInt();
            musicplay(listbyalbum, sid);
        }catch (Exception ex){
            System.out.println("Invalid Input");
            System.out.println("Try Again");
        }
    }

    public void artist() throws SQLException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        ArrayList<Songs> listbyartist=new ArrayList<>();
        try {
            Scanner scan = new Scanner(System.in);
            con = ConnectionClass.getConnection();
            st = con.createStatement();
            System.out.println("--------- Available Artists ---------");
            ResultSet rs = st.executeQuery("select distinct(artistname) from songs");
            while (rs.next()) {
                System.out.println(rs.getString(1));
                //result=rs.getString(1);
            }
            System.out.println("Enter the Artist Name");
            String aname = scan.next();
            System.out.println("--------- Available Songs of Artist ---------");
            ResultSet rs1 = st.executeQuery("select * from songs where artistname='" + aname + "'");
            System.out.println("******************************************************");
            System.out.format(" %-10s %10s %20s \n", "Song ID", "Artist Name", "Song Name");
            System.out.println("******************************************************");
            while (rs1.next()) {
                    System.out.format(" %-10s %10s %20s \n", rs1.getInt(1), rs1.getString(3), rs1.getString(2));
                    listbyartist.add(new Songs(rs1.getInt(1), rs1.getString(2), rs1.getString(3), rs1.getString(4), rs1.getString(5), rs1.getString(6), rs1.getString(7)));
            }
            System.out.println();
            System.out.println("Enter the Song ID");
            int sid = scan.nextInt();
            musicplay(listbyartist, sid);
        }catch(Exception ex){
            System.out.println("Invalid Input");
            System.out.println("Try Again");
        }
    }

    public void musicplay(ArrayList<Songs> list, int songid) throws SQLException, UnsupportedAudioFileException, IOException, LineUnavailableException {
        try {
            Iterator<Songs> it = list.iterator();
            String songpath = "";
            while (it.hasNext()) {
                Songs s = it.next();
                if (s.getSongid() == songid) {
                    songpath = s.getFilepath();
                }
            }
            Scanner scan = new Scanner(System.in);
            int i = -1;
            long time = 0l;
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(songpath).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            while (i != 0) {
                System.out.println("Enter  1.Play▶️   2.Pause⏸️   3.Resume♻️   4.Reset🔃   0.Quit");
                i = scan.nextInt();
                switch (i) {
                    case 1: {
                        clip.start();
                        break;
                    }
                    case 2: {
                        clip.stop();
                        time = clip.getMicrosecondPosition();
                        long total= clip.getMicrosecondLength()/1000000;
                        long micro=clip.getMicrosecondPosition()/1000000;
                        System.out.println("played in seconds : "+micro);
                        System.out.println("remaining time for this song : "+(total-micro)+" seconds");
                        System.out.println("Duration Of Song : "+(total)+" seconds");
                        break;
                    }
                    case 3: {
                        clip.setMicrosecondPosition(time);
                        clip.start();
                        break;
                    }
                    case 4: {
                        clip.setMicrosecondPosition(0);
                        break;
                    }
                    case 0: {
                        clip.close();
                        break;
                    }
                }
            }
        }
        catch (Exception ex){
            System.out.println("Invalid Input");
            System.out.println("Try Again");
        }
    }

    @Override
    public String toString() {
        return "Songs{" +
                "songid=" + songid +
                ", songname='" + songname + '\'' +
                ", artistname='" + artistname + '\'' +
                ", albumname='" + albumname + '\'' +
                ", genre='" + genre + '\'' +
                ", duration='" + duration + '\'' +
                ", filepath='" + filepath + '\'' +
                '}';
    }
}

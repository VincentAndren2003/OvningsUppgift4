import java.util.Objects;

public class Record implements Node{
    private final String name;
    private final String artist;

    public Record(String name, String artist){
        this.name = name;
        this.artist = artist;
    }

    @Override
    public String getName(){
        return name;
    }

    public String getArtist(){
        return artist;
    }
}

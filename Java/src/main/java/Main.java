import classes.RecordAudio;
import classes.SendPictureThread;

import javax.sound.sampled.LineUnavailableException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {
    private static final String PATH_TO_PROPERTIES = "src/main/resources/config.properties";

    public static void main(String[] args){
        try (FileInputStream inputStream = new FileInputStream(PATH_TO_PROPERTIES)){
            Properties properties = new Properties();
            properties.load(inputStream);

            String token = properties.getProperty("TOKEN");
            String path = properties.getProperty("PATH_TO_FILES");
            String glob = properties.getProperty("GLOB");

//            RecordAudio recordAudio = new RecordAudio();
//            recordAudio.saveAudioToLocal();

            SendPictureThread sendPictureThread = new SendPictureThread(token, path, glob);
            sendPictureThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int availableProcessors = Runtime.getRuntime().availableProcessors();
        int threadSetSize = Thread.activeCount();
        if(availableProcessors > threadSetSize){
            System.out.println(String.format("All right %d > %d", availableProcessors, threadSetSize));
        }
        else{
            System.err.println("Thread overload!");
        }
    }

}

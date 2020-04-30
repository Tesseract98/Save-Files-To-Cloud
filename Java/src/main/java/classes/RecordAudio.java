package classes;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class RecordAudio {

    private AudioFormat audioFormat;
    private AudioFileFormat.Type fType;
    private TargetDataLine line;

    public RecordAudio() {
        fType = AudioFileFormat.Type.WAVE;
        audioFormat = new AudioFormat(16000, 16, 2, true, true);
    }

    @SuppressWarnings("unused")
    public void saveAudioToLocal() throws LineUnavailableException {
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
        line = (TargetDataLine) AudioSystem.getLine(info);
        File audFile = new File(String.format("%d.wav", 2));
        start(audFile);
        end(10000);
    }

    private void start(File file) {
        new Thread(() ->{
            try {
                line.open(audioFormat);
                line.start();
                AudioInputStream stream = new AudioInputStream(line);
                AudioSystem.write(stream, fType, file);
            } catch (LineUnavailableException | IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void end(int delayTime) {
        new Thread(() -> {
            try {
                Thread.sleep(delayTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            line.start();
            line.close();
        }).start();
    }

}

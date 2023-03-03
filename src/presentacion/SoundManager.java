package presentacion;


import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class SoundManager {

    public static Clip getSound(String file)
    {
        try
        {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("nivel1" + System.getProperty("nivel1.wav") + file).getAbsoluteFile());
            AudioFormat format = audioInputStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip sound = (Clip)AudioSystem.getLine(info);
            sound.open(audioInputStream);
            return sound;
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public static void playSound(Clip clip)
    {
        clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }

    public static void main(String[] args)
    {
        Clip sound = getSound("nivel1.wav");
        playSound(sound);
    }

}

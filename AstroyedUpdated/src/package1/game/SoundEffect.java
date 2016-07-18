package package1.game;
import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;


public enum SoundEffect {

    SHOT("sounds/shot2.wav"),       // bullet
    THRUST("sounds/thrust.wav"),   // thrusters
    BREAK("sounds/break.wav"),     // asteroid crumble
    BONK("sounds/bonk.wav"),       // ship death
    BLING("sounds/bling.wav"),     // collected shiny
    BACKGROUND("sounds/back.wav"),     // background music
    UH("sounds/uh.wav"),           // reverse
    COMBO("sounds/combo.wav");
    // Nested class for specifying volume
    public static enum Volume {
        MUTE, LOW, MEDIUM, HIGH
    }

    public static Volume volume = Volume.MEDIUM;

    private Clip clip;

    SoundEffect(String soundFileName) {
        try {
            File soundFile = new File(soundFileName);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            // Get a clip resource.
            clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (volume != Volume.MUTE) {
            if (clip.isRunning())
                clip.stop();
            clip.setFramePosition(0); // rewind to the beginning
            clip.start();     // Start playing
         }
    }
    public void loop(){
        clip.loop(200);

    }
    public void stop(){
        clip.setFramePosition(0);
        clip.stop();
    }
    // Optional static method to pre-load all the sound files.
    static void init() {
        values(); // calls the constructor for all the elements
    }
}

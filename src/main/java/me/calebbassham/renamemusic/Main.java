package me.calebbassham.renamemusic;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagException;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        var dir = new File(System.getProperty("user.home") + "/Music");
        if (!dir.exists()) return;

        for (var file : dir.listFiles()) {
            if (file.isDirectory()) continue;
            AudioFile audioFile = null;
            try {
                audioFile = AudioFileIO.read(file);
            } catch (CannotReadException | IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException e) {
                e.printStackTrace();
            }
            if (audioFile == null) continue;
            var tag = audioFile.getTag();
            var title = tag.getFirst(FieldKey.TITLE);
            var artist = tag.getFirstField(FieldKey.ARTIST);

            file.renameTo(new File(file.getParent() + "/" + title + " - " + artist + getExtension(file)));
            System.out.print(".");
        }
    }

    private static String getExtension(File file) {
        var fileName = file.getName();

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            return fileName.substring(i);
        }

        return "";
    }
}

package mediaplayer;

import java.io.File;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


/**
 * Play BGM 
 *
 */
public class BGM {

	private static MediaPlayer mediaPlayer = null;

	private static Media pokemonCapture = null;
	private static Media pokemonEscape = null;
	private static Media battle = null;

	public BGM() {
		
	}

	public static void loadSounds() {

		try {
			File file1 = new File("songfiles/capture.mp3");
			if(! file1.exists()) throw new RuntimeException("MP3 file not exist"); 
			BGM.pokemonCapture = new Media(file1.toURI().toString());

			File file2 = new File("songfiles/pokemonRunAway.mp3");
			if(! file2.exists()) throw new RuntimeException("MP3 file not exist"); 
			BGM.pokemonEscape = new Media(file2.toURI().toString());

			File file3 = new File("songfiles/battleBGM.mp3");
			if(! file3.exists()) throw new RuntimeException("MP3 file not exist"); 
			BGM.battle = new Media(file3.toURI().toString());
		} catch(Exception e) {
			System.err.println("Fail to load mp3 file");
			System.exit(-1);
		}
	}

	public static void playPokemonCapturedSound() {

		playSong(pokemonCapture);
	}

	public static void playPokemonEscapedSound() {

		playSong(pokemonEscape);
	}

	public static void playBattleSound() {

		playSong(battle);
	}

	private static void playSong(Media media) {

		BGM.mediaPlayer = new MediaPlayer(media);
		BGM.mediaPlayer.setAutoPlay(true);
		BGM.mediaPlayer.setVolume(0.5);
		//BGM.mediaPlayer.setStopTime(new Duration(10 * 1000));
		BGM.mediaPlayer.play();

		BGM.mediaPlayer.setOnEndOfMedia(new EndOfSongHandler());
	}

	private static class EndOfSongHandler implements Runnable {

		@Override
		public void run() {
			System.out.println("BGM: finished playing");
		}
	}
}

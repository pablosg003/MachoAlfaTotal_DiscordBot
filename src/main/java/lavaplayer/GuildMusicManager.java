package lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

public class GuildMusicManager {

	private final AudioPlayer audioPlayer;
	private final TrackScheduler scheduler;
	private final AudioPlayerSendHandler sendHandler;
	
	public GuildMusicManager(AudioPlayerManager manager) {
		this.audioPlayer = manager.createPlayer();
		this.scheduler = new TrackScheduler(this.audioPlayer);
		this.audioPlayer.addListener(this.getScheduler());
		this.sendHandler = new AudioPlayerSendHandler(this.audioPlayer);
	}
	
	public AudioPlayerSendHandler getSendHandler() {
		return sendHandler;
	}

	public TrackScheduler getScheduler() {
		return scheduler;
	}
	
}

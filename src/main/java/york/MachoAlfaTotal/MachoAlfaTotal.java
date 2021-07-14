package york.MachoAlfaTotal;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Activity.ActivityType;

public class MachoAlfaTotal {
	
	public static final String GENERAL_TXT = "864487370182492200";
	public static final String GENERAL_VOZ = "864141288420016162";
	
	public static final String prefijo ="/";
	public static boolean usarPrefijo = false;
	public static boolean gritosAleatorios = true;
	public static int frecuenciaGritos = 100;
	
	public static void main(String[] args) throws LoginException{
		
		JDA jda = JDABuilder.createDefault("ODY0MTQyODY3NDY3MjA2NjY2.YOxJwg.HcnH181sq4iZvD8Ren4YGK0_Mhs").build();
		
		jda.getPresence().setStatus(OnlineStatus.ONLINE);
		jda.getPresence().setActivity(Activity.of(ActivityType.WATCHING, "Macho alfa total", "https://youtu.be/6peMik4tpLA"));
		
		jda.addEventListener(new Comandos());
		
	}
}

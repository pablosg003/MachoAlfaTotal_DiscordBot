package york.MachoAlfaTotal;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Activity.ActivityType;

public class MachoAlfaTotal {
	
	public static final String prefijo ="/";
	public static boolean usarPrefijo = false;
	public static boolean gritosAleatorios = true;
	public static int frecuenciaGritos = 100;
	
	public static void main(String[] args) throws LoginException{
		
		JDA jda = JDABuilder.createDefault("your token here").build();
		
		jda.getPresence().setStatus(OnlineStatus.ONLINE);
		jda.getPresence().setActivity(Activity.of(ActivityType.WATCHING, "Macho alfa total", "https://youtu.be/6peMik4tpLA"));
		
		jda.addEventListener(new Comandos());
		
	}
}

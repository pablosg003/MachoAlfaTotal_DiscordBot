package york.MachoAlfaTotal;

import java.awt.Color;
import java.util.Random;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import lavaplayer.AudioPlayerSendHandler;
import lavaplayer.TrackScheduler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

public class Comandos extends ListenerAdapter {
	
	private Random rand = new Random();
	
	private Guild guild;
	private VoiceChannel channel;
	private AudioManager manager;
	private AudioPlayerManager playerManager;
	private AudioPlayer audioPlayer;
	
	public void onMessageReceived(MessageReceivedEvent e) {
		
		// Si el bot ha escrito el mensaje no mira nada más
		if (e.getAuthor().isBot()) return;
		
		/*Enviar un embed
		EmbedBuilder inicio = new EmbedBuilder();
		inicio.setAuthor("York", null, "https://images-ext-2.discordapp.net/external/EFT70mzcB-qrPj_-0QaGGU5bexqKrhZ3SBjpwKIkgbY/https/pbs.twimg.com/profile_images/1148341590110420992/uujMzbD7_400x400.jpg");
		inicio.setTitle("Lista de bots empleados en el server:");
		inicio.addField("Macho Alfa Total", "Es el macho alfa, puedes usar el comando /info para ver más.", false);
		inicio.addField("Rythm", "Sirve para poner música, conectándote a un canal de voz y luego poniendo !play y el título de la canción.", false);
		inicio.addField("Rythm2", "Lo uso para una parte del bot de Macho Alfa Total, no se puede usar.", false);
		inicio.addField("Zira", "Gestiona los roles y los asigna automáticamente en el canal #roles-generales", false);
		inicio.setFooter("Si algún bot te da problemas avísame que lo intentarÃ© arreglar.");
		
		e.getChannel().sendMessageEmbeds(inicio.build()).queue();*/

		
		String mensajeCompleto = e.getMessage().getContentRaw();
		
		mensajeCompleto.toLowerCase();
		mensajeCompleto.replace('á', 'a');
		mensajeCompleto.replace('é', 'e');
		mensajeCompleto.replace('í', 'i');
		mensajeCompleto.replace('ó', 'o');
		mensajeCompleto.replace('ú', 'u');
		
		String[] mensaje = mensajeCompleto.split(" ");
		
		if(MachoAlfaTotal.gritosAleatorios) {
			if((rand.nextInt(MachoAlfaTotal.frecuenciaGritos) + 1)==1) {
				e.getChannel().sendMessage("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHHHH").queue();
			}
		}
		
		if (!MachoAlfaTotal.usarPrefijo) {//Si está desactivado el prefijo se lo pone
			if(mensaje[0].charAt(0)!=MachoAlfaTotal.prefijo.charAt(0)) {
				mensaje[0] = MachoAlfaTotal.prefijo + mensaje[0];
				mensajeCompleto = MachoAlfaTotal.prefijo + mensajeCompleto;
				
			}
		}
				
		if(mensajeCompleto.equalsIgnoreCase(MachoAlfaTotal.prefijo+"prefijo")) {//Activar o desactivar el prefijo
			
			if (MachoAlfaTotal.usarPrefijo) {
				
				MachoAlfaTotal.usarPrefijo=false;
				e.getChannel().sendTyping().queue();
				e.getChannel().sendMessage("Ya no hace falta poner el prefijo \" " + MachoAlfaTotal.prefijo + "\" para que responda").queue();
			} else {
				
				MachoAlfaTotal.usarPrefijo=true;
				e.getChannel().sendTyping().queue();
				e.getChannel().sendMessage("Ahora hace falta poner el prefijo \" " + MachoAlfaTotal.prefijo + "\" para que responda").queue();
			}
			return;
		}
		
		else if (mensajeCompleto.equalsIgnoreCase(MachoAlfaTotal.prefijo + "info")) {//Muestra la información en una tarjetita Embed
			
			String estaActivo;
			if (MachoAlfaTotal.usarPrefijo) {
				estaActivo = " Ahora mismo, hace falta usar el prefijo.";
			} else {
				estaActivo = " Ahora mismo, no hace falta usar el prefijo.";
			}
			
			String gritosActivos;
			if (MachoAlfaTotal.gritosAleatorios) {
				gritosActivos = " Ahora mismo, los gritos están activados.";
			} else {
				gritosActivos = " Ahora mismo, los gritos están desactivados.";
			}
			
			EmbedBuilder informacion = new EmbedBuilder();
			informacion.setTitle("Información del bot");
			informacion.setDescription("Este bot está diseñado para completar las frases de la obra magna \"Macho alfa total\", de Míster Jägger. Solo tienes que escribir el inicio de la frase, con el prefijo si está activado, y el bot la completará." + estaActivo + "\n Nota: si pones símbolos de puntuación, tienen que estar separados por un espacio de cualquier caracter.");
			informacion.addField("Comandos:", MachoAlfaTotal.prefijo+"prefijo: Cambia si es necesario o no usar un prefijo para las frases y comandos. Recomiendo activarlo si estás hasta los cojones del bot.\n"+MachoAlfaTotal.prefijo+" info: Muestra en pantalla esta tarjeta de información.\n"+MachoAlfaTotal.prefijo+"gritos: Activa o desactiva los gritos aleatorios. "+gritosActivos+"\n"+
			MachoAlfaTotal.prefijo+"frecuenciaGritos n: cambia la frecuencia de los gritos a 1 de cada n. Ahora mismo, n = " + MachoAlfaTotal.frecuenciaGritos + ".\n"+MachoAlfaTotal.prefijo+"machoAlfa: Activa la reproducción de Macho Alfa Total en el canal de voz.\n"+MachoAlfaTotal.prefijo+"machoBeta: Desactiva la reproducción de Macho Alfa Total en el canal de voz.", false);
			informacion.setImage("https://i.ytimg.com/vi/6peMik4tpLA/maxresdefault.jpg");
			informacion.setColor(Color.magenta);
			
			e.getChannel().sendMessageEmbeds(informacion.build()).queue();
			informacion.clear();
			
			return;
			
		} else if(mensajeCompleto.equalsIgnoreCase(MachoAlfaTotal.prefijo + "gritos")) {
			
			if (MachoAlfaTotal.gritosAleatorios) {
				
				MachoAlfaTotal.gritosAleatorios = false;
				e.getChannel().sendMessage("Los gritos aleatorios han sido desactivados.").queue();;
			} else {
				
				MachoAlfaTotal.gritosAleatorios = true;
				e.getChannel().sendMessage("Los gritos aleatorios han sido activados.").queue();;
			}
			return;
			
		} else if(mensaje[0].equalsIgnoreCase(MachoAlfaTotal.prefijo + "frecuenciaGritos")) {
			
			if (mensaje.length == 2) {
				try {
					int frec = Integer.parseInt(mensaje[1]);
					if (frec > 0) {
						MachoAlfaTotal.frecuenciaGritos = frec;
						e.getChannel().sendMessage("A partir de ahora, los gritos saldrán en 1 de cada " + frec + " mensajes.").queue();
					} else {
						e.getChannel().sendMessage("Tienes que poner un número entero positivo.").queue();
					}
				} catch (NumberFormatException e1) {
					e.getChannel().sendMessage("Tienes que poner un número entero positivo.").queue();
				}
			} else {
				e.getChannel().sendMessage("Tienes que poner dos palabras: el comando /frecuenciaGritos y n, la frecuencia.").queue();
			}
			return;
			
		} else if (mensajeCompleto.equals(MachoAlfaTotal.prefijo+"ª")) {
			e.getChannel().sendMessage("ª").queue();
			return;
			
		} else if(mensajeCompleto.equalsIgnoreCase(MachoAlfaTotal.prefijo + "machoAlfa")) {
			
			guild = e.getGuild();
			manager = guild.getAudioManager();
			playerManager = new DefaultAudioPlayerManager();
			audioPlayer= playerManager.createPlayer();
			channel = e.getMember().getVoiceState().getChannel();
			
			TrackScheduler trackScheduler = new TrackScheduler(audioPlayer);
			audioPlayer.addListener(trackScheduler);
	
			AudioSourceManagers.registerRemoteSources(playerManager);
			manager.setSendingHandler(new AudioPlayerSendHandler(audioPlayer));
			manager.openAudioConnection(channel);
			
			String trackUrl = "https://youtu.be/6peMik4tpLA";
			
			playerManager.loadItem(trackUrl, new AudioLoadResultHandler() {
				@Override
				public void trackLoaded(AudioTrack track) {
					trackScheduler.queue(track);
				}
				@Override
				public void playlistLoaded(AudioPlaylist playlist) {
					
				}
				@Override
				public void noMatches() {
					
				}
				@Override
				public void loadFailed(FriendlyException exception) {
					
				}
			});
			
			
			
			
		} else if (mensajeCompleto.equalsIgnoreCase(MachoAlfaTotal.prefijo + "machoBeta")) {
			
			manager.closeAudioConnection();
			
		} else {
					
		//Declaración de las frases de macho alfa total
		final String[] frase0 = (MachoAlfaTotal.prefijo+"QUE CALLATE").split(" ");
		final String[] frase1 = (MachoAlfaTotal.prefijo+"ESTE ES EL CUERPO QUE CABALGO").split(" ");
		final String[] frase2 = (MachoAlfaTotal.prefijo+"SI ULTIMAMENTE TE SIENTES APOCADO , AMILANADO O DOMESTICADO ES PORQUE TE FALTA LA ENERGIA DE UN MACHO ALFA").split(" ");
		final String[] frase3 = (MachoAlfaTotal.prefijo+"UN AUTENTICO MACHO ALFA SOLO DESCANSA CUANDO MUERE . MIRAD AL SOL . INVICTO").split(" ");
		final String[] frase4 = (MachoAlfaTotal.prefijo+"MIRAD AL SOL INVICTO").split(" ");
		final String[] frase5 = (MachoAlfaTotal.prefijo+"TU TE COMES EL FUET CON PIEL YO ME COMO EL FUET CON PLASTICO").split(" ");
		final String[] frase6 = (MachoAlfaTotal.prefijo+"HA LLEGADO EL AFILADOR . AFILAMOS CUCHILLOS , TIJERAS").split(" ");
		final String[] frase7 = (MachoAlfaTotal.prefijo+"LA MUSICA URBANA TIENE LA ENERGIA DE UN GUSANO DEPRIMIDO . LOS MACHOS ESCUCHAMOS LA AUTENTICA ENERGIA . ENFASIS , ESO ES LO IMPORTANTE").split(" ");
		final String[] frase8 = (MachoAlfaTotal.prefijo+"ENFASIS , ESO ES LO IMPORTANTE").split(" ");
		final String[] frase9 = (MachoAlfaTotal.prefijo+"LA MASCULINIDAD TE PARECE FRAGIL ? A MI ME PARECE MAS FUERTE QUE EL HORMIGON ARMADO").split(" ");
		final String[] frase9shiny = (MachoAlfaTotal.prefijo+"LA MASCULINIDAD TE PARECE FRAGIL ? PUES UN POCO , UN POCO FRAGIL SI QUE ES PORQUE NO SE HA ROTO").split(" ");
		final String[] frase10 = (MachoAlfaTotal.prefijo+"AGARRA UNA MUJER , AGARRALA Y LANZALA").split(" ");
		final String[] frase11 = (MachoAlfaTotal.prefijo+"YO ESTOY RODEADO DE BECERROS").split(" ");
		final String[] frase12 = (MachoAlfaTotal.prefijo+"POR QUE EL EFECTO CAUSA EFECTO CAUSA EFECTO ? PORQUE LA CAUSA DEL EFECTO CAUSA EFECTO ES A SU VEZ CAUSA Y EFECTO . A VOLAR").split(" ");
		final String[] frase13 = (MachoAlfaTotal.prefijo+"UN VERDADERO MACHO ALFA NO LE TEME AL AMOR . SE PROPULSA CON EL").split(" ");
		final String[] frase14 = (MachoAlfaTotal.prefijo+"ME ESTRELLARE . POR SUPUESTO QUE ME ESTRELLARE . PORQUE MI MANERA DE VOLAR ES QUITANDOME LAS ALAS").split(" ");
		final String[] frase15 = (MachoAlfaTotal.prefijo+"COMO DIJO SOCRATES NO ES MOMENTO PARA PROVERBIOS").split(" ");
		final String[] frase16 = (MachoAlfaTotal.prefijo+"ADONDE HA IDO ? NO SE SABE . COMO HA LLEGADO ? NO SE QUIERE SABER").split(" ");
		final String[] frase17 = (MachoAlfaTotal.prefijo+"AY PERO A MI A ESTE RESPECTO ME GUSTARIA CONOCER AL RESPONSABLE DE EL CAUSANTE DE ESTE CRATER").split(" ");
		final String[] frase18 = (MachoAlfaTotal.prefijo+"AY QUE BIEN QUE BIEN DE CONOCERLE COMPAÃ‘ERO LE VOY A HACER REGALO DE ESTE OBSEQUIO PARA QUE CONTINUE LA DESTRUCCION MASIVA").split(" ");
		final String[] frase19 = (MachoAlfaTotal.prefijo+"ESTE BOT ES COMO EL SOL . YO NO VOY A PARAR NUNCA . TIENES QUE SER TU EL QUE ME VENZA . BUENO LUEGO SI QUE VOY A PARAR . PERO DENTRO DE BASTANTE TIEMPO").split(" ");
		final String[] frase20 = (MachoAlfaTotal.prefijo+"BUCLE BUCLE BUCLE VIRIL").split(" ");
		final String[] frase21 = (MachoAlfaTotal.prefijo+"EL MACHO ALFA PUEDE COMPRIMIR LATAS DE MEDIO LITRO HASTA DEJARLAS DE UNA MANERA QUE PUEDE PRESERVAR EL MEDIO AMBIENTE DEBIDO AL POCO ESPACIO QUE OCUPA . QUE OCUPA LA LATA , NO EL MACHO . EL MACHO VA A OCUPAR EL ESPACIO DE UN HUMANO MEDIO , PERO TIENE MUCHA MAS POTENCIA").split(" ");


		//Declaración de las variables que dirán si es el inicio de una frase
		boolean esFrase0 = true;
		boolean esFrase1 = true;
		boolean esFrase2 = true;
		boolean esFrase3 = true;
		boolean esFrase4 = true;
		boolean esFrase5 = true;
		boolean esFrase6 = true;
		boolean esFrase7 = true;
		boolean esFrase8 = true;
		boolean esFrase9 = true;
		boolean esFrase10 = true;
		boolean esFrase11 = true;
		boolean esFrase12 = true;
		boolean esFrase13 = true;
		boolean esFrase14 = true;
		boolean esFrase15 = true;
		boolean esFrase16 = true;
		boolean esFrase17 = true;
		boolean esFrase18 = true;
		boolean esFrase19 = true;
		boolean esFrase20 = true;
		boolean esFrase21 = true;


		
		//Comprobación de las frases de Macho Alfa Total
		for (int i=0; i<mensaje.length;i++) {
			if (i<=frase0.length-1){
				
				if (!mensaje[i].equalsIgnoreCase(frase0[i])) {
								esFrase0=false;
							}
				}
			if (i<frase1.length){
				if (!mensaje[i].equalsIgnoreCase(frase1[i])) {
								esFrase1=false;
							}
				}
			if (i<frase2.length){
				if (!mensaje[i].equalsIgnoreCase(frase2[i])) {
								esFrase2=false;
							}
				}
			if (i<frase3.length){
				if (!mensaje[i].equalsIgnoreCase(frase3[i])) {
								esFrase3=false;
							}
				}
			if (i<frase4.length){
				if (!mensaje[i].equalsIgnoreCase(frase4[i])) {
								esFrase4=false;
							}
				}
			if (i<frase5.length){
				if (!mensaje[i].equalsIgnoreCase(frase5[i])) {
								esFrase5=false;
							}
				}
			if (i<frase6.length){
				if (!mensaje[i].equalsIgnoreCase(frase6[i])) {
								esFrase6=false;
							}
				}
			if (i<frase7.length){
				if (!mensaje[i].equalsIgnoreCase(frase7[i])) {
								esFrase7=false;
							}
				}
			if (i<frase8.length){
				if (!mensaje[i].equalsIgnoreCase(frase8[i])) {
								esFrase8=false;
							}
				}
			if (i<frase9.length){
				if (!mensaje[i].equalsIgnoreCase(frase9[i])) {
								esFrase9=false;
							}
				}
			if (i<frase10.length){
				if (!mensaje[i].equalsIgnoreCase(frase10[i])) {
								esFrase10=false;
							}
				}
			if (i<frase11.length){
				if (!mensaje[i].equalsIgnoreCase(frase11[i])) {
								esFrase11=false;
							}
				}
			if (i<frase12.length){
				if (!mensaje[i].equalsIgnoreCase(frase12[i])) {
								esFrase12=false;
							}
				}
			if (i<frase13.length){
				if (!mensaje[i].equalsIgnoreCase(frase13[i])) {
								esFrase13=false;
							}
				}
			if (i<frase14.length){
				if (!mensaje[i].equalsIgnoreCase(frase14[i])) {
								esFrase14=false;
							}
				}
			if (i<frase15.length){
				if (!mensaje[i].equalsIgnoreCase(frase15[i])) {
								esFrase15=false;
							}
				}
			if (i<frase16.length){
				if (!mensaje[i].equalsIgnoreCase(frase16[i])) {
								esFrase16=false;
							}
				}
			if (i<frase17.length){
				if (!mensaje[i].equalsIgnoreCase(frase17[i])) {
								esFrase17=false;
							}
				}
			if (i<frase18.length){
				if (!mensaje[i].equalsIgnoreCase(frase18[i])) {
								esFrase18=false;
							}
				}
			if (i<frase19.length){
				if (!mensaje[i].equalsIgnoreCase(frase19[i])) {
								esFrase19=false;
							}
				}
			if (i<frase20.length){
				if (!mensaje[i].equalsIgnoreCase(frase20[i])) {
								esFrase20=false;
							}
				}
			if (i<frase21.length){
				if (!mensaje[i].equalsIgnoreCase(frase21[i])) {
								esFrase21=false;
							}
				}

		}
		//Si es la frase la guarda en la variable
		
		String[] respuesta = null;
		if(esFrase0) {
			respuesta = frase0;
		}
		else if(esFrase1) {
			respuesta = frase1;
		}
		else if(esFrase2) {
			respuesta = frase2;
		}
		else if(esFrase3) {
			respuesta = frase3;
		}
		else if(esFrase4) {
			respuesta = frase4;
		}
		else if(esFrase5) {
			respuesta = frase5;
		}
		else if(esFrase6) {
			respuesta = frase6;
		}
		else if(esFrase9) {
			if ((rand.nextInt(10) + 1)==1) {
				respuesta = frase9shiny;
			} else {
				respuesta = frase9;
			}
		}
		else if(esFrase8) {
			respuesta = frase8;
		}
		else if(esFrase7) {
			respuesta = frase7;
		}
		else if(esFrase10) {
			respuesta = frase10;
		}
		else if(esFrase11) {
			respuesta = frase11;
		}
		else if(esFrase12) {
			respuesta = frase12;
		}
		else if(esFrase13) {
			respuesta = frase13;
		}
		else if(esFrase14) {
			respuesta = frase14;
		}
		else if(esFrase15) {
			respuesta = frase15;
		}
		else if(esFrase16) {
			respuesta = frase16;
		}
		else if(esFrase17) {
			respuesta = frase17;
		}
		else if(esFrase18) {
			respuesta = frase18;
		} else if(esFrase19) {
			respuesta = frase19;
		} else if(esFrase20) {
			respuesta = frase20;
		} else if(esFrase21) {
			respuesta = frase21;
		}

			


		String respuestaDef = "";
		if (respuesta!=null) {
			for (int i=mensaje.length; i<=respuesta.length-1; i++) {
				respuestaDef=respuestaDef.concat(respuesta[i] + " ");
			
			}
		}
		if (!respuestaDef.isEmpty()) {
			e.getChannel().sendMessage(respuestaDef).queue();
			return;
		}
	}
		
}
}

package facebook;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;

@RunWith(JUnitParamsRunner.class)
public class FacebookJUnitEjercicio extends FacebookBaseTestEjercicio{
	
	@Test
//	@FileParameters("./data/params.csv")
//	public void testFacebookEnviarSolicitud(String browser, String url,String user, String password, String friendName) {
	public void testFacebookEnviarSolicitud() {
		setUp("chrome", "facebook.com");
		iniciarSesion("richard_bbmwwqd_perez@tfbnw.net", "123456nat");
		buscarPersona("Richard Perez");
		validarPersonaExista("Richard Perez","Ricky");
		enviarSolicitud("Richard Perez","Ricky");		
	}
	
	@Test
//	@FileParameters("./data/params.csv")
//	public void testFacebookEnviarSolicitud(String browser, String url,String user, String password, String friendName) {
	public void testFacebookAbrirChatConAmigo() {	
		setUp("chrome", "facebook.com");
		iniciarSesion("richard_bbmwwqd_perez@tfbnw.net", "123456nat");
		abrirListadoMensajes();
		abrirChatAmigo("Richard Alba");
	}
}

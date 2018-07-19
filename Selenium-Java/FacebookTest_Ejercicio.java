package classExercise;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FacebookTest_Ejercicio {
	static WebDriver driver;
	static WebDriverWait waitShort,waitMedium,waitLong;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//configurar el navegador
		setUp("chrome", "facebook.com");
		
		//iniciar sesion
		iniciarSesion("richard_bbmwwqd_perez@tfbnw.net", "123456nat");
		
		//buscar a la persona
		buscarPersona("Richard Perez");
		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		//Validar que exista la persona que busque 
		validarPersonaExista("Richard Perez","Ricky");
		//agregarla
		enviarSolicitud("Richard Perez","Ricky");
		
		abrirListadoMensajes();
		
		abrirChatAmigo("Richard Alba");
		

	}

	private static void setUp(String browser, String url) {
		// TODO Auto-generated method stub
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		System.setProperty("webdriver.chrome.driver", "C:\\test_automation\\drivers\\chromedriver.exe"); //propiedad explorador chrome		
		driver = new ChromeDriver(options);
		driver.navigate().to("http:\\www."+url);//navegar a la pagina
		waitShort = new WebDriverWait(driver, 5);
		waitMedium = new WebDriverWait(driver, 10);
		waitLong = new WebDriverWait(driver, 15);
	}

	private static void iniciarSesion(String user, String pass) {
		// TODO Auto-generated method stub
		WebElement userBox = driver.findElement(By.id("email"));
		userBox.clear();
		userBox.sendKeys(user);
		
		WebElement passBox = driver.findElement(By.id("pass"));
		passBox.clear();
		passBox.sendKeys(pass);
		
		WebElement buttonIniciar = driver.findElement(By.id("loginbutton"));
		buttonIniciar.click();
		
	}

	private static void buscarPersona(String nombreAmigo) {
		// TODO Auto-generated method stub
		waitShort.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@data-testid='search_input']")));
		WebElement searchBox = driver.findElement(By.xpath("//input[@data-testid='search_input']"));
		searchBox.clear();
		searchBox.sendKeys(nombreAmigo);
		WebElement buttonSearch = driver.findElement(By.xpath("//button[@data-testid='facebar_search_button']"));
		buttonSearch.submit();
		
	}

	private static void validarPersonaExista(String nombreAmigo, String apodo) {
		List<WebElement> listaElementos = driver.findElements(By.xpath("//*[contains(@class,'_2yer')]"));
		
		for(WebElement elemento: listaElementos) {
			if(elemento.getText().contains(nombreAmigo+" ("+apodo+")")){
				System.out.println("SÍ existe la persona "+ nombreAmigo);
				break;
			}
		}		
	}
	
	private static void enviarSolicitud(String nombreAmigo, String apodo) {
		List<WebElement> listaElementos = driver.findElements(By.xpath("//*[contains(@class,'_2yer')]"));
		
		for(WebElement elemento: listaElementos) {
			if(elemento.getText().contains(nombreAmigo+" ("+apodo+")")){
				try {
					WebElement botonAgregar = elemento.findElement(By.xpath("//button[@aria-label='Agregar a amigos']"));
					botonAgregar.click();
					waitShort.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[2][text()='Solicitud de amistad enviada']")));
					if(elemento.findElement(By.xpath("//button[2]")).getText().contains("Solicitud de amistad enviada"))
						System.out.println("Enviaste una solicitud de amistad a "+ nombreAmigo+"("+apodo+")");
				}
				catch(Exception e) {
					System.out.println("La solicitud ya fue enviada a "+ nombreAmigo);
				}
				break;
			}
		}		
	}
	
	private static void abrirListadoMensajes() {
		waitShort.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Mensajes')]")));
		WebElement botonMensajes = driver.findElement(By.name("mercurymessages"));
		botonMensajes.click();
		
		waitShort.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='messagesContent']")));
		
	}
	
	private static void abrirChatAmigo(String nombreAmigo) {
		List<WebElement> listaElementos = driver.findElements(By.xpath("//*[contains(@class,'messagesContent')]"));
		
		for(WebElement elemento: listaElementos) {
			if(elemento.getText().contains(nombreAmigo)){
				WebElement botonAbrirChat= elemento;
				botonAbrirChat.click();
				try {
					waitShort.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Inicio de la conversación')]")));
					System.out.println("La ventana para escribir mensaje con "+nombreAmigo+" está abierta");
				}
				catch(Exception e) {
					System.out.println("La ventana para escribir mensaje con "+nombreAmigo+" NO está abierta");
				}
			}
		}
	}

	


}

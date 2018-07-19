package facebook;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FacebookBaseTestEjercicio {
	
	protected WebDriver driver;
	protected WebDriverWait waitShort,waitMedium,waitLong;
	
	protected void setUp(String browser, String url) {
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

	protected void iniciarSesion(String user, String pass) {
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

	protected void buscarPersona(String nombreAmigo) {
		// TODO Auto-generated method stub
		waitShort.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@data-testid='search_input']")));
		WebElement searchBox = driver.findElement(By.xpath("//input[@data-testid='search_input']"));
		searchBox.clear();
		searchBox.sendKeys(nombreAmigo);
		WebElement buttonSearch = driver.findElement(By.xpath("//button[@data-testid='facebar_search_button']"));
		buttonSearch.submit();
		
	}

	protected void validarPersonaExista(String nombreAmigo, String apodo) {
		List<WebElement> listaElementos = driver.findElements(By.xpath("//*[contains(@class,'_2yer')]"));
		
		for(WebElement elemento: listaElementos) {
			if(elemento.getText().contains(nombreAmigo+" ("+apodo+")")){
				System.out.println("SÍ existe la persona "+ nombreAmigo);
				break;
			}
		}		
	}
	
	protected void enviarSolicitud(String nombreAmigo, String apodo) {
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
	
	protected void abrirListadoMensajes() {
		waitShort.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Mensajes')]")));
		WebElement botonMensajes = driver.findElement(By.name("mercurymessages"));
		botonMensajes.click();
		
		waitShort.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='messagesContent']")));
		
	}
	
	protected void abrirChatAmigo(String nombreAmigo) {
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

	@After
	public void tearDown() {
		System.out.println("Destruye la configuracion");
		driver.quit();
	}


}

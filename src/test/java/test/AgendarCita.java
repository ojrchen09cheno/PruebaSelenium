package test;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class AgendarCita {

    private WebDriver driver;

    // Manager de la version de los drivers
    @BeforeClass
    public static void setupWebdriverChromeDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void setup() {
        driver = new ChromeDriver();
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void agendarCita() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        // Ingresar a la pagina
        driver.get("https://katalon-demo-cura.herokuapp.com/");

        // Ingresar a la cuenta
        driver.findElement(By.id("btn-make-appointment")).click();
        driver.findElement(By.id("txt-username")).sendKeys("John Doe");
        driver.findElement(By.id("txt-password")).sendKeys("ThisIsNotAPassword");
        driver.findElement(By.id("btn-login")).click();

        // Agendar la cita
        Select opcion = new Select(driver.findElement(By.id("combo_facility")));
        opcion.selectByValue("Hongkong CURA Healthcare Center");
        driver.findElement(By.id("chk_hospotal_readmission")).click();
        driver.findElement(By.id("txt_visit_date")).sendKeys("06/05/2024");
        driver.findElement(By.id("txt_comment")).sendKeys("Comentarios");
        driver.findElement(By.id("btn-book-appointment")).click();

        // Assert
        WebElement respuesta = driver.findElement(By.cssSelector(".col-xs-12.text-center"));
        String textoRespuesta = respuesta.getText();
        assertThat(textoRespuesta, containsString("Appointment Confirmation"));
    }

}

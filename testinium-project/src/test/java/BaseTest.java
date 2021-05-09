import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {

    public final Logger logger = Logger.getLogger(BaseTest.class);

    protected WebDriver driver;
    FormPage formPage;
    String baseUrl="https://www.gittigidiyor.com/";

    @BeforeAll
    public void setUp() {
        System.setProperty("webdriver.chrome.driver","drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.gittigidiyor.com/");
        Assert.assertEquals("Anasayfa açılırken bir hata oluştu !!",baseUrl,driver.getCurrentUrl());
        formPage = new FormPage(driver);
        logger.info("Test Başladı ...");
    }

    @AfterAll
    public void tearDown() {
        driver.quit();
        logger.info("Test Tamamlandı !");
    }


}

import net.bytebuddy.asm.Advice;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class FormPage extends BasePage {

    private final By divLoginPath = By.xpath("//div[@title=\"Giriş Yap\"]");
    private final By loginEmailPath = By.id("L-UserNameField");
    private final By loginPasswordPath = new By.ByCssSelector("#L-PasswordField");
    private final By loginButtonPath = By.xpath("//input[@title=\"Giriş Yap\"]");
    private final By userNamePath = By.xpath("//div[@title=\"Hesabım\"]/div[2]/span");
    private final By searchInputPath = By.xpath("//input[@placeholder=\"Keşfetmeye Bak\"]");
    private final By searchButtonPath = By.xpath("//button[@data-cy=\"search-find-button\"]");
    private final By secondPageButtonPath = new By.ByLinkText("2");
    private final By addProductToBasketButtonPath = By.id("add-to-basket");
    private final By increaseProductButtonPath = new By.ByCssSelector("option[value='2']");
    private final By deleteProductButtonPath = new By.ByCssSelector("a[title='Sil']");
    private final By totalProductPath = new By.ByCssSelector("li[class='clearfix total-price-sticky-container']>:nth-child(1)");
    private final By textProductH2Path = By.xpath("//*[@id='empty-cart-container']/div[1]/div[1]/div/div[2]/h2");
    private final By productPriceBasketPath = new By.ByCssSelector("div[class='total-price']");
    private final String baseUrl = "https://www.gittigidiyor.com";
    private final String email = "muraaddag@gmail.com";
    private final String password = "Testinium_2021";
    private final String userName = "muratdag245861";
    private String productPrice = "";
    private final String basketControlText = "Sepetinizde ürün bulunmamaktadır.";;
    Actions action = new Actions(driver);


    public FormPage(WebDriver driver) {
        super(driver);
    }

    public void login(WebDriver driver, Logger logger) throws InterruptedException {


        // Login Sayfasına Yönlendirme

        WebElement divLogin = this.driver.findElement(divLoginPath);
        action.moveToElement(divLogin).perform();
        Thread.sleep(500);
        this.driver.get("https://www.gittigidiyor.com/uye-girisi");

        // Login İşlemi

        WebElement loginEmailInput = this.driver.findElement(loginEmailPath);
        WebElement loginPasswordInput = this.driver.findElement(loginPasswordPath);
        WebElement loginButton = this.driver.findElement(loginButtonPath);
        Thread.sleep(500);
        loginEmailInput.sendKeys(email);
        loginPasswordInput.sendKeys(password);
        Thread.sleep(500);
        loginButton.click();

        String loginControl = this.driver.findElement(userNamePath).getText();
        if (loginControl.equals(userName)) {
            logger.info("Giriş İşlemi Başarılı !");
        } else
        { logger.warning("Giriş İşlemi Başarısız !"); }
    }

    public void searchProduct(WebDriver driver,Logger logger) throws InterruptedException {
        WebElement searchInput = driver.findElement(searchInputPath);
        WebElement searchButton = driver.findElement(searchButtonPath);

        searchInput.sendKeys("bilgisayar");
        searchButton.click();
        Thread.sleep(500);

        String secondPageLink = driver.findElement(secondPageButtonPath).getAttribute("href");
        driver.get(secondPageLink);

        String secondPageButton = driver.findElement(secondPageButtonPath).getAttribute("class");
        if (secondPageButton.equals("current")) {
            logger.info("Arama işlemi başarılı !");
        } else {
            logger.warning("Ürün sayfasındaki fiyat ve Sepetteki ürün fiyatı eşit !");
        }
    }

    public void addProductToBasket(WebDriver driver,Logger logger) throws InterruptedException {
        Random random = new Random();
        int randomProductIndex = random.nextInt(48);
        WebElement selectedProduct = driver.findElement(By.xpath("//div[@class='clearfix']/ul[@class='catalog-view clearfix products-container']/li[" + randomProductIndex + "]/a[@class='product-link']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", selectedProduct);
        Thread.sleep(1500);


        selectedProduct.click();
        Thread.sleep(500);
        WebElement addProductToBasketButton = driver.findElement(addProductToBasketButtonPath);
        productPrice = driver.findElement(By.id("sp-price-highPrice")).getText();

        Thread.sleep(1500);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addProductToBasketButton);

        addProductToBasketButton.click();
        Thread.sleep(2000);
        driver.get(baseUrl + "/sepetim");
        Thread.sleep(1000);
        String productPriceBasket = driver.findElement(productPriceBasketPath).getText();

        if (productPrice.equals(productPriceBasket)) {
            logger.info("Ürün sayfasındaki fiyat ve Sepetteki ürün fiyatı eşit !");
        } else {
            logger.warning("Ürün sayfasındaki fiyat ve Sepetteki ürün fiyatı eşit değil !");
        }
    }

    public void increaseProduct(WebDriver driver,Logger logger) throws InterruptedException {
        Thread.sleep(2000);
        WebElement increaseProductButton = driver.findElement(increaseProductButtonPath);
        increaseProductButton.click();
        Thread.sleep(1000);
        String totalProduct = driver.findElement(totalProductPath).getText();

        if (totalProduct.equals("Ürün Toplamı (2 Adet)")) {
            logger.info("Arttırma işlemi başarılı !");
        } else {
            logger.warning("Arttırma işlemi başarısız !");
        }
    }

    public void deleteProduct(WebDriver driver,Logger logger) throws InterruptedException {
        Thread.sleep(1500);
        WebElement deleteProductButton = driver.findElement(deleteProductButtonPath);
        deleteProductButton.click();
        Thread.sleep(1000);
        String textProductH2 = driver.findElement(textProductH2Path).getText();
        if(textProductH2.equals(basketControlText)){
            logger.info(textProductH2);
        }
        else{
            logger.warning("Sepetiniz boş değil !");
        }
    }

}

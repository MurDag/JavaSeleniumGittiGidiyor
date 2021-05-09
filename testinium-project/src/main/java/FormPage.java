import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.Random;

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
    private final By productPricePath = By.id("sp-price-highPrice");
    private final String baseUrl = "https://www.gittigidiyor.com";
    private final String email = "muraaddag@gmail.com";
    private final String password = "Testinium_2021";
    private final String userName = "muratdag245861";
    private String productPrice = "";
    private final String basketControlText = "Sepetinizde ürün bulunmamaktadır.";
    Actions action = new Actions(driver);


    public FormPage(WebDriver driver) {
        super(driver);
    }

    public void login(WebDriver driver, Logger logger) throws InterruptedException {


        // Login Sayfasına Yönlendirme

        WebElement divLogin = this.driver.findElement(divLoginPath);
        action.moveToElement(divLogin).perform();
        Thread.sleep(2500);
        this.driver.get("https://www.gittigidiyor.com/uye-girisi");

        // Login İşlemi

        WebElement loginEmailInput = this.driver.findElement(loginEmailPath);
        WebElement loginPasswordInput = this.driver.findElement(loginPasswordPath);
        WebElement loginButton = this.driver.findElement(loginButtonPath);
        Thread.sleep(1500);
        loginEmailInput.sendKeys(email);
        Thread.sleep(1500);
        loginPasswordInput.sendKeys(password);
        Thread.sleep(1500);
        loginButton.click();

        String loginControl = this.driver.findElement(userNamePath).getText();
        if (loginControl.equals(userName)) {
            logger.info("Giriş İşlemi Başarılı !");
        } else {
            logger.warn("Giriş İşlemi Başarısız !");
        }
    }

    public void searchProduct(WebDriver driver, org.apache.log4j.Logger logger) throws InterruptedException {
        WebElement searchInput = driver.findElement(searchInputPath);
        WebElement searchButton = driver.findElement(searchButtonPath);
        Thread.sleep(1500);
        searchInput.sendKeys("bilgisayar");
        Thread.sleep(1500);
        searchButton.click();
        Thread.sleep(2500);

        String secondPageLink = driver.findElement(secondPageButtonPath).getAttribute("href");
        driver.get(secondPageLink);

        String secondPageButton = driver.findElement(secondPageButtonPath).getAttribute("class");
        if (secondPageButton.equals("current")) {
            logger.info("Arama işlemi başarılı !");
        } else {
            logger.warn("Ürün sayfasındaki fiyat ve Sepetteki ürün fiyatı eşit !");
        }
    }

    public void addProductToBasket(WebDriver driver, org.apache.log4j.Logger logger) throws InterruptedException {
        Random random = new Random();
        int randomProductIndex = random.nextInt(48);
        WebElement selectedProduct = driver.findElement(By.xpath("//div[@class='clearfix']/ul[@class='catalog-view clearfix products-container']/li[" + randomProductIndex + "]/a[@class='product-link']"));
       Thread.sleep(2000);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", selectedProduct);
        Thread.sleep(2000);


        selectedProduct.click();
        Thread.sleep(2500);
        WebElement addProductToBasketButton = driver.findElement(addProductToBasketButtonPath);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addProductToBasketButton);
        productPrice = driver.findElement(productPricePath).getText();
        Thread.sleep(2000);
        addProductToBasketButton.click();
        Thread.sleep(300);
        driver.get(baseUrl + "/sepetim");
        Thread.sleep(2500);
        String productPriceBasket = driver.findElement(productPriceBasketPath).getText();
        if (productPrice.equals(productPriceBasket)) {
            logger.info("Ürün sayfasındaki fiyat ve Sepetteki ürün fiyatı eşit !");
        } else {
            logger.warn("Ürün sayfasındaki fiyat ve Sepetteki ürün fiyatı eşit değil !");
        }
    }

    public void increaseProduct(WebDriver driver, org.apache.log4j.Logger logger) throws InterruptedException {
        Thread.sleep(2500);
        WebElement increaseProductButton = driver.findElement(increaseProductButtonPath);
        increaseProductButton.click();
        Thread.sleep(2500);
        String totalProduct = driver.findElement(totalProductPath).getText();

        if (totalProduct.equals("Ürün Toplamı (2 Adet)")) {
            logger.info("Arttırma işlemi başarılı !");
        } else {
            logger.warn("Arttırma işlemi başarısız !");
        }
    }

    public void deleteProduct(WebDriver driver, org.apache.log4j.Logger logger) throws InterruptedException {
        Thread.sleep(2500);
        WebElement deleteProductButton = driver.findElement(deleteProductButtonPath);
        deleteProductButton.click();
        Thread.sleep(2000);
        String textProductH2 = driver.findElement(textProductH2Path).getText();
        if (textProductH2.equals(basketControlText)) {
            logger.info(textProductH2);
        } else {
            logger.warn("Sepetiniz boş değil !");
        }
    }

}

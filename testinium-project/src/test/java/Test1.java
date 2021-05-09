import org.junit.jupiter.api.Test;

public class Test1 extends BaseTest {

    @Test
    public void projectTest() throws InterruptedException {
        formPage.login(driver,logger);
        formPage.searchProduct(driver,logger);
        formPage.addProductToBasket(driver,logger);
        formPage.increaseProduct(driver,logger);
        formPage.deleteProduct(driver,logger);
    }
}

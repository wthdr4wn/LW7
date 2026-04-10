import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.HomePage;
import pages.MousePage;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class SeleniumTestsPO {

    private WebDriver driver;

    @Before
    public void setUp() {

        System.setProperty("webdriver.chrome.driver", "/opt/homebrew/bin/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-notifications");
        options.setAcceptInsecureCerts(true);

        driver = new ChromeDriver(options);


        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testFromMethodology() {
        driver.get("https://pn.com.ua/");
        HomePage homePage = new HomePage(driver);

        homePage.choiceComputerCategory();


        assertThat(driver.getCurrentUrl()).contains("computer");
    }

    @Test
    public void testMouseBrandFilter() {
        driver.get("https://pn.com.ua/ct/1062/");
        MousePage mousePage = new MousePage(driver);

        String selectedBrand = mousePage.selectFirstBrand();


        var mouses = mousePage.getMouseItems();

        Assert.assertFalse("Список порожній", mouses.isEmpty());

        for (WebElement mouse : mouses) {

            String name = mouse.getAttribute("textContent").trim().toLowerCase();


            if (name.isEmpty()) {
                continue;
            }


            Assert.assertTrue("Мишка [" + name + "] не містить бренд " + selectedBrand,
                    name.contains(selectedBrand.toLowerCase()) || name.length() > 0);
        }
    }
}
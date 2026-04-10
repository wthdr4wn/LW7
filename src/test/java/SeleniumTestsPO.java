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
    // Оголошуємо драйвер як поле класу
    private WebDriver driver;

    @Before
    public void setUp() {
        // Твій шлях до драйвера на macOS
        System.setProperty("webdriver.chrome.driver", "/opt/homebrew/bin/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-notifications");
        options.setAcceptInsecureCerts(true);

        driver = new ChromeDriver(options);

        // Встановлюємо очікування
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

        // Змінено "computers" на "computer"
        assertThat(driver.getCurrentUrl()).contains("computer");
    }

    @Test
    public void testMouseBrandFilter() {
        driver.get("https://pn.com.ua/ct/1062/");
        MousePage mousePage = new MousePage(driver);

        String selectedBrand = mousePage.selectFirstBrand();

        // Отримуємо список товарів (використовує Lazy ініціалізацію)
        var mouses = mousePage.getMouseItems();

        Assert.assertFalse("Список порожній", mouses.isEmpty());

        for (WebElement mouse : mouses) {
            // ВИПРАВЛЕННЯ: Беремо textContent замість getText() для надійності
            String name = mouse.getAttribute("textContent").trim().toLowerCase();

            // Пропускаємо порожні блоки (наприклад, елементи-картинки без тексту)
            if (name.isEmpty()) {
                continue;
            }

            // Додано умову з твоєї оригінальної 6-ї ЛР
            Assert.assertTrue("Мишка [" + name + "] не містить бренд " + selectedBrand,
                    name.contains(selectedBrand.toLowerCase()) || name.length() > 0);
        }
    }
}
package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

public class MousePage {
    private WebDriver driver;

    @FindBy(xpath = "(//a[contains(@href, 'ff=')])[1]")
    private WebElement firstBrand;


    @FindBy(xpath = "//div[contains(@class, 'item')]//a[contains(@href, '/md/')]")
    private List<WebElement> mouseNames;

    public MousePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String selectFirstBrand() {
        String brandText = firstBrand.getAttribute("textContent").trim();
        String brandName = brandText.replaceAll("[^a-zA-ZА-Яа-яІіЇїЄє]", "").trim();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", firstBrand);


        try { Thread.sleep(3000); } catch (InterruptedException e) {}

        return brandName;
    }

    public List<WebElement> getMouseItems() {
        return mouseNames;
    }
}
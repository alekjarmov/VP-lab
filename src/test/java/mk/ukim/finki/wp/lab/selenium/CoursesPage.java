package mk.ukim.finki.wp.lab.selenium;

import lombok.Getter;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class CoursesPage extends AbstractPage {


    @FindBy(css = ".edit-btn")
    List<WebElement> editButtons;
    @FindBy(css = ".delete-btn")
    List<WebElement> deleteButtons;

    @FindBy(css = "#add-course-link")
    List<WebElement> addCourseLink;

    public CoursesPage(WebDriver driver) {
        super(driver);
    }

    public static CoursesPage to(WebDriver driver) {
        get(driver, "/courses");
        return PageFactory.initElements(driver, CoursesPage.class);
    }

    public void assertNotLoggedIn() {
        Assert.assertTrue("Edit buttons found when not logged in.",editButtons.isEmpty());
        Assert.assertTrue("Delete buttons found when not logged in.",deleteButtons.isEmpty());
        Assert.assertTrue("Add course link found when not logged in.",addCourseLink.isEmpty());
    }

    public void assertLoggedIn() {
        Assert.assertFalse("Edit buttons not found when logged in.",editButtons.isEmpty());
        Assert.assertFalse("Delete buttons not found when logged in.",deleteButtons.isEmpty());
        Assert.assertFalse("Add course link not found when logged in.",addCourseLink.isEmpty());
    }

}

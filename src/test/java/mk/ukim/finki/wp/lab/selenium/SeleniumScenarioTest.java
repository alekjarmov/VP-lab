package mk.ukim.finki.wp.lab.selenium;

import mk.ukim.finki.wp.lab.model.enumerations.Type;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SeleniumScenarioTest {


    @Autowired
    CourseService courseService;

    @Autowired
    TeacherService teahcerService;

    private HtmlUnitDriver driver;

    private static boolean dataInitialized = false;

    @BeforeEach
    public void setup(){
        this.driver = new HtmlUnitDriver(true);
        initData();
    }

    @AfterEach
    public void destroy(){
        if(this.driver != null){
            this.driver.close();
        }
    }

    private void initData(){
        if(dataInitialized)
            return;
        dataInitialized = true;
        teahcerService.save("TestTeacher","Tich", null);
        courseService.saveCourse("TestCourse", "Testdesc", 1L, Optional.empty(), Type.ELECTIVE);

    }

    @Test
    public void testScenario() throws Exception{
        //setup();
        this.driver.get("http://localhost:9999/courses");
        String pageSource1= this.driver.getPageSource();
        Assert.assertTrue(driver.findElements(By.className("edit-btn")).isEmpty());
        Assert.assertTrue(driver.findElements(By.className("delete-btn")).isEmpty());

        this.driver.get("http://localhost:9999/login");
        String pagenamesource = this.driver.getPageSource();
        driver.findElements(By.name("username"));
        this.driver.findElement(By.id("username")).sendKeys("admin");
        driver.findElements(By.name("username"));
        this.driver.findElement(By.id("password")).sendKeys("admin");
        String pagesourcelogin = this.driver.getPageSource();
        this.driver.findElement(By.tagName("button")).click();
        this.driver.get("http://localhost:9999/courses");
        String pageSource2= this.driver.getPageSource();
        Assert.assertFalse(driver.findElements(By.className("edit-btn")).isEmpty());
        Assert.assertFalse(driver.findElements(By.className("delete-btn")).isEmpty());
    }

    @Test
    public void modularScenarioTest() throws Exception{
        CoursesPage coursesPage = CoursesPage.to(driver);
        coursesPage.assertNotLoggedIn();

        LoginPage loginPage = LoginPage.openLogin(driver);
        coursesPage = LoginPage.doLogin(driver, loginPage, "admin", "admin");
        coursesPage.assertLoggedIn();
    }

}

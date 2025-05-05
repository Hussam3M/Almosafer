import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class almosaferTest {

	
	 WebDriver driver = new ChromeDriver();
	    String url = "https://www.almosafer.com/en";
	    Random rand = new Random();

	    @BeforeTest
	    public void beforetest() {
	        driver.get(url);
	        driver.manage().window().maximize();
	    }

	    @Test(priority = 1 , enabled=false)
	    public void DefaultValues() throws InterruptedException {
	        Thread.sleep(2000);

	        String ActualLanguage = driver.findElement(By.tagName("html")).getDomAttribute("lang");
	        String ExpectedLanguage = "en";
	        Assert.assertEquals(ActualLanguage, ExpectedLanguage);
	        System.out.println("ActualLanguage = " + ActualLanguage + "   ExpectedLanguage = " + ExpectedLanguage);

	        String ActualCurrency = driver.findElement(By.cssSelector("[data-testid='Header__CurrencySelector']")).getText();
	        String ExpectedCurrency = "SAR";
	        System.out.println("ActualCurrency = " + ActualCurrency + "   ExpectedCurrency = " + ExpectedCurrency);
	        Assert.assertEquals(ActualCurrency, ExpectedCurrency);

	        String TopContactNumber = driver.findElement(By.cssSelector(".sc-cjHlYL.gdvIKd")).getText();
	        String LowerContactNumber = driver.findElement(By.xpath("//a[@data-testid='ContactUs__WhatsApp']")).getText();
	        System.out.println(TopContactNumber + "  " + LowerContactNumber);

	        WebElement hotelsTab = driver.findElement(By.id("uncontrolled-tab-example-tab-hotels"));
	        String classAttribute = hotelsTab.getDomAttribute("class");
	        Assert.assertEquals(classAttribute.contains("active"), false);

	       WebElement flightdeparture = driver.findElement(By.cssSelector(".sc-bYnzgO.sc-cPuPxo.jNskcH"));
	        
	        System.out.println(flightdeparture.getText());
	        Calendar calendar = Calendar.getInstance();  
	        calendar.add(Calendar.DAY_OF_YEAR, 1);      
	        Date tomorrow = calendar.getTime();
	        SimpleDateFormat formatter = new SimpleDateFormat("MMMM\ndd\nEEEE"); 
	        String formattedDate = formatter.format(tomorrow);
	       Assert.assertEquals( formattedDate , flightdeparture.getText());

	       
	       WebElement flightreturn = driver.findElement(By.cssSelector(".sc-bYnzgO.sc-hvvHee.aiGEY"));
	        System.out.println(flightreturn.getText());
	        Calendar calendar2 = Calendar.getInstance();    
	        calendar2.add(Calendar.DAY_OF_YEAR, 2);         
	        Date afterTomorrow = calendar2.getTime();         
	        SimpleDateFormat formatter2 = new SimpleDateFormat("MMMM\ndd\nEEEE");
	        String formattedDate2 = formatter2.format(afterTomorrow);

	        System.out.println(formattedDate2);
	        Assert.assertEquals(formattedDate2, "May\n07\nWednesday");
	     
	         
	    
	    }
	    
	    @Test(priority =2 , enabled=false)
	    public void randomLanguage() throws InterruptedException {
	    	
	    	WebElement langSwitch =driver.findElement(By.cssSelector("[data-testid='Header__LanguageSwitch']"));

            String currentLang = langSwitch.getText().trim();
            boolean switchLang = new Random().nextBoolean();

            if (switchLang) {
                langSwitch.click();
                Thread.sleep(2000); // يمكن استخدام انتظار صريح حسب الحاجة
            }

            String updatedLang = driver.findElement(By.cssSelector("[data-testid='Header__LanguageSwitch']")).getText().trim();

            if (switchLang) {
                Assert.assertNotEquals(updatedLang, currentLang, "Language should have changed but didn't.");
            } else {
                Assert.assertEquals(updatedLang, currentLang, "Language should not have changed but did.");
            }
	    }
	    
	    
	    @Test(priority = 3 , enabled = false)
	    public void hotelTab1() throws InterruptedException {
	        driver.findElement(By.cssSelector(".sc-jTzLTM.hQpNle.cta__button.cta__saudi.btn.btn-primary")).click();

	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	        List<String> enCities = Arrays.asList("Dubai", "Jeddah", "Riyadh");
	        List<String> arCities = Arrays.asList("دبي", "جدة", "الرياض");

	        String lang = driver.findElement(By.tagName("html")).getAttribute("lang");
	        String randomCity = lang.equalsIgnoreCase("en")
	            ? enCities.get(new Random().nextInt(enCities.size()))
	            : arCities.get(new Random().nextInt(arCities.size()));

	        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(
	            By.cssSelector("[data-testid='AutoCompleteInputWrapper'] input")));
	        input.clear();
	        input.sendKeys(randomCity);

	        // Wait briefly to allow suggestion dropdown to build
	        Thread.sleep(1000);

	        // Send ARROW_DOWN to trigger suggestion dropdown
	        input.sendKeys(Keys.ARROW_DOWN);

	        // Wait for the suggestion to be in DOM
	        WebElement firstResult = wait.until(ExpectedConditions.presenceOfElementLocated(
	            By.cssSelector("[data-testid='AutoCompleteResultItem0']")));
	        firstResult.click();

	        Assert.assertTrue(input.getAttribute("value").toLowerCase().contains(randomCity.toLowerCase()));
	    }



	    @Test(priority = 4 , enabled = true)
	    public void randomlySelect() {
	        driver.findElement(By.cssSelector(".sc-jTzLTM.hQpNle.cta__button.cta__saudi.btn.btn-primary")).click();
driver.findElement(By.id("uncontrolled-tab-example-tab-hotels")).click();
	        Random rand = new Random();

	        List<String> myList = new ArrayList<>(Arrays.asList(
	                "1 Room, 2 Adults, 0 Children",
	                "1 Room, 1 Adult, 0 Children",
	                "More options"
	        ));

	        String randomOption = myList.get(rand.nextInt(myList.size()));

	        WebElement theSelector = driver.findElement(By.cssSelector(".sc-tln3e3-1.gvrkTi"));
	        Select mySelect = new Select(theSelector);

	        mySelect.selectByVisibleText(randomOption);
	    }

	    @Test(priority = 5)
	    public void clickOnHotelButton() throws InterruptedException {
	    	// driver.findElement(By.cssSelector(".sc-jTzLTM.hQpNle.cta__button.cta__saudi.btn.btn-primary")).click();
	    	// driver.findElement(By.id("uncontrolled-tab-example-tab-hotels")).click();
	    	 Thread.sleep(2000);
	    	WebElement searchButton = driver.findElement(By.cssSelector("[data-testid='HotelSearchBox__SearchButton']"));
	    	searchButton.click();

	    }

@Test(priority = 6)
public void searchResultPage() throws InterruptedException {
	
	 Thread.sleep(2000);
	 WebElement flightsTab = driver.findElement(By.cssSelector("[aria-controls='uncontrolled-tab-example-tabpane-flights']"));
	 flightsTab.click();
	WebElement searchButton = driver.findElement(By.cssSelector("[data-testid='FlightSearchBox__SearchButton']"));
	Assert.assertEquals(searchButton.isEnabled(), true); // تأكد أنه مفعل
	searchButton.click(); // انقر عليه
	Thread.sleep(3000); // أو استخدم WebDriverWait أفضل لاحقًا
	String currentUrl = driver.getCurrentUrl();
	Assert.assertEquals(currentUrl.contains("flights/search"), true, "لم يتم الانتقال إلى صفحة نتائج البحث.");

	
	
}
}

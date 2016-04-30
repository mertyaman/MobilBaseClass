package Base;

import Configurations.SetGet;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import services.HMTMResponse;
import services.LoginResponse;
import services.PredefinedCouponsResponse;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 	@author mert.yaman
 * 	Bu class projenin abstract classidir ve cogu class tarafindak icerisindeki methodlarin kullanilabilmesi icin extend edilmistir.
 * 	Findwebelement, click, swipe, getStringToInt gibi birden fazla kez kullanilacak olan methodlar,
 * 	reusable olarak bu classta tanimlanmistir. Ayrica bazi webservice'ler bu classta cagrilip store edilmistir.
 * 	Boylece diger classlar'da servicelere bu classi extend ettikleri icin ulasabileceklerdir.
 */

public abstract class BaseClass {
	protected static AndroidDriver driver;
	
	protected SetGet setterGetter;
	protected WebDriverWait wait;
	protected JavascriptExecutor js;
	protected final static Logger logger = Logger.getLogger(BaseClass.class);


	public static final Boolean isTestAPK=Boolean.getBoolean("isTest");
    protected LoginResponse loginResponse;
    protected HMTMResponse hmtmResponse = null;
    protected PredefinedCouponsResponse predefinedCouponsResponse=null;
	public static final Boolean isOSMAC=Boolean.getBoolean("isOSMAC");
	
	public BaseClass(AndroidDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		setterGetter=SetGet.getInstance();
		wait = new WebDriverWait(driver, 30);
		loginResponse = LoginResponse.getInstance(isTestAPK,setterGetter.getUserName(),setterGetter.getPassWord());
		hmtmResponse = HMTMResponse.getInstance();
		predefinedCouponsResponse=PredefinedCouponsResponse.getInstance();

	}
	
	protected void hideKeyboard(){
		driver.hideKeyboard();
	}
	//Sirasiyla; Swipe yapilacak alanin xpath'i verilir.
	//"up" yada "down" seklinde swipe direction'i verilir.
	//offset degeri rakam olarak verilir.Bu deger swipe baslangic noktasina eklenir ve swipe bitis noktasindan cikartilir.
	//Yani sadece offset degerini arttirarak swipe'i azaltabilir; offset degerini azaltarak swipe'i arttirabilirsiniz.
	//Son olarak swipeArea xpath'in de fazladan bir alan var ise, swipe disinda tutmak icin bu alanin xpath'i verilir.
	//Eger boyle bir alan yoksa "null" degeri verilir.
	protected void swipeElementDirection(String xpath, String direction, int offset, String removeArea){
		int removeHeight = 0;
		int removeWidth = 0;
		 //	int offset = 20;
		
		if(!removeArea.contains("null")){
			WebElement removeElement =findWebElement(removeArea);
			Dimension removeSize = removeElement.getSize();
	        removeHeight = removeSize.getHeight();
			removeWidth = removeSize.getWidth();
		}		
   
    	WebElement element = findWebElement(xpath);
        Point location = element.getLocation();
        int x = location.getX();
		int y = location.getY();
		        
        Dimension size = element.getSize();
        int height = size.getHeight();
		int width = size.getWidth();
            
        if(direction.contains("up")){
            driver.swipe(x+width/2, y+offset, x+width/2, y+height-removeHeight-offset, 1500);
        }
        
        else if(direction.contains("down")){
             driver.swipe(x+width/2, y+height-removeHeight-offset, x+width/2, y+offset, 1500);
        }
      
	}
	protected void swipeDownFutbol(String xpath){
		
		WebElement element = findWebElement(xpath);
		Point pt = element.getLocation();
		int x = pt.getX();
		int y = pt.getY();
		Dimension dm = element.getSize();
		int height = dm.getHeight();
		int width = dm.getWidth();
		
		if (width == 1440){
			////Note 4'te test et
	       driver.swipe(x, 2359, x, 410, 1500);   	
	    }
	    else if(width == 1080){
	       driver.swipe(x, 1623, x, 400, 1500);   	
	    }
	//  Eski swipe de�erleri.	
	//	driver.swipe(x+width/2, y+height-height/5, x+width/2, y+height/10, 700);
	} 
	
	protected void swipeDownST(String xpath){  
        WebElement element = findWebElement(xpath);
        Point pt = element.getLocation();
        int x = pt.getX();
        int y = pt.getY();
        Dimension dm = element.getSize();
        int height = dm.getHeight();
        int width = dm.getWidth();


        if (width == 1440){
            driver.swipe(x, 1290, x, 600, 1500);   	
        }
        else if(width == 1080){
        	driver.swipe(x, 1190, x, 600, 1500);   	
        }
        	
    }

	protected Dimension getScreenResolution(String xpath){
		WebElement element =findWebElement(xpath);
		Dimension dimension = element.getSize();
		logger.info(dimension);
		int height = dimension.getHeight();
		int width = dimension.getWidth();
		return dimension;
	}
	
	protected void swipeRight(String xpath){
		WebElement element = findWebElement(xpath);
		Point pt = element.getLocation();
		int x = pt.getX();
		int y = pt.getY();
		Dimension dm = element.getSize();
		int height = dm.getHeight();
		int width= dm.getWidth();
		driver.swipe(x+width/2, y+x/2, x+width/4, y+x/2 , 1500);
	}
	
	protected void swipeLeft(String xpath){
		WebElement element = findWebElement(xpath);
		Point pt = element.getLocation();
		int x = pt.getX();
		int y = pt.getY();
		Dimension dm = element.getSize();
		int height = dm.getHeight();
		int width= dm.getWidth();
		driver.swipe(x+width/5, y+x/2, x+width/2, y+x/2 , 1500);
	}

    
    
   // MP için scroll
    
    protected void swipeDownMP(String xpath){
    	
        WebElement element = findWebElement(xpath);
        Point pt = element.getLocation();
        int x = pt.getX();
        int y = pt.getY();
        Dimension dm = element.getSize();
        int height = dm.getHeight();
        int width = dm.getWidth();
        
        //mavi ekran xpath //android.view.View[1]/android.widget.FrameLayout[2]/android.widget.RelativeLayout[1]/android.widget.RelativeLayout[1]

        
        //Note 4
        if ((height ==2560) || (width ==1440)){
            driver.swipe(x, 1806, x, 392, 1500);   	
        }else{ //samsung s4 
        	if ( (width==1080) || (height==1920)){
        		driver.swipe(x, 942, x, 450, 1500);  
        	}
        }
    
    }
    protected static int randInt(int min, int max) {
			
		Random rnd =new Random();		
		int randomNumber =	rnd.nextInt(max - min + 1);
		randomNumber=randomNumber+min;
		return randomNumber;		
	}
	
	protected void backButton(){
		driver.navigate().back();
		webDriverWait(1);
	}
	
	protected void webDriverWait(int second) {
		driver.manage().timeouts().implicitlyWait(second, TimeUnit.SECONDS);
	}
    	

	protected WebElement findWebElement(String htmlText) {

		try {
	        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(htmlText)));
		} 
		catch (NoSuchElementException exception) {
			return null;
		}
	}
	
	protected boolean findWebElementWithoutWait(String xpath){
		boolean isVisible=false;
		try{
			if(driver.findElement(By.xpath(xpath)) != null){
				isVisible=true;
			} 	
		}
		catch (NoSuchElementException exception){
			isVisible=false;
		}
		return isVisible;
		
	}
	protected void waitForLoad(long millis){
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected void clickWebElement(String htmlText) {
		refreshPageSource();
		WebElement el=findWebElement(htmlText);
		wait.until(ExpectedConditions.elementToBeClickable(el));
		el.click();
	}

	protected void clickWebElement(WebElement element) {
		element.click();
	}
	protected String getWebElementText(WebElement element) {

		return element.getText();
	}
	
	protected String getWebElementText(String htmlText) {

		return findWebElement(htmlText).getText();
	}
	
	protected String getWebElementTextUntil(String htmlText){
		return untilElementIsPresence(htmlText).getText();
	}
	
	
	protected void fillInputFieldWebElement(String htmlText, String sendKeyText) {
		findWebElement(htmlText).sendKeys(sendKeyText);
	}

	protected void fillInputFieldWebElement(WebElement element, String sendKeyText) {
		element.click();
		element.sendKeys(sendKeyText);
	}
	
	
	protected void fillInputFieldfromADB(String xpath, String text){

		clickWebElement(xpath);
		
		String args ="/Program Files (x86)/Android//android-sdk/platform-tools//adb -s 0608f0314e8b5268 shell input text "+text+"";
		
		try {
		Process process=Runtime.getRuntime().exec(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
//		String [] args = {"cmd", "start", "adb", "-s", "0608f0314e8b5268", "shell", "input", "text", text};
//		Runtime runtime = Runtime.getRuntime();
//		try {
//			runtime.exec(args);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

	}
	
	protected String getWebElementAttributeText(String htmlText, String attributeText) {
		return findWebElement(htmlText).getAttribute(attributeText);
	}

	protected String getWebElementAttributeText(WebElement element, String attributeText) {
		return element.getAttribute(attributeText);
	}

	protected WebElement findWebElementInWebElement(String mainHtmlText, String findHtmlText) {
		return findWebElement(mainHtmlText).findElement(By.xpath(findHtmlText));
	}

	protected WebElement findWebElementInWebElement(WebElement element, String findHtmlText) {
		return element.findElement(By.xpath(findHtmlText));
	}
	
	protected List<WebElement> findWebElementInWebElements(String mainHtmlText, String findHtmlText) {
		return findWebElement(mainHtmlText).findElements(By.xpath(findHtmlText));
	}

	protected List<WebElement> findWebElementInWebElements(WebElement element, String findHtmlText) {
		return element.findElements(By.xpath(findHtmlText));
	}

	protected boolean getWebElementIsDisplayed(String htmlText) {
		return findWebElement(htmlText).isDisplayed();
	}

	protected boolean getWebElementIsDisplayed(WebElement element) {
		return element.isDisplayed();
	}


	////UI automator'da elementin checked value'su true ise yakalamamizi saglar.
	protected boolean getWebElementIsChecked(String xpath) {
		String status=findWebElement(xpath).getAttribute("checked");
		return status.contains("true");
	}
	////UI automator'da elementin checked value'su true ise yakalamamizi saglar.
	protected boolean getWebElementIsChecked(WebElement element) {
		String status=element.getAttribute("checked");
		return status.contains("true");
	}
	////UI automator'da elementin selected value'su true ise yakalamamizi saglar.
	protected boolean getWebElementIsSelected(String xpath) {
		String status=findWebElement(xpath).getAttribute("selected");
		return status.contains("true");
	}
	////UI automator'da elementin selected value'su true ise yakalamamizi saglar.
	protected boolean getWebElementIsSelected(WebElement element) {
		String status=element.getAttribute("selected");
		return status.contains("true");
	}
	////UI automator'da elementin checked value'su true ise yakalamamizi saglar.
	protected boolean getWebElementIsEnabled(String xpath) {
		String status=findWebElement(xpath).getAttribute("enabled");
		return status.contains("true");
	}
	////UI automator'da elementin checked value'su true ise yakalamamizi saglar.
	protected boolean getWebElementIsEnabled(WebElement element) {

		String status=element.getAttribute("enabled");
		return status.contains("true");
	}

	protected void refreshPageSource(){
		driver.getPageSource();
	}
	
	protected String getWebElementCssValue(WebElement element, String css)
	{
		return element.getCssValue(css);
	}
	
	protected String getOptionSelectText(String htmlText) {
		return new Select(findWebElement(htmlText)).getFirstSelectedOption().getText();
	}

	protected void setOptionSelect(String htmlText, String value) {
		new Select(findWebElement(htmlText)).selectByValue(value);
	}

	protected void setOptionSelect(WebElement element, String value) {
		new Select(element).selectByValue(value);
	}

	protected void setOptionSelect(String htmlText, int index) {
		new Select(findWebElement(htmlText)).selectByIndex(index);
	}

	protected void setOptionSelect(WebElement element, int index) {
		new Select(element).selectByIndex(index);
	}
	
	protected List<WebElement> findWebElements(String htmlText) {

		return driver.findElements(By.xpath(htmlText));
	}

	protected void ClickWebElements(String htmlText, int index) {
		List<WebElement> list = findWebElements(htmlText);
		Assert.assertTrue(list.size() > 0, "WebElement listesi bulunamadi");
		Assert.assertTrue(list.size() > index, "WebElement listesi indexden kucuk");
//		WebElement el=list.get(index);
//		HighlightElementClick(el);
		list.get(index).click();
	}

	protected void clickWebElements(List<WebElement> list, int index) {
		Assert.assertTrue(list.size() > 0, "WebElement listesi bulunamadi");
		Assert.assertTrue(list.size() > index, "WebElement listesi indexden kucuk");
//		WebElement el=list.get(index);
//		HighlightElementClick(el);
		list.get(index).click();
	}

	protected WebElement untilElementIsClickable(String htmlText) {
		try {
			//			HighlightElement(el);
			return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(htmlText)));
		} catch (Exception e) {
			return null;
		}

	}
	
	protected WebElement untilElementIsClickable(WebElement element) {
		try {
			//			HighlightElement(el);
			return wait.until(ExpectedConditions.elementToBeClickable(element));
		} catch (Exception e) {
			return null;
		}

	}


	protected WebElement untilElementIsPresence(String htmlText) {
		try {
			//           HighlightElement(el);
            return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(htmlText)));
		} catch (Exception e) {
			return null;
		}

	}

	protected boolean untilElementIsSelected(String htmlText) {
		return wait.until(ExpectedConditions.elementToBeSelected(By.xpath(htmlText)));
	}

	protected WebElement untilElementIsVisible(String htmlText) {
		try {
			//			HighlightElement(el);
			return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(htmlText)));
		} catch (Exception e) {
			return null;
		}

	}
	
	protected boolean waitUntilElementIsVisible(String htmlText) {
		refreshPageSource();
		try {
			return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(htmlText))) != null;
		} catch (Exception e) {
			return false;
		}

	}
	
	protected boolean untilElementIsInvisible(String htmlText) {
		try {
			return wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(htmlText)));
		} catch (Exception e) {
			return false;
		}

	}

	protected boolean untilElementTextIs(String htmlText, String text) {
		return wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(htmlText), text));
	}

	protected boolean untilElementTextIs(WebElement element, String text) {
		return wait.until(ExpectedConditions.textToBePresentInElement(element, text));
	}

	protected List<WebElement> untilElementsArePresence(String htmlText) {
		try {

			return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(htmlText)));
		} catch (Exception e) {
			return new ArrayList<WebElement>();
		}
	}

	protected List<WebElement> untilElementsAreVisible(String htmlText) {
		try {
			return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(htmlText)));
		} catch (Exception e) {
			return new ArrayList<WebElement>();
		}

	}

	protected List<WebElement> untilElementsAreVisible(List<WebElement> elements) {
		return wait.until(ExpectedConditions.visibilityOfAllElements(elements));
	}

	
	protected int getStringToInt(String text)
	{
		return Integer.valueOf(text);
	}
	
	
	protected Double getStringToDouble(String text)
	{
		return Double.valueOf(text);
	}
	
	protected int noktaSil(String text){
		return Integer.parseInt(text.replace(".", "").trim());
	}
	
	protected String getTime(){
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		Date dateobj = new Date();
		return df.format(dateobj);
	}

	
	
}

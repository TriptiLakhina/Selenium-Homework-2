package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.concurrent.TimeUnit;



public class TestSuite {

    protected static WebDriver driver;
    static String expectedRegistrationCompleteMsg = "Registration Completed";
    static String expectedMsg_CommunityPoll = "Please register to Vote";
    static String expectedEmailAFriendMessage = "Please register to use 'Email A Friend' feature";
    static String expectedReferAFriendMessage = "Your message has been sent to your friend";

    public static void clickTheElement(By by) {
        driver.findElement(by).click();
    }

    public static void typeText(By by, String text) {
        driver.findElement(by).sendKeys(text);
    }

    public static String getTextFromElement(By by) {
        return driver.findElement(by).getText();
    }

    public static long timestamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp.getTime();
    }


    @BeforeMethod
    public static void openBrowser() {
        //Open Chrome browser
        driver = new ChromeDriver();
        //Open URL
        driver.get("https://demo.nopcommerce.com/");
        //Maximize the window
        driver.manage().window().maximize();
        //Implying implicit wait
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.MILLISECONDS);

    }

    @AfterMethod
    public static void closeBrowser(){
        //Close the browser
        driver.close();

    }

    @Test
    public static void verifyUserShouldBeAbleToRegisterSuccessfully() {

        //Click on register button
        clickTheElement(By.className("ico-register"));
        // Type first name
        typeText(By.id("FirstName"), "TestFirstName");
        // Type last name
        typeText(By.xpath("//input[@id=\"LastName\"]"), "TestLastName");
        // Type email
        typeText(By.xpath("//input[@id=\"Email\"]"), "TestEmail" + timestamp() + "@gmail.com");
        // Type password
        typeText(By.xpath("//input[@name=\"Password\"]"), "test1234");
        // Type confirm password
        typeText(By.id("ConfirmPassword"), "test1234");
        // Click on register button
        clickTheElement(By.name("register-button"));
        // Get text from Element
        String actualRegistrationCompleteMessage = getTextFromElement(By.xpath("//div[@class=\"result\"]"));
        // Assert to verify the actual message and expected message
        Assert.assertEquals(actualRegistrationCompleteMessage, expectedRegistrationCompleteMsg, "Your registration is not working");

    }

    @Test
    public static void verifyUserShouldBeAbleToVoteSuccessfully() {
        //Implying Explicit wait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        // Click on Good button
        clickTheElement(By.id("pollanswers-2"));
        // Click on Vote button
        clickTheElement(By.xpath("//button[@id=\"vote-poll-1\"]"));
        // Explicit wait to capture the disappearing text
        wait.until(ExpectedConditions.elementToBeClickable(By.id("block-poll-vote-error-1")));
        // Get Error message Text
        String actualMessageForCommunityPoll = getTextFromElement(By.id("block-poll-vote-error-1"));
        // Assert to verify if the actual message is same as expected message
        Assert.assertEquals(actualMessageForCommunityPoll, expectedMsg_CommunityPoll, "Community poll is not working");
    }

    @Test
    public static void verifyUserShouldBeAbleToEmailAFriendSuccessfully()
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        // Click on add to cart button of Apple MacBook Pro 13-inch
        clickTheElement(By.xpath("//div[@class=\"product-grid home-page-product-grid\"]/div[2]/div[2]/div[1]/div[2]/div[3]/div[2]/button[1]"));
        // Click on Email a friend button
        wait.until(ExpectedConditions.elementToBeClickable(By.className("email-a-friend")));
        clickTheElement(By.className("email-a-friend"));
        // Type friend's email
        typeText(By.id("FriendEmail"), "FriendTest@gmail.com");
        // Type your email address
        typeText(By.id("YourEmailAddress"), ("Test@gmail.com"));
        // Type Personal message
        typeText(By.id("PersonalMessage"), ("This product is really good, try it out"));
        // Click on Send Email button
        clickTheElement(By.name("send-email"));
        // Capture message displayed to user
        String actualEmailAFriendMessage = getTextFromElement(By.xpath("//div[@class= \"message-error validation-summary-errors\"]"));
        // Assert to verify actual message is same as Expected message
        Assert.assertEquals(actualEmailAFriendMessage, expectedEmailAFriendMessage, "Email A Friend' feature is not working");

    }

    @Test
    public static void verifyUserShouldBeAbleToCompareTwoProductsAndClearTheProductComparisonListSuccessfully() //throws InterruptedException
    {
        // Implying explicit wait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        String expectedNoItemsToCompareMessage = "No items in the list to compare";

        // Click on add to compare for Product 1 - HTC One M8 Android L 5.0 Lollipop
        clickTheElement(By.xpath("//div[@class=\"product-grid home-page-product-grid\"]/div[2]/div[3]/div[1]/div[2]/div[3]/div[2]/button[2]"));

        // Close the Green coloured success notification bar
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class=\"bar-notification success\"]/span")));
        clickTheElement(By.xpath("//div[@class=\"bar-notification success\"]/span"));

        // Click on add to compare for product 2 - $25 Virtual Gift Card - from homepage (featured products)
        //      Thread.sleep(3000);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class=\"bar-notification success\"]/span")));
        clickTheElement(By.xpath("//div[@class=\"product-grid home-page-product-grid\"]/div[2]/div[4]/div[1]/div[2]/div[3]/div[2]/button[2]"));

        // Click on Green colour success notification bar to open the Product comparison list
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class=\"bar-notification success\"]/p/a")));
        clickTheElement(By.xpath("//div[@class=\"bar-notification success\"]/p/a"));

        // Capture & print the name of product 1 in product comparison
        String productName1 = getTextFromElement(By.xpath("//table[@class=\"compare-products-table\"]/tbody/tr[3]/td[2]/a"));
        System.out.println("Product 1 name in comparison list is: " + productName1);

        // Capture & print the name of product 2 in product comparison
        String productName2 = getTextFromElement(By.xpath("//table[@class=\"compare-products-table\"]/tbody/tr[3]/td[3]/a"));
        System.out.println("Product 2 name in comparison list is: " + productName2);

        // Click on clear list
        clickTheElement(By.xpath("//div[@class=\"page-body\"]/a"));

        // Capture the message when no products in product comparison
        String actualNoItemsToCompareMessage = getTextFromElement(By.className("no-data"));
        System.out.print("\nEmpty comparison list display message: ");

        // Verify the display message for empty product comparison
        Assert.assertEquals(actualNoItemsToCompareMessage, expectedNoItemsToCompareMessage, "Wrong blank comparison display message");

    }

    @Test
    public static void verifyUserCanSeeTheCorrectProductInShoppingCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        // Click on Electronics menu
        clickTheElement(By.xpath("//ul[@class=\"top-menu notmobile\"]/li[2]/a"));
        // Click on Camera & Photo
        clickTheElement(By.xpath("//div[@class=\"item-grid\"]/div[1]/div[1]/h2[1]/a[1]"));
        // Capture & print the name of product Leica T Mirrorless Digital Camera
        String productNameFromProductListPage = getTextFromElement(By.xpath("//div[@class=\"item-grid\"]/div[3]/div[1]/div[2]/h2[1]/a"));
        System.out.println("Name of product on the product list page: " + productNameFromProductListPage);
        // Click on Add to cart button
        clickTheElement(By.xpath("//div[@class=\"item-grid\"]/div[3]/div[1]/div[2]/div[3]/div[2]/button[1]"));
        // Open the shopping cart from notification bar
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class=\"content\"]/a")));
        clickTheElement(By.xpath("//p[@class=\"content\"]/a"));
        // Capture & print the name of product in the shopping cart
        String productNameInShoppingCart = getTextFromElement(By.className("product-name"));
        System.out.println("Name of the added product on the shopping cart page: " + productNameInShoppingCart);
        // Assert to verify the product in the shopping cart is same as the product added to the shopping cart
        Assert.assertEquals(productNameFromProductListPage, productNameInShoppingCart, "Correct product is not added to the cart");

    }

    @Test
    public static void verifyRegisteredUserShouldBeAbleToReferAProductSuccessfullyToAFriend() //throws InterruptedException
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        //Click on register button
        clickTheElement(By.className("ico-register"));
        // Type first name
        typeText(By.id("FirstName"), "TestFirstName");
        // Type last name
        typeText(By.xpath("//input[@id=\"LastName\"]"), "TestLastName");
        // Type email
        typeText(By.xpath("//input[@id=\"Email\"]"), "TestEmail5@gmail.com");
        // Type password
        typeText(By.xpath("//input[@name=\"Password\"]"), "test@5");
        // Type confirm password
        typeText(By.id("ConfirmPassword"), "test@5");
        // Click on register button
        clickTheElement(By.name("register-button"));
        // Click on login link on homepage
        clickTheElement(By.xpath("//a[@href=\"/login?returnUrl=%2F\"]"));
        // Type your email
        typeText(By.id("Email"), "TestEmail5@gmail.com");
        // Type your password
        typeText(By.id("Password"), "test@5");
        // Click on login button
        clickTheElement(By.xpath("//div[@class=\"buttons\"]//button[@class=\"button-1 login-button\"]"));
        // Click on add to cart - Apple MacBook Pro 13-inch
        clickTheElement(By.xpath("//div[@class=\"product-grid home-page-product-grid\"]/div[2]/div[2]/div[1]/div[2]/div[3]/div[2]/button[1]"));
        // Click on email a friend
        //       Thread.sleep(3000);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class=\"email-a-friend\"]/button")));
        clickTheElement(By.xpath("//div[@class=\"email-a-friend\"]/button"));
        // Type friend's email
        typeText(By.id("FriendEmail"), "friendtest@gmail.com");
        // Type your email
        typeText(By.id("YourEmailAddress"), " ");
        // Write the personal message to refer the product to your friend
        typeText(By.id("PersonalMessage"), ("This product is really good, try it out"));
        // Click on Send email
        clickTheElement(By.name("send-email"));
        // Capture & print the message displayed
        String message1ProductName_ReferAProduct = getTextFromElement(By.xpath("//a[@href=\"/apple-macbook-pro-13-inch\"]"));
        String message2_ReferAProduct = getTextFromElement(By.xpath("//div[@class=\"page-body\"]/div[2]"));
        System.out.println("Message displayed to the user on referring a product to a friend: \n" + message1ProductName_ReferAProduct);
        System.out.println(message2_ReferAProduct);
        // Assert to verify if the actual display message matches the expected display message
        Assert.assertEquals(message2_ReferAProduct, expectedReferAFriendMessage, "The display message is not same");

    }

    @Test
    public static void verifyRegisteredUserShouldBeAbleToVoteSuccessfully() //throws InterruptedException
    {
        String expectedTotalVoteCountMessage = "18 votes";

        //Implying Explicit wait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        //Click on register button
        clickTheElement(By.className("ico-register"));

        // Type first name
        typeText(By.id("FirstName"), "TestFirstName");

        // Type last name
        typeText(By.xpath("//input[@id=\"LastName\"]"), "TestLastName");

        // Type email
        typeText(By.xpath("//input[@id=\"Email\"]"), "TestEmail5@gmail.com");

        // Type password
        typeText(By.xpath("//input[@name=\"Password\"]"), "test@5");

        // Type confirm password
        typeText(By.id("ConfirmPassword"), "test@5");

        // Click on register button
        clickTheElement(By.name("register-button"));

        // click on login link on homepage
        clickTheElement(By.xpath("//a[@href=\"/login?returnUrl=%2F\"]"));

        // Type Email in returning customer
        typeText(By.id("Email"), "TestEmail5@gmail.com");

        // Type password
        typeText(By.id("Password"), "test@5");

        // Click on log in button
        clickTheElement(By.xpath("//button[@class=\"button-1 login-button\"]"));

        // Click on Good radio button
        //      Thread.sleep(3000);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("pollanswers-2")));
        clickTheElement(By.id("pollanswers-2"));

        // Click on Vote button
        clickTheElement(By.xpath("//button[@id=\"vote-poll-1\"]"));

        // Capturing the text on Vote poll for registered customers
        //      Thread.sleep(3000);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//ul[@class=\"poll-results\"]/li[1]")));
        String actualVoteExcellentMessage = getTextFromElement(By.xpath("//ul[@class=\"poll-results\"]/li[1]"));
        String actualVoteGoodMessage = getTextFromElement(By.xpath("//ul[@class=\"poll-results\"]/li[2]"));
        String actualVotePoorMessage = getTextFromElement(By.xpath("//ul[@class=\"poll-results\"]/li[3]"));
        String actualVoteVeryBadMessage = getTextFromElement(By.xpath("//ul[@class=\"poll-results\"]/li[4]"));
        String actualCountMessage = getTextFromElement(By.className("poll-total-votes"));

        // Printing the message displayed to registered user on vote poll
        System.out.println("Message displayed to registered user after vote poll: ");
        System.out.println("Actual message for Excellent Vote: " + actualVoteExcellentMessage);
        System.out.println("Actual message for Good Vote: " + actualVoteGoodMessage);
        System.out.println("Actual message for Poor Vote: " + actualVotePoorMessage);
        System.out.println("Actual message for Very bad Vote: " + actualVoteVeryBadMessage);
        System.out.println("Total no of votes: " + actualCountMessage);
        Assert.assertEquals(actualCountMessage, expectedTotalVoteCountMessage, "Community poll is not giving the correct count for registered users vote");

    }


}


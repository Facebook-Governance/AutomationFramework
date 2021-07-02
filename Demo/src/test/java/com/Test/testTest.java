public testTest(){
WebDriver driver = new WebDriver();
driver ChromeDriver= new ChromeDriver();
String URL = "http://qainterview.pythonanywhere.com/";
DataProperties dataProperteies;
driver.get(URL);
driver.findElements(by.id).sendkeys("10");


driver.findElements(by.id).click();





}
using System;
using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using OpenQA.Selenium;
using TestyForC.Web;

namespace TestyForC
{
    [TestClass]
    public class WebLocatorTest
    {
        [TestMethod]
        //[TestCase(1, 1)]
        public void TestWebLocator()
        {
            WebLocator w = new WebLocator()
                //.setRoot("//ROOT")
                .setContainer(new WebLocator())
                .setText("Test", new List<SearchType> { new SearchType().Equals() })
                .setTag("table")
                .setClasses("", "");
                ;
            Console.WriteLine("XPath: " + w.XPath());
            //Assert.AreEqual(a, b);

            //IWebDriver driver = new ChromeDriver();
            //driver.Navigate().GoToUrl("http://qa-nimbus.sdl.com/task-inbox/");
            //driver.Manage().Window.Maximize();
            //driver.Close();
            //driver.Quit();
        }
    }
}

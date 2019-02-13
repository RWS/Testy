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
        public void TestWebLocator()
        {
            WebLocator w = new WebLocator()
                //.setRoot("//ROOT")
                .setContainer(new WebLocator())
                .setText("Test", new List<SearchType> { new SearchType().Equals() })
                .setTag("table")
                ;
            Console.WriteLine("XPath: " + w.XPath());
         
            //IWebDriver driver = new ChromeDriver();
            //driver.Navigate().GoToUrl("http://qa-nimbus.sdl.com/task-inbox/");
            //driver.Manage().Window.Maximize();
            //driver.Close();
            //driver.Quit();
        }
    }
}

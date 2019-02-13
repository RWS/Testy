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
        //[TestMethod]
        [DataTestMethod]
        [DataRow("//*", "//**")]
        public void TestWebLocator(String el, String xpath)
        {
            WebLocator w = new WebLocator()
                //.setRoot("//ROOT")
                .setContainer(new WebLocator())
                .setText("Test", new List<SearchType> { new SearchType().Equals() })
                .setTag("table")
                ;
            Console.WriteLine("XPath: " + w.XPath());
            Assert.AreEqual(el, xpath);

            //IWebDriver driver = new ChromeDriver();
            //driver.Navigate().GoToUrl("http://qa-nimbus.sdl.com/task-inbox/");
            //driver.Manage().Window.Maximize();
            //driver.Close();
            //driver.Quit();
        }

        [DataTestMethod]
        [DynamicData(nameof(Data), DynamicDataSourceType.Property)]
        public void Test_Add_DynamicData_Property(int a, int b, int expected)
        {
          
            Assert.AreEqual(expected, a+b);
        }

        public static IEnumerable<object[]> Data
        {
            get
            {
                yield return new object[] { 1, 1, 2 };
                yield return new object[] { 12, 30, 42 };
                yield return new object[] { 14, 1, 15 };
            }
        }
    }
}

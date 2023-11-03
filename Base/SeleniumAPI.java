package com.tsui_base;

import org.openqa.selenium.WebElement;

public interface SeleniumAPI {
	/**
	 * This method will launch the Chromebrowser with given url
	 * @author Harish
	 * @param url
	 * @exception NullPointerException
	 */
	void setUp(String url);
	/**
	 * this method will launch the givenbrowser with given url
	 * @param browserName
	 * @param url
	 * @exception NullPointerException
	 */

	void setUp(browser browserName, String url);


	/**
	 * This will close the browser instance
	 */
	void close();

	/**
	 * This will quit the browser completely
	 */
	void quit();

	/**
	 * This will perform the webelement type
	 * Type -{Id,Tagname,classname,Linktext,PartialLinktext,Xpath,cssselector}
	 * @param type
	 * @param value
	 * @return Webelement
	 */

	WebElement element(Locators type ,String value);

	/**
	 * This method handles the windowHandling
	 * @param i
	 */

	void switchToWindow(int i);
	/**
	 * This method used to identify the dropdown by Value
	 * @param ele
	 * @param value
	 */
	void selectValue(WebElement ele, String value);


	/**
	 * This method used to identify the dropdown by Text
	 * @param ele
	 * @param text
	 */
	void selectText(WebElement ele,String text);
	/**
	 *This method used to identify the dropdown by Index

	 * @param ele
	 * @param position
	 */

	void selectIndex(WebElement ele,int position);


	/**
	 * This function will wait until the element is clickable and then click
	 * @param ele
	 */


	void click(WebElement ele);

	/**
	 * This function will wait until the element is ready and clear the existing value and type
	 * @param ele
	 * @param testdata
	 */

	void type(WebElement ele, String testdata);

	/**
	 * This function will wait until the element is ready and gets the input
	 * @param ele
	 */


	void appendText(WebElement ele ,String testdata);

	/**
	 * This function return the active page title
	 * @return String
	 */

	String getTitle();

	/**
	 * This function return the active page URL
	 * @return String
	 */

	String getUrl();

	/**
	 * This function return the element is visible or not
	 * @return boolean
	 */

	boolean isDisplayed(WebElement ele);


















}

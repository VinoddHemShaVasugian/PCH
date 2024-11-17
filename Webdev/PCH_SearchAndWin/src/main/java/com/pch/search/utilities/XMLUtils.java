package com.pch.search.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class XMLUtils {
	/**
	 * Returns the document from xml content contained
	 * in String or File object.
	 * @param xmlContent
	 * @return Document
	 */
	private static Document convertToDocument(final Object xmlContent){		
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder =null;
		Document document=null;		
		try{
			builder=builderFactory.newDocumentBuilder();
			if(xmlContent instanceof String){
				document=builder.parse((String)xmlContent);
			}else if(xmlContent instanceof File){
				document=builder.parse((File)xmlContent);
			}

		}catch(ParserConfigurationException pce){
			pce.printStackTrace();
		} catch (SAXException e) {			
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}

		return document;
	}

	public static Node getNodeWithXPath(String xmlContent,String xPathExpression){
		Document doc=convertToDocument(xmlContent);
		XPath xpath = XPathFactory.newInstance().newXPath();
		try {					
			return (Node)xpath.compile(xPathExpression).evaluate(doc,XPathConstants.NODE);

		} catch (XPathExpressionException e) {			
			e.printStackTrace();
			return null;
		}
	}

	public static String getNodeAttribute(Node n,String attribute){
		return ((Element)n).getAttribute(attribute);
	}

	public static String getXMLFileContent(File f){
		try{			
			String line;
			StringBuilder builder = new StringBuilder();
			BufferedReader br = new BufferedReader(new FileReader(f));
			while((line=br.readLine())!=null){
				builder.append(line.trim());
			}			
			br.close();
			return builder.toString(); 
		}catch(IOException ioe){
			CustomLogger.logException(ioe);
			throw new RuntimeException();
			//return null;
		}
	}
}

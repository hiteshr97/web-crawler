package com.example.capillarytechnolgies.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.capillarytechnolgies.DAO.WebCrawlerDAO;
import com.example.capillarytechnolgies.DAO.WebCrawlerRepository;
import com.example.capillarytechnolgies.response.PhoneNumberDTO;
import com.example.capillarytechnolgies.response.ResponseDTO;

@Service
public class ScraperServiceImpl implements ScraperService{
	
	@Autowired
	WebCrawlerRepository crawlerRepository;

	@Override
	public ResponseDTO getHtmlFromUrl(String url) {
		/*
		 * retunr the html as output and print all the urls present in console
		*/
		
		if(StringUtils.isNotBlank(url))
		{
            try {
            	//loading the HTML to a Document Object
				Document document = Jsoup.connect(url).get();
				
				//printing all the urls in doc
				Elements links = getAllLinks(document);
				links.forEach(e -> System.out.println(e.attr("href")));
				
				ResponseDTO dto = new ResponseDTO();
				dto.setUrl(url);
				dto.setHtml(document.html());
				return dto;
				
			} 
            catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public Elements getAllLinks(Document doc) {
		if(doc !=null)
		{
			Elements links = doc.select("a[href]");
			return links;
		}
		return null;
	}

	@Override
	public void recursivelyPrintAllLinks(String url) {
		/*
		 * recursive calling of function 
		 * will print the current node and move on to the inner nodes
		 * we can limit it to certain pages
		*/
		if(StringUtils.isNotBlank(url))
		{
			try {
            	//loading the HTML to a Document Object
				Document document = Jsoup.connect(url).get();
				
				//get links
				Elements links = getAllLinks(document);
				
				System.out.println("Following links are for : "+url);
				links.forEach(e -> System.out.println(e.attr("abs:href")));
				
				for(Element ele : links)
				{
					String link = ele.attr("abs:href");			
					recursivelyPrintAllLinks(link);				
				}			
				
			} 
            catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}

	@Override
	public List<String> getAllPhoneNumber(String url) {
		
		if(StringUtils.isNotBlank(url))
		{
			try {
            	//loading the HTML to a Document Object
				Document document = Jsoup.connect(url).get();
				return  getPhoneNumberFromDoc(document, url);
				
			} 
            catch (IOException e) {
				e.printStackTrace();
			}
		}		
		
		return null;
	}
	
	private List<String> getPhoneNumberFromDoc(Document doc, String url)
	{
		//regex
		Pattern pattern = Pattern.compile("^\\+?[0-9. ()-]{10,25}$");
		
		Elements phoneNumbersElements = doc.getElementsMatchingOwnText(pattern);
		
		List<String> phone_numbers = new ArrayList<String>();
		
		for(Element e : phoneNumbersElements)
		{
		    Matcher matcher = pattern.matcher(e.text());
		    if (matcher.find()) {
		        phone_numbers.add(matcher.group(0).trim());
		    }
		}
		return phone_numbers;
	}

	@Override
	public List<PhoneNumberDTO> getRecursivePhoneNumber(String url) {
		List<PhoneNumberDTO> phoneList = new ArrayList<PhoneNumberDTO>();
		return getRecursivePhoneNumber(url, phoneList);
	}
	
	public List<PhoneNumberDTO> getRecursivePhoneNumber(String url, List<PhoneNumberDTO> phoneList) {
				
		if(StringUtils.isNotBlank(url))
		{
			try {
            	//loading the HTML to a Document Object
				Document document = Jsoup.connect(url).get();
				
				//get links
				Elements links = getAllLinks(document);
				
				// collect phone number
				List<String> phoneNumber =  getPhoneNumberFromDoc(document, url);
				
				PhoneNumberDTO dto = new PhoneNumberDTO();
				dto.setPhoneNumber(phoneNumber);
				dto.setUrl(url);
								
				phoneList.add(dto);
				
				for(Element ele : links)
				{
					String link = ele.attr("abs:href");			
					getRecursivePhoneNumber(link, phoneList);			
				}			
				
			} 
            catch (IOException e) {
				e.printStackTrace();
			}
		}		
		
		return null;
	}

}

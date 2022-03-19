package com.example.capillarytechnolgies.service;

import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.example.capillarytechnolgies.response.PhoneNumberDTO;
import com.example.capillarytechnolgies.response.ResponseDTO;

public interface ScraperService 
{	
	ResponseDTO getHtmlFromUrl(String url);
	
	Elements getAllLinks(Document doc);
	
	void recursivelyPrintAllLinks(String url);
	
	List<String> getAllPhoneNumber(String url);
	
	List<PhoneNumberDTO> getRecursivePhoneNumber(String url);
}

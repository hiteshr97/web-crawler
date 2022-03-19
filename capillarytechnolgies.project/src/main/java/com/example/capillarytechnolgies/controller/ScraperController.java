package com.example.capillarytechnolgies.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.capillarytechnolgies.response.PhoneNumberDTO;
import com.example.capillarytechnolgies.response.ResponseDTO;
import com.example.capillarytechnolgies.service.ScraperService;

@RestController
public class ScraperController {
	
	@Autowired
    ScraperService scraperService;
	
	
	// return html as output
	// print all the  links present
    @GetMapping(path = "/")
    public ResponseDTO getHTML(@RequestParam("url") String url) {
        return  scraperService.getHtmlFromUrl(url);
    }
        
    // recursively print all the links
    @GetMapping(path = "/rec")
    public void getRecursiveLinks(@RequestParam("url") String url) {
        scraperService.recursivelyPrintAllLinks(url);
    }
    
    // recursively print all the phone number
    @GetMapping(path = "/rec-phone")
    public List<PhoneNumberDTO> getAllPhoneNumber(@RequestParam("url") String url) {
        return scraperService.getRecursivePhoneNumber(url);
    }
}

package com.example.capillarytechnolgies.DAO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class WebCrawlerDAO {
	@Id
    @GeneratedValue
    private int id;
    private String phoneNumber;
    private String link;
}

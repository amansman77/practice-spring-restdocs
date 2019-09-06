package com.ho.practice.restdocs;

import static org.springframework.restdocs.snippet.Attributes.key;

import org.springframework.restdocs.snippet.Attributes;

public interface DocumentFormatGenerator {
	
	static Attributes.Attribute getDateFormat() {
        return key("format").value("yyyy-MM-dd");
    }
	
	static Attributes.Attribute getQueryFormat() {
        return key("format").value("{key}={value},{key}={value},...");
    }
	
}

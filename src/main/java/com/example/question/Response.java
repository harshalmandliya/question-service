package com.example.question;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Response {
public Response(Integer id, String response) {
		super();
		this.id = id;
		this.response = response;
	}
@Id
private Integer id;
private String response;
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public String getResponse() {
	return response;
}
public void setResponse(String response) {
	this.response = response;
}
@Override
public String toString() {
	return "Response [id=" + id + ", response=" + response + "]";
}
}

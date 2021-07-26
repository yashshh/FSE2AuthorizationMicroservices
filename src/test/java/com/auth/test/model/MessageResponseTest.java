package com.auth.test.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;


import org.junit.jupiter.api.Test;

import com.auth.model.MessageResponse;



public class MessageResponseTest {

	MessageResponse messageResponse = new MessageResponse();

	MessageResponse message1 = new MessageResponse("response", "status");

	MessageResponse message2 = new MessageResponse(new Date(), "response", "status");

	@Test
	public void testMessage() {
		messageResponse.setMessage("message");
		messageResponse.setStatus("status");
		messageResponse.setTimeStamp(new Date());
		messageResponse.getTimeStamp();
		assertEquals(messageResponse.getMessage(), "message");
		assertEquals(messageResponse.getStatus(), "status");
	}

	@Test
	public void testCons() {
		assertEquals(message1.getMessage(), "response");
	}
	
//	@Test
//	void testTimeStamp() {
//		messageResponse.setTimeStamp(new Date());
//		assertEquals(messageResponse.getTimeStamp(), new Date());
//	}
//	@Test
//	void testStatus() {
//		messageResponse.setStatus("status");
//		assertEquals(messageResponse.getStatus(), "status");
//	}
//	
//	@Test
//	void testMessages() {
//		messageResponse.setMessage("message");
//		assertEquals(messageResponse.getMessage(), "message");
//	}
}

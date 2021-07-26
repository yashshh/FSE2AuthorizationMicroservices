package com.auth.test.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.auth.dao.UserDAO;
import com.auth.service.JwtUtil;

@SpringBootTest
public class JwUtilTest {

	UserDetails userdetails;

	@InjectMocks
	JwtUtil jwtUtil;

	@Mock
	UserDAO userservice;

	@Test
	public void generateTokenTest() {
		ArrayList<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();
        list.add(new SimpleGrantedAuthority("admin"));
		userdetails = new User("admin", "admin", list);
		String generateToken = jwtUtil.generateToken(userdetails);
		assertNotNull(generateToken);
	}

	@Test
	public void validateTokenTest() {
		ArrayList<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();
        list.add(new SimpleGrantedAuthority("admin"));
		userdetails = new User("admin", "admin", list);
		String generateToken = jwtUtil.generateToken(userdetails);
		Boolean validateToken = jwtUtil.validateToken("Bearer " + generateToken);
		assertEquals(true, validateToken);
	}

	@Test
	public void validateTokenFails1() {
		ArrayList<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();
        list.add(new SimpleGrantedAuthority("admin"));
		userdetails = new User("admin", "admin", list);
		String generateToken = jwtUtil.generateToken(userdetails);
		Boolean validateToken = jwtUtil.validateToken("bearer" + generateToken);
		assertEquals(false, validateToken);
	}

	@Test
	public void validateTokenFails2() {
		ArrayList<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();
        list.add(new SimpleGrantedAuthority("admin"));
		userdetails = new User("admin", "admin", list);
		Boolean validateToken = jwtUtil.validateToken("Bearer ");
		assertEquals(false, validateToken);
	}

	@Test
	public void getUserNameTest() {
		ArrayList<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();
        list.add(new SimpleGrantedAuthority("admin"));
		userdetails = new User("admin", "admin", list);
		String generateToken = jwtUtil.generateToken(userdetails);
		String userName= jwtUtil.extractUsername("Bearer "+generateToken);
		assertEquals(userdetails.getUsername(),userName);
	}

	@Test
	public void getUserNameFails1() {
		assertNull(jwtUtil.extractUsername("tokenSample"));
	}

	@Test
	public void getUserNameFails2() {
		assertNull(jwtUtil.extractUsername("Bearer arfd"));
	}
}

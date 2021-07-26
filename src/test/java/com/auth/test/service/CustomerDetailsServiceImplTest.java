package com.auth.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.auth.controller.UnauthorizedException;
import com.auth.dao.UserDAO;
import com.auth.model.AuthResponse;
import com.auth.model.UserData;
import com.auth.service.CustomerDetailsServiceImpl;
import com.auth.service.JwtUtil;

@SpringBootTest
public class CustomerDetailsServiceImplTest {
	@InjectMocks
	CustomerDetailsServiceImpl customerDetailsServiceImpl;
	@Mock
	UserDAO userdao;
	@Mock
	JwtUtil jwtutil;
	@Test
	void loadUserByUsernameTest()
	{
		UserData user1 = new UserData("admin", "admin", "admin",null,"admin",null,null,null);
		Optional<UserData> data = Optional.of(user1);
		when(userdao.findById("admin")).thenReturn(data);
		UserDetails loadUserByUsername2 = customerDetailsServiceImpl.loadUserByUsername("admin");
		assertEquals(user1.getUserid(), loadUserByUsername2.getUsername());
	}
	@Test
	void loadUserByUsernameFailedTest()
	{
		UserData user1 = new UserData("admin", "admin", "admin",null,"admin",null,null,null);
		Optional<UserData> data = Optional.of(user1);
		when(userdao.findById("admin")).thenReturn(data);
		assertThrows(UnauthorizedException.class,()->customerDetailsServiceImpl.loadUserByUsername("a"));
	}
	@Test
	void loginTest()
	{
		UserData user = new UserData("admin", "pwd", "admin",null,"admin",null,null,null);
		Optional<UserData> data = Optional.of(user);
		when(userdao.findById("admin")).thenReturn(data);
		UserDetails value = customerDetailsServiceImpl.loadUserByUsername("admin");
		when(jwtutil.generateToken(value)).thenReturn("token");
		UserData result = new UserData("admin",null,null, "token","admin",null,null,null);
		when(jwtutil.generateToken(value)).thenReturn("token");
		assertEquals(customerDetailsServiceImpl.login(user).getUname(),result.getUname());
	}
	@Test
	void loginFailedTest()
	{
		UserData user = new UserData("admin", "pwd", "admin",null,"admin",null,null,null);
		UserData user1 = new UserData("admin", "p", "admin",null,"admin",null,null,null);
		Optional<UserData> data = Optional.of(user);
		when(userdao.findById("admin")).thenReturn(data);
		UserDetails value = customerDetailsServiceImpl.loadUserByUsername("admin");
		when(jwtutil.generateToken(value)).thenReturn("token");
		when(jwtutil.generateToken(value)).thenReturn("token");
		assertThrows(UnauthorizedException.class,()->customerDetailsServiceImpl.login(user1));
	}
	@Test
	void testGetValidity()
	{
		 UserData user = new UserData("admin", "pwd", "admin",null,"admin",null,null,null);
		  Optional<UserData> data = Optional.of(user);
		  when(jwtutil.validateToken("token")).thenReturn(true);
		  when(userdao.findById(jwtutil.extractUsername("token"))).thenReturn(data);
		  AuthResponse auth = new AuthResponse("admin","admin",true,"admin","admin"); 
		  assertEquals(customerDetailsServiceImpl.getValidity("token").getName(),auth.getName()); 
	}
	@Test
	void getValidityFailedTest()
	{
		 when(jwtutil.validateToken("token")).thenReturn(false);
		 AuthResponse auth = new AuthResponse(null,null,false,null,"admin");
		 assertEquals(customerDetailsServiceImpl.getValidity("token").isValid(),auth.isValid()); 
	}
	
}

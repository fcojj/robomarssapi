package br.com.contaazul.francisco.robomarsapi.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MarsTest {
	
	  @Autowired
	  private Mars mars;

	   @Test
	   public void shouldReturnPosition02W() throws Exception {
		   //given
		   Position expected = new Position(0, 2, 'W', true);
		   
		  //when
		  Position result = mars.getCurrentPositionOfRobot("MML");
		  
		  //then
	       assertEquals(expected,result);
	   }
	   
	   
	   @Test
	   public void shouldReturnPositionNullWhenInvalidCommand() throws Exception {
		   //given
		   Position expected = null;
		   
		  //when
		  Position result = mars.getCurrentPositionOfRobot("AAA");
		  
		  //then
	       assertEquals(expected,result);
	   }
	   
	   
	   @Test
	   public void shouldReturnPositionNullWhenPositionInvalid() throws Exception {
		   //given
		   Position expected = null;
		   
		  //when
		  Position result = mars.getCurrentPositionOfRobot("MMLM");
		  
		  //then
	       assertEquals(expected,result);
	   }
}

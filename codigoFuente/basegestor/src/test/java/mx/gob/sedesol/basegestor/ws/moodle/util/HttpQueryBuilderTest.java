package mx.gob.sedesol.basegestor.ws.moodle.util;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import mx.gob.sedesol.basegestor.ws.moodle.util.HttpQueryBuilder;

public class HttpQueryBuilderTest {
	
	private class JsonTest{
		
		private class A{
			private Integer one = 1;
			private Integer two = 2;
			private Integer three = 3;
			public Integer getOne() {
				return one;
			}
			public void setOne(Integer one) {
				this.one = one;
			}
			public Integer getTwo() {
				return two;
			}
			public void setTwo(Integer two) {
				this.two = two;
			}
			public Integer getThree() {
				return three;
			}
			public void setThree(Integer three) {
				this.three = three;
			}
		}
		
		private A a = new A();
		List<Integer> b = new LinkedList<Integer>();
		private String c = "plain";
		
		{
			b.add(1);
			b.add(2);
			b.add(3);
		}

		public A getA() {
			return a;
		}

		public void setA(A a) {
			this.a = a;
		}
		
		public List<Integer> getB(){
			return b;
		}
		
		public void setB(LinkedList<Integer> b){
			this.b = b;
		}

		public String getC() {
			return c;
		}

		public void setC(String c) {
			this.c = c;
		}
		
	}
	private static final String jsonTest = 
			"{" +
				"\"a\": {" +
						"\"one\": 1," +
						"\"two\": 2," +
						"\"three\": 3" +
					"}," +
				"\"b\": [ 1, 2, 3 ]," +
				"\"c\": \"plain\"" +
			"}";
	
	private static final String expectedOutput = 
			"&a[one]=1&a[two]=2&a[three]=3&b[0]=1&b[1]=2&b[2]=3&c=plain";
	
	private HttpQueryBuilder builder = new HttpQueryBuilder();

	@Test
	public void testObjectToHttpBuildQuery() {
		JsonTest test = new JsonTest();
		try {
			String parsedJson = builder.ObjectToHttpBuildQuery(test);
			System.out.println(parsedJson);
			assertEquals(expectedOutput, parsedJson);
		} catch (IOException e) {
			e.printStackTrace();
			fail("No se ha podido parsear el objeto Json de prueba");
		}
	}

	@Test
	public void testJsonToHttpBuildQuery() {
		try {
			String parsedJson = builder.JsonToHttpBuildQuery(jsonTest);
			System.out.println(parsedJson);
			assertEquals(expectedOutput, parsedJson);
		} catch (IOException e) {
			e.printStackTrace();
			fail("No se ha podido parsear el objeto Json de prueba");
		}
	}

}

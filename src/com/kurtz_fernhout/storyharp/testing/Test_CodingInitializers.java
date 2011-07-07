package com.kurtz_fernhout.storyharp.testing;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

class MyClass {
	MyClass() {
		System.out.println("value of x:" + x);
	}
	{x = 5;}
	int x = 10;
	{x = 15;}

}

class MyClass2 {
	MyClass nestedClass = new MyClass();
	MyClass2() {
		System.out.println("value of x in nestedClass:" + nestedClass.x);
	}
}

// a check of how initializers work
public class Test_CodingInitializers {

	
	@Test public void test() {
		//MyClass a = new MyClass();
		MyClass2 b = new MyClass2();
		assertTrue(b != null);
	}

}

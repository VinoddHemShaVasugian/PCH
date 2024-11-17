package com.pch.search.utilities;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;

/**
 * 
 * Listener to re-order the test methods based on the given priority level
 * 
 * @author manis
 *
 */
public class PriorityListener implements IMethodInterceptor {

	// IMethodInterceptor will not work for the test methods which contains
	// dependsOnMethods and dependsOnGroups specifications
	// Interface will be invoked twice for a class. It's a bug in the TestNG.

	// If it is implemented we can call the test methods by using the group
	// name.

	public List<IMethodInstance> intercept(List<IMethodInstance> methods,
			ITestContext context) {

		Comparator<IMethodInstance> comparator = new Comparator<IMethodInstance>() {
			public int compare(IMethodInstance m1, IMethodInstance m2) {
				return m1.getMethod().getPriority()
						- m2.getMethod().getPriority();
			}
		};

		IMethodInstance[] array = methods.toArray(new IMethodInstance[methods
				.size()]);
		Arrays.sort(array, comparator);
		return Arrays.asList(array);
	}
}

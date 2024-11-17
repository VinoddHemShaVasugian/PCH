package com.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;

/**
 * 
 * Listener to re-order the test methods based on the given priority level. It
 * will create an user defined annotation to report the QC Test Id and Test Case
 * Name.
 * 
 * @author Maniganda Perumal
 *
 */
public class PriorityListener implements IMethodInterceptor {

	// IMethodInterceptor will not work for the test methods which contains
	// dependsOnMethods and dependsOnGroups specifications
	// Interface will be invoked twice for a class. It's a bug in the
	// TestNG.

	// If it is implemented we can call the test methods by using the group
	// name.

	public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {

		Comparator<IMethodInstance> comparator = new Comparator<IMethodInstance>() {
			public int compare(IMethodInstance m1, IMethodInstance m2) {
				return m1.getMethod().getPriority() - m2.getMethod().getPriority();
			}
		};

		IMethodInstance[] array = methods.toArray(new IMethodInstance[methods.size()]);
		Arrays.sort(array, comparator);
		return Arrays.asList(array);
	}

	// It's used to get the Test Case Name which is mapped to the QC ALM
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.METHOD })
	public @interface testCaseName {
		String test_case_name() default "";
	}

	// It's used to get the Test Case ID which is mapped to the QC ALM
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.METHOD })
	public @interface testId {
		String test_id() default "";
	}
}

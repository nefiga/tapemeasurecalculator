package com.randkprogramming.tapemeasurecalculator.calculator.mechanics;

/** A class for storing fractions and performing arithmetic operations on them.
 * @author Jay */
public class Fraction {

	public class ZeroDenominatorException extends Exception {
	}
	
	//--------------------
	// Members
	//--------------------
	private int numerator;
	private int denominator;
	
	//--------------------
	// Getters
	//--------------------
	/** @return The numerator value of the fraction. */
	public int getNumerator() { return this.numerator; }
	
	/** @return The denominator value of the fraction. */
	public int getDenominator() { return this.denominator; }
	
	/** @return The result of numerator / denominator */
	public double getDecimalRepresentation() {
		double n = (double) this.numerator;
		double d = (double) this.denominator;
		return n/d;
	}
	
	/** @return A new fraction that is the reciprocal of the given fraction. (The numerator and denominator switch places) */
	public Fraction getReciprocal() throws ZeroDenominatorException {
		return new Fraction(this.denominator, this.numerator);
	}
	
	/**
	 * @param decimal The decimal you want to convert.
	 * @param precision Rounds the answer to the closest given fraction.
	 *      e.g. if you give 1/16 as the round value you will get
	 *           an answer that is a multiple of 1/16 in simple reduced form.
	 * @return The resulting fraction in reduced form.
	 * @throws Fraction.ZeroDenominatorException if the denominator of the round parameter you provide is 0.
	 */
	public static Fraction getFractionFromDecimal(double decimal, Fraction precision) throws ZeroDenominatorException {
		double precisionValue = precision.getDecimalRepresentation();
		double roundOffValue = precisionValue / 2;
		
		int numerator = (int) ( (decimal + roundOffValue) / precisionValue );
		int denominator = precision.getDenominator();
		return new Fraction(numerator,denominator,false);
	}
	
	public static Fraction getFractionFromString(String fraction_string) throws ZeroDenominatorException {
		 
		StringBuilder sb = new StringBuilder();
		
		int i = 0;
		char c = fraction_string.charAt(i);
		
		while(c != '/') {
			sb.append(c);
			i++;
			c = fraction_string.charAt(i);
		}
		
		int numerator = Integer.parseInt(sb.toString());
		int denominator = Integer.parseInt(fraction_string.substring(i+1));

		return new Fraction(numerator,denominator);
	}

    /**
     * This method sets the text of the fraction part of the equation. By the time this method gets
     * called, the number should only be a decimal, (all whole numbers for inches will have been taken out of it).
     * If the numerator is 0 it returns an empty string.
     * @param decimal The remaining decimal value that needs to be converted to a fraction.
     */
    public static String getFractionStringFromDecimal(double decimal, Fraction precision) {

        String result = "";

        try {
            Fraction f = getFractionFromDecimal(decimal, precision);
            if(f.getNumerator() > 0) {
                result += f.getNumerator();
                result += "/";
                result += f.getDenominator();
            }
        } catch(Fraction.ZeroDenominatorException e) { e.printStackTrace();  }

        return result;
    }
	
	//--------------------
	// Set Values
	//--------------------
	/**
	 * Sets the values of the numerator and denominator respectively. Negative values are acceptable
	 * for either one, or both. Denominator can not be 0.
	 * @param n numerator
	 * @param d denominator
	 * @throws Fraction.ZeroDenominatorException
	 */
	private void setValues(int n, int d) throws ZeroDenominatorException {
		
		if(d == 0)
			throw new ZeroDenominatorException();
		
		if(d < 0) {
			n *= -1;
			d *= -1;
		}
		
		this.numerator = n;
		this.denominator = d;
		
	}
	
	//--------------------
	// Constructors
	//--------------------
	/** Empty constructor, does nothing. */
	public Fraction() {}
	
	/**
	 * This constructor works much like a copy or clone method. It returns a new Fraction that is equal to the given fraction, and reduces the result if necessary.
	 * @param f fraction
	 * @throws Fraction.ZeroDenominatorException
	 */
	public Fraction(Fraction f) throws ZeroDenominatorException {
		setValues(f.numerator,f.denominator);
		simplify();
	}
	
	/**
	 * Creates a new Fraction from the given parameters and reduces the result.
	 * @param n numerator
	 * @param d denominator
	 * @throws Fraction.ZeroDenominatorException
	 */
    public Fraction(int n, int d) {
        this(n,d,true);
    }
    public Fraction(int n, int d, boolean simplify) {
        try {
            setValues(n,d);
            if(simplify) {
                simplify();
            }
        } catch(ZeroDenominatorException e) {
            e.printStackTrace();
        }

    }
	
	//--------------------
	// Add
	//--------------------
	/**
	 * The result of adding the two fractions together.
	 * This method does not modify the original fractions.
	 * @return a new Fraction containing the result in simplest form.
	 * @throws Fraction.ZeroDenominatorException
	 */
	public static Fraction add(final Fraction leftSide, final Fraction rightSide) throws ZeroDenominatorException {
		
		Fraction ls = new Fraction(leftSide);
		Fraction rs = new Fraction(rightSide);

		findCommonDenominator(ls,rs);
		
		int n = ls.numerator + rs.numerator;
		int d = ls.denominator;
		
		return new Fraction(n,d);
		
	}
	
	
	//--------------------
	// Subtract
	//--------------------
	/**
	 * The result of subtracting the right side from the left. 
	 * This method does not modify the original fractions.
	 * @return a new Fraction containing the result in simplest form.
	 * @throws Fraction.ZeroDenominatorException
	 */
	public static Fraction subtract(final Fraction leftSide, final Fraction rightSide) throws ZeroDenominatorException {
		
		Fraction ls = new Fraction(leftSide);
		Fraction rs = new Fraction(rightSide);

		findCommonDenominator(ls, rs);
		
		int n = ls.numerator - rs.numerator;
		int d = ls.denominator;
		
		return new Fraction(n,d);
		
	}
	
	
	//----------------------------
	// Find Common Denominator
	//----------------------------
	/**
	 * Modifies both fractions so that the denominators are equal. This function is called before adding and subtracting fractions.
	 * Specifically, the denominators are multiplied together to find the new denominator, and the numerators are multiplied
	 * by the corresponding ratio.
	 */
	private static void findCommonDenominator(Fraction leftSide, Fraction rightSide) {
		if(leftSide.denominator != rightSide.denominator) {
			int commonDenominator = leftSide.denominator * rightSide.denominator;

			leftSide.numerator = leftSide.numerator * rightSide.denominator;
			rightSide.numerator = rightSide.numerator * leftSide.denominator;

			leftSide.denominator = commonDenominator;
			rightSide.denominator = commonDenominator;	
		}
	}
	
	
	//--------------------
	// Multiply
	//--------------------
	/**
	 * Multiplies numerators together and denominators together and returns a new fraction as the result.
	 * Does not modify the original fractions.
	 * @return a new Fraction containing the result in simplest form.
	 * @throws Fraction.ZeroDenominatorException
	 */
	public static Fraction multiply(final Fraction leftSide, final Fraction rightSide) throws ZeroDenominatorException {
		int n = leftSide.numerator * rightSide.numerator;
		int d = leftSide.denominator * rightSide.denominator;
		return new Fraction(n,d);
	}

	
	//--------------------
	// Divide
	//--------------------
	/**
	 * Does not modify the fractions. It returns a new fraction as the result.
	 * Dividing a fraction by another fraction yields the same result as multiplying
	 * the fraction by the other fraction's reciprocal (3/4 DIVIDE 2/3 == 3/4 MULTIPLY 3/2)
	 * @return a new Fraction containing the result in simplest form.
	 * @throws Fraction.ZeroDenominatorException
	 */
	public static Fraction divide(final Fraction leftSide, final Fraction rightSide) throws ZeroDenominatorException {
		int n = leftSide.numerator * rightSide.denominator;
		int d = leftSide.denominator * rightSide.numerator;
		return new Fraction(n,d);
	}
	
	
	//--------------------
	// Simplify
	//--------------------
	/**
	 * Reduces a fraction to its simplest form by finding the greatest common divisor of the numerator and denominator
	 * and then divides each one by that number. If there is none, the result stays the same.
	 * @throws Fraction.ZeroDenominatorException
	 */
	public void simplify() throws ZeroDenominatorException {
		int greatestCommonDivisor = findGCD(this.numerator,this.denominator);
		this.numerator /= greatestCommonDivisor;
		this.denominator /= greatestCommonDivisor;
	}
	
	
	//--------------------
	// Find GCD
	//--------------------
	/** Finds the greatest common divisor of two integers. If there is none, the result is 1. */
	public static int findGCD(int a, int b) {
		if (b == 0)
			return a;
		else
			return findGCD(b, a % b);
	}
	
	
	//--------------------
	// To String
	//--------------------
	/** Returns a string representation of the fraction. */
	@Override
	public String toString() {

		int wholeNumber = numerator / denominator;
		int displayedNumerator = 0;
		if(wholeNumber > 0) {
			displayedNumerator = numerator - (wholeNumber * denominator);
			if(displayedNumerator == 0)
				return wholeNumber + "";
			else
				return wholeNumber + " " + displayedNumerator + "/" + denominator;
		}
		
		return numerator + "/" + denominator;
	}
	
	
	//--------------------
	// Hash Code
	//--------------------
	/** Returns a hashcode value for the fraction */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + denominator;
		result = prime * result + numerator;
		return result;
	}
	
	
	//--------------------
	// Equals
	//--------------------
	/** Checks to see whether two fractions are equal or not. A fraction
	 * is equal if both the numerator and denominator are the same when in reduced form.*/
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		
		Fraction other = (Fraction) obj;
		
		try {
			Fraction leftSide = new Fraction(this);
			Fraction rightSide = new Fraction(other);
			
			leftSide.simplify();
			rightSide.simplify();

			if (leftSide.numerator != rightSide.numerator) return false;
			if (leftSide.denominator != rightSide.denominator) return false;
			
		} catch(ZeroDenominatorException e) { e.printStackTrace(); }
		
		return true;
	}
	
}
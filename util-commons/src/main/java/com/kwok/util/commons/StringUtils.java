package com.kwok.util.commons;

import java.util.Arrays;

/**
 * See org.apache.flink:flink-core:org.apache.flink.util.StringUtils
 * 
 * @author Kwok
 */
public class StringUtils {
	
	private static final char[] HEX_CHARS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	
	/**
	 * Given an array of bytes it will convert the bytes to a hex string
	 * representation of the bytes.
	 *
	 * @param bytes
	 *        the bytes to convert in a hex string
	 * @param start
	 *        start index, inclusively
	 * @param end
	 *        end index, exclusively
	 * @return hex string representation of the byte array
	 */
	public static String byteToHexString(final byte[] bytes, final int start, final int end) {
		if (bytes == null) {
			throw new IllegalArgumentException("bytes == null");
		}

		int length = end - start;
		char[] out = new char[length * 2];

		for (int i = start, j = 0; i < end; i++) {
			out[j++] = HEX_CHARS[(0xF0 & bytes[i]) >>> 4];
			out[j++] = HEX_CHARS[0x0F & bytes[i]];
		}

		return new String(out);
	}

	/**
	 * Given an array of bytes it will convert the bytes to a hex string
	 * representation of the bytes.
	 *
	 * @param bytes
	 *        the bytes to convert in a hex string
	 * @return hex string representation of the byte array
	 */
	public static String byteToHexString(final byte[] bytes) {
		return byteToHexString(bytes, 0, bytes.length);
	}

	/**
	 * Given a hex string this will return the byte array corresponding to the
	 * string .
	 *
	 * @param hex
	 *        the hex String array
	 * @return a byte array that is a hex string representation of the given
	 *         string. The size of the byte array is therefore hex.length/2
	 */
	public static byte[] hexStringToByte(final String hex) {
		final byte[] bts = new byte[hex.length() / 2];
		for (int i = 0; i < bts.length; i++) {
			bts[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return bts;
	}
	
	public static String arrayAwareToString(Object o) {
		if (o == null) {
			return "null";
		}
		if (o.getClass().isArray()) {
			return arrayToString(o);
		}

		return o.toString();
	}

	/**
	 * Returns a string representation of the given array. This method takes an Object
	 * to allow also all types of primitive type arrays.
	 *
	 * @param array The array to create a string representation for.
	 * @return The string representation of the array.
	 * @throws IllegalArgumentException If the given object is no array.
	 */
	public static String arrayToString(Object array) {
		if (array == null) {
			throw new NullPointerException();
		}

		if (array instanceof int[]) {
			return Arrays.toString((int[]) array);
		}
		if (array instanceof long[]) {
			return Arrays.toString((long[]) array);
		}
		if (array instanceof Object[]) {
			return Arrays.toString((Object[]) array);
		}
		if (array instanceof byte[]) {
			return Arrays.toString((byte[]) array);
		}
		if (array instanceof double[]) {
			return Arrays.toString((double[]) array);
		}
		if (array instanceof float[]) {
			return Arrays.toString((float[]) array);
		}
		if (array instanceof boolean[]) {
			return Arrays.toString((boolean[]) array);
		}
		if (array instanceof char[]) {
			return Arrays.toString((char[]) array);
		}
		if (array instanceof short[]) {
			return Arrays.toString((short[]) array);
		}

		if (array.getClass().isArray()) {
			return "<unknown array type>";
		} else {
			throw new IllegalArgumentException("The given argument is no array.");
		}
	}
	
	/** Prevent instantiation of this utility class. */
	private StringUtils() {}

}

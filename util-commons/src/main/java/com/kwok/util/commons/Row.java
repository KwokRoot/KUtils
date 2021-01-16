package com.kwok.util.commons;

import java.io.Serializable;
import java.util.Arrays;

/**
 * See org.apache.flink:flink-core:org.apache.flink.types.Row
 * 
 * @author Kwok
 */
public class Row implements Serializable{

 	private static final long serialVersionUID = 1L;

 	/** The array to store actual values. */
 	private final Object[] fields;

 	/**
 	 * Create a new Row instance.
 	 * @param arity The number of fields in the Row
 	 */
 	public Row(int arity) {
 		this.fields = new Object[arity];
 	}

 	/**
 	 * Get the number of fields in the Row.
 	 * @return The number of fields in the Row.
 	 */
 	public int getArity() {
 		return fields.length;
 	}

 	/**
 	 * Gets the field at the specified position.
 	 * @param pos The position of the field, 0-based.
 	 * @return The field at the specified position.
 	 * @throws IndexOutOfBoundsException Thrown, if the position is negative, or equal to, or larger than the number of fields.
 	 */
 	public Object getField(int pos) {
 		return fields[pos];
 	}

 	/**
 	 * Sets the field at the specified position.
 	 *
 	 * @param pos The position of the field, 0-based.
 	 * @param value The value to be assigned to the field at the specified position.
 	 * @throws IndexOutOfBoundsException Thrown, if the position is negative, or equal to, or larger than the number of fields.
 	 */
 	public void setField(int pos, Object value) {
 		fields[pos] = value;
 	}

 	@Override
 	public String toString() {
 		StringBuilder sb = new StringBuilder();
 		for (int i = 0; i < fields.length; i++) {
 			if (i > 0) {
 				sb.append(",");
 			}
 			sb.append(StringUtils.arrayAwareToString(fields[i]));
 		}
 		return sb.toString();
 	}

 	@Override
 	public boolean equals(Object o) {
 		if (this == o) {
 			return true;
 		}
 		if (o == null || getClass() != o.getClass()) {
 			return false;
 		}

 		Row row = (Row) o;

 		return Arrays.deepEquals(fields, row.fields);
 	}

 	@Override
 	public int hashCode() {
 		return Arrays.deepHashCode(fields);
 	}

 	/**
 	 * Creates a new Row and assigns the given values to the Row's fields.
 	 * This is more convenient than using the constructor.
 	 *
 	 * <p>For example:
 	 *
 	 * <pre>
 	 *     Row.of("hello", true, 1L);}
 	 * </pre>
 	 * instead of
 	 * <pre>
 	 *     Row row = new Row(3);
 	 *     row.setField(0, "hello");
 	 *     row.setField(1, true);
 	 *     row.setField(2, 1L);
 	 * </pre>
 	 *
 	 */
 	public static Row of(Object... values) {
 		Row row = new Row(values.length);
 		for (int i = 0; i < values.length; i++) {
 			row.setField(i, values[i]);
 		}
 		return row;
 	}

 	/**
 	 * Creates a new Row which copied from another row.
 	 * This method does not perform a deep copy.
 	 *
 	 * @param row The row being copied.
 	 * @return The cloned new Row
 	 */
 	public static Row copy(Row row) {
 		final Row newRow = new Row(row.fields.length);
 		System.arraycopy(row.fields, 0, newRow.fields, 0, row.fields.length);
 		return newRow;
 	}

 	/**
 	 * Creates a new Row with projected fields from another row.
 	 * This method does not perform a deep copy.
 	 *
 	 * @param fields fields to be projected
 	 * @return the new projected Row
 	 */
 	public static Row project(Row row, int[] fields) {
 		final Row newRow = new Row(fields.length);
 		for (int i = 0; i < fields.length; i++) {
 			newRow.fields[i] = row.fields[fields[i]];
 		}
 		return newRow;
 	}

 	/**
 	 * Creates a new Row which fields are copied from the other rows.
 	 * This method does not perform a deep copy.
 	 *
 	 * @param first The first row being copied.
 	 * @param remainings The other rows being copied.
 	 * @return the joined new Row
 	 */
 	public static Row join(Row first, Row... remainings) {
 		int newLength = first.fields.length;
 		for (Row remaining : remainings) {
 			newLength += remaining.fields.length;
 		}

 		final Row joinedRow = new Row(newLength);
 		int index = 0;

 		// copy the first row
 		System.arraycopy(first.fields, 0, joinedRow.fields, index, first.fields.length);
 		index += first.fields.length;

 		// copy the remaining rows
 		for (Row remaining : remainings) {
 			System.arraycopy(remaining.fields, 0, joinedRow.fields, index, remaining.fields.length);
 			index += remaining.fields.length;
 		}

 		return joinedRow;
 	}
 }
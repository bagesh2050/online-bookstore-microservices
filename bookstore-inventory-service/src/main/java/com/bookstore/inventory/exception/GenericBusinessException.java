package com.bookstore.inventory.exception;

@SuppressWarnings("serial")
public class GenericBusinessException extends Exception {
	protected final ReturnCode code;
	protected final String description;

	/**
	 * @param code
	 *            ReturnCode
	 */
	public GenericBusinessException(ReturnCode code) {
		this(code, "", null);
	}

	/**
	 * @param code
	 *            ReturnCode
	 * @param description
	 *            the description of the business error case
	 */
	public GenericBusinessException(ReturnCode code, String description) {
		this(code, description, null);
	}

	/**
	 * @param code
	 *            ReturnCode
	 * @param cause
	 *            the cause of the exception
	 */
	public GenericBusinessException(ReturnCode code, Throwable cause) {
		super(null, cause);
		this.code = code;
		this.description = "";
	}

	/**
	 * @param code
	 *            ReturnCode
	 * @param description
	 *            the description of the business error case
	 * @param cause
	 *            the cause of the exception
	 */
	public GenericBusinessException(ReturnCode code, String description, Throwable cause) {
		super(description, cause);
		this.code = code;
		this.description = description;
	}

	/**
	 * @return the code
	 */
	public ReturnCode getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
}

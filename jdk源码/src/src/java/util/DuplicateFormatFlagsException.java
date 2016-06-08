/*
 * @(#)DuplicateFormatFlagsException.java	1.3 05/11/17
 *
 * Copyright 2006 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package java.util;

/**
 * Unchecked exception thrown when duplicate flags are provided in the format
 * specifier.  
 *
 * <p> Unless otherwise specified, passing a <tt>null</tt> argument to any
 * method or constructor in this class will cause a {@link
 * NullPointerException} to be thrown.
 *
 * @version 	1.3, 11/17/05
 * @since 1.5
 */
public class DuplicateFormatFlagsException extends IllegalFormatException {		//重复格式标志异常类，设置标志位

    private static final long serialVersionUID = 18890531L;

    private String flags;

    /**
     * Constructs an instance of this class with the specified flags.
     *
     * @param  f
     *         The set of format flags which contain a duplicate flag.
     */
    public DuplicateFormatFlagsException(String f) {
 	if (f == null)
 	    throw new NullPointerException();
	this.flags = f;
    }

    /**
     * Returns the set of flags which contains a duplicate flag.
     *
     * @return  The flags
     */
    public String getFlags() {
	return flags;
    }

    public String getMessage() {
	return String.format("Flags = '%s'", flags);
    }
}

/*
 * @(#)NamedWeakReference.java	1.5 05/11/17
 *
 * Copyright 2006 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.sun.naming.internal;


/**
 * A NamedWeakReference is a WeakReference with an immutable string name.
 *
 * @author Scott Seligman
 * @version 1.5 05/11/17
 */


class NamedWeakReference extends java.lang.ref.WeakReference {

    private final String name;

    NamedWeakReference(Object referent, String name) {
	super(referent);
	this.name = name;
    }

    String getName() {
	return name;
    }
}

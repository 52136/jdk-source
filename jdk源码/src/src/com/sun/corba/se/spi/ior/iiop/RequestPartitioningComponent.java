/**
 * @(#)RequestPartitioningComponent.java	1.3 05/11/17
 *
 * Copyright 2006 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.sun.corba.se.spi.ior.iiop;

import com.sun.corba.se.spi.ior.TaggedComponent;

public interface RequestPartitioningComponent extends TaggedComponent
{
    public int getRequestPartitioningId();
}

/*
 * @(#)InboundConnectionCache.java	1.3 05/11/17
 * 
 * Copyright 2006 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.sun.corba.se.pept.transport;

/**
 * @author Harold Carr
 */
public interface InboundConnectionCache 
    extends ConnectionCache
{
    public Connection get(Acceptor acceptor); // REVISIT

    public void put(Acceptor acceptor, Connection connection);

    public void remove(Connection connection);
}

// End of file.

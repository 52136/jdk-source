/*
 * @(#)IIOReadWarningListener.java	1.22 05/11/17
 *
 * Copyright 2006 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package javax.imageio.event;

import java.util.EventListener;
import javax.imageio.ImageReader;

/**
 * An interface used by <code>ImageReader</code> implementations to
 * notify callers of their image and thumbnail reading methods of
 * warnings (non-fatal errors).  Fatal errors cause the relevant
 * read method to throw an <code>IIOException</code>.
 *
 * <p> Localization is handled by associating a <code>Locale</code>
 * with each <code>IIOReadWarningListener</code> as it is registered
 * with an <code>ImageReader</code>.  It is up to the
 * <code>ImageReader</code> to provide localized messages.
 *
 * @see javax.imageio.ImageReader#addIIOReadWarningListener
 * @see javax.imageio.ImageReader#removeIIOReadWarningListener
 *
 * @version 0.5
 */
public interface IIOReadWarningListener extends EventListener {
    
    /**
     * Reports the occurence of a non-fatal error in decoding.  Decoding
     * will continue following the call to this method.  The application
     * may choose to display a dialog, print the warning to the console,
     * ignore the warning, or take any other action it chooses.
     *
     * @param source the <code>ImageReader</code> object calling this method.
     * @param warning a <code>String</code> containing the warning.
     */
    void warningOccurred(ImageReader source, String warning);
}

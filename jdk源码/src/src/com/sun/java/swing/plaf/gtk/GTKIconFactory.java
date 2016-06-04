/*
 * @(#)GTKIconFactory.java	1.32 06/06/07
 *
 * Copyright 2006 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.sun.java.swing.plaf.gtk;

import java.util.*;
import javax.swing.plaf.synth.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.*;
import javax.swing.*;
import javax.swing.plaf.*;
import sun.swing.plaf.synth.*;
import com.sun.java.swing.plaf.gtk.GTKConstants.ArrowType;
import com.sun.java.swing.plaf.gtk.GTKConstants.ExpanderStyle;
import com.sun.java.swing.plaf.gtk.GTKConstants.Orientation;
import com.sun.java.swing.plaf.gtk.GTKConstants.ShadowType;

/**
 * @version 1.32, 06/07/06
 */
class GTKIconFactory {
    private static final String RADIO_BUTTON_ICON    = "paintRadioButtonIcon";
    private static final String CHECK_BOX_ICON       = "paintCheckBoxIcon";
    private static final String MENU_ARROW_ICON      = "paintMenuArrowIcon";
    private static final String MENU_ITEM_ARROW_ICON = "paintMenuItemArrowIcon";
    private static final String CHECK_BOX_MENU_ITEM_ARROW_ICON =
                                      "paintCheckBoxMenuItemArrowIcon";
    private static final String CHECK_BOX_MENU_ITEM_CHECK_ICON =
                                      "paintCheckBoxMenuItemCheckIcon";
    private static final String RADIO_BUTTON_MENU_ITEM_ARROW_ICON =
                                      "paintRadioButtonMenuItemArrowIcon";
    private static final String RADIO_BUTTON_MENU_ITEM_CHECK_ICON =
                                      "paintRadioButtonMenuItemCheckIcon";
    private static final String TREE_EXPANDED_ICON = "paintTreeExpandedIcon";
    private static final String TREE_COLLAPSED_ICON = "paintTreeCollapsedIcon";
    private static final String ASCENDING_SORT_ICON = "paintAscendingSortIcon";
    private static final String DESCENDING_SORT_ICON = "paintDescendingSortIcon";
    private static final String TOOL_BAR_HANDLE_ICON = "paintToolBarHandleIcon";
    
    private static Map<String, DelegatingIcon> iconsPool = 
            Collections.synchronizedMap(new HashMap<String, DelegatingIcon>());
    
    private static DelegatingIcon getIcon(String methodName) {
        DelegatingIcon result = iconsPool.get(methodName);
        if (result == null) {
            if (methodName == TREE_COLLAPSED_ICON ||
                methodName == TREE_EXPANDED_ICON)
            {
                result = new SynthExpanderIcon(methodName);
                
            } else if (methodName == TOOL_BAR_HANDLE_ICON) {
                result = new ToolBarHandleIcon();
                
            } else if (methodName == MENU_ARROW_ICON) {
                result = new MenuArrowIcon();
                
            } else {
                result = new DelegatingIcon(methodName);
            }
            iconsPool.put(methodName, result);
        }
        return result;
    }
    
    //
    // Sort arrow
    //
    public static Icon getAscendingSortIcon() {
        return getIcon(ASCENDING_SORT_ICON);
    }

    public static Icon getDescendingSortIcon() {
        return getIcon(DESCENDING_SORT_ICON);
    }
    
    //
    // Tree methods
    // 
    public static SynthIcon getTreeExpandedIcon() {
        return getIcon(TREE_EXPANDED_ICON);
    }

    public static SynthIcon getTreeCollapsedIcon() {
        return getIcon(TREE_COLLAPSED_ICON);
    }

    //
    // Radio button
    //
    public static SynthIcon getRadioButtonIcon() {
        return getIcon(RADIO_BUTTON_ICON);
    }

    //
    // CheckBox
    //
    public static SynthIcon getCheckBoxIcon() {
        return getIcon(CHECK_BOX_ICON);
    }

    //
    // Menus
    // 
    public static SynthIcon getMenuArrowIcon() {
        return getIcon(MENU_ARROW_ICON);
    }

    public static SynthIcon getMenuItemArrowIcon() {
        return getIcon(MENU_ITEM_ARROW_ICON);
    }

    public static SynthIcon getCheckBoxMenuItemArrowIcon() {
        return getIcon(CHECK_BOX_MENU_ITEM_ARROW_ICON);
    }

    public static SynthIcon getCheckBoxMenuItemCheckIcon() {
        return getIcon(CHECK_BOX_MENU_ITEM_CHECK_ICON);
    }

    public static SynthIcon getRadioButtonMenuItemArrowIcon() {
        return getIcon(RADIO_BUTTON_MENU_ITEM_ARROW_ICON);
    }

    public static SynthIcon getRadioButtonMenuItemCheckIcon() {
        return getIcon(RADIO_BUTTON_MENU_ITEM_CHECK_ICON);
    }

    //
    // ToolBar Handle
    // 
    public static SynthIcon getToolBarHandleIcon() {
        return getIcon(TOOL_BAR_HANDLE_ICON);
    }

    static void resetIcons() {
        synchronized (iconsPool) {
            for (DelegatingIcon di: iconsPool.values()) {
                di.resetIconDimensions();
            }
        }
    }
    
    private static class DelegatingIcon extends SynthIcon implements
                                   UIResource {
        static final int DEFAULT_ICON_DIMENSION = 13;

        private static final Class[] PARAM_TYPES = new Class[] {
            SynthContext.class, Graphics.class, int.class,
            int.class, int.class, int.class, int.class
        };

        private Object method;
        int iconDimension = -1;

        DelegatingIcon(String methodName ){
            this.method = methodName;
        }

        public void paintIcon(SynthContext context, Graphics g,
                              int x, int y, int w, int h) {
            if (context != null) {
                GTKPainter.INSTANCE.paintIcon(context, g,
                        getMethod(), x, y, w, h);
            }
        }

        public int getIconWidth(SynthContext context) {
            return getIconDimension(context);
        }

        public int getIconHeight(SynthContext context) {
            return getIconDimension(context);
        }

        void resetIconDimensions() {
            iconDimension = -1;
        }

        protected Method getMethod() {
            if (method instanceof String) {
                method = resolveMethod((String)method);
            }
            return (Method)method;
        }

        protected Class[] getMethodParamTypes() {
            return PARAM_TYPES;
        }
        
        private Method resolveMethod(String name) {
            try {
                return GTKPainter.class.getMethod(name, getMethodParamTypes());
            } catch (NoSuchMethodException e) {
                assert false;
            }
            return null;
        }

        int getIconDimension(SynthContext context) {
            if (iconDimension >= 0) {
                return iconDimension;
            }
            
            if (context == null) {
                return DEFAULT_ICON_DIMENSION;
            }

            Region region = context.getRegion();
            GTKStyle style = (GTKStyle) context.getStyle();
            Object propValue = style.getClassSpecificValue(region,
                                                           "indicator-size");

            if (propValue != null) {
                iconDimension = ((Number)propValue).intValue();
            } else if (region == Region.CHECK_BOX ||
                       region == Region.RADIO_BUTTON) {
                iconDimension = DEFAULT_ICON_DIMENSION;
            } else {
                return (iconDimension = DEFAULT_ICON_DIMENSION);
            }
            
            if (region == Region.CHECK_BOX || region == Region.RADIO_BUTTON) {
                propValue = style.getClassSpecificValue(region,
                                                        "indicator-spacing");
                if (propValue != null) {
                    iconDimension += 2 * ((Number) propValue).intValue();
                } else {
                    iconDimension += 2 * 2;
                }
            } else if (region == Region.CHECK_BOX_MENU_ITEM ||
                       region == Region.RADIO_BUTTON_MENU_ITEM) {
                iconDimension++;
            }
            return iconDimension;
        }
    }

    private static class SynthExpanderIcon extends DelegatingIcon {
        SynthExpanderIcon(String method) {
            super(method);
        }

        public void paintIcon(SynthContext context, Graphics g, int x, int y,
                              int w, int h) {
            if (context != null) {
                super.paintIcon(context, g, x, y, w, h);
                updateSizeIfNecessary(context);
            }
        }

        int getIconDimension(SynthContext context) {
            updateSizeIfNecessary(context);
            return (iconDimension == -1) ? DEFAULT_ICON_DIMENSION :
                                           iconDimension;
        }

        private void updateSizeIfNecessary(SynthContext context) {
            if (iconDimension == -1 && context != null) {
                iconDimension = context.getStyle().getInt(context,
                        "Tree.expanderSize", 10);
            }
        }
    }

    // GTK has a separate widget for the handle box, to mirror this
    // we create a unique icon per ToolBar and lookup the style for the
    // HandleBox.
    private static class ToolBarHandleIcon extends DelegatingIcon {
        private static final Class[] PARAM_TYPES = new Class[] {
            SynthContext.class, Graphics.class, int.class,
            int.class, int.class, int.class, int.class, Orientation.class,
        };
        
        private SynthStyle style;

        public ToolBarHandleIcon() {
            super(TOOL_BAR_HANDLE_ICON);
        }

        protected Class[] getMethodParamTypes() {
            return PARAM_TYPES;
        }

        public void paintIcon(SynthContext context, Graphics g, int x, int y,
                              int w, int h) {
            JToolBar toolbar = (JToolBar)context.getComponent();
            Orientation orientation =
                    (toolbar.getOrientation() == JToolBar.HORIZONTAL ?
                        Orientation.HORIZONTAL : Orientation.VERTICAL);

            if (style == null) {
                style = SynthLookAndFeel.getStyleFactory().getStyle(
                        context.getComponent(), GTKRegion.HANDLE_BOX);
            }
            context = new SynthContext(toolbar, GTKRegion.HANDLE_BOX,
                    style, SynthConstants.ENABLED);
            
            GTKPainter.INSTANCE.paintIcon(context, g,
                    getMethod(), x, y, w, h, orientation);
        }

        public int getIconWidth(SynthContext context) {
            if (context == null) {
                return 10;
            }
            if (((JToolBar)context.getComponent()).getOrientation() ==
                    JToolBar.HORIZONTAL) {
                return 10;
            } else {
                return context.getComponent().getWidth();
            }
        }

        public int getIconHeight(SynthContext context) {
            if (context == null) {
                return 10;
            }
            if (((JToolBar)context.getComponent()).getOrientation() ==
                    JToolBar.HORIZONTAL) {
                return context.getComponent().getHeight();
            } else {
                return 10;
            }
        }
    }
    
    private static class MenuArrowIcon extends DelegatingIcon {
        private static final Class[] PARAM_TYPES = new Class[] {
            SynthContext.class, Graphics.class, int.class,
            int.class, int.class, int.class, int.class, ArrowType.class,
        };

        public MenuArrowIcon() {
            super(MENU_ARROW_ICON);
        }
        
        protected Class[] getMethodParamTypes() {
            return PARAM_TYPES;
        }
        
        public void paintIcon(SynthContext context, Graphics g, int x, int y,
                              int w, int h) {
            ArrowType arrowDir = ArrowType.RIGHT;
            if (!context.getComponent().getComponentOrientation().isLeftToRight()) {
                arrowDir = ArrowType.LEFT;
            }
            GTKPainter.INSTANCE.paintIcon(context, g,
                    getMethod(), x, y, w, h, arrowDir);
        }
    }
}

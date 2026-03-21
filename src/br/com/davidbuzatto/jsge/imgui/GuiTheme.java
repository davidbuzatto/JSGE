/*
 * Copyright (C) 2025 Prof. Dr. David Buzatto
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package br.com.davidbuzatto.jsge.imgui;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.core.utils.ColorUtils;
import java.awt.Color;
import java.util.List;

/**
 * Class for creating, managing, and installing themes.
 *
 * @author Prof. Dr. David Buzatto
 */
public class GuiTheme {
    
    /** Default background color. */
    public Color backgroundColor;
    /** Default border color. */
    public Color borderColor;
    /** Default text color. */
    public Color textColor;

    /** Background color when the mouse is over the component. */
    public Color mouseOverBackgroundColor;
    /** Border color when the mouse is over the component. */
    public Color mouseOverBorderColor;
    /** Text color when the mouse is over the component. */
    public Color mouseOverTextColor;

    /** Background color when the mouse button is pressed on the component. */
    public Color mouseDownBackgroundColor;
    /** Border color when the mouse button is pressed on the component. */
    public Color mouseDownBorderColor;
    /** Text color when the mouse button is pressed on the component. */
    public Color mouseDownTextColor;

    /** Background color when the component is disabled. */
    public Color disabledBackgroundColor;
    /** Border color when the component is disabled. */
    public Color disabledBorderColor;
    /** Text color when the component is disabled. */
    public Color disabledTextColor;

    /** Background color for container components. */
    public Color containerBackgroundColor;
    /** Border color for container components. */
    public Color containerBorderColor;
    /** Text color for container components. */
    public Color containerTextColor;
    /** Background color for the title bars of container components. */
    public Color containerTitleBarBackgroundColor;
    /** Border color for the title bars of container components. */
    public Color containerTitleBarBorderColor;
    /** Text color for the title bars of container components. */
    public Color containerTitleBarTextColor;

    /** Background color for disabled container components. */
    public Color disabledContainerBackgroundColor;
    /** Border color for disabled container components. */
    public Color disabledContainerBorderColor;
    /** Text color for disabled container components. */
    public Color disabledContainerTextColor;
    /** Background color for the title bars of disabled container components. */
    public Color disabledContainerTitleBarBackgroundColor;
    /** Border color for the title bars of disabled container components. */
    public Color disabledContainerTitleBarBorderColor;
    /** Text color for the title bars of disabled container components. */
    public Color disabledContainerTitleBarTextColor;

    /** Background color for the progress fill of progress bars. */
    public Color progressBarProgressFillColor;

    /** Background color for item containers of list components. */
    public Color listContainerBackgroundColor;
    /** Background color for item containers of disabled list components. */
    public Color disabledListContainerBackgroundColor;

    /** Background color for the track of scroll bars. */
    public Color scrollBarTrackColor;
    /** Background color for the track of disabled scroll bars. */
    public Color disabledScrollBarTrackColor;

    /** Overlay color of the color picker when it is disabled. */
    public Color colorPickerDisabledOverlayColor;

    /** Overlay color for dialogs. */
    public Color dialogOverlayColor;

    /** Default tooltip background color. */
    public Color toolTipBackgroundColor;
    /** Default tooltip border color. */
    public Color toolTipBorderColor;
    /** Default tooltip text color. */
    public Color toolTipTextColor;
    
    private GuiTheme() {
    }
    
    /**
     * Builds the light theme. This is the default theme.
     *
     * @return The light theme.
     */
    public static GuiTheme buildLightTheme() {
        
        GuiTheme theme = new GuiTheme();
        
        theme.backgroundColor = new Color( 201, 201, 201 );
        theme.borderColor = new Color( 131, 131, 131 );
        theme.textColor = new Color( 104, 104, 104 );

        theme.mouseOverBackgroundColor = new Color( 201, 239, 254 );
        theme.mouseOverBorderColor = new Color( 91, 178, 217 );
        theme.mouseOverTextColor = new Color( 108, 155, 188 );

        theme.mouseDownBackgroundColor = new Color( 151, 232, 255 );
        theme.mouseDownBorderColor = new Color( 4, 146, 199 );
        theme.mouseDownTextColor = new Color( 54, 139, 175 );

        theme.disabledBackgroundColor = new Color( 230, 233, 233 );
        theme.disabledBorderColor = new Color( 181, 193, 194 );
        theme.disabledTextColor = new Color( 174, 183, 184 );

        theme.containerBackgroundColor = new Color( 245, 245, 245 );
        theme.containerBorderColor = new Color( 144, 171, 181 );
        theme.containerTextColor = new Color( 144, 171, 181 );
        theme.containerTitleBarBackgroundColor = new Color( 201, 201, 201 );
        theme.containerTitleBarBorderColor = new Color( 131, 131, 131 );
        theme.containerTitleBarTextColor = new Color( 104, 104, 104 );

        theme.disabledContainerBackgroundColor = new Color( 230, 233, 233 );
        theme.disabledContainerBorderColor = new Color( 181, 193, 194 );
        theme.disabledContainerTextColor = new Color( 181, 193, 194 );
        theme.disabledContainerTitleBarBackgroundColor = new Color( 230, 233, 233 );
        theme.disabledContainerTitleBarBorderColor = new Color( 181, 193, 194 );
        theme.disabledContainerTitleBarTextColor = new Color( 181, 193, 194 );

        theme.progressBarProgressFillColor = new Color( 151, 232, 255 );

        theme.listContainerBackgroundColor = new Color( 245, 245, 245 );
        theme.disabledListContainerBackgroundColor = new Color( 245, 245, 245 );

        theme.scrollBarTrackColor = ColorUtils.colorBrightness( theme.backgroundColor, 0.6 );
        theme.disabledScrollBarTrackColor = new Color( 220, 220, 220 );

        theme.colorPickerDisabledOverlayColor = ColorUtils.fade( EngineFrame.LIGHTGRAY, 0.5 );

        theme.dialogOverlayColor = new Color( 0, 0, 0, 100 );

        theme.toolTipBackgroundColor = new Color( 230, 233, 233 );
        theme.toolTipBorderColor = new Color( 181, 193, 194 );
        theme.toolTipTextColor = new Color( 130, 130, 130 );
        
        return theme;
        
    }
    
    /**
     * Builds the dark theme.
     *
     * @return The dark theme.
     */
    public static GuiTheme buildDarkTheme() {
        
        GuiTheme theme = new GuiTheme();
        
        theme.backgroundColor = new Color( 60, 63, 65 );
        theme.borderColor = new Color( 97, 99, 101 );
        theme.textColor = new Color( 187, 187, 187 );

        theme.mouseOverBackgroundColor = new Color( 80, 83, 85 );
        theme.mouseOverBorderColor = new Color( 118, 120, 122 );
        theme.mouseOverTextColor = new Color( 190, 190, 190 );

        theme.mouseDownBackgroundColor = new Color( 103, 107, 109 );
        theme.mouseDownBorderColor = new Color( 142, 145, 147 );
        theme.mouseDownTextColor = new Color( 190, 190, 190 );

        theme.disabledBackgroundColor = new Color( 136, 141, 145 );
        theme.disabledBorderColor = new Color( 159, 165, 168 );
        theme.disabledTextColor = new Color( 199, 201, 203 );

        theme.containerBackgroundColor = new Color( 43, 43, 43 );
        theme.containerBorderColor = new Color( 110, 110, 110 );
        theme.containerTextColor = new Color( 110, 110, 110 );
        theme.containerTitleBarBackgroundColor = new Color( 20, 20, 20 );
        theme.containerTitleBarBorderColor = new Color( 80, 80, 80 );
        theme.containerTitleBarTextColor = new Color( 160, 160, 160 );

        theme.disabledContainerBackgroundColor = new Color( 136, 141, 145 );
        theme.disabledContainerBorderColor = new Color( 159, 165, 168 );
        theme.disabledContainerTextColor = new Color( 199, 201, 203 );
        theme.disabledContainerTitleBarBackgroundColor = new Color( 136, 141, 145 );
        theme.disabledContainerTitleBarBorderColor = new Color( 159, 165, 168 );
        theme.disabledContainerTitleBarTextColor = new Color( 199, 201, 203 );

        theme.progressBarProgressFillColor = new Color( 180, 180, 180 );

        theme.listContainerBackgroundColor = new Color( 60, 63, 65 );
        theme.disabledListContainerBackgroundColor = new Color( 121, 128, 132 );

        theme.scrollBarTrackColor = ColorUtils.colorBrightness( theme.backgroundColor, 0.6 );
        theme.disabledScrollBarTrackColor = new Color( 180, 180, 180 );

        theme.colorPickerDisabledOverlayColor = ColorUtils.fade( EngineFrame.LIGHTGRAY, 0.5 );

        theme.dialogOverlayColor = new Color( 0, 0, 0, 100 );

        theme.toolTipBackgroundColor = new Color( 30, 32, 33 );
        theme.toolTipBorderColor = new Color( 130, 130, 130 );
        theme.toolTipTextColor = new Color( 187, 187, 187 );
        
        return theme;
        
    }
    
    /**
     * Creates a new theme using a base color and its inverse for highlights.
     *
     * @param color The base color.
     * @return A base-color theme.
     */
    public static GuiTheme buildColoredTheme( Color color ) {
        
        GuiTheme theme = new GuiTheme();
        Color inv = ColorUtils.colorInvert( color );
        Color cw = ColorUtils.lerp( color, Color.WHITE, 0.5 );
        Color cb = ColorUtils.lerp( color, Color.BLACK, 0.5 );
        
        theme.backgroundColor = ColorUtils.colorBrightness( color, .1 );
        theme.borderColor = ColorUtils.colorBrightness( color, -.2 );
        theme.textColor = ColorUtils.colorBrightness( color, -.3 );

        theme.mouseOverBackgroundColor = ColorUtils.colorBrightness( inv, .2 );
        theme.mouseOverBorderColor = ColorUtils.colorBrightness( inv, -.3 );
        theme.mouseOverTextColor = ColorUtils.colorBrightness( inv, .7 );

        theme.mouseDownBackgroundColor = ColorUtils.colorBrightness( inv, -.1 );
        theme.mouseDownBorderColor = ColorUtils.colorBrightness( inv, -.25 );
        theme.mouseDownTextColor = ColorUtils.colorBrightness( inv, .7 );

        theme.disabledBackgroundColor = ColorUtils.colorBrightness( cw, -.1 );
        theme.disabledBorderColor = ColorUtils.colorBrightness( cw, -.2 );
        theme.disabledTextColor = ColorUtils.colorBrightness( cw, .8 );

        theme.containerBackgroundColor = ColorUtils.colorBrightness( cw, .1 );
        theme.containerBorderColor = ColorUtils.colorBrightness( cw, -.2 );;
        theme.containerTextColor = ColorUtils.colorBrightness( cw, -.4 );
        theme.containerTitleBarBackgroundColor = ColorUtils.colorBrightness( cb, .1 );
        theme.containerTitleBarBorderColor = ColorUtils.colorBrightness( cb, -.2 );
        theme.containerTitleBarTextColor = ColorUtils.colorBrightness( cw, .2 );

        theme.disabledContainerBackgroundColor = ColorUtils.colorBrightness( cw, -.1 );
        theme.disabledContainerBorderColor = ColorUtils.colorBrightness( cw, -.2 );
        theme.disabledContainerTextColor = ColorUtils.colorBrightness( cw, .8 );
        theme.disabledContainerTitleBarBackgroundColor = ColorUtils.colorBrightness( cw, -.3 );
        theme.disabledContainerTitleBarBorderColor = ColorUtils.colorBrightness( cw, -.4 );
        theme.disabledContainerTitleBarTextColor = ColorUtils.colorBrightness( cw, .8 );

        theme.progressBarProgressFillColor = ColorUtils.colorBrightness( inv, -.1 );

        theme.listContainerBackgroundColor = ColorUtils.colorBrightness( color, .3 );
        theme.disabledListContainerBackgroundColor = ColorUtils.colorBrightness( cw, -.1 );

        theme.scrollBarTrackColor = ColorUtils.colorBrightness( theme.backgroundColor, 0.6 );
        theme.disabledScrollBarTrackColor = ColorUtils.colorBrightness( cw, -.1 );

        theme.colorPickerDisabledOverlayColor = ColorUtils.fade( EngineFrame.LIGHTGRAY, 0.5 );

        theme.dialogOverlayColor = new Color( 0, 0, 0, 100 );

        theme.toolTipBackgroundColor = ColorUtils.colorBrightness( cb, .1 );
        theme.toolTipBorderColor = ColorUtils.colorBrightness( cb, -.2 );
        theme.toolTipTextColor = ColorUtils.colorBrightness( cw, .2 );
        
        return theme;
        
    }
    
    /**
     * Creates an "empty" theme using the gray color.
     *
     * @return The gray theme.
     */
    public static GuiTheme buildEmptyTheme() {
        return buildColoredTheme( EngineFrame.LIGHTGRAY );
    }
    
    /**
     * Installs the theme globally. Should be called before creating components.
     */
    public void install() {
        
        GuiComponent.BACKGROUND_COLOR = backgroundColor;
        GuiComponent.BORDER_COLOR = borderColor;
        GuiComponent.TEXT_COLOR = textColor;

        GuiComponent.MOUSE_OVER_BACKGROUND_COLOR = mouseOverBackgroundColor;
        GuiComponent.MOUSE_OVER_BORDER_COLOR = mouseOverBorderColor;
        GuiComponent.MOUSE_OVER_TEXT_COLOR = mouseOverTextColor;

        GuiComponent.MOUSE_DOWN_BACKGROUND_COLOR = mouseDownBackgroundColor;
        GuiComponent.MOUSE_DOWN_BORDER_COLOR = mouseDownBorderColor;
        GuiComponent.MOUSE_DOWN_TEXT_COLOR = mouseDownTextColor;

        GuiComponent.DISABLED_BACKGROUND_COLOR = disabledBackgroundColor;
        GuiComponent.DISABLED_BORDER_COLOR = disabledBorderColor;
        GuiComponent.DISABLED_TEXT_COLOR = disabledTextColor;

        GuiComponent.CONTAINER_BACKGROUND_COLOR = containerBackgroundColor;
        GuiComponent.CONTAINER_BORDER_COLOR = containerBorderColor;
        GuiComponent.CONTAINER_TEXT_COLOR = containerTextColor;
        GuiComponent.CONTAINER_TITLE_BAR_BACKGROUND_COLOR = containerTitleBarBackgroundColor;
        GuiComponent.CONTAINER_TITLE_BAR_BORDER_COLOR = containerTitleBarBorderColor;
        GuiComponent.CONTAINER_TITLE_BAR_TEXT_COLOR = containerTitleBarTextColor;

        GuiComponent.DISABLED_CONTAINER_BACKGROUND_COLOR = disabledContainerBackgroundColor;
        GuiComponent.DISABLED_CONTAINER_BORDER_COLOR = disabledContainerBorderColor;
        GuiComponent.DISABLED_CONTAINER_TEXT_COLOR = disabledContainerTextColor;
        GuiComponent.DISABLED_CONTAINER_TITLE_BAR_BACKGROUND_COLOR = disabledContainerTitleBarBackgroundColor;
        GuiComponent.DISABLED_CONTAINER_TITLE_BAR_BORDER_COLOR = disabledContainerTitleBarBorderColor;
        GuiComponent.DISABLED_CONTAINER_TITLE_BAR_TEXT_COLOR = disabledContainerTitleBarTextColor;

        GuiComponent.PROGRESS_BAR_PROGRESS_FILL_COLOR = progressBarProgressFillColor;

        GuiComponent.LIST_CONTAINER_BACKGROUND_COLOR = listContainerBackgroundColor;
        GuiComponent.DISABLED_LIST_CONTAINER_BACKGROUND_COLOR = disabledListContainerBackgroundColor;

        GuiComponent.SCROLL_BAR_TRACK_COLOR = scrollBarTrackColor;
        GuiComponent.DISABLED_SCROLL_BAR_TRACK_COLOR = disabledScrollBarTrackColor;

        GuiComponent.COLOR_PICKER_DISABLED_OVERLAY_COLOR = colorPickerDisabledOverlayColor;

        GuiComponent.DIALOG_OVERLAY_COLOR = dialogOverlayColor;

        GuiComponent.TOOL_TIP_BACKGROUND_COLOR = toolTipBackgroundColor;
        GuiComponent.TOOL_TIP_BORDER_COLOR = toolTipBorderColor;
        GuiComponent.TOOL_TIP_TEXT_COLOR = toolTipTextColor;

    }
    
    /**
     * Applies the theme to the given components.
     *
     * @param components The components.
     */
    public void apply( List<GuiComponent> components ) {
        for ( GuiComponent c : components ) {
            apply( c );
        }
    }
    
    /**
     * Applies the theme to the given components.
     *
     * @param components The components.
     */
    public void apply( GuiComponent... components ) {
        for ( GuiComponent c : components ) {
            apply( c );
        }
    }
    
    /**
     * Installs the theme globally and applies it to the given components.
     *
     * @param components The components.
     */
    public void installAndApply( List<GuiComponent> components ) {
        install();
        apply( components );
    }
    
    /**
     * Installs the theme globally and applies it to the given components.
     *
     * @param components The components.
     */
    public void installAndApply( GuiComponent... components ) {
        install();
        apply( components );
    }
    
    /**
     * Applies the theme to a component and its subcomponents if necessary.
     *
     * @param component The component.
     */
    public void apply( GuiComponent component ) {
        component.apply( this );
    }
    
}

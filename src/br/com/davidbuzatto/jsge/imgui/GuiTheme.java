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
 * Classe para criação de temas.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class GuiTheme {
    
    /** Cor padrão de fundo. */
    public Color backgroundColor;
    /** Cor padrão da borda. */
    public Color borderColor;
    /** Cor padrão do texto. */
    public Color textColor;
    
    /** Cor de fundo quando o mouse está em cima do componente. */
    public Color mouseOverBackgroundColor;
    /** Cor da borda quando o mouse está em cima do componente. */
    public Color mouseOverBorderColor;
    /** Cor do texto quando o mouse está em cima do componente. */
    public Color mouseOverTextColor;
    
    /** Cor de fundo quando o mouse está sendo pressionado no componente. */
    public Color mouseDownBackgroundColor;
    /** Cor da borda quando o mouse está sendo pressionado no componente. */
    public Color mouseDownBorderColor;
    /** Cor do texto quando o mouse está sendo pressionado no componente. */
    public Color mouseDownTextColor;
    
    /** Cor de fundo quando o componente está desabilitado. */
    public Color disabledBackgroundColor;
    /** Cor da borda quando o componente está desabilitado. */
    public Color disabledBorderColor;
    /** Cor do texto quando o componente está desabilitado. */
    public Color disabledTextColor;
    
    /** Cor de fundo para os componentes contâineres. */
    public Color containerBackgroundColor;
    /** Cor da borda para os componentes contâineres. */
    public Color containerBorderColor;
    /** Cor do texto para os componentes contâineres. */
    public Color containerTextColor;
    /** Cor de fundo para as barras de título dos componentes contâineres. */
    public Color containerTitleBarBackgroundColor;
    /** Cor da borda para as barras de título dos componentes contâineres. */
    public Color containerTitleBarBorderColor;
    /** Cor do texto para as barras de título dos componentes contâineres. */
    public Color containerTitleBarTextColor;
    
    /** Cor de fundo para os componentes contâineres desabilitados. */
    public Color disabledContainerBackgroundColor;
    /** Cor da borda para os componentes contâineres desabilitados. */
    public Color disabledContainerBorderColor;
    /** Cor do texto para os componentes contâineres desabilitados. */
    public Color disabledContainerTextColor;
    /** Cor de fundo para as barras de título dos componentes contâineres desabilitados. */
    public Color disabledContainerTitleBarBackgroundColor;
    /** Cor da borda para as barras de título dos componentes contâineres desabilitados. */
    public Color disabledContainerTitleBarBorderColor;
    /** Cor do texto para as barras de título dos componentes contâineres desabilitados. */
    public Color disabledContainerTitleBarTextColor;
    
    /** Cor de fundo para o progresso das barras de progresso. */
    public Color progressBarProgressFillColor;
    
    /** Cor de fundo para os contâineres de itens dos componentes de lista. */
    public Color listContainerBackgroundColor;
    /** Cor de fundo para os contâineres de itens dos componentes de lista desabilitados. */
    public Color disabledListContainerBackgroundColor;
    
    /** Cor de fundo para a trila dos sliders usados como barras de rolagem. */
    public Color scrollBarTrackColor;
    /** Cor de fundo para a trila dos sliders desabiltiados usados como barras de rolagem. */
    public Color disabledScrollBarTrackColor;
    
    /** Cor da sobreposição do seletor de cor quando está desabilitado. */
    public Color colorPickerDisabledOverlayColor;
    
    /** Cor da sobreposição dos diálogos. */
    public Color dialogOverlayColor;
    
    /** Cor padrão de fundo. */
    public Color toolTipBackgroundColor;
    /** Cor padrão da borda. */
    public Color toolTipBorderColor;
    /** Cor padrão do texto. */
    public Color toolTipTextColor;
    
    private GuiTheme() {
    }
    
    /**
     * Constroi o tema claro. É o tema utilizado por padrão.
     * 
     * @return O tema claro.
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

        theme.scrollBarTrackColor = new Color( 220, 220, 220 );
        theme.disabledScrollBarTrackColor = new Color( 220, 220, 220 );

        theme.colorPickerDisabledOverlayColor = ColorUtils.fade( EngineFrame.LIGHTGRAY, 0.5 );

        theme.dialogOverlayColor = new Color( 0, 0, 0, 100 );

        theme.toolTipBackgroundColor = new Color( 230, 233, 233 );
        theme.toolTipBorderColor = new Color( 181, 193, 194 );
        theme.toolTipTextColor = new Color( 130, 130, 130 );
        
        return theme;
        
    }
    
    /**
     * Constroi o tema escuro.
     * 
     * @return O tema escuro.
     */
    public static GuiTheme buildDarkTheme() {
        
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

        theme.scrollBarTrackColor = new Color( 220, 220, 220 );
        theme.disabledScrollBarTrackColor = new Color( 220, 220, 220 );

        theme.colorPickerDisabledOverlayColor = ColorUtils.fade( EngineFrame.LIGHTGRAY, 0.5 );

        theme.dialogOverlayColor = new Color( 0, 0, 0, 100 );

        theme.toolTipBackgroundColor = new Color( 230, 233, 233 );
        theme.toolTipBorderColor = new Color( 181, 193, 194 );
        theme.toolTipTextColor = new Color( 130, 130, 130 );
        
        return theme;
        
    }
    
    /**
     * Instala o tema globalmente. Deve ser usado antes de se inciciar a criação
     * dos componentes.
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
     * Instala o tema globalmente, para os novos componentes que forem criados,
     * e nos componentes passados que já foram criados.
     * 
     * @param components Os componentes.
     */
    public void install( List<GuiComponent> components ) {
        install();
        for ( GuiComponent c : components ) {
            install( c );
        }
    }
    
    /**
     * Instala o tema globalmente, para os novos componentes que forem criados,
     * e nos componentes passados que já foram criados.
     * 
     * @param components Os componentes.
     */
    public void install( GuiComponent... components ) {
        install();
        for ( GuiComponent c : components ) {
            install( c );
        }
    }
    
    /**
     * Instala o tema em um componente e em seus subcomponentes se necessário.
     * 
     * @param component O componente.
     */
    private void install( GuiComponent component ) {
        
        component.setBackgroundColor( backgroundColor );
        component.setBorderColor( borderColor );
        component.setTextColor( textColor );
            
        if ( component instanceof GuiColorPicker c ) {
            install( c.hueSlider );
            install( c.alphaSlider );
        } else if ( component instanceof GuiDropdownList c ) {
            install( c.itemsList );
        } else if ( component instanceof GuiGlue c ) {
            install( c.baseComponent );
            install( c.children );
        } else if ( component instanceof GuiGroup c ) {
            c.setBorderColor( containerBorderColor );
            c.setTextColor( containerTextColor );
        } else if ( component instanceof GuiLine c ) {
            c.setBorderColor( containerBorderColor );
            c.setTextColor( containerTextColor );
        } else if ( component instanceof GuiList c ) {
            c.setBackgroundColor( listContainerBackgroundColor );
            c.setScrollBarTrackColor( scrollBarTrackColor );
            install( c.scrollBar );
        } else if ( component instanceof GuiPanel c ) {
            c.setBackgroundColor( containerBackgroundColor );
            c.setBorderColor( containerBorderColor );
            c.setTitleBarBackgroundColor( containerTitleBarBackgroundColor );
            c.setTitleBarBorderColor( containerTitleBarBorderColor );
            c.setTitleBarTextColor( containerTitleBarTextColor );
        } else if ( component instanceof GuiProgressBar c ) {
            c.setProgressFillColor( progressBarProgressFillColor );
        } else if ( component instanceof GuiSlider c ) {
            c.setBackgroundColor( containerBackgroundColor );
            c.setTrackFillColor( mouseOverBackgroundColor );
            install( c.sliderButton );
        } else if ( component instanceof GuiSpinner c ) {
            c.setBackgroundColor( containerBackgroundColor );
            install( c.leftButton );
            install( c.rightButton );
        } else if ( component instanceof GuiTextField c ) {
            c.setBackgroundColor( containerBackgroundColor );
        }  else if ( component instanceof GuiToolTip c ) {
            c.setBackgroundColor( toolTipBackgroundColor );
            c.setBorderColor( toolTipBorderColor );
            c.setTextColor( toolTipTextColor );
        } else if ( component instanceof GuiWindow c ) {
            install( c.closeButton );
            c.setBackgroundColor( containerBackgroundColor );
            c.setBorderColor( containerBorderColor );
            c.setTitleBarBackgroundColor( containerTitleBarBackgroundColor );
            c.setTitleBarBorderColor( containerTitleBarBorderColor );
            c.setTitleBarTextColor( containerTitleBarTextColor );
            if ( c instanceof GuiConfirmDialog d ) {
                install( d.messageLabel );
                install( d.button1 );
                install( d.button2 );
                install( d.button3 );
            } else if ( c instanceof GuiInputDialog d ) {
                install( d.messageLabel );
                install( d.okButton );
                install( d.cancelButton );
                install( d.textField );
            } else if ( c instanceof GuiMessageDialog d ) {
                install( d.messageLabel );
                install( d.okButton );
            }
        }
        
    }
    
}

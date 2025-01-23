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
import br.com.davidbuzatto.jsge.geom.Rectangle;
import java.awt.Color;

/**
 * Um diálogo para entrada de dados.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class GuiInputDialog extends GuiWindow {
    
    private static final int PADDING = 20;
    private static final Color OVERLAY_COLOR = new Color( 0, 0, 0, 100 );
    private static final double MIN_WIDTH = 250;
    private static final double MIN_HEIGHT = 100;
    
    private String message;
    private boolean showOverlay;
    
    private GuiLabel messageLabel;
    private GuiButton okButton;
    private GuiButton cancelButton;
    private GuiTextField textField;
    
    private double messageWidth;
    private double messageHeight;
    private double lineHeight;
    
    private boolean boundsCalculationOk;
    
    private boolean enterKeyPressed;
    
    /**
     * Cria o componente.
     * 
     * @param title O título do componente.
     * @param message A mensagem do componente.
     * @param showOverlay Verdadeiro para desenhar uma camada de sobreposição atrás do diálogo.
     * @param engine A instância da engine utilizada para desenhar e atualizar
     * o componente.
     */
    public GuiInputDialog( String title, String message, boolean showOverlay, EngineFrame engine ) {
        super( 0, 0, 0, 0, title, engine );
        initComponents( engine, message, showOverlay );
    }
    
    /**
     * Cria o componente.
     * 
     * Essa versão do construtor depende da configuração "injetável" de uma
     * instância de uma engine.
     * @see br.com.davidbuzatto.jsge.core.engine.EngineFrame#useAsDependencyForIMGUI
     * 
     * @param title O título do componente.
     * @param message A mensagem do componente.
     * @param showOverlay Verdadeiro para desenhar uma camada de sobreposição atrás do diálogo. 
     */
    public GuiInputDialog( String title, String message, boolean showOverlay ) {
        super( 0, 0, 0, 0, title );
        initComponents( null, message, showOverlay );
    }
    
    private void initComponents( EngineFrame engine, String message, boolean showOverlay ) {
        super.initComponents( engine );
        this.message = message;
        this.showOverlay = showOverlay;
        if ( engine == null ) {
            this.messageLabel = new GuiLabel( 0, 0, 0, 0, message );
            this.okButton = new GuiButton( 0, 0, 40, 30, "OK" );
            this.cancelButton = new GuiButton( 0, 0, 70, 30, "Cancel" );
            this.textField = new GuiTextField( 0, 0, MIN_WIDTH - PADDING * 2, 25, "" );
        } else {
            this.messageLabel = new GuiLabel( 0, 0, 0, 0, message, engine );
            this.okButton = new GuiButton( 0, 0, 40, 30, "OK", engine );
            this.cancelButton = new GuiButton( 0, 0, 70, 30, "Cancel", engine );
            this.textField = new GuiTextField( 0, 0, MIN_WIDTH - PADDING * 2, 25, "", engine );
        }
        this.visible = false;
    }
    
    private void updateBounds() {
        
        if ( !boundsCalculationOk ) {
                
            String[] ma = message.split( "\n" );
            messageWidth = 0;
            for ( String m : ma ) {
                double iw = engine.measureText( m, FONT_SIZE );
                if ( messageWidth < iw ) {
                    messageWidth = iw;
                }
            }
            
            Rectangle r = engine.measureTextBounds( message );
            lineHeight = r.height;
            messageHeight = ma.length * r.height;

            double width = messageWidth + 2 * PADDING;
            double height = messageHeight + 2 * PADDING + titleBarBounds.height + okButton.bounds.height + lineHeight + textField.bounds.height;

            if ( width < MIN_WIDTH ) {
                width = MIN_WIDTH;
            }

            if ( height < MIN_HEIGHT ) {
                height = MIN_HEIGHT;
            }

            bounds = new Rectangle( 0, 0, width, height );
            titleBarBounds = new Rectangle( bounds.x, bounds.y, bounds.width, 25 );
            messageLabel.bounds.width = messageWidth;
            messageLabel.bounds.height = 0;
            
            boundsCalculationOk = true;
            
        }
        
    }
    
    @Override
    public void update( double delta ) {
        
        super.update( delta );
        enterKeyPressed = false;
        
        if ( engine.isKeyPressed( EngineFrame.KEY_ENTER ) ) {
            hide();
            enterKeyPressed = true;
        }
        
        closeButton.setVisible( visible );
        okButton.setVisible( visible );
        cancelButton.setVisible( visible );
        textField.setVisible( visible );
            
        closeButton.update( delta );
        okButton.update( delta );
        cancelButton.update( delta );
        textField.update( delta );
        
    }
    
    @Override
    public void draw() {
        
        updateBounds();
        
        if ( visible ) {
            
            if ( showOverlay ) {
                engine.fillRectangle( 0, 0, engine.getScreenWidth(), engine.getScreenHeight(), OVERLAY_COLOR );
            }

            super.draw();
            
            messageLabel.draw();
            textField.draw();
            okButton.draw();
            cancelButton.draw();
            
        }
        
    }

    @Override
    public void setEnabled( boolean visible ) {
        throw new IllegalStateException( "This component cannot be enabled/disabled." );
    }

    @Override
    public void setVisible( boolean visible ) {
        throw new IllegalStateException( "You must use show and hide methods." );
    }
    
    /**
     * 
     */
    public void show() {
        show( engine.getScreenWidth() / 2 - bounds.width / 2, engine.getScreenHeight() / 2 - bounds.height / 2 );
    }
    
    /**
     * 
     * @param x Coordenada x do vértice superior esquerdo do retângulo que 
     * define os limites do componente.
     * @param y Coordenada y do vértice superior esquerdo do retângulo que 
     * define os limites do componente. 
     */
    public void show( double x, double y ) {
        bounds.x = x;
        bounds.y = y;
        updateComponentsBounds();
        textField.setValue( "" );
        super.setVisible( true );
    }
    
    /**
     * 
     */
    public void hide() {
        super.setVisible( false );
    }
    
    /**
     * 
     * @return 
     */
    public boolean isOkButtonPressed() {
        return okButton.isMousePressed();
    }
    
    /**
     * 
     * @return 
     */
    public boolean isCancelButtonPressed() {
        return cancelButton.isMousePressed();
    }
    
    /**
     * 
     * @return 
     */
    public boolean isEnterKeyPressed() {
        return enterKeyPressed;
    }
    
    /**
     * 
     * @return 
     */
    public String getValue() {
        return textField.getValue();
    }
    
    private void updateComponentsBounds() {
        
        titleBarBounds.x = bounds.x;
        titleBarBounds.y = bounds.y;
        closeButton.bounds.x = bounds.x + bounds.width - 22;
        closeButton.bounds.y = bounds.y + 3;
        
        messageLabel.bounds.x = bounds.x + PADDING;
        messageLabel.bounds.y = bounds.y + titleBarBounds.height + PADDING + lineHeight / 2;
        
        double w = okButton.bounds.width + cancelButton.bounds.width + PADDING / 2;
        double start = bounds.x + bounds.width / 2 - w / 2;
        
        okButton.bounds.x = start;
        okButton.bounds.y = bounds.y + bounds.height - PADDING - okButton.bounds.height;
        cancelButton.bounds.x = okButton.bounds.x + okButton.bounds.width + PADDING / 2;
        cancelButton.bounds.y = okButton.bounds.y;
        
        textField.bounds.x = bounds.x + PADDING;
        textField.bounds.y = okButton.bounds.y - textField.bounds.height - lineHeight + 3;
        textField.bounds.width = bounds.width - PADDING * 2;
        
    }
    
    @Override
    public void move( double xAmount, double yAmount ) {
        
        super.move( xAmount, yAmount );
        
        messageLabel.bounds.x = bounds.x + PADDING;
        messageLabel.bounds.y = bounds.y + titleBarBounds.height + PADDING + lineHeight / 2;
        
        double w = okButton.bounds.width + cancelButton.bounds.width + PADDING / 2;
        double start = bounds.x + bounds.width / 2 - w / 2;
        
        okButton.bounds.x = start;
        okButton.bounds.y = bounds.y + bounds.height - PADDING - okButton.bounds.height;
        cancelButton.bounds.x = okButton.bounds.x + okButton.bounds.width + PADDING / 2;
        cancelButton.bounds.y = okButton.bounds.y;
        
        textField.bounds.x = bounds.x + PADDING;
        textField.bounds.y = okButton.bounds.y - textField.bounds.height - lineHeight + 3;
        textField.bounds.width = bounds.width - PADDING * 2;
        
    }
    
}

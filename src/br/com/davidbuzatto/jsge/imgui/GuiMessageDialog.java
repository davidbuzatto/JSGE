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
import br.com.davidbuzatto.jsge.math.Vector2;
import java.awt.Color;

/**
 * Um diálogo para mensagens.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class GuiMessageDialog extends GuiWindow {
    
    private static final int PADDING = 20;
    private static final Color OVERLAY_COLOR = new Color( 0, 0, 0, 100 );
    private static final double MIN_WIDTH = 250;
    private static final double MIN_HEIGHT = 100;
    
    private String message;
    private boolean showOverlay;
    
    private GuiLabel messageLabel;
    private GuiButton okButton;
    
    private double messageWidth;
    private double messageHeight;
    private double lineHeight;
    
    private boolean boundsCalculationOk;
    
    /**
     * Cria o componente.
     * 
     * @param title O título do componente.
     * @param message A mensagem do componente.
     * @param showOverlay Verdadeiro para desenhar uma camada de sobreposição atrás do diálogo.
     * @param engine A instância da engine utilizada para desenhar e atualizar
     * o componente.
     */
    public GuiMessageDialog( String title, String message, boolean showOverlay, EngineFrame engine ) {
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
    public GuiMessageDialog( String title, String message, boolean showOverlay ) {
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
        } else {
            this.messageLabel = new GuiLabel( 0, 0, 0, 0, message, engine );
            this.okButton = new GuiButton( 0, 0, 40, 30, "OK", engine );
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
            double height = messageHeight + 2 * PADDING + titleBarBounds.height + okButton.bounds.height + lineHeight;

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
        
        closeButton.setVisible( visible );
        okButton.setVisible( visible );
            
        closeButton.update( delta );
        okButton.update( delta );
        
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
            okButton.draw();
            
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
     * Mostra o diálogo centralizado na tela.
     */
    public void show() {
        show( engine.getScreenWidth() / 2 - bounds.width / 2, engine.getScreenHeight() / 2 - bounds.height / 2 );
    }
    
    /**
     * Mostra o diálogo numa posição específica.
     * 
     * @param x Coordenada x onde o vértice superior esquerdo do retângulo que 
     * define os limites do componente será posicionado.
     * @param y Coordenada y onde o vértice superior esquerdo do retângulo que 
     * define os limites do componente será posicionado.
     */
    public void show( double x, double y ) {
        bounds.x = x;
        bounds.y = y;
        updateComponentsBounds();
        super.setVisible( true );
    }
    
    /**
     * Mostra o diálogo numa posição específica.
     * 
     * @param position Posição onde o vértice superior esquerdo do retângulo que 
     * define os limites do componente será posicionado.
     */
    public void show( Vector2 position ) {
        show( position.x, position.y );
    }
    
    /**
     * Esconde o diálogo.
     */
    public void hide() {
        super.setVisible( false );
    }
    
    /**
     * Retorna se o botão OK foi pressionado no ciclo atual.
     * 
     * @return Verdadeiro caso tenha sido pressionado, falso caso contrário.
     */
    public boolean isOkButtonPressed() {
        return okButton.isMousePressed();
    }
    
    private void updateComponentsBounds() {
        
        titleBarBounds.x = bounds.x;
        titleBarBounds.y = bounds.y;
        closeButton.bounds.x = bounds.x + bounds.width - 22;
        closeButton.bounds.y = bounds.y + 3;
        
        messageLabel.bounds.x = bounds.x + PADDING;
        messageLabel.bounds.y = bounds.y + titleBarBounds.height + PADDING + lineHeight / 2;
        okButton.bounds.x = bounds.x + bounds.width / 2 - okButton.bounds.width / 2;
        okButton.bounds.y = bounds.y + bounds.height - PADDING - okButton.bounds.height;
        
    }
    
    @Override
    public void move( double xAmount, double yAmount ) {
        
        super.move( xAmount, yAmount );
        
        messageLabel.bounds.x = bounds.x + PADDING;
        messageLabel.bounds.y = bounds.y + titleBarBounds.height + PADDING + lineHeight / 2;
        okButton.bounds.x = bounds.x + bounds.width / 2 - okButton.bounds.width / 2;
        okButton.bounds.y = bounds.y + bounds.height - PADDING - okButton.bounds.height;
        
    }
    
}

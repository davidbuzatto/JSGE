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
    private boolean useOverlay;
    
    private GuiLabel messageLabel;
    private GuiButton okButton;
    
    private double messageWidth;
    private double messageHeight;
    private double lineHeight;
    
    private boolean boundsCalculationOk;
    
    public GuiMessageDialog( String title, String message, boolean useOverlay, EngineFrame engine ) {
        super( 0, 0, 0, 0, title, engine );
        initComponents( engine, message, useOverlay );
    }
    
    public GuiMessageDialog( String title, String message, boolean useOverlay ) {
        super( 0, 0, 0, 0, title );
        initComponents( null, message, useOverlay );
    }
    
    private void initComponents( EngineFrame engine, String message, boolean useOverlay ) {
        super.initComponents( engine );
        this.message = message;
        this.useOverlay = useOverlay;
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
            
            if ( useOverlay ) {
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
        throw new IllegalStateException( "You must use shot and hide methods." );
    }
    
    public void show() {
        show( engine.getScreenWidth() / 2 - bounds.width / 2, engine.getScreenHeight() / 2 - bounds.height / 2 );
    }
    
    public void show( double x, double y ) {
        bounds.x = x;
        bounds.y = y;
        updateComponentsBounds();
        super.setVisible( true );
    }
    
    public void hide() {
        super.setVisible( false );
    }
    
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

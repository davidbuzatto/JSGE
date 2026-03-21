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

/**
 * A confirmation dialog.
 *
 * @author Prof. Dr. David Buzatto
 */
public class GuiConfirmDialog extends GuiWindow {
    
    private String message;
    private boolean showOverlay;
    
    protected GuiLabel messageLabel;
    protected GuiButton button1;
    protected GuiButton button2;
    protected GuiButton button3;
    private GuiButton[] buttons;
    
    private double messageWidth;
    private double messageHeight;
    private double lineHeight;
    
    private boolean boundsCalculationOk;
    
    /**
     * Creates the component.
     *
     * @param title The title of the component.
     * @param message The message of the component.
     * @param button1Text Text for the first button.
     * @param button2Text Text for the second button.
     * @param button3Text Text for the third button.
     * @param showOverlay True to draw an overlay layer behind the dialog.
     * @param engine The engine instance used to draw and update the component.
     */
    public GuiConfirmDialog( String title, String message, String button1Text, String button2Text, String button3Text, boolean showOverlay, EngineFrame engine ) {
        super( 0, 0, 0, 0, title, engine );
        initComponents( engine, message, button1Text, button2Text, button3Text, showOverlay );
    }
    
    /**
     * Creates the component.
     *
     * This constructor version depends on the "injectable" configuration of an
     * engine instance.
     * @see br.com.davidbuzatto.jsge.core.engine.EngineFrame#useAsDependencyForIMGUI
     *
     * @param title The title of the component.
     * @param message The message of the component.
     * @param button1Text Text for the first button.
     * @param button2Text Text for the second button.
     * @param button3Text Text for the third button.
     * @param showOverlay True to draw an overlay layer behind the dialog.
     */
    public GuiConfirmDialog( String title, String message, String button1Text, String button2Text, String button3Text, boolean showOverlay ) {
        super( 0, 0, 0, 0, title );
        initComponents( null, message, button1Text, button2Text, button3Text, showOverlay );
    }
    
    /**
     * Cria o componente.
     * 
     * @param engine A instância da engine utilizada para desenhar e atualizar
     * o componente.
     * @param message A mensagem do componente.
     * @param button1Text Texto do primeiro botão.
     * @param button2Text Texto do segundo botão.
     * @param button3Text Texto do terceiro botão.
     * @param showOverlay Verdadeiro para desenhar uma camada de sobreposição atrás do diálogo. 
     */
    private void initComponents( EngineFrame engine, String message, String button1Text, String button2Text, String button3Text, boolean showOverlay ) {
        super.initComponents( engine );
        this.message = message;
        this.showOverlay = showOverlay;
        if ( engine == null ) {
            this.messageLabel = new GuiLabel( 0, 0, 0, 0, message );
            if ( button1Text != null && !button1Text.trim().isEmpty() ) {
                this.button1 = new GuiButton( 0, 0, 0, DIALOG_BUTTON_HEIGHT, button1Text );
            } else {
                this.button1 = new GuiButton( 0, 0, 0, DIALOG_BUTTON_HEIGHT, "" );
            }
            if ( button2Text != null && !button2Text.trim().isEmpty() ) {
                this.button2 = new GuiButton( 0, 0, 0, DIALOG_BUTTON_HEIGHT, button2Text );
            } else {
                this.button2 = new GuiButton( 0, 0, 0, DIALOG_BUTTON_HEIGHT, "" );
            }
            if ( button3Text != null && !button3Text.trim().isEmpty() ) {
                this.button3 = new GuiButton( 0, 0, 0, DIALOG_BUTTON_HEIGHT, button3Text );
            } else {
                this.button3 = new GuiButton( 0, 0, 0, DIALOG_BUTTON_HEIGHT, "" );
            }
        } else {
            this.messageLabel = new GuiLabel( 0, 0, 0, 0, message, engine );
            if ( button1Text != null && !button1Text.trim().isEmpty() ) {
                this.button1 = new GuiButton( 0, 0, 0, DIALOG_BUTTON_HEIGHT, button1Text, engine );
            } else {
                this.button1 = new GuiButton( 0, 0, 0, DIALOG_BUTTON_HEIGHT, "", engine );
            }
            if ( button2Text != null && !button2Text.trim().isEmpty() ) {
                this.button2 = new GuiButton( 0, 0, 0, DIALOG_BUTTON_HEIGHT, button2Text, engine );
            } else {
                this.button2 = new GuiButton( 0, 0, 0, DIALOG_BUTTON_HEIGHT, "", engine );
            }
            if ( button3Text != null && !button3Text.trim().isEmpty() ) {
                this.button3 = new GuiButton( 0, 0, 0, DIALOG_BUTTON_HEIGHT, button3Text, engine );
            } else {
                this.button3 = new GuiButton( 0, 0, 0, DIALOG_BUTTON_HEIGHT, "", engine );
            }
        }
        buttons = new GuiButton[] { button1, button2, button3 };
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

            double width = messageWidth + 2 * DIALOG_CONTENT_PADDING;
            double height = messageHeight + 2 * DIALOG_CONTENT_PADDING + titleBarBounds.height + buttons[0].bounds.height + lineHeight;

            if ( width < DIALOG_MIN_WIDTH ) {
                width = DIALOG_MIN_WIDTH;
            }

            updateButtonsBounds( buttons );
            double buttonsWidth = button3.bounds.x + button3.bounds.width - button1.bounds.x + DIALOG_CONTENT_PADDING * 2;
            if ( buttonsWidth > width ) {
                width = buttonsWidth;
            }
            
            if ( height < DIALOG_MIN_HEIGHT ) {
                height = DIALOG_MIN_HEIGHT;
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
        closeButton.update( delta );
        
        for ( int i = 0; i < buttons.length; i++ ) {
            if ( !buttons[i].text.isEmpty() ) {
                buttons[i].setVisible( visible );
                buttons[i].update( delta );
            }
        }
        
    }
    
    @Override
    public void draw() {
        
        updateBounds();
        
        if ( visible ) {
            
            if ( showOverlay ) {
                engine.fillRectangle( 0, 0, engine.getScreenWidth(), engine.getScreenHeight(), DIALOG_OVERLAY_COLOR );
            }

            super.draw();
            
            messageLabel.draw();
            
            for ( int i = 0; i < buttons.length; i++ ) {
                if ( !buttons[i].text.isEmpty() ) {
                    buttons[i].draw();
                }
            }
            
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
     * Shows the dialog centered on the screen.
     */
    public void show() {
        show( engine.getScreenWidth() / 2 - bounds.width / 2, engine.getScreenHeight() / 2 - bounds.height / 2 );
    }
    
    /**
     * Shows the dialog at a specific position.
     *
     * @param x The x coordinate where the upper-left vertex of the rectangle
     * that defines the bounds of the component will be positioned.
     * @param y The y coordinate where the upper-left vertex of the rectangle
     * that defines the bounds of the component will be positioned.
     */
    public void show( double x, double y ) {
        bounds.x = x;
        bounds.y = y;
        updateComponentsBounds();
        super.setVisible( true );
    }
    
    /**
     * Shows the dialog at a specific position.
     *
     * @param position The position where the upper-left vertex of the rectangle
     * that defines the bounds of the component will be positioned.
     */
    public void show( Vector2 position ) {
        show( position.x, position.y );
    }
    
    /**
     * Hides the dialog.
     */
    public void hide() {
        super.setVisible( false );
    }
    
    /**
     * Returns whether button 1 was pressed in the current cycle.
     *
     * @return True if it was pressed, false otherwise.
     */
    public boolean isButton1Pressed() {
        return button1.isMousePressed();
    }
    
    /**
     * Returns whether button 2 was pressed in the current cycle.
     *
     * @return True if it was pressed, false otherwise.
     */
    public boolean isButton2Pressed() {
        return button2.isMousePressed();
    }
    
    /**
     * Returns whether button 3 was pressed in the current cycle.
     *
     * @return True if it was pressed, false otherwise.
     */
    public boolean isButton3Pressed() {
        return button3.isMousePressed();
    }
    
    /**
     * Returns the text of the button pressed in the current cycle.
     *
     * @return The text of the pressed button, or null if no button was pressed.
     */
    public String getPressedButton() {
        for ( int i = 0; i < buttons.length; i++ ) {
            if ( buttons[i].isMousePressed() ) {
                return buttons[i].text;
            }
        }
        return null;
    }
    
    /**
     * Returns the number of the button pressed in the current cycle.
     *
     * @return The number of the pressed button, or zero if no button was pressed.
     */
    public int getPressedButtonNumber() {
        for ( int i = 0; i < buttons.length; i++ ) {
            if ( buttons[i].isMousePressed() ) {
                return i + 1;
            }
        }
        return 0;
    }
    
    private void updateComponentsBounds() {
        
        titleBarBounds.x = bounds.x;
        titleBarBounds.y = bounds.y;
        closeButton.bounds.x = bounds.x + bounds.width - 22;
        closeButton.bounds.y = bounds.y + 3;
        
        messageLabel.bounds.x = bounds.x + DIALOG_CONTENT_PADDING;
        messageLabel.bounds.y = bounds.y + titleBarBounds.height + DIALOG_CONTENT_PADDING + lineHeight / 2;
        
        updateButtonsBounds( buttons );
        
    }
    
    @Override
    public void move( double xAmount, double yAmount ) {
        
        super.move( xAmount, yAmount );
        
        messageLabel.bounds.x = bounds.x + DIALOG_CONTENT_PADDING;
        messageLabel.bounds.y = bounds.y + titleBarBounds.height + DIALOG_CONTENT_PADDING + lineHeight / 2;
        
        updateButtonsBounds( buttons );
        
    }
    
    @Override
    public void apply( GuiTheme theme ) {
        super.apply( theme );
        messageLabel.apply( theme );
        button1.apply( theme );
        button2.apply( theme );
        button3.apply( theme );
    }
    
}

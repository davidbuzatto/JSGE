/*
 * Copyright (C) 2026 Prof. Dr. David Buzatto
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
 * A data input dialog.
 *
 * @author Prof. Dr. David Buzatto
 */
public class GuiInputDialog extends GuiWindow {
    
    private String message;
    private boolean showOverlay;
    
    protected GuiLabel messageLabel;
    protected GuiTextField textField;
    protected GuiButton okButton;
    protected GuiButton cancelButton;
    private GuiButton[] buttons;
    
    private double messageWidth;
    private double messageHeight;
    private double lineHeight;
    
    private boolean boundsCalculationOk;
    
    private boolean enterKeyPressed;
    
    /**
     * Creates the component.
     *
     * @param title The title of the component.
     * @param message The message of the component.
     * @param showOverlay True to draw an overlay layer behind the dialog.
     * @param engine The engine instance used to draw and update the component.
     */
    public GuiInputDialog( String title, String message, boolean showOverlay, EngineFrame engine ) {
        super( 0, 0, 0, 0, title, engine );
        initComponents( engine, message, "Cancel", showOverlay );
    }
    
    /**
     * Creates the component.
     *
     * @param title The title of the component.
     * @param message The message of the component.
     * @param showOverlay True to draw an overlay layer behind the dialog.
     * @param cancelButonText Text for the cancel button.
     * @param engine The engine instance used to draw and update the component.
     */
    public GuiInputDialog( String title, String message, String cancelButonText, boolean showOverlay, EngineFrame engine ) {
        super( 0, 0, 0, 0, title, engine );
        initComponents( engine, message, cancelButonText, showOverlay );
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
     * @param showOverlay True to draw an overlay layer behind the dialog.
     */
    public GuiInputDialog( String title, String message, boolean showOverlay ) {
        super( 0, 0, 0, 0, title );
        initComponents( null, message, "Cancel", showOverlay );
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
     * @param cancelButonText Text for the cancel button.
     * @param showOverlay True to draw an overlay layer behind the dialog.
     */
    public GuiInputDialog( String title, String message, String cancelButonText, boolean showOverlay ) {
        super( 0, 0, 0, 0, title );
        initComponents( null, message, cancelButonText, showOverlay );
    }
    
    private void initComponents( EngineFrame engine, String message, String cancelButonText, boolean showOverlay ) {
        super.initComponents( engine );
        this.message = message;
        this.showOverlay = showOverlay;
        if ( engine == null ) {
            this.messageLabel = new GuiLabel( 0, 0, 0, 0, message );
            this.okButton = new GuiButton( 0, 0, 0, DIALOG_BUTTON_HEIGHT, "OK" );
            this.cancelButton = new GuiButton( 0, 0, 0, DIALOG_BUTTON_HEIGHT, cancelButonText );
            this.textField = new GuiTextField( 0, 0, DIALOG_MIN_WIDTH - DIALOG_CONTENT_PADDING * 2, 25, "" );
        } else {
            this.messageLabel = new GuiLabel( 0, 0, 0, 0, message, engine );
            this.okButton = new GuiButton( 0, 0, 0, DIALOG_BUTTON_HEIGHT, "OK", engine );
            this.cancelButton = new GuiButton( 0, 0, 0, DIALOG_BUTTON_HEIGHT, cancelButonText, engine );
            this.textField = new GuiTextField( 0, 0, DIALOG_MIN_WIDTH - DIALOG_CONTENT_PADDING * 2, 25, "", engine );
        }
        buttons = new GuiButton[] { okButton, cancelButton };
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
            double height = messageHeight + 2 * DIALOG_CONTENT_PADDING + titleBarBounds.height + okButton.bounds.height + lineHeight + textField.bounds.height;

            if ( width < DIALOG_MIN_WIDTH ) {
                width = DIALOG_MIN_WIDTH;
            }

            updateButtonsBounds( buttons );
            double buttonsWidth = cancelButton.bounds.x + cancelButton.bounds.width - okButton.bounds.x + DIALOG_CONTENT_PADDING * 2;
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
                engine.fillRectangle( 0, 0, engine.getScreenWidth(), engine.getScreenHeight(), DIALOG_OVERLAY_COLOR );
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
        textField.setValue( "" );
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
     * Returns whether the OK button was pressed in the current cycle.
     *
     * @return True if it was pressed, false otherwise.
     */
    public boolean isOkButtonPressed() {
        return okButton.isMousePressed();
    }
    
    /**
     * Returns whether the Cancel button was pressed in the current cycle.
     *
     * @return True if it was pressed, false otherwise.
     */
    public boolean isCancelButtonPressed() {
        return cancelButton.isMousePressed();
    }
    
    /**
     * Returns whether the ENTER key was pressed in the current cycle while
     * editing the text field.
     *
     * @return True if it was pressed, false otherwise.
     */
    public boolean isEnterKeyPressed() {
        return enterKeyPressed;
    }
    
    /**
     * Gets the value contained in the text field.
     *
     * @return The value of the text field.
     */
    public String getValue() {
        return textField.getValue();
    }
    
    private void updateComponentsBounds() {
        
        titleBarBounds.x = bounds.x;
        titleBarBounds.y = bounds.y;
        closeButton.bounds.x = bounds.x + bounds.width - 22;
        closeButton.bounds.y = bounds.y + 3;
        
        messageLabel.bounds.x = bounds.x + DIALOG_CONTENT_PADDING;
        messageLabel.bounds.y = bounds.y + titleBarBounds.height + DIALOG_CONTENT_PADDING + lineHeight / 2;
        
        double w = okButton.bounds.width + cancelButton.bounds.width + DIALOG_CONTENT_PADDING / 2;
        double start = bounds.x + bounds.width / 2 - w / 2;
        
        okButton.bounds.x = start;
        okButton.bounds.y = bounds.y + bounds.height - DIALOG_CONTENT_PADDING - okButton.bounds.height;
        cancelButton.bounds.x = okButton.bounds.x + okButton.bounds.width + DIALOG_CONTENT_PADDING / 2;
        cancelButton.bounds.y = okButton.bounds.y;
        
        textField.bounds.x = bounds.x + DIALOG_CONTENT_PADDING;
        textField.bounds.y = okButton.bounds.y - textField.bounds.height - lineHeight + 3;
        textField.bounds.width = bounds.width - DIALOG_CONTENT_PADDING * 2;
        
        updateButtonsBounds( buttons );
        
    }
    
    @Override
    public void move( double xAmount, double yAmount ) {
        
        super.move( xAmount, yAmount );
        
        messageLabel.bounds.x = bounds.x + DIALOG_CONTENT_PADDING;
        messageLabel.bounds.y = bounds.y + titleBarBounds.height + DIALOG_CONTENT_PADDING + lineHeight / 2;
        
        double w = okButton.bounds.width + cancelButton.bounds.width + DIALOG_CONTENT_PADDING / 2;
        double start = bounds.x + bounds.width / 2 - w / 2;
        
        okButton.bounds.x = start;
        okButton.bounds.y = bounds.y + bounds.height - DIALOG_CONTENT_PADDING - okButton.bounds.height;
        cancelButton.bounds.x = okButton.bounds.x + okButton.bounds.width + DIALOG_CONTENT_PADDING / 2;
        cancelButton.bounds.y = okButton.bounds.y;
        
        textField.bounds.x = bounds.x + DIALOG_CONTENT_PADDING;
        textField.bounds.y = okButton.bounds.y - textField.bounds.height - lineHeight + 3;
        textField.bounds.width = bounds.width - DIALOG_CONTENT_PADDING * 2;
        
        updateButtonsBounds( buttons );
        
    }
    
    @Override
    public void apply( GuiTheme theme ) {
        super.apply( theme );
        messageLabel.apply( theme );
        okButton.apply( theme );
        cancelButton.apply( theme );
        textField.apply( theme );
    }
    
}

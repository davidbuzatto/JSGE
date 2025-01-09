/*
 * Copyright (C) 2024 Prof. Dr. David Buzatto
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
package br.com.davidbuzatto.jsge.core.engine.imgui;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import java.awt.Color;

/**
 * Representação genérica de um componente.
 * 
 * @author Prof. Dr. David Buzatto
 */
public abstract class GuiComponent {
    
    private static int idCounter;
    private int id;
    
    protected Rectangle bounds;
    protected EngineFrame e;
    protected GuiComponentState mouseState = GuiComponentState.MOUSE_OUT;
    protected boolean enabled = true;
    protected boolean visible = true;
    protected boolean drawBounds;
    
    protected static final int FONT_SIZE = 12;
    protected static final int LINE_WIDTH = 2;
    
    protected static final Color MOUSE_OUT_BACKGROUND_COLOR = new Color( 201, 201, 201 );
    protected static final Color MOUSE_OUT_BORDER_COLOR = new Color( 131, 131, 131 );
    protected static final Color MOUSE_OUT_TEXT_COLOR = new Color( 104, 104, 104 );
    
    protected static final Color MOUSE_OVER_BACKGROUND_COLOR = new Color( 201, 239, 254 );
    protected static final Color MOUSE_OVER_BORDER_COLOR = new Color( 91, 178, 217 );
    protected static final Color MOUSE_OVER_TEXT_COLOR = new Color( 108, 155, 188 );
    
    protected static final Color MOUSE_DOWN_BACKGROUND_COLOR = new Color( 151, 232, 255 );
    protected static final Color MOUSE_DOWN_BORDER_COLOR = new Color( 4, 146, 199 );
    protected static final Color MOUSE_DOWN_TEXT_COLOR = new Color( 54, 139, 175 );
    
    protected static final Color DISABLED_BACKGROUND_COLOR = new Color( 230, 233, 233 );
    protected static final Color DISABLED_BORDER_COLOR = new Color( 181, 193, 194 );
    protected static final Color DISABLED_TEXT_COLOR = new Color( 174, 183, 184 );
    
    public abstract void update( double delta );
    public abstract void draw();
    
    public GuiComponent() {
        this.id = idCounter++;
    }
    
    protected void drawCheckBox( Color borderColor, double checkSize, boolean fillInternal ) {
        double x = bounds.x;
        double y = bounds.y + bounds.height / 2 - checkSize / 2;
        e.drawRectangle( x, y, checkSize, checkSize, borderColor );
        if ( fillInternal ) {
            e.fillRectangle( x + 2, y + 2, checkSize - 3, checkSize - 3, borderColor );
        }
    }
    
    protected void drawCheckBox( Color borderColor, boolean fillInternal ) {
        e.drawRectangle( bounds, borderColor );
        if ( fillInternal ) {
            e.fillRectangle( bounds.x + 2, bounds.y + 2, bounds.width - 3, bounds.height - 3, borderColor );
        }
    }
    
    protected void drawRadio( Color borderColor, double radioRadius, boolean fillInternal ) {
        double x = bounds.x + radioRadius;
        double y = bounds.y + bounds.height / 2;
        e.drawCircle( x, y, radioRadius, borderColor );
        if ( fillInternal ) {
            e.fillCircle( x + 0.5, y + 0.5, radioRadius - 2, borderColor );
        }
    }
    
    protected void drawRadio( Color borderColor, boolean fillInternal ) {
        e.drawEllipse( bounds.x + bounds.width / 2, bounds.y + bounds.height / 2, bounds.width / 2, bounds.height / 2, borderColor );
        if ( fillInternal ) {
            e.fillEllipse( bounds.x + bounds.width / 2 + 0.5, bounds.y + bounds.height / 2 + 0.5, bounds.width / 2 - 2, bounds.height / 2 - 2, borderColor );
        }
    }
    
    protected void fillBoundsAsRectangle( Color backgroundColor, Color borderColor ) {
        e.fillRectangle( bounds, backgroundColor );
        e.drawRectangle( bounds, borderColor );
    }
    
    protected void drawBounds() {
        if ( drawBounds ) {
            e.setStrokeLineWidth( 1 );
            e.drawRectangle( bounds, EngineFrame.BLUE );
        }
    }
    
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled( boolean enabled ) {
        this.enabled = enabled;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible( boolean visible ) {
        this.visible = visible;
    }

    public boolean isDrawBounds() {
        return drawBounds;
    }

    public void setDrawBounds( boolean drawBounds ) {
        this.drawBounds = drawBounds;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final GuiComponent other = (GuiComponent) obj;
        return this.id == other.id;
    }
    
}

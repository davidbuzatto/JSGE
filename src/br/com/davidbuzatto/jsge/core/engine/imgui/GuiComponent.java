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
    protected EngineFrame engine;
    
    protected GuiComponentState mouseState = GuiComponentState.MOUSE_OUT;
    protected boolean enabled = true;
    protected boolean visible = true;
    protected boolean drawBounds;
    
    public static final int FONT_SIZE = 12;
    public static final int LINE_WIDTH = 2;
    
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
    
    public GuiComponent( Rectangle bounds, EngineFrame engine ) {
        this.id = idCounter++;
        this.engine = engine;
        this.bounds = bounds;
    }
    
    public GuiComponent( double x, double y, double width, double height, EngineFrame engine ) {
        this( new Rectangle( x, y, width, height ), engine );
    }
    
    public GuiComponent( Rectangle bounds ) {
        this( bounds, EngineFrame.getDependencyEngine() );
    }
    
    public GuiComponent( double x, double y, double width, double height ) {
        this( new Rectangle( x, y, width, height ) );
    }
    
    protected void drawBounds() {
        if ( drawBounds ) {
            engine.setStrokeLineWidth( 1 );
            engine.drawRectangle( bounds, EngineFrame.BLUE );
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

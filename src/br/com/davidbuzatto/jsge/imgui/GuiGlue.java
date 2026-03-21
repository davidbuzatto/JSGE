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
import java.util.ArrayList;
import java.util.List;

/**
 * A component glue. The idea is that there is a base component
 * to which various child components are "glued". Ideal for use
 * with container-type components.
 *
 * @author Prof. Dr. David Buzatto
 */
public class GuiGlue extends GuiComponent {

    private static final double GLUE_SIZE = 30;
    
    protected GuiComponent baseComponent;
    protected final List<GuiComponent> children;
    
    /**
     * Creates the component.
     *
     * @param baseComponent The base component of the glue. This component
     * acts as the anchor, carrying all child components together.
     * @param engine The engine instance used to draw and update the component.
     */
    public GuiGlue( GuiComponent baseComponent, EngineFrame engine ) {
        super( baseComponent.bounds.x, baseComponent.bounds.y, GLUE_SIZE, GLUE_SIZE, engine );
        this.baseComponent = baseComponent;
        children = new ArrayList<>();
    }
    
    /**
     * Creates the component.
     *
     * This constructor version depends on the "injectable" configuration of an
     * engine instance.
     * @see br.com.davidbuzatto.jsge.core.engine.EngineFrame#useAsDependencyForIMGUI
     *
     * @param baseComponent The base component of the glue. This component
     * acts as the anchor, carrying all child components together.
     */
    public GuiGlue( GuiComponent baseComponent ) {
        super( baseComponent.bounds.x, baseComponent.bounds.y, GLUE_SIZE, GLUE_SIZE );
        this.baseComponent = baseComponent;
        children = new ArrayList<>();
    }

    @Override
    public void update( double delta ) {
        
        super.update( delta );
        
        if ( visible && enabled ) {
            
            bounds.x = baseComponent.bounds.x;
            bounds.y = baseComponent.bounds.y;

            baseComponent.update( delta );
            for ( GuiComponent c : children ) {
                c.update( delta );
            }
            
        }
        
    }
    
    @Override
    public void draw() {
        
        if ( visible ) {
            
            baseComponent.draw();
            for ( GuiComponent c : children ) {
                c.draw();
            }
            
            drawBounds();
            
        }
        
    }

    @Override
    public void move( double xAmount, double yAmount ) {
        bounds.x += xAmount;
        bounds.y += yAmount;
        baseComponent.move( xAmount, yAmount );
        for ( GuiComponent c : children ) {
            c.move( xAmount, yAmount );
        }
    }

    @Override
    public void setVisible( boolean visible ) {
        super.setVisible( visible );
        baseComponent.setVisible( visible );
        for ( GuiComponent c : children ) {
            c.setVisible( visible );
        }
    }

    @Override
    public void setEnabled( boolean enabled ) {
        super.setEnabled( enabled );
        baseComponent.setEnabled( enabled );
        for ( GuiComponent c : children ) {
            c.setEnabled( enabled );
        }
    }

    @Override
    public void setDrawingBounds( boolean drawingBounds ) {
        super.setDrawingBounds( drawingBounds );
        baseComponent.setDrawingBounds( drawingBounds );
        for ( GuiComponent c : children ) {
            c.setDrawingBounds( drawingBounds );
        }
    }
    
    @Override
    protected void drawBounds() {
        if ( drawingBounds ) {
            engine.setStrokeLineWidth( LINE_WIDTH );
            engine.drawRectangle( bounds, EngineFrame.BLUE );
            engine.drawLine( bounds.x, bounds.y, bounds.x + bounds.width, bounds.y + bounds.height, EngineFrame.BLUE );
            engine.drawLine( bounds.x, bounds.y + bounds.height, bounds.x + bounds.width, bounds.y, EngineFrame.BLUE );
        }
    }
    
    /**
     * Adds a child component.
     * The drawing order follows the insertion order.
     *
     * @param child A child component.
     * @param x X coordinate relative to the base component.
     * @param y Y coordinate relative to the base component.
     */
    public void addChild( GuiComponent child, double x, double y ) {
        child.move( 
                baseComponent.bounds.x - child.bounds.x + x,
                baseComponent.bounds.y - child.bounds.y + y
        );
        children.add( child );
    }
    
    @Override
    public void apply( GuiTheme theme ) {
        super.apply( theme );
        baseComponent.apply( theme );
        for ( GuiComponent c : children ) {
            c.apply( theme );
        }
    }
    
}

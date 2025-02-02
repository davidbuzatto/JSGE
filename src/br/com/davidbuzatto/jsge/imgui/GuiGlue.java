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
 * Uma cola de componentes. A ideia é que exista um componente básico
 * onde serão "colados" diversos componentes filhos. Ideal para ser utilizada
 * com componentes do tipo contâiner.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class GuiGlue extends GuiComponent {

    private static final double GLUE_SIZE = 30;
    
    protected GuiComponent baseComponent;
    protected final List<GuiComponent> children;
    
    /**
     * Cria o componente.
     * 
     * @param baseComponent O componente base da cola. É esse componente
     * que atuará como âncora, carregando todos os componentes filhos juntos.
     * @param engine A instância da engine utilizada para desenhar e atualizar
     * o componente.
     */
    public GuiGlue( GuiComponent baseComponent, EngineFrame engine ) {
        super( baseComponent.bounds.x, baseComponent.bounds.y, GLUE_SIZE, GLUE_SIZE, engine );
        this.baseComponent = baseComponent;
        children = new ArrayList<>();
    }
    
    /**
     * Cria o componente.
     * 
     * Essa versão do construtor depende da configuração "injetável" de uma
     * instância de uma engine.
     * @see br.com.davidbuzatto.jsge.core.engine.EngineFrame#useAsDependencyForIMGUI
     * 
     * @param baseComponent O componente base da cola. É esse componente
     * que atuará como âncora, carregando todos os componentes filhos juntos.
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
     * Adiciona um componente filho.
     * A ordem de desenho segue a ordem de inserção.
     * 
     * @param child Um componente filho.
     * @param x Coordenada x relativa ao componente principal.
     * @param y Coordenada y relativa ao componente principal.
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

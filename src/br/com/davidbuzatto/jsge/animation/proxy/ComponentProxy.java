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
package br.com.davidbuzatto.jsge.animation.proxy;

import java.awt.Color;

/**
 * Um proxy para componentes.
 * 
 * @param <ComponentType> Tipo do componente.
 * @author Prof. Dr. David Buzatto
 */
public abstract class ComponentProxy<ComponentType> {
    
    protected ComponentType component;
    
    /**
     * Cria o adaptador de proxy de componentes.
     * 
     * @param component O componente em que os dados serão redirecionados.
     */
    public ComponentProxy( ComponentType component ) {
        this.component = component;
    }

    /**
     * Obtém o componente do proxy.
     * 
     * @return O componente.
     */
    public ComponentType getComponent() {
        return component;
    }
    
    public abstract void setX( double x );
    public abstract void setX1( double x );
    public abstract void setX2( double x );
    public abstract void setStartX( double x );
    public abstract void setEndX( double x );
    public abstract void setY( double y );
    public abstract void setY1( double y );
    public abstract void setY2( double y );
    public abstract void setStartY( double y );
    public abstract void setEndY( double y );
    public abstract void setAngle( double angle );
    public abstract void setStartAngle( double angle );
    public abstract void setEndAngle( double angle );
    public abstract void setRotation( double rotation );
    public abstract void setStartRotation( double rotation );
    public abstract void setEndRotation( double rotation );
    public abstract void setScale( double scale );
    public abstract void setRadius( double radius );
    public abstract void setWidth( double width );
    public abstract void setHeight( double height );
    public abstract void setRed( double red );
    public abstract void setGreen( double green );
    public abstract void setBlue( double blue );
    public abstract void setAlpha( double alpha );
    public abstract void setColor( Color color );
    
    public abstract void setVelX( double xVel );
    public abstract void setVelY( double yVel );
    public abstract void setVelAngle( double angleVel );
    public abstract void setVelRotation( double rotationVel );
    public abstract void setVelScale( double scaleVel );
    public abstract void setVelRadius( double radiusVel );
    
    public abstract double getX();
    public abstract double getX1();
    public abstract double getX2();
    public abstract double getStartX();
    public abstract double getEndX();
    public abstract double getY();
    public abstract double getY1();
    public abstract double getY2();
    public abstract double getStartY();
    public abstract double getEndY();
    public abstract double getAngle();
    public abstract double getStartAngle();
    public abstract double getEndAngle();
    public abstract double getRotation();
    public abstract double getStartRotation();
    public abstract double getEndRotation();
    public abstract double getScale();
    public abstract double getRadius();
    public abstract double getWidth();
    public abstract double getHeight();
    public abstract double getRed();
    public abstract double getGreen();
    public abstract double getBlue();
    public abstract double getAlpha();
    public abstract Color getColor();
    
    public abstract double getVelX();
    public abstract double getVelY();
    public abstract double getVelAngle();
    public abstract double getVelRotation();
    public abstract double getVelScale();
    public abstract double getVelRadius();
    
}

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
import java.awt.Paint;

/**
 * Um proxy para componentes. Essa classe contém uma série de métodos que representam
 * possíveis propriedades dos componentes que serão manipulados.
 * 
 * @param <ComponentType> Tipo do componente.
 * @author Prof. Dr. David Buzatto
 */
public abstract class ComponentProxy<ComponentType> {
    
    /**
     * O componente que será manipulado através do proxy.
     */
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
    public abstract void setAngle1( double angle );
    public abstract void setAngle2( double angle );
    public abstract void setStartAngle( double angle );
    public abstract void setEndAngle( double angle );
    public abstract void setRotation( double rotation );
    public abstract void setRotation1( double rotation );
    public abstract void setRotation2( double rotation );
    public abstract void setStartRotation( double rotation );
    public abstract void setEndRotation( double rotation );
    public abstract void setScale( double scale );
    public abstract void setScale1( double scale );
    public abstract void setScale2( double scale );
    public abstract void setStartScale( double scale );
    public abstract void setEndScale( double scale );
    public abstract void setRadius( double radius );
    public abstract void setRadius1( double radius );
    public abstract void setRadius2( double radius );
    public abstract void setStartRadius( double radius );
    public abstract void setEndRadius( double radius );
    public abstract void setInnerRadius( double radius );
    public abstract void setInnerRadius1( double radius );
    public abstract void setInnerRadius2( double radius );
    public abstract void setStartInnerRadius( double radius );
    public abstract void setEndInnerRadius( double radius );
    public abstract void setOuterRadius( double radius );
    public abstract void setOuterRadius1( double radius );
    public abstract void setOuterRadius2( double radius );
    public abstract void setOuterInnerRadius( double radius );
    public abstract void setEndOuterRadius( double radius );
    public abstract void setSides( double sides );
    public abstract void setSides1( double sides );
    public abstract void setSides2( double sides );
    public abstract void setStartSides( double sides );
    public abstract void setEndSides( double sides );
    public abstract void setWidth( double width );
    public abstract void setWidth1( double width );
    public abstract void setWidth2( double width );
    public abstract void setStartWidth( double width );
    public abstract void setEndWidth( double width );
    public abstract void setHeight( double height );
    public abstract void setHeight1( double height );
    public abstract void setHeight2( double height );
    public abstract void setStartHeight( double height );
    public abstract void setEndHeight( double height );
    public abstract void setRed( int red );
    public abstract void setRed1( int red );
    public abstract void setRed2( int red );
    public abstract void setStartRed( int red );
    public abstract void setEndRed( int red );
    public abstract void setGreen( int green );
    public abstract void setGreen1( int green );
    public abstract void setGreen2( int green );
    public abstract void setStartGreen( int green );
    public abstract void setEndGreen( int green );
    public abstract void setBlue( int blue );
    public abstract void setBlue1( int blue );
    public abstract void setBlue2( int blue );
    public abstract void setStartBlue( int blue );
    public abstract void setEndBlue( int blue );
    public abstract void setAlpha( int alpha );
    public abstract void setAlpha1( int alpha );
    public abstract void setAlpha2( int alpha );
    public abstract void setStartAlpha( int alpha );
    public abstract void setEndAlpha( int alpha );
    public abstract void setColorHex( int colorHex );
    public abstract void setColorHex1( int colorHex );
    public abstract void setColorHex2( int colorHex );
    public abstract void setStartColorHex( int colorHex );
    public abstract void setEndColorHex( int colorHex );
    public abstract void setColor( Color color );
    public abstract void setColor1( Color color );
    public abstract void setColor2( Color color );
    public abstract void setStartColor( Color color );
    public abstract void setEndColor( Color color );
    public abstract void setPaint( Paint paint );
    public abstract void setPaint1( Paint paint );
    public abstract void setPaint2( Paint paint );
    public abstract void setStartPaint( Paint paint );
    public abstract void setEndPaint( Paint paint );
    
    public abstract void setVelX( double xVel );
    public abstract void setVelY( double yVel );
    public abstract void setVelAngle( double angleVel );
    public abstract void setVelRotation( double rotationVel );
    public abstract void setVelScale( double scaleVel );
    public abstract void setVelRadius( double radiusVel );
    
    public abstract void setSpeed( double speed );
    public abstract void setFriction( double friction );
    public abstract void setElasticity( double elasticity );
    public abstract void setGravity( double gravity );
    
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
    public abstract double getAngle1();
    public abstract double getAngle2();
    public abstract double getStartAngle();
    public abstract double getEndAngle();
    public abstract double getRotation();
    public abstract double getRotation1();
    public abstract double getRotation2();
    public abstract double getStartRotation();
    public abstract double getEndRotation();
    public abstract double getScale();
    public abstract double getScale1();
    public abstract double getScale2();
    public abstract double getStartScale();
    public abstract double getEndScale();
    public abstract double getRadius();
    public abstract double getRadius1();
    public abstract double getRadius2();
    public abstract double getStartRadius();
    public abstract double getEndRadius();
    public abstract double getInnerRadius();
    public abstract double getInnerRadius1();
    public abstract double getInnerRadius2();
    public abstract double getStartInnerRadius();
    public abstract double getEndInnerRadius();
    public abstract double getOuterRadius();
    public abstract double getOuterRadius1();
    public abstract double getOuterRadius2();
    public abstract double getOuterInnerRadius();
    public abstract double getEndOuterRadius();
    public abstract double getSides();
    public abstract double getSides1();
    public abstract double getSides2();
    public abstract double getStartSides();
    public abstract double getEndSides();
    public abstract double getWidth();
    public abstract double getWidth1();
    public abstract double getWidth2();
    public abstract double getStartWidth();
    public abstract double getEndWidth();
    public abstract double getHeight();
    public abstract double getHeight1();
    public abstract double getHeight2();
    public abstract double getStartHeight();
    public abstract double getEndHeight();
    public abstract int getRed();
    public abstract int getRed1();
    public abstract int getRed2();
    public abstract int getStartRed();
    public abstract int getEndRed();
    public abstract int getGreen();
    public abstract int getGreen1();
    public abstract int getGreen2();
    public abstract int getStartGreen();
    public abstract int getEndGreen();
    public abstract int getBlue();
    public abstract int getBlue1();
    public abstract int getBlue2();
    public abstract int getStartBlue();
    public abstract int getEndBlue();
    public abstract int getAlpha();
    public abstract int getAlpha1();
    public abstract int getAlpha2();
    public abstract int getStartAlpha();
    public abstract int getEndAlpha();
    public abstract int getColorHex();
    public abstract int getColorHex1();
    public abstract int getColorHex2();
    public abstract int getStartColorHex();
    public abstract int getEndColorHex();
    public abstract Color getColor();
    public abstract Color getColor1();
    public abstract Color getColor2();
    public abstract Color getStartColor();
    public abstract Color getEndColor();
    public abstract Paint getPaint();
    public abstract Paint getPaint1();
    public abstract Paint getPaint2();
    public abstract Paint getStartPaint();
    public abstract Paint getEndPaint();
    
    public abstract double getVelX();
    public abstract double getVelY();
    public abstract double getVelAngle();
    public abstract double getVelRotation();
    public abstract double getVelScale();
    public abstract double getVelRadius();
    
    public abstract double getSpeed();
    public abstract double getFriction();
    public abstract double getElasticity();
    public abstract double getGravity();
    
}

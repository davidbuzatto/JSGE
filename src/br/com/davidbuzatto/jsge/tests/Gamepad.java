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
package br.com.davidbuzatto.jsge.tests;

/**
 *
 * @author Prof. Dr. David Buzatto
 */
public class Gamepad {
    
    private static final int HAT_SWITCH_BUTTONS_LENGTH = 9;
    private static final boolean[] HAT_SWITCH_DEFAULT_VALUES = new boolean[HAT_SWITCH_BUTTONS_LENGTH];
    
    private int id;
    private String name;
    private boolean available;
    private boolean[] pressedButtons;
    private boolean[] lastPressedButtons;
    private boolean[] pressedHatSwitchButtons;
    private boolean[] lastPressedHatSwitchButtons;
    private float hatSwitch;
    private double x;
    private double y;
    private double z;
    private double rx;
    private double ry;
    private double rz;
    
    public Gamepad( int id ) {
        this.id = id;
        this.pressedButtons = new boolean[40];
        this.lastPressedButtons = new boolean[40];
        this.pressedHatSwitchButtons = new boolean[HAT_SWITCH_BUTTONS_LENGTH];
        this.lastPressedHatSwitchButtons = new boolean[HAT_SWITCH_BUTTONS_LENGTH];
    }
    
    public void setButtonState( int button, boolean value ) {
        if ( button < pressedButtons.length ) {
            pressedButtons[button] = value;
        }
    }
    
    public void setHatSwitchButtonState( int button, boolean value ) {
        if ( button < pressedHatSwitchButtons.length ) {
            pressedHatSwitchButtons[button] = value;
        }
    }
    
    public void resetHatSwitchButtons() {
        System.arraycopy( HAT_SWITCH_DEFAULT_VALUES, 0, pressedHatSwitchButtons, 0, HAT_SWITCH_BUTTONS_LENGTH );
    }
    
    public boolean isButtonPressed( int button ) {
        if ( button < pressedButtons.length ) {
            return pressedButtons[button];
        }
        return false;
    }
    
    public boolean isHatSwitchButtonPressed( int button ) {
        if ( button < pressedHatSwitchButtons.length ) {
            return pressedHatSwitchButtons[button];
        }
        return false;
    }

    public void copyLastState() {
        System.arraycopy( pressedButtons, 0, lastPressedButtons, 0, pressedButtons.length );
        System.arraycopy( pressedHatSwitchButtons, 0, lastPressedHatSwitchButtons, 0, pressedHatSwitchButtons.length );
    }

    public float getHatSwitch() {
        return hatSwitch;
    }

    public void setHatSwitch( float hatSwitch ) {
        this.hatSwitch = hatSwitch;
    }
    
    public double getX() {
        return x;
    }

    public void setX( double x ) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY( double y ) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ( double z ) {
        this.z = z;
    }

    public double getRx() {
        return rx;
    }

    public void setRx( double rx ) {
        this.rx = rx;
    }

    public double getRy() {
        return ry;
    }

    public void setRy( double ry ) {
        this.ry = ry;
    }

    public double getRz() {
        return rz;
    }

    public void setRz( double rz ) {
        this.rz = rz;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable( boolean available ) {
        this.available = available;
    }

    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        
        sb.append( "Gamepad: " ).append( id ).append( " " ).append( name );
        
        sb.append( "\nbuttons: " );
        for ( int i = 0; i < pressedButtons.length; i++ ) {
            if ( pressedButtons[i] ) {
                sb.append( String.format( "%d ", i ) );
            }
        }
        
        sb.append( "\nhat switch: " ).append( hatSwitch ).append( " " );
        for ( int i = 0; i < pressedHatSwitchButtons.length; i++ ) {
            if ( pressedHatSwitchButtons[i] ) {
                sb.append( String.format( "%d ", i ) );
            }
        }
        
        sb.append( "\naxes: " )
            .append( String.format( "x: %.2f ", x ) )
            .append( String.format( "y: %.2f ", y ) )
            .append( String.format( "z: %.2f ", z ) )
            .append( String.format( "rx: %.2f ", rx ) )
            .append( String.format( "ry: %.2f ", ry ) )
            .append( String.format( "rz: %.2f ", rz ) );
        
        return sb.toString();
        
    }
    
}

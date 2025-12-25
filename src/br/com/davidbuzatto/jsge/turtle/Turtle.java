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
package br.com.davidbuzatto.jsge.turtle;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.math.MathUtils;
import java.awt.BasicStroke;
import java.awt.Paint;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 *
 * @author Prof. Dr. David Buzatto
 */
public class Turtle {
    
    private List<TurtleState> frames;
    private Deque<TurtleStateSnapshot> snapshots;
    private TurtleState currentState;
    
    public Turtle() {
        reset();
    }
    
    public Turtle( double x, double y ) {
        reset( x, y );
    }
    
    private void addFrame( double x, double y, double angle, Paint color, BasicStroke bs, boolean brushDown ) {
        currentState = new TurtleState( x, y, angle, color, bs, brushDown );
        frames.add( currentState );
    }
    
    public void moveTo( double x, double y ) {
        addFrame(
            x, 
            y, 
            currentState.angle(), 
            currentState.color(), 
            currentState.stroke(),
            currentState.brushDown()
        );
    }
    
    public void moveForward( double length ) {
        addFrame(
            currentState.x() + length * Math.cos( currentState.angle() * MathUtils.DEG2RAD ),
            currentState.y() + length * Math.sin( currentState.angle() * MathUtils.DEG2RAD ),
            currentState.angle(),
            currentState.color(),
            currentState.stroke(),
            currentState.brushDown()
        );
    }
    
    public void moveBackward( double length ) {
        addFrame(
            currentState.x() - length * Math.cos( currentState.angle() * MathUtils.DEG2RAD ),
            currentState.y() - length * Math.sin( currentState.angle() * MathUtils.DEG2RAD ),
            currentState.angle(),
            currentState.color(),
            currentState.stroke(),
            currentState.brushDown()
        );
    }
    
    public void moveLeft( double length ) {
        addFrame(
            currentState.x() + length * Math.cos( ( currentState.angle() - 90 ) * MathUtils.DEG2RAD ),
            currentState.y() + length * Math.sin( ( currentState.angle() - 90 ) * MathUtils.DEG2RAD ),
            currentState.angle(),
            currentState.color(),
            currentState.stroke(),
            currentState.brushDown()
        );
    }
    
    public void moveRight( double length ) {
        addFrame(
            currentState.x() + length * Math.cos( ( currentState.angle() + 90 ) * MathUtils.DEG2RAD ),
            currentState.y() + length * Math.sin( ( currentState.angle() + 90 ) * MathUtils.DEG2RAD ),
            currentState.angle(),
            currentState.color(),
            currentState.stroke(),
            currentState.brushDown()
        );
    }
    
    public void rotate( double amount ) {
        addFrame(
            currentState.x(), 
            currentState.y(), 
            currentState.angle() + amount, 
            currentState.color(), 
            currentState.stroke(),
            currentState.brushDown()
        );
    }
    
    public void moveBrushUp() {
        addFrame(
            currentState.x(), 
            currentState.y(), 
            currentState.angle(), 
            currentState.color(), 
            currentState.stroke(),
            false
        );
    }
    
    public void moveBrushDown() {
        addFrame(
            currentState.x(), 
            currentState.y(), 
            currentState.angle(), 
            currentState.color(), 
            currentState.stroke(),
            true
        );
    }
    
    public void toggleBrush() {
        addFrame(
            currentState.x(), 
            currentState.y(), 
            currentState.angle(), 
            currentState.color(), 
            currentState.stroke(),
            !currentState.brushDown()
        );
    }
    
    public void setBrushPaint( Paint color ) {
        addFrame(
            currentState.x(), 
            currentState.y(), 
            currentState.angle(), 
            color, 
            currentState.stroke(),
            currentState.brushDown()
        );
    }
    
    public void increaseLineWidth() {
        addFrame( 
            currentState.x(), 
            currentState.y(), 
            currentState.angle(), 
            currentState.color(), 
            new BasicStroke( currentState.stroke().getLineWidth() + 1 ),
            currentState.brushDown()
        );
    }
    
    public void decreaseLineWidth() {
        float lineWidth = currentState.stroke().getLineWidth() - 1;
        if ( lineWidth > 0.0f ) {
            addFrame( 
                currentState.x(), 
                currentState.y(), 
                currentState.angle(), 
                currentState.color(), 
                new BasicStroke( lineWidth ),
                currentState.brushDown()
            );
        }
    }
    
    public void setLineWidth( double thikness ) {
        addFrame( 
            currentState.x(), 
            currentState.y(), 
            currentState.angle(), 
            currentState.color(), 
            new BasicStroke( (float) thikness ),
            currentState.brushDown()
        );
    }
    
    public void setStroke( BasicStroke stroke ) {
        addFrame( 
            currentState.x(), 
            currentState.y(), 
            currentState.angle(), 
            currentState.color(), 
            stroke,
            currentState.brushDown()
        );
    }
    
    public void undo() {
        
    }
    
    public void redo() {
        
    }
    
    public void save() {
        snapshots.push( 
            new TurtleStateSnapshot(
                new TurtleState(
                    currentState.x(),
                    currentState.y(),
                    currentState.angle(), 
                    currentState.color(),
                    currentState.stroke(),
                    currentState.brushDown()
                ),
                frames.size()
            )
        );
    }
    
    public void restore() {
        if ( !snapshots.isEmpty() ) {
            TurtleStateSnapshot ss = snapshots.pop();
            currentState = ss.state();
            while ( frames.size() > ss.frameCount() ) {
                frames.removeLast();
            }
        }
    }
    
    public void reset() {
        reset( 0, 0 );
    }
    
    public void reset( double x, double y ) {
        reset( x, y, -90, EngineFrame.BLACK, new BasicStroke( 1 ), true );
    }
    
    public void reset( double x, double y, double angle, Paint color, BasicStroke bs, boolean brushDown ) {
        frames = new ArrayList<>();
        snapshots = new ArrayDeque<>();
        addFrame( x, y, angle, color, bs, brushDown );
    }
    
    public void draw( EngineFrame engine ) {
        
        if ( frames.size() <= 1 ) {
            return;
        }
        
        draw( 1, frames.size() - 1, engine );
        
        /*BasicStroke bs = engine.getStroke();
        
        for ( int i = 0; i < frames.size() - 1; i++ ) {
            TurtleState f1 = frames.get( i );
            TurtleState f2 = frames.get( i + 1 );
            if ( f2.brushDown() ) {
                engine.setStroke( f2.stroke() );
                engine.drawLine( f1.x(), f1.y(), f2.x(), f2.y(), f2.color() );
            }
        }
        
        engine.setStroke( bs );*/
        
    }
    
    public void draw( int until, EngineFrame engine ) {
        
        if ( frames.size() <= 1 ) {
            return;
        }
        
        draw( 1, until, engine );
        
    }
    
    public void draw( int from, int until, EngineFrame engine  ) {
        
        if ( frames.isEmpty() ) {
            return;
        }
        
        int startIndex = from - 1;
        int endIndex = until - 1;
        
        if ( from < 1 ) {
            throw new IllegalArgumentException( "from must be greater than zero" );
        }
        
        if ( until < 1 ) {
            throw new IllegalArgumentException( "until must be greater than zero" );
        }
        
        if ( from > until ) {
            throw new IllegalArgumentException( "from must be lesser or equal than until" );
        }
        
        if ( startIndex >= frames.size() ) {
            return;
        }
        
        if ( endIndex >= frames.size() - 1 ) {
            endIndex = frames.size() - 2;
            if ( endIndex < startIndex ) {
                return;
            }
        }
        
        BasicStroke originalStroke = engine.getStroke();
        
        for ( int i = startIndex; i <= endIndex; i++ ) {
            TurtleState f1 = frames.get( i );
            TurtleState f2 = frames.get( i + 1 );
            if ( f2.brushDown() ) {
                engine.setStroke( f2.stroke() );
                engine.drawLine( f1.x(), f1.y(), f2.x(), f2.y(), f2.color() );
            }
        }
        
        engine.setStroke( originalStroke );
        
    }
    
    public TurtleState getCurrentState() {
        return currentState;
    }
    
    public int getFrameCount() {
        return frames.size() - 1;
    }
    
    private static record TurtleStateSnapshot( TurtleState state, int frameCount ) {
    }
    
    private static class TurtlePad extends EngineFrame {
        
        Turtle t;
        
        public TurtlePad() {
            super( 800, 450, "Turtle Pad", 60, true, false, false, false, false, false );
        }
        
        @Override
        public void create() {
            t = new Turtle( getScreenWidth() / 2, getScreenHeight() / 2 );
        }
        
        @Override
        public void update( double delta ) {
            
            double length = 10;
            double amount = 5;
            
            if ( isKeyPressed( KEY_UP ) ) {
                t.moveForward( length );
            } else if ( isKeyPressed( KEY_DOWN ) ) {
                t.moveBackward(length );
            } else if ( isKeyPressed( KEY_LEFT ) ) {
                t.moveLeft( length );
            } else if ( isKeyPressed( KEY_RIGHT ) ) {
                t.moveRight( length );
            } else if ( isKeyPressed( KEY_PAGE_UP ) ) {
                t.rotate( -amount );
            } else if ( isKeyPressed( KEY_PAGE_DOWN ) ) {
                t.rotate( amount );
            } else if ( isKeyPressed( KEY_HOME ) ) {
                t.increaseLineWidth();
            } else if ( isKeyPressed( KEY_END ) ) {
                t.decreaseLineWidth();
            } else if ( isKeyPressed( KEY_F1 ) ) {
                t.toggleBrush();
            } else if ( isKeyPressed( KEY_S ) ) {
                t.save();
            } else if ( isKeyPressed( KEY_R ) ) {
                t.restore();
            }
            
        }
        
        @Override
        public void draw() {
            clearBackground( WHITE );
            desenharTartaruga();
            drawText( String.format( "frames: " + t.getFrameCount() ), 10, 10, 20, BLACK );
        }
        
        private void desenharTartaruga() {
            
            /*if ( t.frames.size() > 10 ) {
                t.draw( 1, 3, this );
            }*/
            
            t.draw( this );
            
            double width = 6;
            double veWidth = 20;
            
            TurtleState s = t.currentState;
            fillRectangle( s.x() - width/2, s.y() - width/2, width, width, BLUE );
            
            drawLine( 
                s.x(), s.y(),
                s.x() + veWidth * Math.cos( s.angle() * MathUtils.DEG2RAD ),
                s.y() + veWidth * Math.sin( s.angle() * MathUtils.DEG2RAD ),
                DARKGREEN
            );
            
        }
        
    }
    
    public static void main( String[] args ) {
        new TurtlePad();
    }
    
}

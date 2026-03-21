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
 * Class that encapsulates a Turtle Graphics component.
 *
 * @author Prof. Dr. David Buzatto
 */
public class Turtle {
    
    private List<TurtleStep> steps;
    private Deque<StateSnapshot> stateStack;
    private TurtleStep currentStep;
    
    /**
     * Creates a new Turtle at the origin, with a black pen and an initial angle
     * of -90 degrees.
     */
    public Turtle() {
        reset();
    }
    
    /**
     * Creates a new Turtle with a black pen and an initial angle of -90 degrees
     * at a specified position.
     *
     * @param x X coordinate of the upper-left vertex.
     * @param y Y coordinate of the upper-left vertex.
     */
    public Turtle( double x, double y ) {
        reset( x, y );
    }
    
    /**
     * Creates a new Turtle with a black pen at a specified position with an
     * initial angle.
     *
     * @param x X coordinate of the upper-left vertex.
     * @param y Y coordinate of the upper-left vertex.
     * @param angle Initial angle in degrees (clockwise).
     */
    public Turtle( double x, double y, double angle ) {
        reset( x, y, angle );
    }
    
    /**
     * Creates a new Turtle with an initial angle of -90 degrees at a specified
     * position with an initial color.
     *
     * @param x X coordinate of the upper-left vertex.
     * @param y Y coordinate of the upper-left vertex.
     * @param paint Initial paint.
     */
    public Turtle( double x, double y, Paint paint ) {
        reset( x, y, paint );
    }
    
    /**
     * Creates a new Turtle.
     *
     * @param x X coordinate of the upper-left vertex.
     * @param y Y coordinate of the upper-left vertex.
     * @param angle Initial angle in degrees (clockwise).
     * @param paint Initial paint.
     */
    public Turtle( double x, double y, double angle, Paint paint ) {
        reset( x, y, angle, paint );
    }
    
    /**
     * Creates a new Turtle.
     *
     * @param x X coordinate of the upper-left vertex.
     * @param y Y coordinate of the upper-left vertex.
     * @param angle Initial angle in degrees (clockwise).
     * @param paint Initial paint.
     * @param stroke Initial pen stroke.
     * @param penDown Whether the pen is down.
     */
    public Turtle( double x, double y, double angle, Paint paint, BasicStroke stroke, boolean penDown ) {
        reset( x, y, angle, paint, stroke, penDown );
    }
    
    /**
     * Moves the Turtle.
     *
     * @param x Destination X coordinate.
     * @param y Destination Y coordinate.
     */
    public void moveTo( double x, double y ) {
        addStep(
            x, 
            y, 
            currentStep.angle(), 
            currentStep.paint(), 
            currentStep.stroke(),
            currentStep.penDown(),
            TurtleOperation.DRAW
        );
    }
    
    /**
     * Moves the Turtle forward.
     *
     * @param distance Movement distance.
     */
    public void forward( double distance ) {
        addStep(
            currentStep.x() + distance * Math.cos(currentStep.angle() * MathUtils.DEG2RAD ),
            currentStep.y() + distance * Math.sin(currentStep.angle() * MathUtils.DEG2RAD ),
            currentStep.angle(),
            currentStep.paint(),
            currentStep.stroke(),
            currentStep.penDown(),
            TurtleOperation.DRAW
        );
    }
    
    /**
     * Moves the Turtle backward.
     *
     * @param distance Movement distance.
     */
    public void backward( double distance ) {
        addStep(
            currentStep.x() - distance * Math.cos(currentStep.angle() * MathUtils.DEG2RAD ),
            currentStep.y() - distance * Math.sin(currentStep.angle() * MathUtils.DEG2RAD ),
            currentStep.angle(),
            currentStep.paint(),
            currentStep.stroke(),
            currentStep.penDown(),
            TurtleOperation.DRAW
        );
    }
    
    /**
     * Moves the Turtle to the left.
     *
     * @param distance Movement distance.
     */
    public void left( double distance ) {
        addStep(
            currentStep.x() + distance * Math.cos(( currentStep.angle() - 90 ) * MathUtils.DEG2RAD ),
            currentStep.y() + distance * Math.sin(( currentStep.angle() - 90 ) * MathUtils.DEG2RAD ),
            currentStep.angle(),
            currentStep.paint(),
            currentStep.stroke(),
            currentStep.penDown(),
            TurtleOperation.DRAW
        );
    }
    
    /**
     * Moves the Turtle to the right.
     *
     * @param distance Movement distance.
     */
    public void right( double distance ) {
        addStep(
            currentStep.x() + distance * Math.cos(( currentStep.angle() + 90 ) * MathUtils.DEG2RAD ),
            currentStep.y() + distance * Math.sin(( currentStep.angle() + 90 ) * MathUtils.DEG2RAD ),
            currentStep.angle(),
            currentStep.paint(),
            currentStep.stroke(),
            currentStep.penDown(),
            TurtleOperation.DRAW
        );
    }
    
    /**
     * Rotates the Turtle.
     *
     * @param amount Amount in degrees (clockwise).
     */
    public void rotate( double amount ) {
        addStep(
            currentStep.x(), 
            currentStep.y(), 
            currentStep.angle() + amount, 
            currentStep.paint(), 
            currentStep.stroke(),
            currentStep.penDown(),
            TurtleOperation.CONFIG
        );
    }
    
    /**
     * Rotates the Turtle to the left.
     *
     * @param amount Amount in degrees (counter-clockwise).
     */
    public void rotateLeft( double amount ) {
        rotate( -amount );
    }
    
    /**
     * Rotates the Turtle to the right.
     *
     * @param amount Amount in degrees (clockwise).
     */
    public void rotateRight( double amount ) {
        rotate( amount );
    }
    
    /**
     * Sets the rotation of the Turtle.
     *
     * @param angle Angle in degrees (clockwise).
     */
    public void setRotation( double angle ) {
        addStep(
            currentStep.x(), 
            currentStep.y(), 
            angle, 
            currentStep.paint(), 
            currentStep.stroke(),
            currentStep.penDown(),
            TurtleOperation.CONFIG
        );
    }
    
    /**
     * Lifts the pen.
     */
    public void penUp() {
        addStep(
            currentStep.x(), 
            currentStep.y(), 
            currentStep.angle(), 
            currentStep.paint(), 
            currentStep.stroke(),
            false,
            TurtleOperation.CONFIG
        );
    }
    
    /**
     * Lowers the pen.
     */
    public void penDown() {
        addStep(
            currentStep.x(), 
            currentStep.y(), 
            currentStep.angle(), 
            currentStep.paint(), 
            currentStep.stroke(),
            true,
            TurtleOperation.CONFIG
        );
    }
    
    /**
     * Toggles the pen state (up or down).
     */
    public void togglePen() {
        addStep(
            currentStep.x(), 
            currentStep.y(), 
            currentStep.angle(), 
            currentStep.paint(), 
            currentStep.stroke(),
            !currentStep.penDown(),
            TurtleOperation.CONFIG
        );
    }
    
    /**
     * Sets the pen color.
     *
     * @param paint Any valid paint object that will be used in drawing operations.
     */
    public void setPenColor( Paint paint ) {
        addStep(
            currentStep.x(), 
            currentStep.y(), 
            currentStep.angle(), 
            paint, 
            currentStep.stroke(),
            currentStep.penDown(),
            TurtleOperation.CONFIG
        );
    }
    
    /**
     * Increases the pen width by one pixel.
     */
    public void increasePenWidth() {
        addStep(
            currentStep.x(), 
            currentStep.y(), 
            currentStep.angle(), 
            currentStep.paint(), 
            new BasicStroke( currentStep.stroke().getLineWidth() + 1 ),
            currentStep.penDown(),
            TurtleOperation.CONFIG
        );
    }
    
    /**
     * Decreases the pen width by one pixel.
     */
    public void decreasePenWidth() {
        float lineWidth = currentStep.stroke().getLineWidth() - 1;
        if ( lineWidth > 0.0f ) {
            addStep(
                currentStep.x(), 
                currentStep.y(), 
                currentStep.angle(), 
                currentStep.paint(), 
                new BasicStroke( lineWidth ),
                currentStep.penDown(),
                TurtleOperation.CONFIG
            );
        }
    }
    
    /**
     * Sets the pen width.
     *
     * @param width The new pen width.
     */
    public void setPenWidth( double width ) {
        if ( width < 0 ) {
            width = 0;
        }
        addStep(
            currentStep.x(), 
            currentStep.y(), 
            currentStep.angle(), 
            currentStep.paint(), 
            new BasicStroke( (float) width ),
            currentStep.penDown(),
            TurtleOperation.CONFIG
        );
    }
    
    /**
     * Sets the pen stroke.
     *
     * @param stroke New pen stroke.
     */
    public void setPenStroke( BasicStroke stroke ) {
        addStep(
            currentStep.x(), 
            currentStep.y(), 
            currentStep.angle(), 
            currentStep.paint(), 
            stroke,
            currentStep.penDown(),
            TurtleOperation.CONFIG
        );
    }
    
    /**
     * Saves the current state of the Turtle.
     */
    public void saveState() {
        stateStack.push(new StateSnapshot(
                new TurtleStep(
                    currentStep.x(),
                    currentStep.y(),
                    currentStep.angle(), 
                    currentStep.paint(),
                    currentStep.stroke(),
                    currentStep.penDown(),
                    currentStep.operation()
                ),
                steps.size()
            )
        );
    }
    
    /**
     * Restores the last saved state of the Turtle (if any), without removing
     * the steps generated up to the moment of saving.
     */
    public void restoreStateKeepPath() {
        if ( !stateStack.isEmpty() ) {
            currentStep = stateStack.pop().step();
        }
    }
    
    /**
     * Restores the last saved state of the Turtle (if any), removing
     * the steps generated up to the moment of saving.
     */
    public void restoreState() {
        if ( !stateStack.isEmpty() ) {
            StateSnapshot snapshot = stateStack.pop();
            currentStep = snapshot.step();
            while ( steps.size() > snapshot.stepCount() ) {
                steps.removeLast();
            }
        }
    }
    
    /**
     * Resets the Turtle to the origin, with a black pen and an initial angle
     * of -90 degrees.
     */
    public void reset() {
        reset( 0, 0 );
    }
    
    /**
     * Resets the Turtle with a black pen and an initial angle of -90 degrees
     * at a specified position.
     *
     * @param x X coordinate of the upper-left vertex.
     * @param y Y coordinate of the upper-left vertex.
     */
    public void reset( double x, double y ) {
        reset( x, y, -90, EngineFrame.BLACK, new BasicStroke( 1 ), true );
    }
    
    /**
     * Resets the Turtle with a black pen at a specified position with an
     * initial angle.
     *
     * @param x X coordinate of the upper-left vertex.
     * @param y Y coordinate of the upper-left vertex.
     * @param angle Initial angle in degrees (clockwise).
     */
    public void reset( double x, double y, double angle ) {
        reset( x, y, angle, EngineFrame.BLACK, new BasicStroke( 1 ), true );
    }
    
    /**
     * Resets the Turtle with an initial angle of -90 degrees at a specified
     * position with an initial color.
     *
     * @param x X coordinate of the upper-left vertex.
     * @param y Y coordinate of the upper-left vertex.
     * @param paint Initial paint.
     */
    public void reset( double x, double y, Paint paint ) {
        reset( x, y, -90, paint, new BasicStroke( 1 ), true );
    }
    
    /**
     * Resets the Turtle.
     *
     * @param x X coordinate of the upper-left vertex.
     * @param y Y coordinate of the upper-left vertex.
     * @param angle Initial angle in degrees (clockwise).
     * @param paint Initial paint.
     */
    public void reset( double x, double y, double angle, Paint paint ) {
        reset( x, y, angle, paint, new BasicStroke( 1 ), true );
    }
    
    /**
     * Resets the Turtle.
     *
     * @param x X coordinate of the upper-left vertex.
     * @param y Y coordinate of the upper-left vertex.
     * @param angle Initial angle in degrees (clockwise).
     * @param paint Initial paint.
     * @param stroke Initial pen stroke.
     * @param penDown Whether the pen is down.
     */
    public void reset( double x, double y, double angle, Paint paint, BasicStroke stroke, boolean penDown ) {
        steps = new ArrayList<>();
        stateStack = new ArrayDeque<>();
        addStep( x, y, angle, paint, stroke, penDown, TurtleOperation.CONFIG );
    }
    
    /**
     * Draws all Turtle steps.
     *
     * @param engine EngineFrame being used.
     */
    public void draw( EngineFrame engine ) {
        
        if ( steps.size() <= 1 ) {
            return;
        }
        
        draw( 1, steps.size() - 1, engine );
        
    }
    
    /**
     * Draws from the first step up to a limit.
     *
     * @param until Final step.
     * @param engine EngineFrame being used.
     */
    public void draw( int until, EngineFrame engine ) {
        
        if ( steps.size() <= 1 ) {
            return;
        }
        
        draw( 1, until, engine );
        
    }
    
    /**
     * Draws a range of steps.
     *
     * @param from Initial step.
     * @param until Final step.
     * @param engine EngineFrame being used.
     */
    public void draw( int from, int until, EngineFrame engine  ) {
        
        if ( steps.isEmpty() ) {
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
        
        if ( startIndex >= steps.size() ) {
            return;
        }
        
        if ( endIndex >= steps.size() - 1 ) {
            endIndex = steps.size() - 2;
            if ( endIndex < startIndex ) {
                return;
            }
        }
        
        BasicStroke originalStroke = engine.getStroke();
        
        for ( int i = startIndex; i <= endIndex; i++ ) {
            TurtleStep f1 = steps.get( i );
            TurtleStep f2 = steps.get( i + 1 );
            if ( f2.penDown() && f2.operation() == TurtleOperation.DRAW ) {
                engine.setStroke( f2.stroke() );
                engine.drawLine( f1.x(), f1.y(), f2.x(), f2.y(), f2.paint() );
            }
        }
        
        engine.setStroke( originalStroke );
        
    }
    
    /**
     * Gets the current state of the Turtle.
     *
     * @return The current state of the Turtle.
     */
    public TurtleStep getCurrentState() {
        return currentStep;
    }
    
    /**
     * Gets the number of steps of the Turtle.
     *
     * @return The number of steps of the Turtle.
     */
    public int getStepCount() {
        return steps.size() - 1;
    }
    
    /**
     * Adds a new step.
     */
    private void addStep( double x, double y, double angle, Paint paint, BasicStroke bs, boolean penDown, TurtleOperation operation ) {
        currentStep = new TurtleStep( x, y, angle, paint, bs, penDown, operation );
        steps.add(currentStep );
    }
    
    /**
     * Record for storing the saved state of the Turtle.
     */
    private static record StateSnapshot( TurtleStep step, int stepCount ) {
    }
    
}

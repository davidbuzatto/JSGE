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
package br.com.davidbuzatto.jsge.collision.aabb;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.core.utils.ColorUtils;
import java.awt.Color;
import java.util.List;

/**
 * A quadtree for AABBs.
 *
 * @author Prof. Dr. David Buzatto
 */
public class AABBQuadtree {
    
    private static final Color OUTLINE_COLOR = EngineFrame.BLACK;
    private static final Color COMMON_COLOR = ColorUtils.fade( EngineFrame.GRAY, 0.3 );
    private static final Color AABB_COLOR = ColorUtils.fade( EngineFrame.GOLD, 1 );
    private static final Color INACTIVE_AABB_COLOR = ColorUtils.fade( EngineFrame.RED, 1 );
    private static final Color NEARBY_AABB_COLOR = ColorUtils.fade( EngineFrame.LIME, 1 );
        
    /**
     * Root.
     */
    private AABBQuadtreeNode root;

    /**
     * Maximum depth.
     */
    private int maxDepth;

    /**
     * List of AABBs in the tree.
     */
    private List<AABB> aabbs;

    /**
     * Enumeration for the quadtree node shapes.
     */
    public static enum Shape {
        SQUARE,
        RECTANGULAR;
    }

    /**
     * Constructs a new quadtree from a set of AABBs.
     *
     * @param aabbs The AABBs of the tree.
     * @param width Width of the tree (outermost rectangle).
     * @param height Height of the tree (outermost rectangle).
     * @param maxDepth Maximum depth of the tree.
     * @param shape Shape of the tree nodes.
     */
    public AABBQuadtree( List<AABB> aabbs, int width, int height, int maxDepth, Shape shape ) {
        this.aabbs = aabbs;
        this.maxDepth = maxDepth;
        reshape( width, height, shape );
    }
    
    /**
     * Constructs a new quadtree from a set of AABBs with square nodes.
     *
     * @param aabbs The AABBs of the tree.
     * @param width Width of the tree (outermost rectangle).
     * @param height Height of the tree (outermost rectangle).
     * @param maxDepth Maximum depth of the tree.
     */
    public AABBQuadtree( List<AABB> aabbs, int width, int height, int maxDepth ) {
        this( aabbs, width, height, maxDepth, Shape.SQUARE );
    }

    /**
     * Builds the tree structure recursively.
     *
     * @param x1 X coordinate of the upper-left vertex of the current node.
     * @param y1 Y coordinate of the upper-left vertex of the current node.
     * @param x2 X coordinate of the lower-right vertex of the current node.
     * @param y2 Y coordinate of the lower-right vertex of the current node.
     * @param depth The depth of the current node.
     * @return
     */
    public AABBQuadtreeNode buildQuadtree( int x1, int y1, int x2, int y2, int depth ) {
        
        AABBQuadtreeNode node = null;
        
        if ( depth <= maxDepth ) {
            node = new AABBQuadtreeNode( x1, y1, x2, y2, depth );
            node.nw = buildQuadtree( x1, y1, node.xCenter, node.yCenter, depth + 1 );
            node.ne = buildQuadtree( node.xCenter, y1, x2, node.yCenter, depth + 1 );
            node.sw = buildQuadtree( x1, node.yCenter, node.xCenter, y2, depth + 1 );
            node.se = buildQuadtree( node.xCenter, node.yCenter, x2, y2, depth + 1 );
        }
        
        return node;
        
    }

    /**
     * Inserts a new node into the tree.
     *
     * @param node The node to be inserted.
     */
    public void insert( AABBQuadtreeNode node ) {

        if ( node.depth < maxDepth ) {
            if ( node.aabbs.size() > 1 ) {
                for ( AABB aabb : node.aabbs ) {
                    if ( aabb.y1 < node.yCenter ) {
                        if ( aabb.x1 < node.xCenter ) {
                            if ( node.nw != null ) {
                                node.nw.aabbs.add( aabb );
                            }
                        }
                        if ( aabb.x2 > node.xCenter ) {
                            if ( node.ne != null ) {
                                node.ne.aabbs.add( aabb );
                            }
                        }
                    }
                    if ( aabb.y2 > node.yCenter ) {
                        if ( aabb.x1 < node.xCenter ) {
                            if ( node.sw != null ) {
                                node.sw.aabbs.add( aabb );
                            }
                        }
                        if ( aabb.x2 > node.xCenter ) {
                            if ( node.se != null ) {
                                node.se.aabbs.add( aabb );
                            }
                        }
                    }
                }
                
                insert( node.nw );
                insert( node.ne );
                insert( node.sw );
                insert( node.se );
                
            }
            
        } else {
            for ( AABB aabb : node.aabbs ) {
                setNearby( node.aabbs, aabb );
            }
        }
    }

    /**
     * Reshapes the tree.
     *
     * @param width New width.
     * @param height New height.
     * @param shape The shape of the tree.
     */
    public void reshape( int width, int height, AABBQuadtree.Shape shape ) {
        
        if ( shape != AABBQuadtree.Shape.SQUARE && shape != AABBQuadtree.Shape.RECTANGULAR ) {
            throw new IllegalArgumentException( shape + " is not recognized as a valid argument for Quadtree shape" );
        } else {
            
            int newWidth = width;
            int newHeight = height;

            if ( shape == AABBQuadtree.Shape.SQUARE ) {
                newWidth = newHeight = width > height ? width - 1 : height - 1;
            }
            
            root = buildQuadtree( 0, 0, newWidth, newHeight, 0 );
            
        }
        
    }
    
    /**
     * Updates the tree.
     * Only active AABBs are inserted.
     */
    public void update() {

        resetNodes( root );

        // only active AABBs enter the root,
        // which is then propagated downward
        for ( AABB aabb : aabbs ) {
            if ( aabb.active ) {
                root.aabbs.add( aabb );
            }
        }

        // all AABBs (previous implementation)
        //root.aabbs.addAll( aabbs );
        
        for ( AABB aabb : aabbs ) {
            aabb.nearby = null;
        }
        
        insert( root );
        
    }

    /**
     * Gets the AABBs.
     *
     * @return A list of AABBs.
     */
    public List<AABB> getAABBs() {
        return aabbs;
    }

    /**
     * Gets the root of the tree.
     *
     * @return The root of the tree.
     */
    public AABBQuadtreeNode getRoot() {
        return root;
    }
    
    /**
     * Sets the AABBs.
     *
     * @param aabbs New AABBs.
     */
    public void setAABBs( List<AABB> aabbs ) {
        this.aabbs = aabbs;
    }

    /**
     * Gets the maximum depth of the quadtree.
     *
     * @return The maximum depth.
     */
    public int getMaxDepth() {
        return maxDepth;
    }
    
    /**
     * Sets the maximum depth.
     *
     * @param maxDepth The new maximum depth.
     */
    public void setMaxDepth( int maxDepth ) {
        if ( this.maxDepth != maxDepth ) {
            this.maxDepth = ( maxDepth < 1 || maxDepth > 10 ) ? this.maxDepth : maxDepth;
            root = buildQuadtree( root.x1, root.y1, root.x2, root.y2, 0 );
            root.aabbs.addAll( aabbs );
        }
    }

    /**
     * Resets nodes recursively.
     *
     * @param node The node to be reset.
     */
    private void resetNodes( AABBQuadtreeNode node ) {
        if ( node != null ) {
            if ( !node.aabbs.isEmpty() ) {
                node.aabbs.clear();
            }
            resetNodes( node.nw );
            resetNodes( node.ne );
            resetNodes( node.sw );
            resetNodes( node.se );
        }
    }
    
    /**
     * Sets the nearby AABBs.
     *
     * The assignment is performed only when the target is a
     * dynamic AABB.
     *
     * @param aabbs The list of AABBs.
     * @param target The target, i.e., the nearby AABB.
     */
    private void setNearby( List<AABB> aabbs, AABB target ) {
        if ( target.type == AABB.Type.DYNAMIC ) {
            for ( AABB aabb : aabbs ) {
                aabb.nearby = target;
            }
        }
    }
    
    /**
     * Draws the quadtree.
     *
     * @param engine The engine.
     * @param x X coordinate of the drawing position.
     * @param y Y coordinate of the drawing position.
     */
    public void draw( EngineFrame engine, double x, double y ) {
        draw( engine, x, y, 1.0 );
    }

    /**
     * Draws the quadtree.
     *
     * @param engine The engine.
     * @param x X coordinate of the drawing position.
     * @param y Y coordinate of the drawing position.
     * @param scale The drawing scale.
     */
    public void draw( EngineFrame engine, double x, double y, double scale ) {
        drawAABBs( engine, x, y, scale );
        drawQuadnode( engine, root, x, y, scale );
    }
    
    private void drawAABBs( EngineFrame engine, double x, double y, double scale ) {
        for ( AABB aabb : aabbs ) {
            engine.fillRectangle( 
                x + aabb.x1 * scale, 
                y + aabb.y1 * scale, 
                aabb.width * scale, 
                aabb.height * scale, 
                aabb.nearby == null ? 
                    aabb.active ? 
                        AABB_COLOR
                        : 
                        INACTIVE_AABB_COLOR
                    : 
                    NEARBY_AABB_COLOR
            );
        }
    }
    
    private void drawQuadnode( EngineFrame engine, AABBQuadtreeNode node, double x, double y, double scale ) {
        
        if ( node.depth < getMaxDepth() ) {
            
            int size = node.aabbs.size();
            
            if ( size > 1 ) {
                if ( node.depth == getMaxDepth() - 1 ) {
                    engine.fillRectangle( 
                        x + node.x1 * scale, 
                        y + node.y1 * scale, 
                        ( node.x2 - node.x1 ) * scale, 
                        ( node.y2 - node.y1 ) * scale, 
                        COMMON_COLOR
                    );
                }
                engine.drawRectangle( 
                    x + node.x1 * scale, 
                    y + node.y1 * scale, 
                    ( node.x2 - node.x1 ) * scale, 
                    ( node.y2 - node.y1 ) * scale, 
                    OUTLINE_COLOR
                );
            }
            
            drawQuadnode( engine, node.nw, x, y, scale );
            drawQuadnode( engine, node.ne, x, y, scale );
            drawQuadnode( engine, node.sw, x, y, scale );
            drawQuadnode( engine, node.se, x, y, scale );
            
        }
        
    }

}

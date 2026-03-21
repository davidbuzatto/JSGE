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

import java.util.ArrayList;
import java.util.List;

/**
 * A node of the AABBQuadtree, represented by a quadrant with start,
 * end, and center coordinates.
 *
 * @author Prof. Dr. David Buzatto
 */
public class AABBQuadtreeNode {

    /**
     * X coordinate of the upper-left vertex.
     */
    public int x1;

    /**
     * Y coordinate of the upper-left vertex.
     */
    public int y1;

    /**
     * X coordinate of the lower-right vertex.
     */
    public int x2;

    /**
     * Y coordinate of the lower-right vertex.
     */
    public int y2;

    /**
     * X coordinate of the quadrant center.
     */
    public int xCenter;

    /**
     * Y coordinate of the quadrant center.
     */
    public int yCenter;

    /**
     * The depth in the tree.
     */
    public int depth;

    /**
     * Northwest child node (upper-left).
     */
    public AABBQuadtreeNode nw;

    /**
     * Northeast child node (upper-right).
     */
    public AABBQuadtreeNode ne;

    /**
     * Southwest child node (lower-left).
     */
    public AABBQuadtreeNode sw;

    /**
     * Southeast child node (lower-right).
     */
    public AABBQuadtreeNode se;

    /**
     * A list of AABBs that intersect the boundaries of this node.
     */
    public List<AABB> aabbs;

    /**
     * Constructs a new AABBQuadtree node.
     *
     * @param x1 X coordinate of the upper-left vertex.
     * @param y1 Y coordinate of the upper-left vertex.
     * @param x2 X coordinate of the lower-right vertex.
     * @param y2 Y coordinate of the lower-right vertex.
     * @param depth Depth of the node in the tree.
     */
    public AABBQuadtreeNode( int x1, int y1, int x2, int y2, int depth ) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.xCenter = ( x1 + x2 ) / 2;
        this.yCenter = ( y1 + y2 ) / 2;
        this.depth = depth;
        this.aabbs = new ArrayList<>();
    }

}

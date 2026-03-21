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
package br.com.davidbuzatto.jsge.core;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import java.awt.Paint;

/**
 * Interface for drawable elements.
 *
 * @author Prof. Dr. David Buzatto
 */
public interface Drawable {

    /**
     * Draws the current element using the engine.
     *
     * @param engine The EngineFrame used.
     * @param paint Paint for drawing.
     */
    void draw( EngineFrame engine, Paint paint );

    /**
     * Fills/paints the current element using the engine.
     *
     * @param engine The EngineFrame used.
     * @param paint Paint for drawing.
     */
    void fill( EngineFrame engine, Paint paint );

}

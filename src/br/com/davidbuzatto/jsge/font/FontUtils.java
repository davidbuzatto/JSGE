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
package br.com.davidbuzatto.jsge.font;

import br.com.davidbuzatto.jsge.core.utils.CoreUtils;
import br.com.davidbuzatto.jsge.core.utils.TraceLogUtils;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Interface with static utility methods for font handling.
 *
 * @author Prof. Dr. David Buzatto
 */
public interface FontUtils {
    
    /**
     * Default font for the engine.
     */
    public static final Font DEFAULT_FONT = new Font( Font.MONOSPACED, Font.BOLD, 10 );

    /**
     * Default font for drawing the engine FPS.
     */
    public static final Font DEFAULT_FPS_FONT = new Font( Font.MONOSPACED, Font.BOLD, 20 );
    
    /**
     * Loads a new font and registers it in the GraphicsEnvironment.
     *
     * @param filePath Path to the font file.
     * @return The loaded font.
     */
    public static Font loadFont( String filePath ) {
        
        try {
            
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            File fontFile = new File( filePath );
            
            if ( fontFile.exists() ) {
                Font font = Font.createFont( getFontTypeByExtension( fontFile ), fontFile );
                ge.registerFont( font );
                return font;
            } else {
                throw new FileNotFoundException( filePath + " font file does not exists!" );
            }
            
        } catch ( FontFormatException | IOException exc ) {
            TraceLogUtils.traceLogError( CoreUtils.stackTraceToString( exc ) );
        }
        
        return DEFAULT_FONT;
        
    }
    
    /**
     * Loads a new font and registers it in the GraphicsEnvironment.
     *
     * @param fontFile The font file.
     * @return The loaded font.
     */
    public static Font loadFont( File fontFile ) {
        
        try {
            
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            
            if ( fontFile.exists() ) {
                Font font = Font.createFont( getFontTypeByExtension( fontFile ), fontFile );
                ge.registerFont( font );
                return font;
            } else {
                throw new FileNotFoundException( fontFile.getAbsolutePath() + " font file does not exists!" );
            }
            
        } catch ( FontFormatException | IOException exc ) {
            TraceLogUtils.traceLogError( CoreUtils.stackTraceToString( exc ) );
        }
        
        return DEFAULT_FONT;
        
    }
    
    /**
     * Loads a new font and registers it in the GraphicsEnvironment.
     *
     * @param inputStream InputStream for the font.
     * @param fontType The font type (Font.TRUETYPE_FONT or Font.TYPE1_FONT).
     * @return The loaded font.
     */
    public static Font loadFont( InputStream inputStream, int fontType ) {
        
        try {
            
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Font font = Font.createFont( fontType, inputStream );
            ge.registerFont( font );
            return font;
            
        } catch ( FontFormatException | IOException exc ) {
            TraceLogUtils.traceLogError( CoreUtils.stackTraceToString( exc ) );
        }
        
        return DEFAULT_FONT;
        
    }
    
    /**
     * Gets the font type by the file extension.
     * Extensions ttf and otf are treated as Font.TRUETYPE_FONT; any other
     * extension is treated as Font.TYPE1_FONT.
     *
     * @param file The file.
     * @return The font type based on the file extension.
     */
    private static int getFontTypeByExtension( File file ) {
        
        String fileName = file.getName();
        
        if ( fileName.endsWith( "ttf" ) || fileName.endsWith( "otf" ) ) {
            return Font.TRUETYPE_FONT;
        }
        
        return Font.TYPE1_FONT;
        
    }
    
}

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
package br.com.davidbuzatto.jsge.image;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.core.utils.ColorUtils;
import br.com.davidbuzatto.jsge.core.utils.CoreUtils;
import br.com.davidbuzatto.jsge.core.utils.TraceLogUtils;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * Interface with static utility methods for image handling.
 *
 * @author Prof. Dr. David Buzatto
 */
public interface ImageUtils {
    
    /**
     * Creates a resized copy of the original image.
     *
     * @param image An image.
     * @param newImageWidth The width of the new image.
     * @param newImageHeight The height of the new image.
     * @return A new resized image.
     */
    public static Image imageResize( Image image, int newImageWidth, int newImageHeight ) {
        
        Image newImage = new Image( newImageWidth, newImageHeight );
        
        Graphics2D g2d = newImage.createGraphics();
        g2d.drawImage( image.buffImage, 0, 0, newImageWidth, newImageHeight, 0, 0, image.getWidth(), image.getHeight(), null );
        g2d.dispose();
        
        return newImage;
        
    }
    
    /**
     * Creates a resized copy of the original image.
     *
     * @param image An image.
     * @param newImageWidth The width of the new image. The height will be calculated
     * proportionally relative to the new width.
     * @return A new resized image.
     */
    public static Image imageResize( Image image, int newImageWidth ) {
        
        double percentage = ( (double) newImageWidth ) / image.getWidth();
        int newImageHeight = (int) ( image.getHeight() * percentage );
        Image newImage = new Image( newImageWidth, newImageHeight );
        
        Graphics2D g2d = newImage.createGraphics();
        g2d.drawImage( image.buffImage, 0, 0, newImageWidth, newImageHeight, 0, 0, image.getWidth(), image.getHeight(), null );
        g2d.dispose();
        
        return newImage;
        
    }
    
    /**
     * Creates a resized copy of the original image.
     *
     * @param image An image.
     * @param percentage The resize percentage (0 to 1).
     * @return A new resized image.
     */
    public static Image imageResize( Image image, double percentage ) {
        
        int newImageWidth = (int) ( image.getWidth() * percentage );
        int newImageHeight = (int) ( image.getHeight() * percentage );
        Image newImage = new Image( newImageWidth, newImageHeight );
        
        Graphics2D g2d = newImage.createGraphics();
        g2d.drawImage( image.buffImage, 0, 0, newImageWidth, newImageHeight, 0, 0, image.getWidth(), image.getHeight(), null );
        g2d.dispose();
        
        return newImage;
        
    }
    
    /**
     * Creates a vertically flipped image.
     *
     * @param image An image.
     * @return A new vertically flipped image.
     */
    public static Image imageFlipVertical( Image image ) {
        
        Image newImage = new Image( image.getWidth(), image.getHeight() );
        
        for ( int i = 0; i < image.getWidth(); i++ ) {
            for ( int j = 0; j < image.getHeight(); j++ ) {
                newImage.setRGB( i, j, image.getRGB( i, image.getHeight() - j - 1 ) );
            }
        }
        
        return newImage;
        
    }
    
    /**
     * Creates a horizontally flipped image.
     *
     * @param image An image.
     * @return A new horizontally flipped image.
     */
    public static Image imageFlipHorizontal( Image image ) {
        
        Image newImage = new Image( image.getWidth(), image.getHeight() );
        
        for ( int i = 0; i < image.getHeight(); i++ ) {
            for ( int j = 0; j < image.getWidth(); j++ ) {
                newImage.setRGB( j, i, image.getRGB( image.getWidth() - j - 1, i ) );
            }
        }
        
        return newImage;
        
    }
    
    /**
     * Creates a rotated image.
     *
     * @param image An image.
     * @param rotation Rotation in degrees (clockwise).
     * @return A new rotated image.
     */
    public static Image imageRotate( Image image, double rotation ) {
        
        rotation -= ( (int) ( rotation / 360.0 ) ) * 360.0;
        if ( rotation < 0.0 ) {
            rotation = 360.0 + rotation;
        }
        
        while ( rotation >= 90.0 ) {
            rotation -= 90.0;
            image = imageRotateCW( image );
        }
        
        double newWidth  =  image.getWidth() * Math.cos( Math.toRadians( rotation ) ) +
                           image.getHeight() * Math.sin( Math.toRadians( rotation ) );
        
        double newHeight =  image.getWidth() * Math.sin( Math.toRadians( rotation ) ) +
                           image.getHeight() * Math.cos( Math.toRadians( rotation ) );
        
        Image newImage = new Image( (int) newWidth, (int) newHeight );
        
        Graphics2D g2d = newImage.createGraphics();
        g2d.setColor( Color.BLACK );
        g2d.translate( newImage.getWidth() / 2, newImage.getHeight() / 2 );
        g2d.rotate( Math.toRadians( rotation ) );
        g2d.drawImage( image.buffImage, -image.getWidth() / 2, -image.getHeight() / 2, null );
        g2d.dispose();
        
        return newImage;
        
    }
    
    /**
     * Creates an image rotated 90 degrees clockwise from the original.
     *
     * @param image An image.
     * @return A new rotated image.
     */
    public static Image imageRotateCW( Image image ) {
        
        Image newImage = new Image( image.getHeight(), image.getWidth() );
        
        Graphics2D g2d = newImage.createGraphics();
        g2d.translate( newImage.getWidth(), 0 );
        g2d.rotate( Math.toRadians( 90 ) );
        g2d.drawImage(image.buffImage, 0, 0, null );
        g2d.dispose();
        
        return newImage;
        
    }
    
    /**
     * Creates an image rotated 90 degrees counter-clockwise from the original.
     *
     * @param image An image.
     * @return A new rotated image.
     */
    public static Image imageRotateCCW( Image image ) {
        
        Image newImage = new Image( image.getHeight(), image.getWidth() );
        
        Graphics2D g2d = newImage.createGraphics();
        g2d.translate( 0, newImage.getHeight() );
        g2d.rotate( Math.toRadians( -90 ) );
        g2d.drawImage(image.buffImage, 0, 0, null );
        g2d.dispose();
        
        return newImage;
        
    }
    
    /**
     * Creates an image with an altered tint.
     *
     * @param image An image.
     * @param color The color that will be used to tint the image.
     * @return A new tinted image.
     */
    public static Image imageColorTint( Image image, Color color ) {
        
        Image newImage = new Image( image.getWidth(), image.getHeight() );
        
        for ( int i = 0; i < image.getHeight(); i++ ) {
            for ( int j = 0; j < image.getWidth(); j++ ) {
                
                Color c = ColorUtils.colorTint( new Color( image.getRGB( j, i ) ), color );
                int pixel = image.getRGB( j, i );
                int alpha = (pixel >> 24) & 0xff;
                c = new Color( c.getRed(), c.getGreen(), c.getBlue(), alpha );
                
                newImage.setRGB( j, i, c.getRGB() );
                
            }
        }
        
        return newImage;
        
    }
    
    /**
     * Creates an image with inverted colors.
     *
     * @param image An image.
     * @return A new image with inverted colors.
     */
    public static Image imageColorInvert( Image image ) {
        
        Image newImage = new Image( image.getWidth(), image.getHeight() );
        
        for ( int i = 0; i < image.getHeight(); i++ ) {
            for ( int j = 0; j < image.getWidth(); j++ ) {
                int pixel = image.getRGB( j, i );
                int alpha = (pixel >> 24) & 0xff;
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel >> 0) & 0xff;
                newImage.setRGB( j, i, new Color( 255 - red, 255 - green, 255 - blue, alpha ).getRGB() );
            }
        }
        
        return newImage;
        
    }
    
    /**
     * Creates a grayscale image.
     *
     * @param image An image.
     * @return A new grayscale image.
     */
    public static Image imageColorGrayscale( Image image ) {
        
        Image newImage = new Image( image.getWidth(), image.getHeight() );
        
        for ( int i = 0; i < image.getHeight(); i++ ) {
            for ( int j = 0; j < image.getWidth(); j++ ) {
                int pixel = image.getRGB( j, i );
                int alpha = (pixel >> 24) & 0xff;
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel >> 0) & 0xff;
                int gray = ( red + green + blue ) / 3;
                newImage.setRGB( j, i, new Color( gray, gray, gray, alpha ).getRGB() );
            }
        }
        
        return newImage;
        
    }
    
    /**
     * Creates an image with adjusted contrast. The contrast factor ranges from
     * -1.0 to 1.0.
     *
     * @param image An image.
     * @param contrast The contrast factor from -1.0 to 1.0.
     * @return A new image with adjusted contrast.
     */
    public static Image imageColorContrast( Image image, double contrast ) {
        
        Image newImage = new Image( image.getWidth(), image.getHeight() );
        
        for ( int i = 0; i < image.getHeight(); i++ ) {
            for ( int j = 0; j < image.getWidth(); j++ ) {
                
                Color c = ColorUtils.colorContrast( new Color( image.getRGB( j, i ) ), contrast );
                int pixel = image.getRGB( j, i );
                int alpha = (pixel >> 24) & 0xff;
                c = new Color( c.getRed(), c.getGreen(), c.getBlue(), alpha );
                
                newImage.setRGB( j, i, c.getRGB() );
                
            }
        }
        
        return newImage;
    }
    
    /**
     * Creates an image with adjusted brightness. The brightness factor ranges from
     * -1.0 to 1.0.
     *
     * @param image An image.
     * @param brightness The brightness factor from -1.0 to 1.0.
     * @return A new image with adjusted brightness.
     */
    public static Image imageColorBrightness( Image image, double brightness ) {
        
        Image newImage = new Image( image.getWidth(), image.getHeight() );
        
        for ( int i = 0; i < image.getHeight(); i++ ) {
            for ( int j = 0; j < image.getWidth(); j++ ) {
                
                Color c = ColorUtils.colorBrightness( new Color( image.getRGB( j, i ) ), brightness );
                int pixel = image.getRGB( j, i );
                int alpha = (pixel >> 24) & 0xff;
                c = new Color( c.getRed(), c.getGreen(), c.getBlue(), alpha );
                
                newImage.setRGB( j, i, c.getRGB() );
                
            }
        }
        
        return newImage;
        
    }
    
    /**
     * Creates a new image replacing one color with another.
     *
     * @param image An image.
     * @param color The color to be replaced.
     * @param replace The color that will replace the other color.
     * @return A new image with the colors replaced.
     */
    public static Image imageColorReplace( Image image, Color color, Color replace ) {
        
        Image newImage = new Image( image.getWidth(), image.getHeight() );
        
        for ( int i = 0; i < image.getHeight(); i++ ) {
            for ( int j = 0; j < image.getWidth(); j++ ) {
                
                int pixel = image.getRGB( j, i );
                int alpha = (pixel >> 24) & 0xff;
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel >> 0) & 0xff;
                Color c = new Color( red, green, blue, alpha );
                
                if ( pixel == color.getRGB() ) {
                    newImage.setRGB( j, i, replace.getRGB() );
                } else {
                    newImage.setRGB( j, i, c.getRGB() );
                }
                
            }
        }
        
        return newImage;
        
    }
    
    /**
     * Gets the color of a pixel in an image.
     *
     * @param image An image.
     * @param x X coordinate of the pixel.
     * @param y Y coordinate of the pixel.
     * @return The color of the pixel.
     */
    public static Color getRGB( Image image, int x, int y ) {
        return new Color( image.getRGB( x, y ), true );
    }
    
    /**
     * Creates an image with text.
     *
     * @param text The text of the image.
     * @param fontSize The font size of the text.
     * @param fontStyle The font style of the text.
     * @param textColor The color of the text.
     * @param backgroundColor The background color of the image.
     * @return An image with text.
     */
    public static Image createTextImage( String text, int fontSize, int fontStyle, Color textColor, Color backgroundColor ) {
        
        BufferedImage dummy = new BufferedImage( 1, 1, BufferedImage.TYPE_INT_ARGB );
        Graphics gd = dummy.createGraphics();
        gd.setFont( new Font( Font.MONOSPACED, fontStyle, fontSize ) );
        int w = gd.getFontMetrics().stringWidth( text );
        gd.dispose();
        
        Image newImage = new Image( w + 20, 30 );
        
        Graphics g = newImage.createGraphics();
        g.setColor( backgroundColor );
        g.fillRect( 0, 0, newImage.getWidth(), newImage.getHeight() );
        g.setColor( textColor );
        g.setFont( new Font( Font.MONOSPACED, fontStyle, fontSize ) );
        g.drawString( text, newImage.getWidth() / 2 - w / 2, 20 );
        g.dispose();
        
        return newImage;
        
    }
    
    /**
     * Creates a copy of an image.
     *
     * @param image Image to be copied.
     * @return A copy of the original image.
     */
    public static Image copyImage( Image image ) {
        return new Image( copyBufferedImage( image.buffImage ) );
    }
    
    /**
     * Creates a copy of an image.
     *
     * @param image Image to be copied.
     * @return A copy of the original image.
     */
    public static BufferedImage copyBufferedImage( BufferedImage image ) {
        
        BufferedImage newImage = new BufferedImage( image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB );
        
        for ( int i = 0; i < image.getHeight(); i++ ) {
            for ( int j = 0; j < image.getWidth(); j++ ) {
                newImage.setRGB( j, i, image.getRGB( j, i ) );
            }
        }
        
        return newImage;
        
    }
    
    /**
     * Loads an image.
     *
     * @param filePath Path to the image file.
     * @return An image.
     */
    public static Image loadImage( String filePath ) {
        
        try {
            return new Image( ImageIO.read( new File( filePath ) ) );
        } catch ( IOException exc ) {
            TraceLogUtils.traceLogError( CoreUtils.stackTraceToString( exc ) );
        }
        
        return createTextImage( "error", 20, Font.BOLD, EngineFrame.WHITE, EngineFrame.BLACK );
        
    }
    
    /**
     * Loads an image.
     *
     * @param input An input stream for an image.
     * @return An image.
     */
    public static Image loadImage( InputStream input ) {
        
        try {
            return new Image( ImageIO.read( input ) );
        } catch ( IOException exc ) {
            TraceLogUtils.traceLogError( CoreUtils.stackTraceToString( exc ) );
        }
        
        return createTextImage( "error", 20, Font.BOLD, EngineFrame.WHITE, EngineFrame.BLACK );
        
    }
    
    /**
     * Loads an image.
     *
     * @param url A URL to an image.
     * @return An image.
     */
    public static Image loadImage( URL url ) {
        
        try {
            return new Image( ImageIO.read( url ) );
        } catch ( IOException exc ) {
            TraceLogUtils.traceLogError( CoreUtils.stackTraceToString( exc ) );
        }
        
        return createTextImage( "error", 20, Font.BOLD, EngineFrame.WHITE, EngineFrame.BLACK );
        
    }
    
}

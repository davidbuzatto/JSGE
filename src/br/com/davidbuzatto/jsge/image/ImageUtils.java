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
package br.com.davidbuzatto.jsge.image;

import br.com.davidbuzatto.jsge.core.utils.ColorUtils;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Classe com métodos estáticos utilitários para tratamento de imagens.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class ImageUtils {
    
    /**
     * Cria uma imagem redimensionada da imagem original.
     * 
     * @param image Uma imagem.
     * @param newImageWidth A altura da nova imagem.
     * @param newImageHeight A largura da nova imagem.
     * @return Uma nova imagem redimensionada.
     */
    public static Image imageResize( Image image, int newImageWidth, int newImageHeight ) {
        
        Image newImage = new Image( newImageWidth, newImageHeight );
        
        Graphics2D g2d = newImage.createGraphics();
        g2d.drawImage(image.buffImage, 0, 0, newImageWidth, newImageHeight, 0, 0, image.getWidth(), image.getHeight(), null );
        g2d.dispose();
        
        return newImage;
        
    }
    
    /**
     * Cria uma imagem invertida verticalmente.
     * 
     * @param image Uma imagem.
     * @return Uma nova imagem invertida verticalmente.
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
     * Cria uma imagem invertida horizontalmente.
     * 
     * @param image Uma imagem.
     * @return Uma nova imagem invertida horizontalmente.
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
     * Cria uma imagem rotacionada.
     * 
     * @param image Uma imagem.
     * @param rotation Rotação em graus (sentido horário).
     * @return Uma nova imagem rotacionada.
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
     * Cria uma imagem rotacionada em 90 graus (sentido horário) da imagem original.
     * 
     * @param image Uma imagem.
     * @return Uma nova imagem rotacionada.
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
     * Cria uma imagem rotacionada em 90 graus (sentido anti-horário) da imagem original.
     * 
     * @param image Uma imagem.
     * @return Uma nova imagem rotacionada.
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
     * Cria uma imagem com a tonalidade alterada.
     * 
     * @param image Uma imagem.
     * @param color A cor que será usada para tonalizar a imagem.
     * @return Uma nova imagem tonalizada.
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
     * Cria uma imagem com as cores invertidas.
     * 
     * @param image Uma imagem.
     * @return Uma nova imagem com cores invertidas.
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
     * Cria uma imagem em tons de cinza.
     * 
     * @param image Uma imagem.
     * @return Uma nova imagem em tons de cinza.
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
     * Cria uma imagem com ajuste no contraste. O fator de contraste vai de 
     * -1.0 a 1.0.
     * 
     * @param image Uma imagem.
     * @param contrast O fator de contraste de -1.0 a 1.0.
     * @return Uma nova imagem com ajuste no contraste.
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
     * Cria uma imagem com ajuste no brilho. O fator de brilho vai de 
     * -1.0 a 1.0.
     * 
     * @param image Uma imagem.
     * @param brightness O fator de brilho de -1.0 a 1.0.
     * @return Uma nova imagem com ajuste no brilho.
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
     * Cria uma nova imagem trocando uma cor.
     * 
     * @param image Uma imagem.
     * @param color A cor que será substituída.
     * @param replace A cor que substituirá a outra cor.
     * @return Uma nova imagem com as cores trocadas.
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
     * Obtém a cor de um pixel de uma imagem.
     * 
     * @param image Uma imagem.
     * @param x Coordenada x do pixel.
     * @param y Coordenada y do pixel.
     * @return A cor do pixel.
     */
    public static Color getImageColor( Image image, int x, int y ) {
        return new Color( image.getRGB( x, y ), true );
    }
    
    /**
     * Cria uma imagem com texto.
     * 
     * @param text O texto da imagem.
     * @param fontSize O tamanho da fonte do texto.
     * @param fontStyle O estilo da fonte do texto.
     * @param textColor A cor do texto.
     * @param backgroundColor A cor de fundo da imagem.
     * @return Uma imagem com texto.
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
     * Cria uma cópia de uma imagem.
     * 
     * @param image Imagem a ser copiada.
     * @return Uma cópia da imagem original.
     */
    public static Image copyImage( Image image ) {
        return new Image( copyBufferedImage( image.buffImage ) );
    }
    
    /**
     * Cria uma cópia de uma imagem.
     * 
     * @param image Imagem a ser copiada.
     * @return  Uma cópia da imagem original.
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
    
}

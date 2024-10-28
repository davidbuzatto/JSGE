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

import br.com.davidbuzatto.jsge.core.utils.DrawingUtils;
import br.com.davidbuzatto.jsge.geom.Arc;
import br.com.davidbuzatto.jsge.geom.Circle;
import br.com.davidbuzatto.jsge.geom.CircleSector;
import br.com.davidbuzatto.jsge.geom.CubicCurve;
import br.com.davidbuzatto.jsge.geom.Ellipse;
import br.com.davidbuzatto.jsge.geom.EllipseSector;
import br.com.davidbuzatto.jsge.geom.Line;
import br.com.davidbuzatto.jsge.geom.Path;
import br.com.davidbuzatto.jsge.geom.Polygon;
import br.com.davidbuzatto.jsge.geom.QuadCurve;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.geom.Ring;
import br.com.davidbuzatto.jsge.geom.RoundRectangle;
import br.com.davidbuzatto.jsge.geom.Triangle;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

/**
 * Abstração para as BufferedImage.
 * 
 * Possui a maioria, senão todos os métodos de desenho da engine,
 * entretanto, o desenho é feito na imagem.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Image {
    
    private static Font font;
    private static BasicStroke stroke;
    private static boolean antialiasing;
    
    static {
        resetFont();
        resetStroke();
        disableAntialiasing();
    }
    
    public BufferedImage buffImage;
    
    /**
     * Cria uma imagem a partir de uma buffered image.
     * @param buffImage 
     */
    public Image( BufferedImage buffImage ) {
        this.buffImage = buffImage;
    }
    
    /**
     * Cria um imagem vazia de tamnho especificado.
     * 
     * @param width Largura.
     * @param height Alura.
     */
    public Image( int width, int height ) {
        this( new BufferedImage( width, height, BufferedImage.TYPE_INT_ARGB ) );
    }
    
    /**
     * Obtém a largura.
     * 
     * @return A largura.
     */
    public int getWidth() {
        return buffImage.getWidth();
    }
    
    /**
     * Obtém a altura.
     * 
     * @return A altura.
     */
    public int getHeight() {
        return buffImage.getHeight();
    }
    
    /**
     * Cria um novo contexto gráfico da buffered image interna.
     * 
     * @return Um novo contexto gráfico.
     */
    public Graphics2D createGraphics() {
        
        Graphics2D g2d = (Graphics2D) buffImage.createGraphics();
        
        if ( antialiasing ) {
            g2d.setRenderingHint( 
                    RenderingHints.KEY_ANTIALIASING, 
                    RenderingHints.VALUE_ANTIALIAS_ON );
        }
        
        g2d.setFont( font );
        g2d.setStroke( stroke );
        
        return g2d;
        
    }
    
    /**
     * Obtém a cor RGB de um pixel.
     * 
     * @param x Coordenada x.
     * @param y Coordenada y
     * @return A cor RGB codificada como inteiro.
     */
    public int getRGB( int x, int y ) {
        return buffImage.getRGB( x, y );
    }
    
    /**
     * Configura a cor RGB de um pixel.
     * 
     * @param x Coordenada x.
     * @param y Coordenada y.
     * @param rgb A cor RGB codificada como inteiro.
     */
    public void setRGB( int x, int y, int rgb ) {
        buffImage.setRGB( x, y, rgb );
    }
    
    
    
    //**************************************************************************
    // Métodos de desenho
    //**************************************************************************

    /**
     * Desenha um pixel.
     * 
     * @param x Coordenada x do pixel.
     * @param y Coordenada y do pixel.
     * @param paint Paint para o desenho.
     */
    public void drawPixel( double x, double y, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.draw( new Line2D.Double( x, y, x, y ) );
        g2d.dispose();
    }

    /**
     * Desenha um pixel.
     * 
     * @param point Ponto do pixel.
     * @param paint Paint para o desenho.
     */
    public void drawPixel( Vector2 point, Paint paint ) {
        drawPixel( point.x, point.y, paint );
    }

    /**
     * Desenha uma linha.
     * 
     * @param startX Coordenada x do ponto inicial.
     * @param startY Coordenada y do ponto inicial.
     * @param endX Coordenada x do ponto final.
     * @param endY Coordenada y do ponto final.
     * @param paint Paint para o desenho.
     */
    public void drawLine( double startX, double startY, double endX, double endY, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.draw( new Line2D.Double( startX, startY, endX, endY ) );
        g2d.dispose();
    }

    /**
     * Desenha uma linha.
     * 
     * @param start Ponto inicial.
     * @param end Ponto final.
     * @param paint Paint para o desenho.
     */
    public void drawLine( Vector2 start, Vector2 end, Paint paint ) {
        drawLine( start.x, start.y, end.x, end.y, paint );
    }

    /**
     * Desenha uma linha.
     * 
     * @param line Uma linha.
     * @param paint Paint para o desenho.
     */
    public void drawLine( Line line, Paint paint ) {
        drawLine( line.x1, line.y1, line.x2, line.y2, paint );
    }

    /**
     * Desenha um retângulo.
     * 
     * @param x Coordenada x do vértice superior esquerdo do retângulo.
     * @param y Coordenada y do vértice superior esquerdo do retângulo.
     * @param width Largura.
     * @param height Altura.
     * @param paint Paint para o desenho.
     */
    public void drawRectangle( double x, double y, double width, double height, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.draw( new Rectangle2D.Double( x, y, width, height ) );
        g2d.dispose();
    }

    /**
     * Desenha um retângulo.
     * 
     * @param pos Vértice superior esquerdo.
     * @param width Largura.
     * @param height Altura.
     * @param paint Paint para o desenho.
     */
    public void drawRectangle( Vector2 pos, double width, double height, Paint paint ) {
        drawRectangle( pos.x, pos.y, width, height, paint );
    }

    /**
     * Desenha um retângulo.
     * 
     * @param rectangle Um retângulo.
     * @param paint Paint para o desenho.
     */
    public void drawRectangle( Rectangle rectangle, Paint paint ) {
        drawRectangle( rectangle.x, rectangle.y, rectangle.width, rectangle.height, paint );
    }

    /**
     * Pinta um retângulo.
     * 
     * @param x Coordenada x do vértice superior esquerdo do retângulo.
     * @param y Coordenada y do vértice superior esquerdo do retângulo.
     * @param width Largura.
     * @param height Altura.
     * @param paint Paint para o desenho.
     */
    public void fillRectangle( double x, double y, double width, double height, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.fill( new Rectangle2D.Double( x, y, width, height ) );
        g2d.dispose();
    }

    /**
     * Pinta um retângulo.
     * 
     * @param pos Vértice superior esquerdo.
     * @param width Largura.
     * @param height Altura.
     * @param paint Paint para o desenho.
     */
    public void fillRectangle( Vector2 pos, double width, double height, Paint paint ) {
        fillRectangle( pos.x, pos.y, width, height, paint );
    }

    /**
     * Pinta um retângulo.
     * 
     * @param rectangle Um retângulo.
     * @param paint Paint para o desenho.
     */
    public void fillRectangle( Rectangle rectangle, Paint paint ) {
        fillRectangle( rectangle.x, rectangle.y, rectangle.width, rectangle.height, paint );
    }

    /**
     * Desenha um retângulo rotacionado.
     * 
     * @param x Coordenada x do vértice superior esquerdo do retângulo.
     * @param y Coordenada y do vértice superior esquerdo do retângulo.
     * @param width Largura.
     * @param height Altura.
     * @param originX Coordenada x do pivô da rotação.
     * @param originY Coordenada y do pivô da rotação.
     * @param rotation Rotação em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void drawRectanglePro( double x, double y, double width, double height, double originX, double originY, double rotation, Paint paint ) {

        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );

        g2d.rotate( Math.toRadians( rotation ), x + originX, y + originY );
        g2d.draw( new Rectangle2D.Double( x, y, width, height ) );

        g2d.dispose();

    }

    /**
     * Desenha um retângulo rotacionado.
     * 
     * @param pos Vértice superior esquerdo.
     * @param width Largura.
     * @param height Altura.
     * @param origin Pivô da rotação.
     * @param rotation Rotação em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void drawRectanglePro( Vector2 pos, double width, double height, Vector2 origin, double rotation, Paint paint ) {
        drawRectanglePro( pos.x, pos.y, width, height, origin.x, origin.y, rotation, paint );
    }

    /**
     * Desenha um retângulo rotacionado.
     * 
     * @param rectangle Um retângulo.
     * @param origin Pivô da rotação.
     * @param rotation Rotação em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void drawRectanglePro( Rectangle rectangle, Vector2 origin, double rotation, Paint paint ) {
        drawRectanglePro( rectangle.x, rectangle.y, rectangle.width, rectangle.height, origin.x, origin.y, rotation, paint );
    }

    /**
     * Pinta um retângulo rotacionado.
     * 
     * @param x Coordenada x do vértice superior esquerdo do retângulo.
     * @param y Coordenada y do vértice superior esquerdo do retângulo.
     * @param width Largura.
     * @param height Altura.
     * @param originX Coordenada x do pivô da rotação.
     * @param originY Coordenada y do pivô da rotação.
     * @param rotation Rotação em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void fillRectanglePro( double x, double y, double width, double height, double originX, double originY, double rotation, Paint paint ) {

        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );

        g2d.rotate( Math.toRadians( rotation ), x + originX, y + originY );
        g2d.fill( new Rectangle2D.Double( x, y, width, height ) );

        g2d.dispose();

    }

    /**
     * Pinta um retângulo rotacionado.
     * 
     * @param pos Vértice superior esquerdo.
     * @param width Largura.
     * @param height Altura.
     * @param origin Pivô da rotação.
     * @param rotation Rotação em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void fillRectanglePro( Vector2 pos, double width, double height, Vector2 origin, double rotation, Paint paint ) {
        fillRectanglePro( pos.x, pos.y, width, height, origin.x, origin.y, rotation, paint );
    }

    /**
     * Pinta um retângulo rotacionado.
     * 
     * @param rectangle Um retângulo.
     * @param origin Pivô da rotação.
     * @param rotation Rotação em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void fillRectanglePro( Rectangle rectangle, Vector2 origin, double rotation, Paint paint ) {
        fillRectanglePro( rectangle.x, rectangle.y, rectangle.width, rectangle.height, origin.x, origin.y, rotation, paint );
    }

    /**
     * Desenha um retângulo com cantos arredondados.
     * 
     * @param x Coordenada x do vértice superior esquerdo do retângulo.
     * @param y Coordenada y do vértice superior esquerdo do retângulo.
     * @param width Largura.
     * @param height Altura.
     * @param roundness Arredondamento dos cantos.
     * @param paint Paint para o desenho.
     */
    public void drawRoundRectangle( double x, double y, double width, double height, double roundness, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.draw( new RoundRectangle2D.Double( x, y, width, height, roundness, roundness ) );
        g2d.dispose();
    }

    /**
     * Desenha um retângulo com cantos arredondados.
     * 
     * @param pos Vértice superior esquerdo do retângulo.
     * @param width Largura.
     * @param height Altura.
     * @param roundness Arredondamento dos cantos.
     * @param paint Paint para o desenho.
     */
    public void drawRoundRectangle( Vector2 pos, double width, double height, double roundness, Paint paint ) {
        drawRoundRectangle( pos.x, pos.y, width, height, roundness, paint );
    }

    /**
     * Desenha um retângulo com cantos arredondados.
     * 
     * @param roundRectangle Um retângulo com os cantos arredondados.
     * @param paint Paint para o desenho.
     */
    public void drawRoundRectangle( RoundRectangle roundRectangle, Paint paint ) {
        drawRoundRectangle( roundRectangle.x, roundRectangle.y, roundRectangle.width, roundRectangle.height, roundRectangle.roundness, paint );
    }

    /**
     * Pinta um retângulo com cantos arredondados.
     * 
     * @param x Coordenada x do vértice superior esquerdo do retângulo.
     * @param y Coordenada y do vértice superior esquerdo do retângulo.
     * @param width Largura.
     * @param height Altura.
     * @param roundness Arredondamento dos cantos.
     * @param paint Paint para o desenho.
     */
    public void fillRoundRectangle( double x, double y, double width, double height, double roundness, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.fill( new RoundRectangle2D.Double( x, y, width, height, roundness, roundness ) );
        g2d.dispose();
    }

    /**
     * Pinta um retângulo com cantos arredondados.
     * 
     * @param pos Vértice superior esquerdo do retângulo.
     * @param width Largura.
     * @param height Altura.
     * @param roundness Arredondamento dos cantos.
     * @param paint Paint para o desenho.
     */
    public void fillRoundRectangle( Vector2 pos, double width, double height, double roundness, Paint paint ) {
        fillRoundRectangle( pos.x, pos.y, width, height, roundness, paint );
    }

    /**
     * Pinta um retângulo com cantos arredondados.
     * 
     * @param roundRectangle Um retângulo com os cantos arredondados.
     * @param paint Paint para o desenho.
     */
    public void fillRoundRectangle( RoundRectangle roundRectangle, Paint paint ) {
        fillRoundRectangle( roundRectangle.x, roundRectangle.y, roundRectangle.width, roundRectangle.height, roundRectangle.roundness, paint );
    }

    /**
     * Desenha um círculo.
     * 
     * @param x Coordenada x do centro do círculo.
     * @param y Coordenada y do centro do círculo.
     * @param radius Raio.
     * @param paint Paint para o desenho.
     */
    public void drawCircle( double x, double y, double radius, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.draw( new Ellipse2D.Double( x - radius, y - radius, radius * 2, radius * 2 ) );
        g2d.dispose();
    }

    /**
     * Desenha um círculo.
     * 
     * @param center Centro do círculo.
     * @param radius Raio.
     * @param paint Paint para o desenho.
     */
    public void drawCircle( Vector2 center, double radius, Paint paint ) {
        drawCircle( center.x, center.y, radius, paint );
    }

    /**
     * Desenha um círculo.
     * 
     * @param circle Um círculo.
     * @param paint Paint para o desenho.
     */
    public void drawCircle( Circle circle, Paint paint ) {
        drawCircle( circle.x, circle.y, circle.radius, paint );
    }

    /**
     * Pinta um círculo.
     * 
     * @param x Coordenada x do centro do círculo.
     * @param y Coordenada y do centro do círculo.
     * @param radius Raio.
     * @param paint Paint para o desenho.
     */
    public void fillCircle( double x, double y, double radius, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.fill( new Ellipse2D.Double( x - radius, y - radius, radius * 2, radius * 2 ) );
        g2d.dispose();
    }

    /**
     * Pinta um círculo.
     * 
     * @param center Centro do círculo.
     * @param radius Raio.
     * @param paint Paint para o desenho.
     */
    public void fillCircle( Vector2 center, double radius, Paint paint ) {
        fillCircle( center.x, center.y, radius, paint );
    }

    /**
     * Pinta um círculo.
     * 
     * @param circle Um círculo.
     * @param paint Paint para o desenho.
     */
    public void fillCircle( Circle circle, Paint paint ) {
        fillCircle( circle.x, circle.y, circle.radius, paint );
    }

    /**
     * Desenha uma elipse.
     * 
     * @param x Coordenada x do centro da elipse.
     * @param y Coordenada y do centro da elipse.
     * @param radiusH Raio horizontal.
     * @param radiusV Raio vertical.
     * @param paint Paint para o desenho.
     */
    public void drawEllipse( double x, double y, double radiusH, double radiusV, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.draw( new Ellipse2D.Double( x - radiusH, y - radiusV, radiusH * 2, radiusV * 2 ) );
        g2d.dispose();
    }

    /**
     * Desenha uma elipse.
     * 
     * @param center Centro da elipse.
     * @param radiusH Raio horizontal.
     * @param radiusV Raio vertical.
     * @param paint Paint para o desenho.
     */
    public void drawEllipse( Vector2 center, double radiusH, double radiusV, Paint paint ) {
        drawEllipse( center.x, center.y, radiusH, radiusV, paint );
    }

    /**
     * Desenha uma elipse.
     * 
     * @param ellipse Uma elipse.
     * @param paint Paint para o desenho.
     */
    public void drawEllipse( Ellipse ellipse, Paint paint ) {
        drawEllipse( ellipse.x, ellipse.y, ellipse.radiusH, ellipse.radiusV, paint );
    }

    /**
     * Pinta uma elipse.
     * 
     * @param x Coordenada x do centro da elipse.
     * @param y Coordenada y do centro da elipse.
     * @param radiusH Raio horizontal.
     * @param radiusV Raio vertical.
     * @param paint Paint para o desenho.
     */
    public void fillEllipse( double x, double y, double radiusH, double radiusV, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.fill( new Ellipse2D.Double( x - radiusH, y - radiusV, radiusH * 2, radiusV * 2 ) );
        g2d.dispose();
    }

    /**
     * Pinta uma elipse.
     * 
     * @param center Centro da elipse.
     * @param radiusH Raio horizontal.
     * @param radiusV Raio vertical.
     * @param paint Paint para o desenho.
     */
    public void fillEllipse( Vector2 center, double radiusH, double radiusV, Paint paint ) {
        fillEllipse( center.x, center.y, radiusH, radiusV, paint );
    }

    /**
     * Pinta uma elipse.
     * 
     * @param ellipse Uma elipse.
     * @param paint Paint para o desenho.
     */
    public void fillEllipse( Ellipse ellipse, Paint paint ) {
        fillEllipse( ellipse.x, ellipse.y, ellipse.radiusH, ellipse.radiusV, paint );
    }

    /**
     * Desenha um setor circular.
     * 
     * @param x Coordenada x do centro.
     * @param y Coordenada y do centro.
     * @param radius Raio.
     * @param startAngle Ângulo inicial em graus (sentido horário).
     * @param endAngle Ângulo final em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void drawCircleSector( double x, double y, double radius, double startAngle, double endAngle, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        double extent = endAngle - startAngle;
        g2d.draw( new Arc2D.Double( x - radius, y - radius, radius * 2, radius * 2, startAngle, -extent, Arc2D.PIE ) );
        g2d.dispose();
    }

    /**
     * Desenha um setor circular.
     * 
     * @param center Centro do setor circular.
     * @param radius Raio.
     * @param startAngle Ângulo inicial em graus (sentido horário).
     * @param endAngle Ângulo final em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void drawCircleSector( Vector2 center, double radius, double startAngle, double endAngle, Paint paint ) {
        drawCircleSector( center.x, center.y, radius, startAngle, endAngle, paint );
    }

    /**
     * Desenha um setor circular.
     * 
     * @param circle Um círculo.
     * @param startAngle Ângulo inicial em graus (sentido horário).
     * @param endAngle Ângulo final em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void drawCircleSector( Circle circle, double startAngle, double endAngle, Paint paint ) {
        drawCircleSector( circle.x, circle.y, circle.radius, startAngle, endAngle, paint );
    }

    /**
     * Desenha um setor circular.
     * 
     * @param circleSector Um setor circular.
     * @param paint Paint para o desenho.
     */
    public void drawCircleSector( CircleSector circleSector, Paint paint ) {
        drawCircleSector( circleSector.x, circleSector.y, circleSector.radius, circleSector.startAngle, circleSector.endAngle, paint );
    }

    /**
     * Pinta um setor circular.
     * 
     * @param x Coordenada x do centro.
     * @param y Coordenada y do centro.
     * @param radius Raio.
     * @param startAngle Ângulo inicial em graus (sentido horário).
     * @param endAngle Ângulo final em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void fillCircleSector( double x, double y, double radius, double startAngle, double endAngle, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        double extent = endAngle - startAngle;
        g2d.fill( new Arc2D.Double( x - radius, y - radius, radius * 2, radius * 2, startAngle, -extent, Arc2D.PIE ) );
        g2d.dispose();
    }

    /**
     * Pinta um setor circular.
     * 
     * @param center Centro do setor circular.
     * @param radius Raio.
     * @param startAngle Ângulo inicial em graus (sentido horário).
     * @param endAngle Ângulo final em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void fillCircleSector( Vector2 center, double radius, double startAngle, double endAngle, Paint paint ) {
        fillCircleSector( center.x, center.y, radius, startAngle, endAngle, paint );
    }

    /**
     * Pinta um setor circular.
     * 
     * @param circle Um círculo.
     * @param startAngle Ângulo inicial em graus (sentido horário).
     * @param endAngle Ângulo final em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void fillCircleSector( Circle circle, double startAngle, double endAngle, Paint paint ) {
        fillCircleSector( circle.x, circle.y, circle.radius, startAngle, endAngle, paint );
    }

    /**
     * Pinta um setor circular.
     * 
     * @param circleSector Um setor circular.
     * @param paint Paint para o desenho.
     */
    public void fillCircleSector( CircleSector circleSector, Paint paint ) {
        fillCircleSector( circleSector.x, circleSector.y, circleSector.radius, circleSector.startAngle, circleSector.endAngle, paint );
    }

    /**
     * Desenha um setor de uma elipse.
     * 
     * @param x Coordenada x do centro.
     * @param y Coordenada y do centro.
     * @param radiusH Raio horizontal.
     * @param radiusV Raio vertical.
     * @param startAngle Ângulo inicial em graus (sentido horário).
     * @param endAngle Ângulo final em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void drawEllipseSector( double x, double y, double radiusH, double radiusV, double startAngle, double endAngle, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        double extent = endAngle - startAngle;
        g2d.draw( new Arc2D.Double( x - radiusH, y - radiusV, radiusH * 2, radiusV * 2, startAngle, -extent, Arc2D.PIE ) );
        g2d.dispose();
    }

    /**
     * Desenha um setor de uma elipse.
     * 
     * @param center Centro do setor de uma elipse.
     * @param radiusH Raio horizontal.
     * @param radiusV Raio vertical.
     * @param startAngle Ângulo inicial em graus (sentido horário).
     * @param endAngle Ângulo final em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void drawEllipseSector( Vector2 center, double radiusH, double radiusV, double startAngle, double endAngle, Paint paint ) {
        drawEllipseSector( center.x, center.y, radiusH, radiusV, startAngle, endAngle, paint );
    }

    /**
     * Desenha um setor de uma elipse.
     * 
     * @param ellipse Uma elipse.
     * @param startAngle Ângulo inicial em graus (sentido horário).
     * @param endAngle Ângulo final em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void drawEllipseSector( Ellipse ellipse, double startAngle, double endAngle, Paint paint ) {
        drawEllipseSector( ellipse.x, ellipse.y, ellipse.radiusH, ellipse.radiusV, startAngle, endAngle, paint );
    }

    /**
     * Desenha um setor de uma elipse.
     * 
     * @param ellipseSector Um setor de uma elipse.
     * @param paint Paint para o desenho.
     */
    public void drawEllipseSector( EllipseSector ellipseSector, Paint paint ) {
        drawEllipseSector( ellipseSector.x, ellipseSector.y, ellipseSector.radiusH, ellipseSector.radiusV, ellipseSector.startAngle, ellipseSector.endAngle, paint );
    }

    /**
     * Pinta um setor de uma elipse.
     * 
     * @param x Coordenada x do centro.
     * @param y Coordenada y do centro.
     * @param radiusH Raio horizontal.
     * @param radiusV Raio vertical.
     * @param startAngle Ângulo inicial em graus (sentido horário).
     * @param endAngle Ângulo final em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void fillEllipseSector( double x, double y, double radiusH, double radiusV, double startAngle, double endAngle, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        double extent = endAngle - startAngle;
        g2d.fill( new Arc2D.Double( x - radiusH, y - radiusV, radiusH * 2, radiusV * 2, startAngle, -extent, Arc2D.PIE ) );
        g2d.dispose();
    }

    /**
     * Pinta um setor de uma elipse.
     * 
     * @param center Centro do setor de uma elipse.
     * @param radiusH Raio horizontal.
     * @param radiusV Raio vertical.
     * @param startAngle Ângulo inicial em graus (sentido horário).
     * @param endAngle Ângulo final em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void fillEllipseSector( Vector2 center, double radiusH, double radiusV, double startAngle, double endAngle, Paint paint ) {
        fillEllipseSector( center.x, center.y, radiusH, radiusV, startAngle, endAngle, paint );
    }

    /**
     * Pinta um setor de uma elipse.
     * 
     * @param ellipse Uma elipse.
     * @param startAngle Ângulo inicial em graus (sentido horário).
     * @param endAngle Ângulo final em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void fillEllipseSector( Ellipse ellipse, double startAngle, double endAngle, Paint paint ) {
        fillEllipseSector( ellipse.x, ellipse.y, ellipse.radiusH, ellipse.radiusV, startAngle, endAngle, paint );
    }

    /**
     * Pinta um setor de uma elipse.
     * 
     * @param ellipseSector Um setor de uma elipse.
     * @param paint Paint para o desenho.
     */
    public void fillEllipseSector( EllipseSector ellipseSector, Paint paint ) {
        fillEllipseSector( ellipseSector.x, ellipseSector.y, ellipseSector.radiusH, ellipseSector.radiusV, ellipseSector.startAngle, ellipseSector.endAngle, paint );
    }

    /**
     * Desenha um arco.
     * 
     * @param x Coordenada x do centro.
     * @param y Coordenada y do centro.
     * @param radiusH Raio horizontal.
     * @param radiusV Raio vertical.
     * @param startAngle Ângulo inicial em graus (sentido horário).
     * @param endAngle Ângulo final em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void drawArc( double x, double y, double radiusH, double radiusV, double startAngle, double endAngle, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        double extent = endAngle - startAngle;
        g2d.draw( new Arc2D.Double( x - radiusH, y - radiusV, radiusH * 2, radiusV * 2, startAngle, -extent, Arc2D.OPEN ) );
        g2d.dispose();
    }

    /**
     * Desenha um arco.
     * 
     * @param center Centro do arco.
     * @param radiusH Raio horizontal.
     * @param radiusV Raio vertical.
     * @param startAngle Ângulo inicial em graus (sentido horário).
     * @param endAngle Ângulo final em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void drawArc( Vector2 center, double radiusH, double radiusV, double startAngle, double endAngle, Paint paint ) {
        drawArc( center.x, center.y, radiusH, radiusV, startAngle, endAngle, paint );
    }

    /**
     * Desenha um arco
     * 
     * @param arc Um arco.
     * @param paint Paint para o desenho.
     */
    public void drawArc( Arc arc, Paint paint ) {
        drawArc( arc.x, arc.y, arc.radiusH, arc.radiusV, arc.startAngle, arc.endAngle, paint );
    }

    /**
     * Pinta um arco.
     * 
     * @param x Coordenada x do centro.
     * @param y Coordenada y do centro.
     * @param radiusH Raio horizontal.
     * @param radiusV Raio vertical.
     * @param startAngle Ângulo inicial em graus (sentido horário).
     * @param endAngle Ângulo final em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void fillArc( double x, double y, double radiusH, double radiusV, double startAngle, double endAngle, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        double extent = endAngle - startAngle;
        g2d.fill( new Arc2D.Double( x - radiusH, y - radiusV, radiusH * 2, radiusV * 2, startAngle, -extent, Arc2D.CHORD ) );
        g2d.dispose();
    }

    /**
     * Pinta um arco.
     * 
     * @param center Centro do arco.
     * @param radiusH Raio horizontal.
     * @param radiusV Raio vertical.
     * @param startAngle Ângulo inicial em graus (sentido horário).
     * @param endAngle Ângulo final em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void fillArc( Vector2 center, double radiusH, double radiusV, double startAngle, double endAngle, Paint paint ) {
        fillArc( center.x, center.y, radiusH, radiusV, startAngle, endAngle, paint );
    }

    /**
     * Pinta um arco
     * 
     * @param arc Um arco.
     * @param paint Paint para o desenho.
     */
    public void fillArc( Arc arc, Paint paint ) {
        fillArc( arc.x, arc.y, arc.radiusH, arc.radiusV, arc.startAngle, arc.endAngle, paint );
    }

    /**
     * Desenha um anel.
     * 
     * @param x Coordenada x do centro.
     * @param y Coordenada y do centro.
     * @param innerRadius Raio interno.
     * @param outerRadius Raio externo.
     * @param startAngle Ângulo inicial em graus (sentido horário).
     * @param endAngle Ângulo final em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void drawRing( double x, double y, double innerRadius, double outerRadius, double startAngle, double endAngle, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.draw( DrawingUtils.createRing( x, y, innerRadius, outerRadius, startAngle, endAngle ) );
        g2d.dispose();
    }

    /**
     * Desenha um anel.
     * 
     * @param center Centro do anel.
     * @param innerRadius Raio interno.
     * @param outerRadius Raio externo.
     * @param startAngle Ângulo inicial em graus (sentido horário).
     * @param endAngle Ângulo final em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void drawRing( Vector2 center, double innerRadius, double outerRadius, double startAngle, double endAngle, Paint paint ) {
        drawRing( center.x, center.y, innerRadius, outerRadius, startAngle, endAngle, paint );
    }

    /**
     * Desenha um anel.
     * 
     * @param ring Um anel.
     * @param paint Paint para o desenho.
     */
    public void drawRing( Ring ring, Paint paint ) {
        drawRing( ring.x, ring.y, ring.innerRadius, ring.outerRadius, ring.startAngle, ring.endAngle, paint );
    }

    /**
     * Pinta um anel.
     * 
     * @param x Coordenada x do centro.
     * @param y Coordenada y do centro.
     * @param innerRadius Raio interno.
     * @param outerRadius Raio externo.
     * @param startAngle Ângulo inicial em graus (sentido horário).
     * @param endAngle Ângulo final em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void fillRing( double x, double y, double innerRadius, double outerRadius, double startAngle, double endAngle, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.fill( DrawingUtils.createRing( x, y, innerRadius, outerRadius, startAngle, endAngle ) );
        g2d.dispose();
    }

    /**
     * Pinta um anel.
     * 
     * @param center Centro do anel.
     * @param innerRadius Raio interno.
     * @param outerRadius Raio externo.
     * @param startAngle Ângulo inicial em graus (sentido horário).
     * @param endAngle Ângulo final em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void fillRing( Vector2 center, double innerRadius, double outerRadius, double startAngle, double endAngle, Paint paint ) {
        fillRing( center.x, center.y, innerRadius, outerRadius, startAngle, endAngle, paint );
    }

    /**
     * Pinta um anel.
     * 
     * @param ring Um anel.
     * @param paint Paint para o desenho.
     */
    public void fillRing( Ring ring, Paint paint ) {
        fillRing( ring.x, ring.y, ring.innerRadius, ring.outerRadius, ring.startAngle, ring.endAngle, paint );
    }

    /**
     * Desenha um triângulo.
     * 
     * @param v1x Coordenada x do primeiro vértice.
     * @param v1y Coordenada y do primeiro vértice.
     * @param v2x Coordenada x do segundo vértice.
     * @param v2y Coordenada y do segundo vértice.
     * @param v3x Coordenada x do terceiro vértice.
     * @param v3y Coordenada y do terceiro vértice.
     * @param paint Paint para o desenho.
     */
    public void drawTriangle( double v1x, double v1y, double v2x, double v2y, double v3x, double v3y, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.draw( DrawingUtils.createTriangle( v1x, v1y, v2x, v2y, v3x, v3y ) );
        g2d.dispose();
    }

    /**
     * Desenha um triângulo.
     * 
     * @param v1 Primeiro vértice.
     * @param v2 Segundo vértice.
     * @param v3 Terceiro vértice.
     * @param paint Paint para o desenho.
     */
    public void drawTriangle( Vector2 v1, Vector2 v2, Vector2 v3, Paint paint ) {
        drawTriangle( v1.x, v1.y, v2.x, v2.y, v3.x, v3.y, paint );
    }

    /**
     * Desenha um triângulo.
     * 
     * @param triangle Um triângulo.
     * @param paint Paint para o desenho.
     */
    public void drawTriangle( Triangle triangle, Paint paint ) {
        drawTriangle( triangle.x1, triangle.y1, triangle.x2, triangle.y2, triangle.x3, triangle.y3, paint );
    }

    /**
     * Pinta um triângulo.
     * 
     * @param v1x Coordenada x do primeiro vértice.
     * @param v1y Coordenada y do primeiro vértice.
     * @param v2x Coordenada x do segundo vértice.
     * @param v2y Coordenada y do segundo vértice.
     * @param v3x Coordenada x do terceiro vértice.
     * @param v3y Coordenada y do terceiro vértice.
     * @param paint Paint para o desenho.
     */
    public void fillTriangle( double v1x, double v1y, double v2x, double v2y, double v3x, double v3y, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.fill( DrawingUtils.createTriangle( v1x, v1y, v2x, v2y, v3x, v3y ) );
        g2d.dispose();
    }

    /**
     * Pinta um triângulo.
     * 
     * @param v1 Primeiro vértice.
     * @param v2 Segundo vértice.
     * @param v3 Terceiro vértice.
     * @param paint Paint para o desenho.
     */
    public void fillTriangle( Vector2 v1, Vector2 v2, Vector2 v3, Paint paint ) {
        fillTriangle( v1.x, v1.y, v2.x, v2.y, v3.x, v3.y, paint );
    }

    /**
     * Pinta um triângulo.
     * 
     * @param triangle Um triângulo.
     * @param paint Paint para o desenho.
     */
    public void fillTriangle( Triangle triangle, Paint paint ) {
        fillTriangle( triangle.x1, triangle.y1, triangle.x2, triangle.y2, triangle.x3, triangle.y3, paint );
    }

    /**
     * Desenha um polígono regular.
     * 
     * @param x Coordenada x do centro do polígono.
     * @param y Coordenada y do centro do polígono.
     * @param sides Quantidade de lados.
     * @param radius Raio.
     * @param rotation Rotação em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void drawPolygon( double x, double y, double sides, double radius, double rotation, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.draw( DrawingUtils.createPolygon( x, y, sides, radius, rotation ) );
        g2d.dispose();
    }

    /**
     * Desenha um polígono regular.
     * 
     * @param center Centro do polígono.
     * @param sides Quantidade de lados.
     * @param radius Raio.
     * @param rotation Rotação em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void drawPolygon( Vector2 center, double sides, double radius, double rotation, Paint paint ) {
        drawPolygon( center.x, center.y, sides, radius, rotation, paint );
    }

    /**
     * Desenha um polígono regular.
     * 
     * @param polygon Um polígono regular.
     * @param paint Paint para o desenho.
     */
    public void drawPolygon( Polygon polygon, Paint paint ) {
        drawPolygon( polygon.x, polygon.y, polygon.sides, polygon.radius, polygon.rotation, paint );
    }

    /**
     * Pinta um polígono regular.
     * 
     * @param x Coordenada x do centro do polígono.
     * @param y Coordenada y do centro do polígono.
     * @param sides Quantidade de lados.
     * @param radius Raio.
     * @param rotation Rotação em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void fillPolygon( double x, double y, double sides, double radius, double rotation, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.fill( DrawingUtils.createPolygon( x, y, sides, radius, rotation ) );
        g2d.dispose();
    }

    /**
     * Pinta um polígono regular.
     * 
     * @param center Centro do polígono.
     * @param sides Quantidade de lados.
     * @param radius Raio.
     * @param rotation Rotação em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void fillPolygon( Vector2 center, double sides, double radius, double rotation, Paint paint ) {
        fillPolygon( center.x, center.y, sides, radius, rotation, paint );
    }

    /**
     * Pinta um polígono regular.
     * 
     * @param polygon Um polígono regular.
     * @param paint Paint para o desenho.
     */
    public void fillPolygon( Polygon polygon, Paint paint ) {
        fillPolygon( polygon.x, polygon.y, polygon.sides, polygon.radius, polygon.rotation, paint );
    }

    /**
     * Desenha um caminho.
     * 
     * @param path Caminho a ser desenhado.
     * @param paint Paint para o desenho.
     */
    public void drawPath( Path path, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.draw( path.path );
        g2d.dispose();
    }

    /**
     * Ponta um caminho.
     * 
     * @param path Caminho a ser desenhado.
     * @param paint Paint para o desenho.
     */
    public void fillPath( Path path, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.fill( path.path );
        g2d.dispose();
    }

    
    
    //**************************************************************************
    // Métodos de desenhos de curvas.
    //**************************************************************************

    /**
     * Desenha uma curva quadrática (curva Bézier quadrática).
     * 
     * @param p1x Coordenada x do ponto inicial.
     * @param p1y Coordenada y do ponto inicial.
     * @param cx Coordenada x do ponto de controle.
     * @param cy Coordenada y do ponto de controle.
     * @param p2x Coordenada x do ponto final.
     * @param p2y Coordenada y do ponto final.
     * @param paint Paint para o desenhho.
     */
    public void drawQuadCurve( double p1x, double p1y, double cx, double cy, double p2x, double p2y, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.draw( new QuadCurve2D.Double( p1x, p1y, cx, cy, p2x, p2y ) );
        g2d.dispose();
    }

    /**
     * Desenha uma curva quadrática (curva Bézier quadrática).
     * 
     * @param p1 Ponto inicial.
     * @param c Ponto de controle.
     * @param p2 Ponto final.
     * @param paint Paint para o desenhho.
     */
    public void drawQuadCurve( Vector2 p1, Vector2 c, Vector2 p2, Paint paint ) {
        drawQuadCurve( p1.x, p1.y, c.x, c.y, p2.x, p2.y, paint );
    }

    /**
     * Desenha uma curva quadrática (curva Bézier quadrática).
     * 
     * @param quadCurve uma curva Bézier quadrática.
     * @param paint Paint para o desenhho.
     */
    public void drawQuadCurve( QuadCurve quadCurve, Paint paint ) {
        drawQuadCurve( quadCurve.x1, quadCurve.y1, quadCurve.cx, quadCurve.cy, quadCurve.x2, quadCurve.y2, paint );
    }

    /**
     * Pinta uma curva quadrática (curva Bézier quadrática).
     * 
     * @param p1x Coordenada x do ponto inicial.
     * @param p1y Coordenada y do ponto inicial.
     * @param cx Coordenada x do ponto de controle.
     * @param cy Coordenada y do ponto de controle.
     * @param p2x Coordenada x do ponto final.
     * @param p2y Coordenada y do ponto final.
     * @param paint Paint para o desenhho.
     */
    public void fillQuadCurve( double p1x, double p1y, double cx, double cy, double p2x, double p2y, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.fill( new QuadCurve2D.Double( p1x, p1y, cx, cy, p2x, p2y ) );
        g2d.dispose();
    }

    /**
     * Pinta uma curva quadrática (curva Bézier quadrática).
     * 
     * @param p1 Ponto inicial.
     * @param c Ponto de controle.
     * @param p2 Ponto final.
     * @param paint Paint para o desenhho.
     */
    public void fillQuadCurve( Vector2 p1, Vector2 c, Vector2 p2, Paint paint ) {
        fillQuadCurve( p1.x, p1.y, c.x, c.y, p2.x, p2.y, paint );
    }

    /**
     * Pinta uma curva quadrática (curva Bézier quadrática).
     * 
     * @param quadCurve uma curva Bézier quadrática.
     * @param paint Paint para o desenhho.
     */
    public void fillQuadCurve( QuadCurve quadCurve, Paint paint ) {
        fillQuadCurve( quadCurve.x1, quadCurve.y1, quadCurve.cx, quadCurve.cy, quadCurve.x2, quadCurve.y2, paint );
    }

    /**
     * Desenha uma curva cúbica (curva Bézier cúbica).
     * 
     * @param p1x Coordenada x do ponto inicial.
     * @param p1y Coordenada y do ponto inicial.
     * @param c1x Coordenada x do primeiro ponto de controle.
     * @param c1y Coordenada y do primeiro ponto de controle.
     * @param c2x Coordenada x do segundo ponto de controle.
     * @param c2y Coordenada y do segundo ponto de controle.
     * @param p2x Coordenada x do ponto final.
     * @param p2y Coordenada y do ponto final.
     * @param paint Paint para o desenhho.
     */
    public void drawCubicCurve( double p1x, double p1y, double c1x, double c1y, double c2x, double c2y, double p2x, double p2y, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.draw( new CubicCurve2D.Double( p1x, p1y, c1x, c1y, c2x, c2y, p2x, p2y ) );
        g2d.dispose();
    }

    /**
     * Desenha uma curva cúbica (curva Bézier cúbica).
     * 
     * @param p1 Ponto inicial.
     * @param c1 Primeiro ponto de controle.
     * @param c2 Segundo ponto de controle.
     * @param p2 Ponto final.
     * @param paint Paint para o desenhho.
     */
    public void drawCubicCurve( Vector2 p1, Vector2 c1, Vector2 c2, Vector2 p2, Paint paint ) {
        drawCubicCurve( p1.x, p1.y, c1.x, c1.y, c2.x, c2.y, p2.x, p2.y, paint );
    }

    /**
     * Desenha uma curva cúbica (curva Bézier cúbica).
     * 
     * @param cubicCurve Uma curva Bézier cúbica.
     * @param paint Paint para o desenhho.
     */
    public void drawCubicCurve( CubicCurve cubicCurve, Paint paint ) {
        drawCubicCurve( cubicCurve.x1, cubicCurve.y1, cubicCurve.c1x, cubicCurve.c1y, cubicCurve.c2x, cubicCurve.c2y, cubicCurve.x2, cubicCurve.y2, paint );
    }

    /**
     * Pinta uma curva cúbica (curva Bézier cúbica).
     * 
     * @param p1x Coordenada x do ponto inicial.
     * @param p1y Coordenada y do ponto inicial.
     * @param c1x Coordenada x do primeiro ponto de controle.
     * @param c1y Coordenada y do primeiro ponto de controle.
     * @param c2x Coordenada x do segundo ponto de controle.
     * @param c2y Coordenada y do segundo ponto de controle.
     * @param p2x Coordenada x do ponto final.
     * @param p2y Coordenada y do ponto final.
     * @param paint Paint para o desenhho.
     */
    public void fillCubicCurve( double p1x, double p1y, double c1x, double c1y, double c2x, double c2y, double p2x, double p2y, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.fill( new CubicCurve2D.Double( p1x, p1y, c1x, c1y, c2x, c2y, p2x, p2y ) );
        g2d.dispose();
    }

    /**
     * Pinta uma curva cúbica (curva Bézier cúbica).
     * 
     * @param p1 Ponto inicial.
     * @param c1 Primeiro ponto de controle.
     * @param c2 Segundo ponto de controle.
     * @param p2 Ponto final.
     * @param paint Paint para o desenhho.
     */
    public void fillCubicCurve( Vector2 p1, Vector2 c1, Vector2 c2, Vector2 p2, Paint paint ) {
        fillCubicCurve( p1.x, p1.y, c1.x, c1.y, c2.x, c2.y, p2.x, p2.y, paint );
    }

    /**
     * Pinta uma curva cúbica (curva Bézier cúbica).
     * 
     * @param cubicCurve Uma curva Bézier cúbica.
     * @param paint Paint para o desenhho.
     */
    public void fillCubicCurve( CubicCurve cubicCurve, Paint paint ) {
        fillCubicCurve( cubicCurve.x1, cubicCurve.y1, cubicCurve.c1x, cubicCurve.c1y, cubicCurve.c2x, cubicCurve.c2y, cubicCurve.x2, cubicCurve.y2, paint );
    }

    
    
    //**************************************************************************
    // Métodos de desenho de texto.
    //**************************************************************************

    /**
     * Desenha um texto usando o tamanho de fonte corrente.
     * 
     * @param text O texto a ser desenhado.
     * @param x Coordenada x do início do desenho do texto.
     * @param y Coordenada y do início do desenho do texto.
     * @param paint Paint para o desenho.
     */
    public void drawText( String text, double x, double y, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        //Rectangle2D r = g2d.getFontMetrics().getStringBounds( text, g2d );
        //g2d.drawString( text, (int) x, (int) ( y + r.getHeight() / 2 ) );
        DrawingUtils.drawTextMultilineHelper( text, x, y, g2d );
        g2d.dispose();
    }
    
    /**
     * Desenha um texto rotacionado usando o tamanho de fonte corrente.
     * 
     * @param text O texto a ser desenhado.
     * @param x Coordenada x do início do desenho do texto.
     * @param y Coordenada y do início do desenho do texto.
     * @param rotation Ângulo de rotação em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void drawText( String text, double x, double y, double rotation, Paint paint ) {
        drawText( text, x, y, 0, 0, rotation, paint );
    }

    /**
     * Desenha um texto rotacionado usando o tamanho de fonte corrente.
     * 
     * @param text O texto a ser desenhado.
     * @param x Coordenada x do início do desenho do texto.
     * @param y Coordenada y do início do desenho do texto.
     * @param originX Coordenada x do pivô de rotação.
     * @param originY Coordenada y do pivô de rotação.
     * @param rotation Ângulo de rotação em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void drawText( String text, double x, double y, double originX, double originY, double rotation, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.rotate( Math.toRadians( rotation ), x + originX, y + originY );
        //Rectangle2D r = g2d.getFontMetrics().getStringBounds( text, g2d );
        //g2d.drawString( text, (int) x, (int) ( y + r.getHeight() / 2 ) );
        DrawingUtils.drawTextMultilineHelper( text, x, y, g2d );
        g2d.dispose();
    }

    /**
     * Desenha um texto.
     * 
     * @param text O texto a ser desenhado.
     * @param x Coordenada x do início do desenho do texto.
     * @param y Coordenada y do início do desenho do texto.
     * @param fontSize Tamanho da fonte.
     * @param paint Paint para o desenho.
     */
    public void drawText( String text, double x, double y, int fontSize, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.setFont( g2d.getFont().deriveFont( (float) fontSize ) );
        //Rectangle2D r = g2d.getFontMetrics().getStringBounds( text, g2d );
        //g2d.drawString( text, (int) x, (int) ( y + r.getHeight() / 2 ) );
        DrawingUtils.drawTextMultilineHelper( text, x, y, g2d );
        g2d.dispose();
    }
    
    /**
     * Desenha um texto rotacionado.
     * 
     * @param text O texto a ser desenhado.
     * @param x Coordenada x do início do desenho do texto.
     * @param y Coordenada y do início do desenho do texto.
     * @param rotation Ângulo de rotação em graus (sentido horário).
     * @param fontSize Tamanho da fonte.
     * @param paint Paint para o desenho.
     */
    public void drawText( String text, double x, double y, double rotation, int fontSize, Paint paint ) {
        drawText( text, x, y, 0, 0, rotation, fontSize, paint );
    }
    
    /**
     * Desenha um texto rotacionado.
     * 
     * @param text O texto a ser desenhado.
     * @param x Coordenada x do início do desenho do texto.
     * @param y Coordenada y do início do desenho do texto.
     * @param originX Coordenada x do pivô de rotação.
     * @param originY Coordenada y do pivô de rotação.
     * @param rotation Ângulo de rotação em graus (sentido horário).
     * @param fontSize Tamanho da fonte.
     * @param paint Paint para o desenho.
     */
    public void drawText( String text, double x, double y, double originX, double originY, double rotation, int fontSize, Paint paint ) {
        Graphics2D g2d = createGraphics();
        g2d.setPaint( paint );
        g2d.setFont( g2d.getFont().deriveFont( (float) fontSize ) );
        g2d.rotate( Math.toRadians( rotation ), x + originX, y + originY );
        //Rectangle2D r = g2d.getFontMetrics().getStringBounds( text, g2d );
        //g2d.drawString( text, (int) x, (int) ( y + r.getHeight() / 2 ) );
        DrawingUtils.drawTextMultilineHelper( text, x, y, g2d );
        g2d.dispose();
    }

    /**
     * Desenha um texto usando o tamanho de fonte corrente.
     * 
     * @param text O texto a ser desenhado.
     * @param point Ponto do inicio do desenho do texto.
     * @param paint Paint para o desenho.
     */
    public void drawText( String text, Vector2 point, Paint paint ) {
        drawText( text, point.x, point.y, paint );
    }
    
    /**
     * Desenha um texto rotacionado usando o tamanho de fonte corrente.
     * 
     * @param text O texto a ser desenhado.
     * @param point Ponto do inicio do desenho do texto.
     * @param rotation Ângulo de rotação em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void drawText( String text, Vector2 point, double rotation, Paint paint ) {
        drawText( text, point.x, point.y, 0, 0, rotation, paint );
    }

    /**
     * Desenha um texto rotacionado usando o tamanho de fonte corrente.
     * 
     * @param text O texto a ser desenhado.
     * @param point Ponto do inicio do desenho do texto.
     * @param origin Ponto do pivô de rotação.
     * @param rotation Ângulo de rotação em graus (sentido horário).
     * @param paint Paint para o desenho.
     */
    public void drawText( String text, Vector2 point, Vector2 origin, double rotation, Paint paint ) {
        drawText( text, point.x, point.y, origin.x, origin.y, rotation, paint );
    }

    /**
     * Desenha um texto.
     * 
     * @param text O texto a ser desenhado.
     * @param point Ponto do inicio do desenho do texto.
     * @param fontSize Tamanho da fonte.
     * @param paint Paint para o desenho.
     */
    public void drawText( String text, Vector2 point, int fontSize, Paint paint ) {
        drawText( text, point.x, point.y, fontSize, paint );
    }
    
    /**
     * Desenha um texto rotacionado.
     * 
     * @param text O texto a ser desenhado.
     * @param point Ponto do inicio do desenho do texto.
     * @param rotation Ângulo de rotação em graus (sentido horário).
     * @param fontSize Tamanho da fonte.
     * @param paint Paint para o desenho.
     */
    public void drawText( String text, Vector2 point, double rotation, int fontSize, Paint paint ) {
        drawText( text, point.x, point.y, 0, 0, rotation, fontSize, paint );
    }

    /**
     * Desenha um texto.
     * 
     * @param text O texto a ser desenhado.
     * @param point Ponto do inicio do desenho do texto.
     * @param origin Ponto do pivô de rotação.
     * @param rotation Ângulo de rotação em graus (sentido horário).
     * @param fontSize Tamanho da fonte.
     * @param paint Paint para o desenho.
     */
    public void drawText( String text, Vector2 point, Vector2 origin, double rotation, int fontSize, Paint paint ) {
        drawText( text, point.x, point.y, origin.x, origin.y, rotation, fontSize, paint );
    }
    
    
    
    //**************************************************************************
    // Métodos para desenho de imagens dentro de imagens.
    //**************************************************************************
    
    /**
     * Desenha uma imagem com fundo colorido.
     * 
     * @param image A imagem a ser desenhada.
     * @param x Coordenada x do desenho da imagem.
     * @param y Coordenada y do desenho da imagem.
     * @param bgColor Uma cor de fundo.
     */
    public void drawImage( Image image, double x, double y, Color bgColor ) {
        Graphics2D g2d = createGraphics();
        g2d.drawImage( image.buffImage, (int) x, (int) y, bgColor, null );
        g2d.dispose();
    }
    
    /**
     * Desenha uma imagem.
     * 
     * @param image A imagem a ser desenhada.
     * @param x Coordenada x do desenho da imagem.
     * @param y Coordenada y do desenho da imagem.
     */
    public void drawImage( Image image, double x, double y ) {
        drawImage( image, x, y, null );
    }
    
    /**
     * Desenha uma imagem rotacionada com fundo colorido.
     * 
     * @param image A imagem a ser desenhada.
     * @param x Coordenada x do desenho da imagem.
     * @param y Coordenada y do desenho da imagem.
     * @param rotation Rotação em graus do desenho da imagem (sentido horário).
     * @param bgColor Uma cor de fundo.
     */
    public void drawImage( Image image, double x, double y, double rotation, Color bgColor ) {
        drawImage( image, x, y, 0, 0, rotation, bgColor );
    }
    
    /**
     * Desenha uma imagem rotacionada.
     * 
     * @param image A imagem a ser desenhada.
     * @param x Coordenada x do desenho da imagem.
     * @param y Coordenada y do desenho da imagem.
     * @param rotation Rotação em graus do desenho da imagem (sentido horário).
     */
    public void drawImage( Image image, double x, double y, double rotation ) {
        drawImage( image, x, y, 0, 0, rotation, null );
    }
    
    /**
     * Desenha uma imagem rotacionada com fundo colorido.
     * 
     * @param image A imagem a ser desenhada.
     * @param x Coordenada x do desenho da imagem.
     * @param y Coordenada y do desenho da imagem.
     * @param originX Coordenada x do eixo de rotação.
     * @param originY Coordenada y do eixo de rotação.
     * @param rotation Rotação em graus do desenho da imagem (sentido horário).
     * @param bgColor Uma cor de fundo.
     */
    public void drawImage( Image image, double x, double y, double originX, double originY, double rotation, Color bgColor ) {
        Graphics2D g2d = createGraphics();
        g2d.rotate( Math.toRadians( rotation ), x + originX, y + originY );
        g2d.drawImage( image.buffImage, (int) x, (int) y, bgColor, null );
        g2d.dispose();
    }
    
    /**
     * Desenha uma imagem rotacionada.
     * 
     * @param image A imagem a ser desenhada.
     * @param x Coordenada x do desenho da imagem.
     * @param y Coordenada y do desenho da imagem.
     * @param originX Coordenada x do eixo de rotação.
     * @param originY Coordenada y do eixo de rotação.
     * @param rotation Rotação em graus do desenho da imagem (sentido horário).
     */
    public void drawImage( Image image, double x, double y, double originX, double originY, double rotation ) {
        drawImage( image, x, y, originX, originY, rotation, null );
    }
    
    /**
     * Desenha o recorte de uma imagem com fundo colorido.
     * 
     * @param image A imagem a ser desenhada.
     * @param source Um retângulo que delimita o recorte da imagem que será desenhado.
     * @param x Coordenada x do desenho da imagem.
     * @param y Coordenada y do desenho da imagem.
     * @param bgColor Uma cor de fundo.
     */
    public void drawImage( Image image, Rectangle source, double x, double y, Color bgColor ) {
        Graphics2D g2d = createGraphics();
        g2d.drawImage( image.buffImage, 
                (int) x, 
                (int) y, 
                (int) ( x + source.width ), 
                (int) ( y + source.height ), 
                (int) source.x, 
                (int) source.y, 
                (int) ( source.x + source.width ), 
                (int) ( source.y + source.height ), 
                bgColor,
                null
        );
        g2d.dispose();
    }
    
    /**
     * Desenha o recorte de uma imagem.
     * 
     * @param image A imagem a ser desenhada.
     * @param source Um retângulo que delimita o recorte da imagem que será desenhado.
     * @param x Coordenada x do desenho da imagem.
     * @param y Coordenada y do desenho da imagem.
     */
    public void drawImage( Image image, Rectangle source, double x, double y ) {
        drawImage( image, source, x, y, null );
    }
    
    /**
     * Desenha o recorte rotacionado de uma imagem com fundo colorido.
     * 
     * @param image A imagem a ser desenhada.
     * @param source Um retângulo que delimita o recorte da imagem que será desenhado.
     * @param x Coordenada x do desenho da imagem.
     * @param y Coordenada y do desenho da imagem.
     * @param rotation Rotação em graus do desenho da imagem (sentido horário).
     * @param bgColor Uma cor de fundo.
     */
    public void drawImage( Image image, Rectangle source, double x, double y, double rotation, Color bgColor ) {
        drawImage( image, source, x, y, 0, 0, rotation, bgColor );
    }
    
    /**
     * Desenha o recorte rotacionado de uma imagem.
     * 
     * @param image A imagem a ser desenhada.
     * @param source Um retângulo que delimita o recorte da imagem que será desenhado.
     * @param x Coordenada x do desenho da imagem.
     * @param y Coordenada y do desenho da imagem.
     * @param rotation Rotação em graus do desenho da imagem (sentido horário).
     */
    public void drawImage( Image image, Rectangle source, double x, double y, double rotation ) {
        drawImage( image, source, x, y, 0, 0, rotation, null );
    }
    
    /**
     * Desenha o recorte rotacionado de uma imagem com fundo colorido.
     * 
     * @param image A imagem a ser desenhada.
     * @param source Um retângulo que delimita o recorte da imagem que será desenhado.
     * @param x Coordenada x do desenho da imagem.
     * @param y Coordenada y do desenho da imagem.
     * @param originX Coordenada x do eixo de rotação.
     * @param originY Coordenada y do eixo de rotação.
     * @param rotation Rotação em graus do desenho da imagem (sentido horário).
     * @param bgColor Uma cor de fundo.
     */
    public void drawImage( Image image, Rectangle source, double x, double y, double originX, double originY, double rotation, Color bgColor ) {
        Graphics2D g2d = createGraphics();
        g2d.rotate( Math.toRadians( rotation ), x + originX, y + originY );
        g2d.drawImage( image.buffImage, 
                (int) x, 
                (int) y, 
                (int) ( x + source.width ), 
                (int) ( y + source.height ), 
                (int) source.x, 
                (int) source.y, 
                (int) ( source.x + source.width ), 
                (int) ( source.y + source.height ), 
                bgColor,
                null
        );
        g2d.dispose();
    }
    
    /**
     * Desenha o recorte rotacionado de uma imagem.
     * 
     * @param image A imagem a ser desenhada.
     * @param source Um retângulo que delimita o recorte da imagem que será desenhado.
     * @param x Coordenada x do desenho da imagem.
     * @param y Coordenada y do desenho da imagem.
     * @param originX Coordenada x do eixo de rotação.
     * @param originY Coordenada y do eixo de rotação.
     * @param rotation Rotação em graus do desenho da imagem (sentido horário).
     */
    public void drawImage( Image image, Rectangle source, double x, double y, double originX, double originY, double rotation ) {
        drawImage( image, source, x, y, originX, originY, rotation, null );
    }
    
    /**
     * Desenha o recorte de uma imagem em um retângulo de destino com fundo colorido.
     * 
     * @param image A imagem a ser desenhada.
     * @param source Um retângulo que delimita o recorte da imagem que será desenhado.
     * @param dest Um retângulo de destino que define a posição e dimensões que a imagem será desenhada.
     * @param bgColor Uma cor de fundo.
     */
    public void drawImage( Image image, Rectangle source, Rectangle dest, Color bgColor ) {
        Graphics2D g2d = createGraphics();
        g2d.drawImage( image.buffImage, 
                (int) dest.x, 
                (int) dest.y, 
                (int) ( dest.x + dest.width ), 
                (int) ( dest.y + dest.height ), 
                (int) source.x, 
                (int) source.y, 
                (int) ( source.x + source.width ), 
                (int) ( source.y + source.height ), 
                bgColor,
                null
        );
        g2d.dispose();
    }
    
    /**
     * Desenha o recorte de uma imagem em um retângulo de destino.
     * 
     * @param image A imagem a ser desenhada.
     * @param source Um retângulo que delimita o recorte da imagem que será desenhado.
     * @param dest Um retângulo de destino que define a posição e dimensões que a imagem será desenhada.
     */
    public void drawImage( Image image, Rectangle source, Rectangle dest ) {
        drawImage( image, source, dest, null );
    }
    
    /**
     * Desenha o recorte rotacionado de uma imagem em um retângulo de destino com fundo colorido.
     * 
     * @param image A imagem a ser desenhada.
     * @param source Um retângulo que delimita o recorte da imagem que será desenhado.
     * @param dest Um retângulo de destino que define a posição e dimensões que a imagem será desenhada.
     * @param rotation Rotação em graus do desenho da imagem (sentido horário).
     * @param bgColor Uma cor de fundo.
     */
    public void drawImage( Image image, Rectangle source, Rectangle dest, double rotation, Color bgColor ) {
        drawImage( image, source, dest, 0, 0, rotation, bgColor );
    }
    
    /**
     * Desenha o recorte rotacionado de uma imagem em um retângulo de destino.
     * 
     * @param image A imagem a ser desenhada.
     * @param source Um retângulo que delimita o recorte da imagem que será desenhado.
     * @param dest Um retângulo de destino que define a posição e dimensões que a imagem será desenhada.
     * @param rotation Rotação em graus do desenho da imagem (sentido horário).
     */
    public void drawImage( Image image, Rectangle source, Rectangle dest, double rotation ) {
        drawImage( image, source, dest, 0, 0, rotation, null );
    }
    
    /**
     * Desenha o recorte rotacionado de uma imagem em um retângulo de destino com fundo colorido.
     * 
     * @param image A imagem a ser desenhada.
     * @param source Um retângulo que delimita o recorte da imagem que será desenhado.
     * @param dest Um retângulo de destino que define a posição e dimensões que a imagem será desenhada.
     * @param originX Coordenada x do eixo de rotação.
     * @param originY Coordenada y do eixo de rotação.
     * @param rotation Rotação em graus do desenho da imagem (sentido horário).
     * @param bgColor Uma cor de fundo.
     */
    public void drawImage( Image image, Rectangle source, Rectangle dest, double originX, double originY, double rotation, Color bgColor ) {
        Graphics2D g2d = createGraphics();
        g2d.rotate( Math.toRadians( rotation ), dest.x + originX, dest.y + originY );
        g2d.drawImage( image.buffImage, 
                (int) dest.x, 
                (int) dest.y, 
                (int) ( dest.x + dest.width ), 
                (int) ( dest.y + dest.height ), 
                (int) source.x, 
                (int) source.y, 
                (int) ( source.x + source.width ), 
                (int) ( source.y + source.height ), 
                bgColor,
                null
        );
        g2d.dispose();
    }
    
    /**
     * Desenha o recorte rotacionado de uma imagem em um retângulo de destino.
     * 
     * @param image A imagem a ser desenhada.
     * @param source Um retângulo que delimita o recorte da imagem que será desenhado.
     * @param dest Um retângulo de destino que define a posição e dimensões que a imagem será desenhada.
     * @param originX Coordenada x do eixo de rotação.
     * @param originY Coordenada y do eixo de rotação.
     * @param rotation Rotação em graus do desenho da imagem (sentido horário).
     */
    public void drawImage( Image image, Rectangle source, Rectangle dest, double originX, double originY, double rotation ) {
        drawImage( image, source, dest, originX, originY, rotation, null );
    }
    
    
    
    //**************************************************************************
    // Métodos para gerenciamento de fonte e contornos para o contexto gráfico
    // das imagens.
    //**************************************************************************
    
    /**
     * Reconfigura a fonte corrente que é usada para os processos de desenho
     * do contexto gráfico das imagens.
     * 
     * A fonte padrão tem os seguintes atributos:
     * <ul>
     * <li>Nome: {@link #FONT_MONOSPACED}</li>
     * <li>Estilo: {@link #FONT_BOLD}</li>
     * <li>Tamanho: 10</li>
     * </ul>
     * 
     */
    public static void resetFont() {
        font = new Font( Font.MONOSPACED, Font.BOLD, 10 );
    }
    
    /**
     * Configura o nome da fonte corrente que é usada para os processos de desenho
     * do contexto gráfico das imagens.
     * 
     * @param name O nome da fonte.
     */
    public static void setFontName( String name ) {
        font = new Font( name, font.getStyle(), font.getSize() );
    }
    
    /**
     * Configura o estilo da fonte corrente que é usada para os processos de desenho
     * do contexto gráfico das imagens.
     * 
     * @param style O estilo da fonte.
     */
    public static void setFontStyle( int style ) {
        font = font.deriveFont( style );
    }
    
    /**
     * Configura o tamanho da fonte corrente que é usada para os processos de desenho
     * do contexto gráfico das imagens.
     * 
     * @param size O tamanho da fonte.
     */
    public static void setFontSize( int size ) {
        font = font.deriveFont( (float) size );
    }
    
    /**
     * Configura a fonte corrente que é usada para os processos de desenho
     * do contexto gráfico das imagens.
     * 
     * @param font A nova fonte.
     */
    public static void setFont( Font font ) {
        Image.font = font;
    }
    
    /**
     * Reconfigura a contorno corrente que é usado para os processos de desenho
     * do contexto gráfico das imagens.
     */
    public static void resetStroke() {
        stroke = new BasicStroke( 1 );
    }
    
    /**
     * Configura a largura do desenho da linha do contorno corrente que é usado
     * para os processos de desenho do contexto gráfico das imagens.
     * 
     * @param width A largura da linha do contorno padrão.
     */
    public static void setStrokeWidth( float width ) {
        stroke = new BasicStroke( width, stroke.getEndCap(), stroke.getLineJoin() );
    }
    
    /**
     * Configura o modelo de desenho do fim das linhas do contorno corrente que é usado
     * para os processos de desenho do contexto gráfico das imagens.
     * 
     * @param endCap O novo modelo de desenho.
     */
    public static void setStrokeEndCap( int endCap ) {
        stroke = new BasicStroke( stroke.getLineWidth(), endCap, stroke.getLineJoin() );
    }
    
    /**
     * Configura o modelo de junção de linhas do contorno corrente que é usado
     * para os processos de desenho do contexto gráfico das imagens.
     * 
     * @param lineJoin O novo modelo de junção de linhas.
     */
    public static void setStrokeLineJoin( int lineJoin ) {
        stroke = new BasicStroke( stroke.getLineWidth(), stroke.getEndCap(), lineJoin );
    }
    
    /**
     * Configura o contorno corrente que é usada para os processos de desenho
     * do contexto gráfico das imagens.
     * 
     * @param stroke O novo contorno.
     */
    public static void setStroke( BasicStroke stroke ) {
        Image.stroke = stroke;
    }
    
    /**
     * Liga a suavização para os processos de desenho do contexto gráfico das
     * imagens.
     */
    public static void enableAntialiasing() {
        antialiasing = true;
    }
    
    /**
     * Desliga a suavização para os processos de desenho do contexto gráfico das
     * imagens.
     */
    public static void disableAntialiasing() {
        antialiasing = false;
    }
    
}

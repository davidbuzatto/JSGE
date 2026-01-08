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
 * Classe que encapsula um componente de Gráfico de Tartaruga (Turtle Graphics).
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Turtle {
    
    private List<TurtleStep> steps;
    private Deque<StateSnapshot> stateStack;
    private TurtleStep currentStep;
    
    /**
     * Cria uma nova Tartaruga na origem, com caneta na cor preta e ângulo inicial
     * igual a -90 graus.
     */
    public Turtle() {
        reset();
    }
    
    /**
     * Cria uma nova Tartaruga com caneta na cor preta e ângulo inicial igual a
     * -90 graus em uma posição definida.
     * 
     * @param x Coordenada x do vértice superior esquerdo.
     * @param y Coordenada y do vértice superior esquerdo.
     */
    public Turtle( double x, double y ) {
        reset( x, y );
    }
    
    /**
     * Cria uma nova Tartaruga com caneta na cor preta em uma posição definida
     * com um ângulo inicial.
     * 
     * @param x Coordenada x do vértice superior esquerdo.
     * @param y Coordenada y do vértice superior esquerdo.
     * @param angle Ângulo inicial em graus (sentido horário).
     */
    public Turtle( double x, double y, double angle ) {
        reset( x, y, angle );
    }
    
    /**
     * Cria uma nova Tartaruga com ângulo inicial igual a -90 graus em uma
     * posição definida com cor inicial.
     * 
     * @param x Coordenada x do vértice superior esquerdo.
     * @param y Coordenada y do vértice superior esquerdo.
     * @param paint Paint inicial.
     */
    public Turtle( double x, double y, Paint paint ) {
        reset( x, y, paint );
    }
    
    /**
     * Cria uma nova Tartaruga.
     * 
     * @param x Coordenada x do vértice superior esquerdo.
     * @param y Coordenada y do vértice superior esquerdo.
     * @param angle Ângulo inicial em graus (sentido horário).
     * @param paint Paint inicial.
     */
    public Turtle( double x, double y, double angle, Paint paint ) {
        reset( x, y, angle, paint );
    }
    
    /**
     * Cria uma nova Tartaruga.
     * 
     * @param x Coordenada x do vértice superior esquerdo.
     * @param y Coordenada y do vértice superior esquerdo.
     * @param angle Ângulo inicial em graus (sentido horário).
     * @param paint Paint inicial.
     * @param stroke Formato inicial da caneta.
     * @param penDown Se a caneta está abaixada.
     */
    public Turtle( double x, double y, double angle, Paint paint, BasicStroke stroke, boolean penDown ) {
        reset( x, y, angle, paint, stroke, penDown );
    }
    
    /**
     * Move a Tartaruga.
     * 
     * @param x Coordenada x de destino.
     * @param y Coordenada y de destino.
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
     * Move a Tartaruga para frente.
     * 
     * @param distance Distância da movimentação.
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
     * Move a Tartaruga para trás.
     * 
     * @param distance Distância da movimentação.
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
     * Move a Tartaruga para esquerda.
     * 
     * @param distance Distância da movimentação.
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
     * Move a Tartaruga para direita.
     * 
     * @param distance Distância da movimentação.
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
     * Gira a Tartaruga.
     * 
     * @param amount Quantidade de graus (sentido horário).
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
     * Gira a Tartaruga para a esquerda.
     * 
     * @param amount Quantidade de graus (sentido anti-horário).
     */
    public void rotateLeft( double amount ) {
        rotate( -amount );
    }
    
    /**
     * Gira a Tartaruga para a direita.
     * 
     * @param amount Quantidade de graus (sentido horário).
     */
    public void rotateRight( double amount ) {
        rotate( amount );
    }
    
    /**
     * Configura a rotação da Tartaruga.
     * 
     * @param angle Ângulo em graus (sentido horário).
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
     * Levanta a caneta.
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
     * Abaixa a caneta.
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
     * Alterna o estado da caneta (levantada ou abaixada).
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
     * Configura a cor da caneta.
     * 
     * @param paint Qualquer objeto paint válido que será usado nas operações
     * de desenho.
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
     * Aumenta a grossura da caneta em um pixel.
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
     * Diminui a grossura da caneta em um pixel.
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
     * Configura a largura da caneta.
     * 
     * @param width A nova largura da caneta.
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
     * Configura o formato da caneta.
     * 
     * @param stroke Novo formato da caneta.
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
     * Salva o estado atual da Tartaruga.
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
     * Restaura o último estado salvo da Tartaruga (caso exista), não removendo
     * os passos gerados até o momento do salvamento.
     */
    public void restoreStateKeepPath() {
        if ( !stateStack.isEmpty() ) {
            currentStep = stateStack.pop().step();
        }
    }
    
    /**
     * Restaura o último estado salvo da Tartaruga (caso exista), removendo
     * os passos gerados até o momento do salvamento.
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
     * Reconfigura a Tartaruga na origem, com caneta na cor preta e ângulo inicial
     * igual a -90 graus.
     */
    public void reset() {
        reset( 0, 0 );
    }
    
    /**
     * Reconfigura a Tartaruga com caneta na cor preta e ângulo inicial igual a
     * -90 graus em uma posição definida.
     * 
     * @param x Coordenada x do vértice superior esquerdo.
     * @param y Coordenada y do vértice superior esquerdo.
     */
    public void reset( double x, double y ) {
        reset( x, y, -90, EngineFrame.BLACK, new BasicStroke( 1 ), true );
    }
    
    /**
     * Reconfigura a Tartaruga com caneta na cor preta em uma posição definida
     * com um ângulo inicial.
     * 
     * @param x Coordenada x do vértice superior esquerdo.
     * @param y Coordenada y do vértice superior esquerdo.
     * @param angle Ângulo inicial em graus (sentido horário).
     */
    public void reset( double x, double y, double angle ) {
        reset( x, y, angle, EngineFrame.BLACK, new BasicStroke( 1 ), true );
    }
    
    /**
     * Reconfigura a Tartaruga com ângulo inicial igual a -90 graus em uma
     * posição definida com cor inicial.
     * 
     * @param x Coordenada x do vértice superior esquerdo.
     * @param y Coordenada y do vértice superior esquerdo.
     * @param paint Paint inicial.
     */
    public void reset( double x, double y, Paint paint ) {
        reset( x, y, -90, paint, new BasicStroke( 1 ), true );
    }
    
    /**
     * Reconfigura a Tartaruga.
     * 
     * @param x Coordenada x do vértice superior esquerdo.
     * @param y Coordenada y do vértice superior esquerdo.
     * @param angle Ângulo inicial em graus (sentido horário).
     * @param paint Paint inicial.
     */
    public void reset( double x, double y, double angle, Paint paint ) {
        reset( x, y, angle, paint, new BasicStroke( 1 ), true );
    }
    
    /**
     * Reconfigura a Tartaruga.
     * 
     * @param x Coordenada x do vértice superior esquerdo.
     * @param y Coordenada y do vértice superior esquerdo.
     * @param angle Ângulo inicial em graus (sentido horário).
     * @param paint Paint inicial.
     * @param stroke Formato inicial da caneta.
     * @param penDown Se a caneta está abaixada.
     */
    public void reset( double x, double y, double angle, Paint paint, BasicStroke stroke, boolean penDown ) {
        steps = new ArrayList<>();
        stateStack = new ArrayDeque<>();
        addStep( x, y, angle, paint, stroke, penDown, TurtleOperation.CONFIG );
    }
    
    /**
     * Desenha todos os passos da Tartaruga.
     * 
     * @param engine EngineFrame utilizada.
     */
    public void draw( EngineFrame engine ) {
        
        if ( steps.size() <= 1 ) {
            return;
        }
        
        draw( 1, steps.size() - 1, engine );
        
    }
    
    /**
     * Desenha do primeiro passo até um limite.
     * 
     * @param until Passo final.
     * @param engine EngineFrame utilizada.
     */
    public void draw( int until, EngineFrame engine ) {
        
        if ( steps.size() <= 1 ) {
            return;
        }
        
        draw( 1, until, engine );
        
    }
    
    /**
     * Desenha um intervalo de passos.
     * 
     * @param from Passo inicial.
     * @param until Passo final.
     * @param engine EngineFrame utilizada.
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
     * Obtém o estado atual da Tartaruga.
     * 
     * @return O estado atual da Tartaruga.
     */
    public TurtleStep getCurrentState() {
        return currentStep;
    }
    
    /**
     * Obtém a quantidade de passos da Tartaruga.
     * 
     * @return A quantidade de passos da Tartaruga.
     */
    public int getStepCount() {
        return steps.size() - 1;
    }
    
    /**
     * Adiciona um novo passo.
     */
    private void addStep( double x, double y, double angle, Paint paint, BasicStroke bs, boolean penDown, TurtleOperation operation ) {
        currentStep = new TurtleStep( x, y, angle, paint, bs, penDown, operation );
        steps.add(currentStep );
    }
    
    /**
     * Record para armazenar o estado salvo da Tartaruga.
     */
    private static record StateSnapshot( TurtleStep step, int stepCount ) {
    }
    
}

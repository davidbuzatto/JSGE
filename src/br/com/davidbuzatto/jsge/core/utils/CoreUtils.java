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
package br.com.davidbuzatto.jsge.core.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Classe com métodos estáticos utilitários.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class CoreUtils {
    
    /**
     * Obtém a versão atual.
     * 
     * @return A versão.
     */
    public static String getVersion() {
        return "1.1.0";
    }
    
    /**
     * Escreve os dados da stack trace de uma exceção em uma string.
     * 
     * @param exc A exceção a ser processada.
     * @return Uma string com o conteúdo da stack trace.
     */
    public static String stackTraceToString( Exception exc ) {
        
        StringWriter out = new StringWriter();
        
        try ( PrintWriter pw = new PrintWriter( out ) ) {
            exc.printStackTrace( pw );
        }
        
        return out.toString();
        
    }

}

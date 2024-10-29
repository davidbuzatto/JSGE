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
package br.com.davidbuzatto.jsge.math;

/**
 * Classe para representação de um vetor de quatro dimensões.
 * 
 * Pode ser usada para retornar valores com quatro componentes.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Vector4 {
    
    /**
     * Coordenada x.
     */
    public double x;
    
    /**
     * Coordenada y.
     */
    public double y;
    
    /**
     * Coordenada z.
     */
    public double z;
    
    /**
     * Coordenada w.
     */
    public double w;

    /**
     * Cria um novo vetor de quatro dimensões com valores padrão.
     */
    public Vector4() {
    }

    /**
     * Cria um novo vetor de quatro dimensões.
     * 
     * @param x coordenada x.
     * @param y coordenada y.
     * @param z coordenada z.
     * @param w coordenada w.
     */
    public Vector4( double x, double y, double z, double w ) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    //**************************************************************************
    // Métodos utilitários para vetores 4D.
    //**************************************************************************
/*
    RMAPI Vector4 Vector4One(void)
    {
    Vector4 result = { 1.0f, 1.0f, 1.0f, 1.0f };
    return result;
    }
    RMAPI Vector4 Vector4Add(Vector4 v1, Vector4 v2)
    {
    Vector4 result = {
    v1.x + v2.x,
    v1.y + v2.y,
    v1.z + v2.z,
    v1.w + v2.w
    };
    return result;
    }
    RMAPI Vector4 Vector4AddValue(Vector4 v, float add)
    {
    Vector4 result = {
    v.x + add,
    v.y + add,
    v.z + add,
    v.w + add
    };
    return result;
    }
    RMAPI Vector4 Vector4Subtract(Vector4 v1, Vector4 v2)
    {
    Vector4 result = {
    v1.x - v2.x,
    v1.y - v2.y,
    v1.z - v2.z,
    v1.w - v2.w
    };
    return result;
    }
    RMAPI Vector4 Vector4SubtractValue(Vector4 v, float add)
    {
    Vector4 result = {
    v.x - add,
    v.y - add,
    v.z - add,
    v.w - add
    };
    return result;
    }
    RMAPI float Vector4Length(Vector4 v)
    {
    float result = sqrtf((v.x*v.x) + (v.y*v.y) + (v.z*v.z) + (v.w*v.w));
    return result;
    }
    RMAPI float Vector4LengthSqr(Vector4 v)
    {
    float result = (v.x*v.x) + (v.y*v.y) + (v.z*v.z) + (v.w*v.w);
    return result;
    }
    RMAPI float Vector4DotProduct(Vector4 v1, Vector4 v2)
    {
    float result = (v1.x*v2.x + v1.y*v2.y + v1.z*v2.z + v1.w*v2.w);
    return result;
    }
    // Calculate distance between two vectors
    RMAPI float Vector4Distance(Vector4 v1, Vector4 v2)
    {
    float result = sqrtf(
    (v1.x - v2.x)*(v1.x - v2.x) + (v1.y - v2.y)*(v1.y - v2.y) +
    (v1.z - v2.z)*(v1.z - v2.z) + (v1.w - v2.w)*(v1.w - v2.w));
    return result;
    }
    // Calculate square distance between two vectors
    RMAPI float Vector4DistanceSqr(Vector4 v1, Vector4 v2)
    {
    float result =
    (v1.x - v2.x)*(v1.x - v2.x) + (v1.y - v2.y)*(v1.y - v2.y) +
    (v1.z - v2.z)*(v1.z - v2.z) + (v1.w - v2.w)*(v1.w - v2.w);
    return result;
    }
    RMAPI Vector4 Vector4Scale(Vector4 v, float scale)
    {
    Vector4 result = { v.x*scale, v.y*scale, v.z*scale, v.w*scale };
    return result;
    }
    // Multiply vector by vector
    RMAPI Vector4 Vector4Multiply(Vector4 v1, Vector4 v2)
    {
    Vector4 result = { v1.x*v2.x, v1.y*v2.y, v1.z*v2.z, v1.w*v2.w };
    return result;
    }
    // Negate vector
    RMAPI Vector4 Vector4Negate(Vector4 v)
    {
    Vector4 result = { -v.x, -v.y, -v.z, -v.w };
    return result;
    }
    // Divide vector by vector
    RMAPI Vector4 Vector4Divide(Vector4 v1, Vector4 v2)
    {
    Vector4 result = { v1.x/v2.x, v1.y/v2.y, v1.z/v2.z, v1.w/v2.w };
    return result;
    }
    // Normalize provided vector
    RMAPI Vector4 Vector4Normalize(Vector4 v)
    {
    Vector4 result = { 0 };
    float length = sqrtf((v.x*v.x) + (v.y*v.y) + (v.z*v.z) + (v.w*v.w));
    if (length > 0)
    {
    float ilength = 1.0f/length;
    result.x = v.x*ilength;
    result.y = v.y*ilength;
    result.z = v.z*ilength;
    result.w = v.w*ilength;
    }
    return result;
    }
    // Get min value for each pair of components
    RMAPI Vector4 Vector4Min(Vector4 v1, Vector4 v2)
    {
    Vector4 result = { 0 };
    result.x = fminf(v1.x, v2.x);
    result.y = fminf(v1.y, v2.y);
    result.z = fminf(v1.z, v2.z);
    result.w = fminf(v1.w, v2.w);
    return result;
    }
    // Get max value for each pair of components
    RMAPI Vector4 Vector4Max(Vector4 v1, Vector4 v2)
    {
    Vector4 result = { 0 };
    result.x = fmaxf(v1.x, v2.x);
    result.y = fmaxf(v1.y, v2.y);
    result.z = fmaxf(v1.z, v2.z);
    result.w = fmaxf(v1.w, v2.w);
    return result;
    }
    // Calculate linear interpolation between two vectors
    RMAPI Vector4 Vector4Lerp(Vector4 v1, Vector4 v2, float amount)
    {
    Vector4 result = { 0 };
    result.x = v1.x + amount*(v2.x - v1.x);
    result.y = v1.y + amount*(v2.y - v1.y);
    result.z = v1.z + amount*(v2.z - v1.z);
    result.w = v1.w + amount*(v2.w - v1.w);
    return result;
    }
    // Move Vector towards target
    RMAPI Vector4 Vector4MoveTowards(Vector4 v, Vector4 target, float maxDistance)
    {
    Vector4 result = { 0 };
    float dx = target.x - v.x;
    float dy = target.y - v.y;
    float dz = target.z - v.z;
    float dw = target.w - v.w;
    float value = (dx*dx) + (dy*dy) + (dz*dz) + (dw*dw);
    if ((value == 0) || ((maxDistance >= 0) && (value <= maxDistance*maxDistance))) return target;
    float dist = sqrtf(value);
    result.x = v.x + dx/dist*maxDistance;
    result.y = v.y + dy/dist*maxDistance;
    result.z = v.z + dz/dist*maxDistance;
    result.w = v.w + dw/dist*maxDistance;
    return result;
    }
    // Invert the given vector
    RMAPI Vector4 Vector4Invert(Vector4 v)
    {
    Vector4 result = { 1.0f/v.x, 1.0f/v.y, 1.0f/v.z, 1.0f/v.w };
    return result;
    }
     */
    
    @Override
    public int hashCode(  ) {
        int hash = 7;
        hash = 37 * hash + (int) ( Double.doubleToLongBits( this.x ) ^ ( Double.doubleToLongBits( this.x ) >>> 32 ) );
        hash = 37 * hash + (int) ( Double.doubleToLongBits( this.y ) ^ ( Double.doubleToLongBits( this.y ) >>> 32 ) );
        hash = 37 * hash + (int) ( Double.doubleToLongBits( this.z ) ^ ( Double.doubleToLongBits( this.z ) >>> 32 ) );
        hash = 37 * hash + (int) ( Double.doubleToLongBits( this.w ) ^ ( Double.doubleToLongBits( this.w ) >>> 32 ) );
        return hash;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final Vector4 other = (Vector4) obj;
        if ( Double.doubleToLongBits( this.x ) != Double.doubleToLongBits( other.x ) ) {
            return false;
        }
        if ( Double.doubleToLongBits( this.y ) != Double.doubleToLongBits( other.y ) ) {
            return false;
        }
        if ( Double.doubleToLongBits( this.z ) != Double.doubleToLongBits( other.z ) ) {
            return false;
        }
        return Double.doubleToLongBits( this.w ) == Double.doubleToLongBits( other.w );
    }

    @Override
    public String toString() {
        return String.format( "Vector4[%.2f, %.2f, %.2f, %.2f]", x, y, z, w );
    }
        
}

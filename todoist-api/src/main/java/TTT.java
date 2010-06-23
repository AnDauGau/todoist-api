/*
 * Copyright 2010 Original Author(s)
 * 
 * This file is part of Kommando
 * 
 * Kommando is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Kommando is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Kommando.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * @author exb896
 * 
 */
public class TTT {
    public static void main(String[] args) {
        long millisUntilFinished = 3 * 60 * 1000 - 5264;
        System.out.println(millisUntilFinished);

        int minutes = (int) (millisUntilFinished / 1000 / 60);
        float seconds = ((float) millisUntilFinished % (60 * 1000)) / 1000;

        System.out.println(String.format("%02d:%04.1f", minutes, seconds));
    }
}

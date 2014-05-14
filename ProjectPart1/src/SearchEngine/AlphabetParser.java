/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SearchEngine;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import krakloader.DataLine;

/**
 *
 * @author Peter Ø. Clausen <pvcl@itu.dk>
 */
public class AlphabetParser
{
    static HashMap<String, Integer> letterToIndex = new HashMap<>();
    
    public AlphabetParser()
    {
        
    }
    
    public HashMap <String, Integer> getHashMap()
    {
        return letterToIndex;
    }
}

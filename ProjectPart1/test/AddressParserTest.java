/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import SearchEngine.AddressParser;

/**
 *
 * @author Peter Ø. Clausen <pvcl@itu.dk>
 */
public class AddressParserTest
{
    AddressParser ap;
    public AddressParserTest()
    {
        ap = new AddressParser();
    }
    
    @BeforeClass
    public static void setUpClass()
    {
    }
    
    @AfterClass
    public static void tearDownClass()
    {
    }
    
    @Before
    public void setUp()
    {
    }
    
    @After
    public void tearDown()
    {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void A()
    {
        String[] expected = {"Rued Langgaards Vej", "7", "", null, "2300", null}; //Wierd "" istead of null
        String[] actual = AddressParser.parse("Rued Langgaards Vej 7 2300");
        assertArrayEquals(expected, actual);
    }
    
    @Test
    public void B()
    {
        String[] expected = {"Rued Langgaards Vej", null, null, null, null, null};
        String[] actual = AddressParser.parse("Rued Langgaards Vej");
        assertArrayEquals(expected, actual);
    }
    
    @Test
    public void C()
    {
        String[] expected = {"Rued Langgaards Vej", null, "", null, "2300", null};
        String[] actual = AddressParser.parse("Rued Langgaards Vej7 2300");
        assertArrayEquals(expected, actual);
    }
    
    @Test
    public void D()
    {
        String[] expected = {"Rued Langgaards Vej", null, null, null, null, null};
        String[] actual = AddressParser.parse("Rued Langgaards Vej ");
        assertArrayEquals(expected, actual);
    }
    
    @Test
    public void E()
    {
        String[] expected = {"Rued Langgaards Vej", "7", null, null, null, null};
        String[] actual = AddressParser.parse("Rued Langgaards Vej 7");
        assertArrayEquals(expected, actual);
    }
    
    @Test
    public void F()
    {
        String[] expected = {"Rued Langgaards Vej", "7", "A", null, null, null};
        String[] actual = AddressParser.parse("Rued Langgaards Vej 7A");
        assertArrayEquals(expected, actual);
    }
    
    @Test
    public void G()
    {
        String[] expected = {"Rued Langgaards Vej", "7", null, "5", null, null};
        String[] actual = AddressParser.parse("Rued Langgaards Vej 7 5. Sal");
        assertArrayEquals(expected, actual);
    }
    
    @Test
    public void H()
    {
        String[] expected = {"Rued Langgaards Vej", "7", null, null, null, "København S"};
        String[] actual = AddressParser.parse("Rued Langgaards Vej 7 København S");
        assertArrayEquals(expected, actual);
    }
    
    @Test
    public void I()
    {
        String[] expected = {"Rued Langgaards Vej", "7", "", null, "2300", "København S"};
        String[] actual = AddressParser.parse("Rued Langgaards Vej 7 2300 København S");
        assertArrayEquals(expected, actual);
    }
    
    @Test
    public void J()
    {
        String[] expected = {"Rued Langgaards Vej", "7", null, null, null, "København S"};
        String[] actual = AddressParser.parse("Rued Langgaards Vej 7, København S");
        assertArrayEquals(expected, actual);
    }
    
    @Test
    public void K()
    {
        String[] expected = {"Rued Langgaards Vej", "7", "", null, "2300", "København S"};
        String[] actual = AddressParser.parse("Rued Langgaards Vej 7, 2300 København S");
        assertArrayEquals(expected, actual);
    }
    
    @Test
    public void L()
    {
        String[] expected = {"Rued Langgaards Vej", null, "", null, "2300", "København S"};
        String[] actual = AddressParser.parse("Rued Langgaards Vej, 2300 København S");
        assertArrayEquals(expected, actual);
    }
    
    @Test
    public void M()
    {
        String[] expected = {"Rued Langgaards Vej", null, "", null, "2300", "København S"};
        String[] actual = AddressParser.parse("Rued Langgaards Vej, 2300 København S");
        assertArrayEquals(expected, actual);
    }
    
    @Test
    public void N()
    {
        String[] expected = {"Rued Langgaards Vej", null, null, null, null, null};
        String[] actual = AddressParser.parse("Rued Langgaards Vej,");
        assertArrayEquals(expected, actual);
    }
    
    @Test
    public void Specal1()
    {
        String[] expected = {"Rued Langgaards Vej", "7", null, "5", null, null};
        String[] actual = AddressParser.parse("Rued Langgaards Vej 7 5.");
        assertArrayEquals(expected, actual);
    }
    
    @Test
    public void Specal2()
    {
        String[] expected = {"Rued Langgaards Vej", "7", null, "5", null, null};
        String[] actual = AddressParser.parse("Rued Langgaards Vej 7 5");
        assertArrayEquals(expected, actual);
    }
    
    @Test
    public void Specal3()
    {
        String[] expected = {"Rued Langgaards Vej", null, "", null, "2300", null};
        String[] actual = AddressParser.parse("Rued Langgaards Vej, 2300");
        assertArrayEquals(expected, actual);
    }
    
    @Test
    public void Specal4()
    {
        String[] expected = {"Rued Langgaards Vej", null, null, null, null, "København S"};
        String[] actual = AddressParser.parse("Rued Langgaards Vej, København S");
        assertArrayEquals(expected, actual);
    }
    
    @Test
    public void Specal5()
    {
        String[] expected = {"Rued Langgaards Vej", "7", "A", "5", "2300", "København S"};
        String[] actual = AddressParser.parse("Rued Langgaards Vej 7A, 5. Sal, 2300 København S");
        assertArrayEquals(expected, actual);
    }
    
    @Test
    public void Specal6()
    {
        String[] expected = {"Rued Langgaards Vej", "7", "A", "5", "2300", "København S"};
        String[] actual = AddressParser.parse("Rued Langgaards Vej 7A 5. Sal, 2300 København S");
        assertArrayEquals(expected, actual);
    }
}

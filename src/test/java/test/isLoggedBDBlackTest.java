package test;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dataAccess.Emaitza;
import domain.Seller;
import dataAccess.DataAccess;

public class isLoggedBDBlackTest {

    private DataAccess sut;
    private Seller testSeller;

    @Before
    public void setUp() {
        sut = new DataAccess();
        sut.open();
        
        // PK1 eta PK2 probatzeko erabiliko den bezeroa gorde
        testSeller = new Seller("seller1", "123", "seller1@shop.com");
        sut.isRegistered("seller1@shop.com", "seller1", "123");
    }

    
    // PK1: estalitako BK= 1,2,3 
    @Test
    public void testIsLogged_ValidSellerAndPass() {
        Emaitza res = sut.isLogged("seller1", "123");
        assertTrue(res.getLog());
        assertEquals("seller1@shop.com", res.getEmail());
    }
    
    //PK2: estalitako BK = 1,2,4
    @Test
    public void testIsLogged_ValidSellerWrongPass() {
        Emaitza res = sut.isLogged("seller1", "wrongpass");
        assertFalse(res.getLog());
        assertNull(res.getSeller());
        assertNull(res.getAdmin());
        assertEquals("",res.getEmail());
    }
    
    
    //PK3: estalitako BK = 5
    @Test
    public void testIsLogged_NullLog() {
        Emaitza res = sut.isLogged(null, "1234");
        assertFalse(res.getLog());
        assertNull(res.getAdmin());
        assertNull(res.getSeller());
        assertEquals("", res.getEmail());
    }

    //PK4: estalitako BK = 6
    @Test
    public void testIsLogged_NullPass() {
        Emaitza res = sut.isLogged("seller1", null);
        assertFalse(res.getLog());
        assertNull(res.getSeller());
        assertNull(res.getAdmin());
        assertEquals("",res.getEmail());
    }
}

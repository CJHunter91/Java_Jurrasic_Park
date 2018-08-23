package models;


import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static junit.framework.Assert.assertEquals;

public class PaddockTest {

    Paddock paddock;
    @Before
    public void before() {
        paddock = new Paddock();
    }

    @Test
    public void canSetId () {
        UUID id = UUID.randomUUID();
        paddock.setId(id);
        assertEquals(paddock.getId(), id);
    }

    @Test
    public void canSetName () {
        String name = "Velocity Raptor";
        paddock.setName(name);
        assertEquals(paddock.getName(), name);
    }
}

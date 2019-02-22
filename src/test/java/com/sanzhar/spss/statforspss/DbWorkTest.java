/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanzhar.spss.statforspss;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author admin
 */
public class DbWorkTest {

    private static final String TABLE_FOR_TESTONG = "ovarian_general_data";

    public DbWorkTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @org.junit.Test
    public void testGetColumnNamesAndComments() throws Exception {
        final List<VariableLabel> columnNamesAndComments = DbWork.getColumnNamesAndComments(TABLE_FOR_TESTONG);

        assertTrue(columnNamesAndComments.size() == 115);
        VariableLabel patient = null;
        for (VariableLabel columnNamesAndComment : columnNamesAndComments) {
            if (columnNamesAndComment.getColumnName().equals("patient_id")) {
                patient = columnNamesAndComment;
            }
        }
        assertNotNull(patient);
        assertNotNull(patient.getColumnName());
        assertEquals(patient.getColumnName(), "patient_id");
        assertEquals(patient.getColumnComment(), "Пациент (внеш.ключ)");
    }

    @org.junit.Test
    public void testGetValueLabels() throws Exception {
        final List<ValueLabel> valueLabels = DbWork.getValueLabels(TABLE_FOR_TESTONG);
        System.out.println("valueLabels.size() = " + valueLabels.size());
        assertTrue(valueLabels.size() == 66);
        int numId = 0;
        int numBool = 0;
        for (ValueLabel valueLabel : valueLabels) {
            if (valueLabel.getLabel().endsWith("_id")) {

                if (valueLabel.getLabel().endsWith("_yes_no_id")) {
                    numBool++;
                } else {
                    numId++;
                }
            }
        }
        assertEquals(56, numBool);
        assertEquals(10, numId);
    }

}

package ru.fizteh.fivt.students.adanilyak.multifilehashmap;

import org.junit.*;
import ru.fizteh.fivt.storage.strings.Table;

import java.io.File;
import java.io.IOException;

/**
 * User: Alexander
 * Date: 26.10.13
 * Time: 18:32
 */
public class TableStorageUnitTest {
    TableManager testManager;
    Table testTableEng;
    Table testTableRus;

    @Before
    public void setUpTestObject() throws IOException {
        testManager = new TableManager(new File("/Users/Alexander/Documents/JavaDataBase/Tests"));
        testTableEng = testManager.createTable("testTable#9");
        testTableRus = testManager.createTable("тестоваяТаблица#10");
    }

    @After
    public void tearDownTestObject() {
        testManager.removeTable("testTable#9");
        testManager.removeTable("тестоваяТаблица#10");
    }

    /**
     * TEST BLOCK
     * GET NAME TESTS
     */

    @Test
    public void getNameTest() {
        Assert.assertEquals("testTable#9", testTableEng.getName());
        Assert.assertEquals("тестоваяТаблица#10", testTableRus.getName());
    }

    /**
     * TEST BLOCK
     * GET TESTS
     */

    @Test
    public void getTest() {
        testTableEng.put("key", "value");
        Assert.assertEquals("value", testTableEng.get("key"));
        Assert.assertNull(testTableEng.get("nonExictingKey"));
        testTableEng.remove("key");

        testTableRus.put("ключ", "значение");
        Assert.assertEquals("значение", testTableRus.get("ключ"));
        Assert.assertNull(testTableRus.get("несуществующийКлюч"));
        testTableRus.remove("ключ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getNullTest() {
        testTableEng.get(null);
    }

    /**
     * TEST BLOCK
     * PUT TESTS
     */

    @Test
    public void putTest() {
        Assert.assertNull(testTableEng.put("key", "value"));
        Assert.assertEquals("value", testTableEng.put("key", "value"));
        Assert.assertEquals("value", testTableEng.put("key", "anotherValue"));
        testTableEng.remove("key");

        Assert.assertNull(testTableRus.put("ключ", "значение"));
        Assert.assertEquals("значение", testTableRus.put("ключ", "значение"));
        Assert.assertEquals("значение", testTableRus.put("ключ", "другоеЗначение"));
        testTableRus.remove("ключ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void putNullTest() {
        testTableEng.put(null, "value");
        testTableEng.put("key", null);
        testTableEng.put(null, null);
    }

    /**
     * TEST BLOCK
     * REMOVE TESTS
     */

    @Test
    public void removeTest() {
        testTableEng.put("key", "value");
        Assert.assertNull(testTableEng.remove("nonExictingKey"));
        Assert.assertEquals("value", testTableEng.remove("key"));

        testTableEng.put("ключ", "значение");
        Assert.assertNull(testTableEng.remove("несуществующийКлюч"));
        Assert.assertEquals("значение", testTableEng.remove("ключ"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeNullTest() {
        testTableEng.remove(null);
    }

    /**
     * TEST BLOCK
     * SIZE TESTS
     */

    @Test
    public void sizeTest() {
        Assert.assertEquals(0, testTableEng.size());
        testTableEng.put("key1", "value1");
        Assert.assertEquals(1, testTableEng.size());
        testTableEng.put("key2", "value2");
        testTableEng.put("key3", "value3");
        Assert.assertEquals(3, testTableEng.size());
        testTableEng.put("key4", "value4");
        testTableEng.put("key5", "value5");
        Assert.assertEquals(5, testTableEng.size());
        testTableEng.commit();
        Assert.assertEquals(5, testTableEng.size());

        for(int i = 1; i <= 5; ++i) {
            testTableRus.put("ключ" + i, "значение" + i);
        }
        Assert.assertEquals(5, testTableRus.size());
        testTableRus.rollback();
        Assert.assertEquals(0, testTableRus.size());

        for(int i = 1; i <= 5; ++i) {
            testTableEng.remove("key" + i);
        }

        for(int i = 1; i <= 5; ++i) {
            testTableRus.remove("ключ" + i);
        }
    }

    /**
     * TEST BLOCK
     * COMMIT TESTS
     */

    @Test
    public void commitTest() {
        Assert.assertEquals(0, testTableEng.commit());
        for(int i = 1; i <= 5; ++i) {
            testTableEng.put("key" + i, "value" + i);
        }
        Assert.assertEquals(5, testTableEng.commit());
        for(int i = 1; i <= 5; ++i) {
            testTableEng.remove("key" + i);
        }
    }

    /**
     * TEST BLOCK
     * ROLLBACK TESTS
     */

    @Test
    public void rollbackTest() {
        Assert.assertEquals(0, testTableEng.rollback());
        for(int i = 1; i <= 5; ++i) {
            testTableEng.put("key" + i, "value" + i);
        }
        testTableEng.commit();
        testTableEng.put("key2", "anotherValue2");
        testTableEng.put("key4", "anotherValue4");
        Assert.assertEquals(2, testTableEng.rollback());
        Assert.assertEquals("value2", testTableEng.get("key2"));
        Assert.assertEquals("value4", testTableEng.get("key4"));
        for(int i = 1; i <= 5; ++i) {
            testTableEng.remove("key" + i);
        }
    }

}
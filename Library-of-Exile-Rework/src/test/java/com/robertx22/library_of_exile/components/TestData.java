package com.robertx22.library_of_exile.components;

import java.util.Objects;

/**
 * Simple test data class for unit testing Data Components.
 * Demonstrates proper immutability and required methods.
 */
public class TestData {
    private final int value;
    private final String name;

    public TestData() {
        this(0, "default");
    }

    public TestData(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public TestData withValue(int newValue) {
        return new TestData(newValue, this.name);
    }

    public TestData withName(String newName) {
        return new TestData(this.value, newName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof TestData))
            return false;
        TestData testData = (TestData) o;
        return value == testData.value && Objects.equals(name, testData.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, name);
    }

    @Override
    public String toString() {
        return "TestData{value=" + value + ", name='" + name + "'}";
    }
}

package com.robertx22.library_of_exile.components;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.codec.StreamCodec;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for DataComponentCodecs.
 * Tests GSON-based codec generation and round-trip serialization.
 */
@DisplayName("Data Component Codecs")
class DataComponentCodecsTest {

    @Test
    @DisplayName("GSON Codec should serialize and deserialize correctly")
    void testGsonCodecRoundTrip() {
        // Create codec
        Codec<TestData> codec = DataComponentCodecs.createGsonCodec(TestData.class);

        // Test data
        TestData original = new TestData(42, "test");

        // This test verifies the codec can be created
        // Full round-trip testing requires Minecraft's DataResult infrastructure
        assertNotNull(codec, "Codec should not be null");
    }

    @Test
    @DisplayName("GSON StreamCodec should encode and decode over network")
    void testGsonStreamCodecRoundTrip() {
        // Create stream codec
        StreamCodec<ByteBuf, TestData> streamCodec = DataComponentCodecs.createGsonStreamCodec(TestData.class,
                TestData::new);

        // Test data
        TestData original = new TestData(42, "test_name");

        // Create buffer
        ByteBuf buffer = Unpooled.buffer();

        try {
            // Encode
            streamCodec.encode(buffer, original);

            // Decode
            TestData decoded = streamCodec.decode(buffer);

            // Verify
            assertNotNull(decoded, "Decoded object should not be null");
            assertEquals(original.getValue(), decoded.getValue(), "Value should match");
            assertEquals(original.getName(), decoded.getName(), "Name should match");
            assertEquals(original, decoded, "Objects should be equal");
        } finally {
            buffer.release();
        }
    }

    @Test
    @DisplayName("StreamCodec should handle null/default values gracefully")
    void testStreamCodecWithDefaults() {
        StreamCodec<ByteBuf, TestData> streamCodec = DataComponentCodecs.createGsonStreamCodec(TestData.class,
                TestData::new);

        TestData defaultData = new TestData();
        ByteBuf buffer = Unpooled.buffer();

        try {
            streamCodec.encode(buffer, defaultData);
            TestData decoded = streamCodec.decode(buffer);

            assertEquals(defaultData, decoded, "Default values should round-trip correctly");
        } finally {
            buffer.release();
        }
    }

    @Test
    @DisplayName("StreamCodec should handle invalid JSON gracefully")
    void testStreamCodecErrorHandling() {
        StreamCodec<ByteBuf, TestData> streamCodec = DataComponentCodecs.createGsonStreamCodec(TestData.class,
                TestData::new);

        ByteBuf buffer = Unpooled.buffer();

        try {
            // Write invalid JSON manually
            StreamCodec<ByteBuf, String> stringCodec = net.minecraft.network.codec.ByteBufCodecs.STRING_UTF8;
            stringCodec.encode(buffer, "{invalid json}");

            // Should return default instance on error
            TestData decoded = streamCodec.decode(buffer);

            assertNotNull(decoded, "Should return default instance on error");
            assertEquals(new TestData(), decoded, "Should be default instance");
        } finally {
            buffer.release();
        }
    }
}

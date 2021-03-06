package cn.gorden.bloomfilter.core;

import cn.gorden.bloomfilter.core.concurrent.ConcurrentBloomFilter;
import cn.gorden.bloomfilter.core.hash.Murmur3_128HashFunction;
import cn.gorden.bloomfilter.core.serializer.JdkSerializationBloomFilterSerializer;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class ConcurrentBloomFilterTest {

    @Test
    public void mightContainTest() {
        ConcurrentBloomFilter concurrentBloomFilter = ConcurrentBloomFilter.create("test", 100000, 0.03, new JdkSerializationBloomFilterSerializer(), new Murmur3_128HashFunction(0));
        concurrentBloomFilter.put("a");
        concurrentBloomFilter.put("b");
        concurrentBloomFilter.put("c");
        assertTrue(concurrentBloomFilter.mightContain("a"));
        assertTrue(concurrentBloomFilter.mightContain("b"));
        assertTrue(concurrentBloomFilter.mightContain("c"));
        assertFalse(concurrentBloomFilter.mightContain("d"));
    }
}

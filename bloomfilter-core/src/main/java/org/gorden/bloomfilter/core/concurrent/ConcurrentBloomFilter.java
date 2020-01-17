package org.gorden.bloomfilter.core.concurrent;

import org.gorden.bloomfilter.core.BloomFilter;
import org.gorden.bloomfilter.core.bitset.BitSet;
import org.gorden.bloomfilter.core.bitset.LockFreeBitSet;
import org.gorden.bloomfilter.core.hash.Hasher;
import org.gorden.bloomfilter.core.hash.Murmur3_128Hasher;
import org.gorden.bloomfilter.core.serializer.BloomFilterSerializer;
import org.gorden.bloomfilter.core.serializer.JdkSerializationBloomFilterSerializer;

/**
 * @author: GordenTam
 * @create: 2020-01-15
 **/

public class ConcurrentBloomFilter<T> extends BloomFilter<T> {

    private ConcurrentBloomFilter(int numHashFunctions, BitSet bitSet, BloomFilterSerializer bloomFilterSerializer, Hasher hasher) {
        super(numHashFunctions, bitSet, bloomFilterSerializer, hasher);
    }

    public static <T> ConcurrentBloomFilter<T> create(long expectedInsertions, double fpp) {
        if (expectedInsertions <= 0) {
            throw new IllegalArgumentException(String.format("expectedInsertions (%s) must be > 0", expectedInsertions));
        }
        if (fpp >= 1.0) {
            throw new IllegalArgumentException(String.format("numHashFunctions (%s) must be < 1.0", fpp));
        }
        if (fpp <= 0.0) {
            throw new IllegalArgumentException(String.format("numHashFunctions (%s) must be > 0.0", fpp));
        }
        long numBits = optimalNumOfBits(expectedInsertions, fpp);
        int numHashFunctions = optimalNumOfHashFunctions(expectedInsertions, numBits);
        return new ConcurrentBloomFilter<T>(numHashFunctions, new LockFreeBitSet(numBits), new JdkSerializationBloomFilterSerializer(), new Murmur3_128Hasher(0));
    }

}

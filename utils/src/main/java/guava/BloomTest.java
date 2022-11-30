package guava;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2022/11/11 0:09
 */
public class BloomTest {

    // 布隆过滤器: 缓存穿透
    @Test
    public void bloomFilter() {
        int size = 1000;
        // fpp: 误判率
        BloomFilter<CharSequence> charSequenceBloomFilter = BloomFilter.create(Funnels.stringFunnel(StandardCharsets.UTF_8), size);

        // 放入数据
        charSequenceBloomFilter.put("1");
        charSequenceBloomFilter.put("1");
        charSequenceBloomFilter.put("1");
    }

}

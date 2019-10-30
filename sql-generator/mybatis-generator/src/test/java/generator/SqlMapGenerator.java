package generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author child
 * 2019/5/21 22:35
 */
public class SqlMapGenerator {

    private static void generator() throws IOException, XMLParserException, InvalidConfigurationException, SQLException, InterruptedException {

        List<String> warnings = new ArrayList<>();
        InputStream is = SqlMapGenerator.class.getClassLoader().getResourceAsStream("generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(is);
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);

    }

    public static void main(String[] args) throws InterruptedException, SQLException, InvalidConfigurationException, XMLParserException, IOException {
        generator();

    }

}

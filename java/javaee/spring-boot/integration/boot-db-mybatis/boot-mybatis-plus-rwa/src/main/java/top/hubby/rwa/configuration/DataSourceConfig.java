package top.hubby.rwa.configuration;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import top.hubby.rwa.constants.enums.DynamicDataSourceEnum;
import top.hubby.rwa.dynamic.DataSourceRouter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author asd <br>
 * @create 2021-09-28 4:42 PM <br>
 * @project boot-security-shiro <br>
 */
@Slf4j
@Configuration
@MapperScan(basePackages = "top.hubby.rwa.mapper", sqlSessionTemplateRef = "sqlTemplate")
public class DataSourceConfig {

    /** 主库 */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDb() {
        return DruidDataSourceBuilder.create().build();
    }

    /** 从库 */
    @Bean
    @ConditionalOnProperty(prefix = "spring.datasource", name = "slave", matchIfMissing = true)
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDb() {
        return DruidDataSourceBuilder.create().build();
    }

    /** 主从动态配置 */
    @Bean
    public DataSourceRouter dynamicDb(
            @Qualifier("masterDb") DataSource masterDataSource,
            @Autowired(required = false) @Qualifier("slaveDb") DataSource slaveDataSource) {
        DataSourceRouter dynamicDataSource = new DataSourceRouter();
        Map<Object, Object> targetDataSources = new HashMap<>(2);
        targetDataSources.put(DynamicDataSourceEnum.MASTER.getDataSourceName(), masterDataSource);
        if (slaveDataSource != null) {
            targetDataSources.put(DynamicDataSourceEnum.SLAVE.getDataSourceName(), slaveDataSource);
        }
        dynamicDataSource.setTargetDataSources(targetDataSources);
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource);
        return dynamicDataSource;
    }

    @Bean
    public SqlSessionFactory sessionFactory(@Qualifier("dynamicDb") DataSource dynamicDataSource)
            throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dynamicDataSource);
        return bean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlTemplate(
            @Qualifier("sessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "dataSourceTx")
    public DataSourceTransactionManager dataSourceTx(
            @Qualifier("dynamicDb") DataSource dynamicDataSource) {
        DataSourceTransactionManager dataSourceTransactionManager =
                new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dynamicDataSource);
        return dataSourceTransactionManager;
    }
}

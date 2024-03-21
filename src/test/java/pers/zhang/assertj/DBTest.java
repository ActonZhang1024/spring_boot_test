package pers.zhang.assertj;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.assertj.db.type.DateValue;
import org.assertj.db.type.Source;
import org.assertj.db.type.Table;
import org.junit.jupiter.api.Test;

/**
 * @Author: acton_zhang
 * @Date: 2024/1/25 5:34 下午
 * @Version 1.0
 */

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;

import static org.assertj.db.api.Assertions.assertThat;

public class DBTest {

    @Test
    public void test() {
        //创建mysql数据源
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL("jdbc:mysql://sh-cynosdbmysql-grp-3nt28ij8.sql.tencentcdb.com:29749/demo");
        dataSource.setUser("root");
        dataSource.setPassword("15043577079zcL");
        //创建表
        Table table = new Table(dataSource, "members");
        //检查列
        assertThat(table).column("name")
                .value().isEqualTo("Hewson")
                .value().isEqualTo("Evans")
                .value().isEqualTo("Clayton")
                .value().isEqualTo("Mullen");
        //检查行
        assertThat(table).row(1)
                .value().isEqualTo(2)
                .value().isEqualTo("Evans")
                .value().isEqualTo("David Howell")
                .value().isEqualTo("The Edge")
                .value().isEqualTo(DateValue.of(2008, 8, 1))
                .value().isEqualTo(1.77);
    }
}

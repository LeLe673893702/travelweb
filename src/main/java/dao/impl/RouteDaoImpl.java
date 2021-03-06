package dao.impl;

import dao.RouteDao;
import model.Route;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.annotation.Transactional;
import utils.JDBCUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<Route> getPageRoutesByCid(String cid, int startIndex, int pageCount) {
        return jdbcTemplate.query("SELECT * FROM " +
                        "travel.tab_route " +
                        "WHERE " +
                        "tab_route.cid = ? LIMIT ?,?;",
                new Object[]{cid, startIndex, pageCount},
                new BeanPropertyRowMapper<>(Route.class));
    }

    @Override
    public int getRoutesCountByCid(String cid) {
        return jdbcTemplate.queryForInt("SELECT COUNT(*) FROM travel.tab_route WHERE tab_route.cid = ?",
                cid);
    }


    @Override
    public Route getRouteById(String rid) {
        return DataAccessUtils.uniqueResult(jdbcTemplate.query("SELECT * FROM tab_route WHERE rid = ?",
                new Object[]{rid},
                new BeanPropertyRowMapper<>(Route.class))
        );
    }

    @Override
    public Route updateRouteCollectCount(String rid, int collectCount) {
        int count = jdbcTemplate.update("UPDATE tab_route SET tab_route.count = ? WHERE rid = ?", collectCount, rid);
        if (count == 1) {
            return getRouteById(rid);
        } else {
            return null;
        }
    }

    @Override
    public int getRouteCollectCount(String rid) {
        return jdbcTemplate.queryForInt("SELECT count FROM tab_route WHERE rid = ?", rid);
    }
}

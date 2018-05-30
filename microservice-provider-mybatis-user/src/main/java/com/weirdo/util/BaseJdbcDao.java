package com.weirdo.util;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
/**
 * @Author ML.Zhang
 * @Date 2018/5/25
 */
public abstract class BaseJdbcDao {
    private static final JsonRowMapper JSON_ROW_MAPPER = new JsonRowMapper();
    private JdbcTemplate jdbcTemplate;
    private static Date startTime;

    public BaseJdbcDao() {
    }

    @Autowired
    public void initJdbcTemplate(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        if (startTime == null) {
            startTime = this.getCurrentTime();
        }

    }

    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getCurrentTime() {
        return (Date) this.getJdbcTemplate().queryForObject("SELECT current_timestamp() FROM DUAL", Date.class);
    }

    public List<JSONObject> selectForJsonList(String sql, Object... args) {
        return this.jdbcTemplate.query(sql, JSON_ROW_MAPPER, args);
    }

    public JSONObject selectForJsonObject(String sql, Object... args) {
        List<JSONObject> jsonList = this.selectForJsonList(sql, args);
        return jsonList != null && jsonList.size() >= 1 ? (JSONObject) jsonList.get(0) : null;
    }

    public String selectForString(String sql, Object... args) {
        List<String> dataList = this.jdbcTemplate.queryForList(sql, args, String.class);
        return dataList != null && dataList.size() >= 1 ? (String) dataList.get(0) : null;
    }

    public void appendPageSql(StringBuffer sql, int start, int limit) {
        sql.insert(0, "SELECT * FROM (SELECT PAGE_VIEW.*, ROWNUM AS ROW_SEQ_NO FROM (");
        sql.append(") PAGE_VIEW WHERE ROWNUM <= " + (start + limit));
        sql.append(") WHERE ROW_SEQ_NO > " + start);
    }

    public void appendOrgSubQuerySql(StringBuffer sql, List<Object> params, String orgId) {
        sql.append("SELECT ORG_ID FROM MST_ORG_REF WHERE P_ID = ?");
        params.add(orgId);
    }

    public String generateKey() {
        String sql = "SELECT CONCAT('0000',date_format(current_timestamp(), '%Y%m%d')) FROM DUAL ";
        String pre = (String) this.getJdbcTemplate().queryForObject(sql, String.class);
        String uid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        return pre + uid.substring(12);
    }

    public void appendSqlIn(StringBuffer sql, List<Object> sqlArgs, String[] params) {
        if (params != null && params.length > 0) {
            sql.append(" (");

            for (int i = 0; i < params.length; ++i) {
                if (i == 0) {
                    sql.append("?");
                } else {
                    sql.append(",?");
                }

                sqlArgs.add(params[i]);
            }

            sql.append(") ");
        }

    }

    public static String c(String c) {
        return StringUtils.isBlank(c) ? null : c.trim().toUpperCase();
    }

    public static String v(String v) {
        return StringUtils.isBlank(v) ? null : v.trim().replaceAll("'", "''");
    }

    protected String getDate(ResultSet rs, String column) throws SQLException {
        Date date = rs.getDate(column);
        return date == null ? null : DateFormatUtils.format(date, "yyyy-MM-dd");
    }

    protected String getDateTime(ResultSet rs, String column) throws SQLException {
        Date date = rs.getDate(column);
        return date == null ? null : DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
    }

    protected String getTimestamp(ResultSet rs, String column) throws SQLException {
        Date date = rs.getDate(column);
        return date == null ? null : DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss.SSS");
    }

    protected int insert(String tableName, JSONObject data) {
        if (data.size() <= 0) {
            return 0;
        } else {
            StringBuffer sql = new StringBuffer();
            sql.append(" INSERT INTO ");
            sql.append(tableName + " ( ");
            Set<Map.Entry<String, Object>> set = data.entrySet();
            List<Object> sqlArgs = new ArrayList();
            Iterator iterator = set.iterator();

            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = (Map.Entry) iterator.next();
                sql.append((String) entry.getKey() + ",");
                sqlArgs.add(entry.getValue());
            }

            sql.delete(sql.length() - 1, sql.length());
            sql.append(" ) VALUES ( ");

            for (int i = 0; i < set.size(); ++i) {
                sql.append("?,");
            }

            sql.delete(sql.length() - 1, sql.length());
            sql.append(" ) ");
            return this.getJdbcTemplate().update(sql.toString(), sqlArgs.toArray());
        }
    }

    protected void insertBatch(String tableName, final List<LinkedHashMap<String, Object>> list) {
        if (list.size() > 0) {
            LinkedHashMap<String, Object> linkedHashMap = (LinkedHashMap) list.get(0);
            StringBuffer sql = new StringBuffer();
            sql.append(" INSERT INTO ");
            sql.append(tableName + " ( ");
            String[] keyset = (String[]) ((String[]) linkedHashMap.keySet().toArray(new String[linkedHashMap.size()]));

            int i;
            for (i = 0; i < linkedHashMap.size(); ++i) {
                sql.append(keyset[i] + ",");
            }

            sql.delete(sql.length() - 1, sql.length());
            sql.append(" ) VALUES ( ");

            for (i = 0; i < linkedHashMap.size(); ++i) {
                sql.append("?,");
            }

            sql.delete(sql.length() - 1, sql.length());
            sql.append(" ) ");
            this.getJdbcTemplate().batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    LinkedHashMap<String, Object> map = (LinkedHashMap) list.get(i);
                    Object[] valueset = map.values().toArray(new Object[map.size()]);
                    int len = map.keySet().size();

                    for (int j = 0; j < len; ++j) {
                        ps.setObject(j + 1, valueset[j]);
                    }

                }

                public int getBatchSize() {
                    return list.size();
                }
            });
        }
    }
}

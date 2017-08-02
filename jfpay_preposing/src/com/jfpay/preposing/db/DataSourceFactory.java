package com.jfpay.preposing.db;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

/**
 * Title : <br>
 * Project : jfpay_preposing <br>
 * Class : com.jfpay.preposing.db.DataSourceFactory <br>
 * Description : <br>
 * CopyRights (c) 2015 <br>
 * Company Yoolink Co,.Ltd <br>
 * Date : Jan 3, 2015 <br>
 * 
 * @author L7
 * @version 1.0
 */
public class DataSourceFactory {
    private static Logger logger = Logger.getLogger(DataSourceFactory.class);
    private static DataSource ds = null;

    public static DataSource getDataSource() {
        Context ctx = null;
        try {
            ctx = new InitialContext();
             ds = (DataSource) ctx.lookup("frtds");

        } catch (Exception e) {
            logger.error("|> Data source not found. " + e.toString(), e);
        }
        return ds;
    }

}

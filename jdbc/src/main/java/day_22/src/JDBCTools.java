package day_22.src;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * JDBC �Ĺ�����
 * 
 * ���а���: ��ȡ���ݿ�����, �ر����ݿ���Դ�ȷ���.
 */
public class JDBCTools {
	
	//�������ݿ������
	//�ύ����
	public static void commit(Connection connection){
		if(connection != null){
			try {
				connection.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//�ع�����
	public static void rollback(Connection connection){
		if(connection != null){
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//��ʼ����
	public static void beginTx(Connection connection){
		if(connection != null){
			try {
				connection.setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static DataSource dataSource = null;

	//���ݿ����ӳ�Ӧֻ����ʼ��һ��. 
	static{
		dataSource = new ComboPooledDataSource("c3p0");
	}
	
	public static Connection getConnection() throws Exception {
		return dataSource.getConnection();
	}

	public static void releaseDB(ResultSet resultSet, Statement statement,
			Connection connection) {

		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (connection != null) {
			try {
				//���ݿ����ӳص� Connection ������� close ʱ
				//��������Ľ��йر�, ���ǰѸ����ݿ����ӻ�黹�����ݿ����ӳ���. 
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}

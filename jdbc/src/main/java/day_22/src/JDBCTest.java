package day_22.src;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import org.junit.Test;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCTest {

	/**
	 * ���ʹ�� JDBC ���ô洢�����ݿ��еĺ�����洢����
	 */
	@Test
	public void testCallableStatment() {

		Connection connection = null;
		CallableStatement callableStatement = null;

		try {
			connection = JDBCTools.getConnection();

			// 1. ͨ�� Connection ����� prepareCall()
			// ��������һ�� CallableStatement �����ʵ��.
			// ��ʹ�� Connection ����� preparedCall() ����ʱ,
			// ��Ҫ����һ�� String ���͵��ַ���, ���ַ�������ָ����ε��ô洢����.
			String sql = "{?= call sum_salary(?, ?)}";
			callableStatement = connection.prepareCall(sql);

			// 2. ͨ�� CallableStatement ����� 
			//reisterOutParameter() ����ע�� OUT ����.
			callableStatement.registerOutParameter(1, Types.NUMERIC);
			callableStatement.registerOutParameter(3, Types.NUMERIC);
			
			// 3. ͨ�� CallableStatement ����� setXxx() �����趨 IN �� IN OUT ����. ���뽫����Ĭ��ֵ��Ϊ
			// null, ����ʹ�� setNull() ����.
			callableStatement.setInt(2, 80);
			
			// 4. ͨ�� CallableStatement ����� execute() ����ִ�д洢����
			callableStatement.execute();
			
			// 5. ��������õ��Ǵ����ز����Ĵ洢����, 
			//����Ҫͨ�� CallableStatement ����� getXxx() ������ȡ�䷵��ֵ.
			double sumSalary = callableStatement.getDouble(1);
			long empCount = callableStatement.getLong(3);
			
			System.out.println(sumSalary);
			System.out.println(empCount);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.releaseDB(null, callableStatement, connection);
		}


	}

	@Test
	public void testC3P0() throws SQLException {
		DataSource dataSource = new ComboPooledDataSource("c3p0");

		System.out.println(dataSource.getConnection());
	}

}

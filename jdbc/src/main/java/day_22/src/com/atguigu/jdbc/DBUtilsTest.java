package day_22.src.com.atguigu.jdbc;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.*;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DBUtilsTest {

	
	
	/**
	 * ScalarHandler: �ѽ����תΪһ����ֵ(��������������������ͺ��ַ����� Date ��)����
	 */
	@Test
	public void testScalarHandler(){
		Connection connection = null;
		
		try {
			connection = JDBCTools.getConnection();
			String sql = "SELECT name, email " +
					"FROM customers";
			
			Object result = queryRunner.query(connection, 
					sql, new ScalarHandler());
			
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			JDBCTools.releaseDB(null, null, connection);
		}
	}
	
	/**
	 * MapListHandler: �������תΪһ�� Map �� List
	 * Map ��Ӧ��ѯ��һ����¼: ��: SQL ��ѯ������(�����еı���), ֵ: �е�ֵ. 
	 * �� MapListHandler: ���صĶ�����¼��Ӧ�� Map �ļ���. 
	 */
	@Test
	public void testMapListHandler(){
		Connection connection = null;
		
		try {
			connection = JDBCTools.getConnection();
			String sql = "SELECT id, name, email, birth " +
					"FROM customers";
			
			List<Map<String, Object>> result = queryRunner.query(connection, 
					sql, new MapListHandler());
			
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			JDBCTools.releaseDB(null, null, connection);
		}
	}
	
	/**
	 * MapHandler: ���� SQL ��Ӧ�ĵ�һ����¼��Ӧ�� Map ����.
	 * ��: SQL ��ѯ������(�����еı���), ֵ: �е�ֵ. 
	 */
	@Test
	public void testMapHandler(){
		Connection connection = null;
		
		try {
			connection = JDBCTools.getConnection();
			String sql = "SELECT id, name, email, birth " +
					"FROM customers";
			
			Map<String, Object> result = queryRunner.query(connection, 
					sql, new MapHandler());
			
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			JDBCTools.releaseDB(null, null, connection);
		}
	}
	
	/**
	 * BeanListHandler: �ѽ����תΪһ�� List, �� List ��Ϊ null, ������Ϊ
	 * �ռ���(size() �������� 0)
	 * �� SQL ����ȷ�ܹ���ѯ����¼, List �д�Ŵ��� BeanListHandler ����� Class
	 * �����Ӧ�Ķ���. 
	 */
	@Test
	public void testBeanListHandler(){
		Connection connection = null;
		
		try {
			connection = JDBCTools.getConnection();
			String sql = "SELECT id, name, email, birth " +
					"FROM customers";
			
			Object customers = queryRunner.query(connection,
					sql, new BeanListHandler(Customer.class));
			
			System.out.println(customers);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			JDBCTools.releaseDB(null, null, connection);
		}
	}
	
	/**
	 * BeanHandler: �ѽ�����ĵ�һ����¼תΪ���� BeanHandler ����ʱ����� Class
	 * ������Ӧ�Ķ���. 
	 */
	@Test
	public void testBeanHanlder(){
		Connection connection = null;
		
		try {
			connection = JDBCTools.getConnection();
			String sql = "SELECT id, name customerName, email, birth " +
					"FROM customers WHERE id >= ?";
			
			Object customer = queryRunner.query(connection,
					sql, new BeanHandler(Customer.class), 5);
			
			System.out.println(customer);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			JDBCTools.releaseDB(null, null, connection);
		}
	}
	
	QueryRunner queryRunner = new QueryRunner();

	class MyResultSetHandler implements ResultSetHandler{

		@Override
		public Object handle(ResultSet resultSet) 
				throws SQLException {
//			System.out.println("handle....");
//			return "atguigu";
			
			List<Customer> customers = new ArrayList<>();
			
			while(resultSet.next()){
				Integer id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				String email = resultSet.getString(3);
				Date birth = resultSet.getDate(4);
				
				Customer customer = 
						new Customer(id, name, email, birth);
				customers.add(customer);
			}
			
			return customers;
		}
		
	}
	
	/**
	 * QueryRunner �� query �����ķ���ֵȡ������ ResultSetHandler ������
	 * handle �����ķ���ֵ
	 * 
	 */
	@Test
	public void testQuery(){
		Connection connection = null;
		
		try {
			connection = JDBCTools.getConnection();
			String sql = "SELECT id, name, email, birth " +
					"FROM customers";
			Object obj = queryRunner.query(connection, sql, 
							new MyResultSetHandler());
			
			System.out.println(obj); 
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			JDBCTools.releaseDB(null, null, connection);
		}
	}
	
	@Test
	public void testUpdate(){
		Connection connection = null;
		
		try {
			connection = JDBCTools.getConnection();
			String sql = "UPDATE customers SET name = ? " +
					"WHERE id = ?";
			queryRunner.update(connection, sql, "MIKE", 11);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			JDBCTools.releaseDB(null, null, connection);
		}
	}

}

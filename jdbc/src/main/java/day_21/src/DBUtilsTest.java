package day_21.src;

import org.apache.commons.dbutils.QueryLoader;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ���� DBUtils ������
 *
 */
public class DBUtilsTest {

	/**
	 * QueryLoader: �����������ش���� SQL ������Դ�ļ�.
	 * ʹ�ø�����԰� SQL ������û���һ����Դ�ļ���. ���ṩ���õĽ���
	 * @throws IOException 
	 */
	@Test
	public void testQueryLoader() throws IOException{
		// / ������·���ĸ�Ŀ¼. 
		Map<String, String> sqls = 
				QueryLoader.instance().load("/sql.properties");
		
		String updateSql = sqls.get("UPDATE_CUSTOMER");
		System.out.println(updateSql); 
	}
	
	/**
	 * 1. ResultSetHandler ������: QueryRunner �� query �����ķ���ֵ����ȡ����
	 * query ������ ResultHandler ������ hanlde �����ķ���ֵ. 
	 * 
	 * 2. BeanListHandler: �ѽ����תΪһ�� Bean �� List, ������. Bean ��������
	 * ���� BeanListHanlder ����ʱ�� Class ����ķ�ʽ����. ������Ӧ�еı�����ӳ�� 
	 * JavaBean ��������: 
	 * String sql = "SELECT id, name customerName, email, birth " +
	 *			"FROM customers WHERE id = ?";
	 * 
	 * BeanListHandler(Class<T> type)
	 * 
	 * 3. BeanHandler: �ѽ����תΪһ�� Bean, ������. Bean �������ڴ��� BeanHandler
	 * ����ʱ�� Class ����ķ�ʽ����
	 * BeanHandler(Class<T> type) 
	 * 
	 * 4. MapHandler: �ѽ����תΪһ�� Map ����, ������. ����������ж�����¼, ������
	 * ��һ����¼��Ӧ�� Map ����. Map �ļ�: ����(�����еı���), ֵ: �е�ֵ
	 * 
	 * 5. MapListHandler: �ѽ����תΪһ�� Map ����ļ���, ������. 
	 * Map �ļ�: ����(�����еı���), ֵ: �е�ֵ
	 * 
	 * 6. ScalarHandler: ���Է���ָ���е�һ��ֵ�򷵻�һ��ͳ�ƺ�����ֵ. 
	 */
	
	@Test
	public void testScalarHandler(){
		Connection connection = null;
		QueryRunner queryRunner = new QueryRunner();
		
		String sql = "SELECT name FROM customers " +
				"WHERE id = ?";
		
		try {
			connection = JDBCTools.getConnection();
			Object count = queryRunner.query(connection, sql, 
					new ScalarHandler(), 6);
			
			System.out.println(count); 
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			JDBCTools.releaseDB(null, null, connection);
		}
	}
	
	@Test
	public void testMapListHandler(){
		Connection connection = null;
		QueryRunner queryRunner = new QueryRunner();
		
		String sql = "SELECT id, name, email, birth " +
				"FROM customers";
		
		try {
			connection = JDBCTools.getConnection();
			List<Map<String, Object>> mapList = queryRunner.query(connection, 
					sql, new MapListHandler());
			
			System.out.println(mapList); 
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			JDBCTools.releaseDB(null, null, connection);
		}
	}
	
	@Test
	public void testMapHandler(){
		Connection connection = null;
		QueryRunner queryRunner = new QueryRunner();
		
		String sql = "SELECT id, name customerName, email, birth " +
				"FROM customers WHERE id = ?";
		
		try {
			connection = JDBCTools.getConnection();
			Map<String, Object> map = queryRunner.query(connection, 
					sql, new MapHandler(), 4);
			
			System.out.println(map); 
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			JDBCTools.releaseDB(null, null, connection);
		}
	}
	
	/**
	 * ���� ResultSetHandler �� BeanListHandler ʵ����
	 * BeanListHandler: �ѽ����תΪһ�� Bean �� List. �� Bean
	 * �������ڴ��� BeanListHandler ����ʱ����:
	 * 
	 * new BeanListHandler<>(Customer.class)
	 * 
	 */
	@Test
	public void testBeanListHandler(){
		String sql = "SELECT id, name customerName, email, birth " +
				"FROM customers";
		
		//1. ���� QueryRunner ����
		QueryRunner queryRunner = new QueryRunner();
		
		Connection conn = null;
		
		try {
			conn = JDBCTools.getConnection();
			
			Object object = queryRunner.query(conn, sql, 
					new BeanListHandler<>(Customer.class)); 			
			
			System.out.println(object); 
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			JDBCTools.releaseDB(null, null, conn);
		}
	}

	/**
	 * ���� QueryRunner �� query ����
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testResultSetHandler(){
		String sql = "SELECT id, name, email, birth " +
				"FROM customers";
		
		//1. ���� QueryRunner ����
		QueryRunner queryRunner = new QueryRunner();
		
		Connection conn = null;
		
		try {
			conn = JDBCTools.getConnection();
			/**
			 * 2. ���� query ����:
			 * ResultSetHandler ����������: query �����ķ���ֱֵ��ȡ���� 
			 * ResultSetHandler �� hanlde(ResultSet rs) �����ʵ�ֵ�. ʵ����, ��
			 * QueryRunner ��� query ������Ҳ�ǵ����� ResultSetHandler �� handle()
			 * ������Ϊ����ֵ�ġ�
			 */
			Object object = queryRunner.query(conn, sql, 
					new ResultSetHandler(){
						@Override
						public Object handle(ResultSet rs) throws SQLException {
							List<Customer> customers = new ArrayList<>();
							
							while(rs.next()){
								int id = rs.getInt(1);
								String name = rs.getString(2);
								String email = rs.getString(3);
								Date birth = rs.getDate(4);
								
								Customer customer = 
										new Customer(id, name, email, birth);
								customers.add(customer);
							}
							
							return customers;
						}
					}
			
					);			
			
			System.out.println(object); 
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			JDBCTools.releaseDB(null, null, conn);
		}
		
	}
	
	/**
	 * ���� QueryRunner ��� update ����
	 * �÷��������� INSERT, UPDATE �� DELETE
	 */
	@Test
	public void testQueryRunnerUpdate() {
		//1. ���� QueryRunner ��ʵ����
		QueryRunner queryRunner = new QueryRunner();
		
		String sql = "DELETE FROM customers " +
				"WHERE id IN (?,?)";
		
		Connection connection = null;
		
		try {
			connection = JDBCTools.getConnection();
			//2. ʹ���� update ����
			queryRunner.update(connection, 
					sql, 12, 13);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			JDBCTools.releaseDB(null, null, connection);
		}
		
	}

}

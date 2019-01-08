package day_20.src.com.atguigu.jdbc.review;

import day_20.src.com.atguigu.jdbc.JDBCTools;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;


/**
 * 1. ʹ�� PreparedStatement: Statement ���ӽӿ�, ����ִ�д�ռλ���� SQL
 * 2. ʹ�� JDBC ��Ԫ���� ResultSetMetaData. �ͷ�����дһ��ͨ�õĲ�ѯ��
 */
public class ReviewTest {

	@Test
	public void testResultSetMetaData(){
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = JDBCTools.getConnection();
			String sql = "SELECT id, name cusstomerName, email, birth " +
					"FROM customers";
			
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			
			//1. �õ� ResultSetMetaData ����: ���� ResultSet ��  getMetaData() ����
			ResultSetMetaData rsmd = resultSet.getMetaData();
			
			//2. �õ���������ж�����, �Լ�ÿһ�е��еı�������ʲô
			//getColumnCount(), getColumnLabel()
			int columnCount = rsmd.getColumnCount();
			System.out.println(columnCount);
			
			for(int i = 0; i < columnCount; i++){
				String columnLabel = rsmd.getColumnLabel(i + 1);
				System.out.println(columnLabel); 
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			JDBCTools.releaseDB(resultSet, preparedStatement, connection);
		}
		
	}
	
	@Test
	public void testPreparedStatement(){
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = JDBCTools.getConnection();
			
			//1. ��ȡ PreparedStatement 
			String sql = "UPDATE customers SET email = ? " +
					"WHERE id = ?";
			preparedStatement = connection.prepareStatement(sql);

			//2. ���ռλ��
			preparedStatement.setString(1, "atguigu@gmail.com");
			preparedStatement.setInt(2, 5);
			
			//3. ���� executeUpdate() �� executeQuery() ����ִ�� SQL ����. 
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			JDBCTools.releaseDB(null, preparedStatement, connection);
		}
		
	}
	
}

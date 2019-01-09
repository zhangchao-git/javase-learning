package day_20.src;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.junit.Test;

public class MetaDataTest {

	/**
	 * ResultSetMetaData: �����������Ԫ����. 
	 * ���Եõ�������еĻ�����Ϣ: �����������Щ��, ����, �еı�����.
	 * ��Ϸ������д��ͨ�õĲ�ѯ����. 
	 */
	@Test
	public void testResultSetMetaData(){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = JDBCTools.getConnection();
			String sql = "SELECT id, name customerName, email, birth " +
					"FROM customers";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			//1. �õ� ResultSetMetaData ����
			ResultSetMetaData rsmd = resultSet.getMetaData();
			
			//2. �õ��еĸ���
			int columnCount = rsmd.getColumnCount();
			System.out.println(columnCount);
			
			for(int i = 0 ; i < columnCount; i++){
				//3. �õ�����
				String columnName = rsmd.getColumnName(i + 1);
				
				//4. �õ��еı���
				String columnLabel = rsmd.getColumnLabel(i + 1);
				
				System.out.println(columnName + ", " + columnLabel);
			}
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			JDBCTools.releaseDB(resultSet, preparedStatement, connection);
		}
	}
	
	/**
	 * DatabaseMetaData ������ ���ݿ� ��Ԫ���ݶ���.
	 * ������ Connection �õ�. 
	 * �˽�. 
	 */
	@Test
	public void testDatabaseMetaData(){
		Connection connection = null;
		ResultSet resultSet = null;
		
		try {
			connection = JDBCTools.getConnection();
			DatabaseMetaData data = connection.getMetaData();
			
			//���Եõ����ݿⱾ���һЩ������Ϣ
			//1. �õ����ݿ�İ汾��
			int version = data.getDatabaseMajorVersion();
			System.out.println(version);
			
			//2. �õ����ӵ����ݿ���û���
			String user = data.getUserName();
			System.out.println(user);
			
			//3. �õ� MySQL ������Щ���ݿ�
			resultSet = data.getCatalogs();
			while(resultSet.next()){
				System.out.println(resultSet.getString(1));
			}
			
			//...
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			JDBCTools.releaseDB(resultSet, null, connection);
		}
	}

}

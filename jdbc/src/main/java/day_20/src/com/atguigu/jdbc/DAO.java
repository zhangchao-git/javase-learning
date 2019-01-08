package day_20.src.com.atguigu.jdbc;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

public class DAO {

	// INSERT, UPDATE, DELETE ���������԰���������
	public void update(String sql, Object... args) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = JDBCTools.getConnection();
			preparedStatement = connection.prepareStatement(sql);

			for (int i = 0; i < args.length; i++) {
				preparedStatement.setObject(i + 1, args[i]);
			}

			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.releaseDB(null, preparedStatement, connection);
		}
	}

	// ��ѯһ����¼, ���ض�Ӧ�Ķ���
	public <T> T get(Class<T> clazz, String sql, Object... args) {
		List<T> result = getForList(clazz, sql, args);
		if(result.size() > 0){
			return result.get(0);
		}
		
		return null;
	}

	/**
	 * ���� SQL ���� Class ����, ���� SQL ����ѯ���ļ�¼��Ӧ�� Class ��Ķ���ļ���
	 * @param clazz: ���������
	 * @param sql: SQL ���
	 * @param args: ��� SQL ����ռλ���Ŀɱ����. 
	 * @return
	 */
	public <T> List<T> getForList(Class<T> clazz, 
			String sql, Object... args) {

		List<T> list = new ArrayList<>();

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			//1. �õ������
			connection = JDBCTools.getConnection();
			preparedStatement = connection.prepareStatement(sql);

			for (int i = 0; i < args.length; i++) {
				preparedStatement.setObject(i + 1, args[i]);
			}

			resultSet = preparedStatement.executeQuery();
			
			//2. ��������, �õ� Map �� List, ����һ�� Map ����
			//����һ����¼. Map �� key Ϊ reusltSet ���еı���, Map �� value
			//Ϊ�е�ֵ. 
			List<Map<String, Object>> values = 
					handleResultSetToMapList(resultSet);
			
			//3. �� Map �� List תΪ clazz ��Ӧ�� List
			//���� Map �� key ��Ϊ clazz ��Ӧ�Ķ���� propertyName, 
			//�� Map �� value ��Ϊ clazz ��Ӧ�Ķ���� propertyValue
			list = transfterMapListToBeanList(clazz, values);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.releaseDB(resultSet, preparedStatement, connection);
		}

		return list;
	}

	public <T> List<T> transfterMapListToBeanList(Class<T> clazz,
			List<Map<String, Object>> values) throws InstantiationException,
			IllegalAccessException, InvocationTargetException {

		List<T> result = new ArrayList<>();

		T bean = null;

		if (values.size() > 0) {
			for (Map<String, Object> m : values) {
				bean = clazz.newInstance();
				for (Map.Entry<String, Object> entry : m.entrySet()) {
					String propertyName = entry.getKey();
					Object value = entry.getValue();

					BeanUtils.setProperty(bean, propertyName, value);
				}
				// 13. �� Object ������뵽 list ��.
				result.add(bean);
			}
		}

		return result;
	}

	/**
	 * ��������, �õ� Map ��һ�� List, ����һ�� Map �����Ӧһ����¼
	 * 
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> handleResultSetToMapList(
			ResultSet resultSet) throws SQLException {
		// 5. ׼��һ�� List<Map<String, Object>>:
		// ��: ����еı���, ֵ: ����е�ֵ. ����һ�� Map �����Ӧ��һ����¼
		List<Map<String, Object>> values = new ArrayList<>();

		List<String> columnLabels = getColumnLabels(resultSet);
		Map<String, Object> map = null;

		// 7. ���� ResultSet, ʹ�� while ѭ��
		while (resultSet.next()) {
			map = new HashMap<>();

			for (String columnLabel : columnLabels) {
				Object value = resultSet.getObject(columnLabel);
				map.put(columnLabel, value);
			}

			// 11. ��һ����¼��һ�� Map ������� 5 ׼���� List ��
			values.add(map);
		}
		return values;
	}

	/**
	 * ��ȡ������� ColumnLabel ��Ӧ�� List
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private List<String> getColumnLabels(ResultSet rs) throws SQLException {
		List<String> labels = new ArrayList<>();

		ResultSetMetaData rsmd = rs.getMetaData();
		for (int i = 0; i < rsmd.getColumnCount(); i++) {
			labels.add(rsmd.getColumnLabel(i + 1));
		}

		return labels;
	}

	// ����ĳ����¼��ĳһ���ֶε�ֵ �� һ��ͳ�Ƶ�ֵ(һ���ж�������¼��.)
	public <E> E getForValue(String sql, Object... args) {
		
		//1. �õ������: �ý����Ӧ��ֻ��һ��, ��ֻ��һ��
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			//1. �õ������
			connection = JDBCTools.getConnection();
			preparedStatement = connection.prepareStatement(sql);

			for (int i = 0; i < args.length; i++) {
				preparedStatement.setObject(i + 1, args[i]);
			}

			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()){
				return (E) resultSet.getObject(1);
			}
		} catch(Exception ex){
			ex.printStackTrace();
		} finally{
			JDBCTools.releaseDB(resultSet, preparedStatement, connection);
		}
		//2. ȡ�ý��
		
		return null;
	}

}

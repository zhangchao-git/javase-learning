package day_20.src;

import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionTest {

	/**
	 * ��������ĸ��뼶�� �� JDBC �����п���ͨ�� Connection �� setTransactionIsolation ����������ĸ��뼶��.
	 */
	@Test
	public void testTransactionIsolationUpdate() {
		
		Connection connection = null;

		try {
			connection = JDBCTools.getConnection();
			connection.setAutoCommit(false);
			
			String sql = "UPDATE users SET balance = "
					+ "balance - 500 WHERE id = 1";
			update(connection, sql);
			
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	}
	
	@Test
	public void testTransactionIsolationRead() {
		String sql = "SELECT balance FROM users WHERE id = 1";
		Integer balance = getForValue(sql);
		System.out.println(balance); 
	}

	// ����ĳ����¼��ĳһ���ֶε�ֵ �� һ��ͳ�Ƶ�ֵ(һ���ж�������¼��.)
	public <E> E getForValue(String sql, Object... args) {

		// 1. �õ������: �ý����Ӧ��ֻ��һ��, ��ֻ��һ��
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// 1. �õ������
			connection = JDBCTools.getConnection();
			System.out.println(connection.getTransactionIsolation()); 
			
//			connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			
			preparedStatement = connection.prepareStatement(sql);

			for (int i = 0; i < args.length; i++) {
				preparedStatement.setObject(i + 1, args[i]);
			}

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return (E) resultSet.getObject(1);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			JDBCTools.releaseDB(resultSet, preparedStatement, connection);
		}
		// 2. ȡ�ý��

		return null;
	}

	/**
	 * Tom �� Jerry ��� 500 Ԫ.
	 * 
	 * ��������: 1. ����������, ÿ������ʹ�õ����Լ��ĵ���������, ���޷���֤����. 2. ���岽��: 1). ���������ʼǰ, ��ʼ����:
	 * ȡ�� Connection ��Ĭ���ύ��Ϊ. connection.setAutoCommit(false); 2). �������Ĳ������ɹ�,
	 * ���ύ����: connection.commit(); 3). �ع�����: �������쳣, ���� catch ���лع�����:
	 */
	@Test
	public void testTransaction() {

		Connection connection = null;

		try {

			connection = JDBCTools.getConnection();
			System.out.println(connection.getAutoCommit());

			// ��ʼ����: ȡ��Ĭ���ύ.
			connection.setAutoCommit(false);

			String sql = "UPDATE users SET balance = "
					+ "balance - 500 WHERE id = 1";
			update(connection, sql);

			int i = 10 / 0;
			System.out.println(i);

			sql = "UPDATE users SET balance = " + "balance + 500 WHERE id = 2";
			update(connection, sql);

			// �ύ����
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();

			// �ع�����
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			JDBCTools.releaseDB(null, null, connection);
		}

		/*
		 * try {
		 * 
		 * //��ʼ����: ȡ��Ĭ���ύ. connection.setAutoCommit(false);
		 * 
		 * //...
		 * 
		 * //�ύ���� connection.commit(); } catch (Exception e) { //...
		 * 
		 * //�ع����� try { connection.rollback(); } catch (SQLException e1) {
		 * e1.printStackTrace(); } } finally{ JDBCTools.releaseDB(null, null,
		 * connection); }
		 */

		// DAO dao = new DAO();
		//
		// String sql = "UPDATE users SET balance = " +
		// "balance - 500 WHERE id = 1";
		// dao.update(sql);
		//
		// int i = 10 / 0;
		// System.out.println(i);
		//
		// sql = "UPDATE users SET balance = " +
		// "balance + 500 WHERE id = 2";
		// dao.update(sql);

	}

	public void update(Connection connection, String sql, Object... args) {
		PreparedStatement preparedStatement = null;

		try {
			preparedStatement = connection.prepareStatement(sql);

			for (int i = 0; i < args.length; i++) {
				preparedStatement.setObject(i + 1, args[i]);
			}

			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.releaseDB(null, preparedStatement, null);
		}
	}

}

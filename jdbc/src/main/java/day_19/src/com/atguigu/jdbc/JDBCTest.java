package day_19.src.com.atguigu.jdbc;

import org.junit.Test;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class JDBCTest {

	@Test
	public void testResultSetMetaData() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			String sql = "SELECT flow_id flowId, type, id_card idCard, "
					+ "exam_card examCard, student_name studentName, "
					+ "location, grade " + "FROM examstudent WHERE flow_id = ?";
			
			connection = JDBCTools.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, 5);
			
			resultSet = preparedStatement.executeQuery();

			Map<String, Object> values = 
					new HashMap<String, Object>();
			
			//1. �õ� ResultSetMetaData ����
			ResultSetMetaData rsmd = resultSet.getMetaData();
			
			while(resultSet.next()){
				//2. ��ӡÿһ�е�����
				for(int i = 0; i < rsmd.getColumnCount(); i++){
					String columnLabel = rsmd.getColumnLabel(i + 1);
					Object columnValue = resultSet.getObject(columnLabel);
					
					values.put(columnLabel, columnValue);
				}
			}
			
//			System.out.println(values); 

			Class clazz = Student.class;
			
			Object object = clazz.newInstance();
			for(Map.Entry<String, Object> entry: values.entrySet()){
				String fieldName = entry.getKey();
				Object fieldValue = entry.getValue();
				
//				System.out.println(fieldName + ": " + fieldValue);
				
				ReflectionUtils.setFieldValue(object, fieldName, fieldValue);
			}
			
			System.out.println(object); 
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.releaseDB(resultSet, preparedStatement, connection);
		}
	}

	@Test
	public void testGet() {
		String sql = "SELECT id, name, email, birth "
				+ "FROM customers WHERE id = ?";
//		System.out.println(sql);

		Customer customer = get(Customer.class, sql, 5);
		System.out.println(customer);

		sql = "SELECT flow_id flowId, type, id_card idCard, "
				+ "exam_card examCard, student_name studentName, "
				+ "location, grade " + "FROM examstudent WHERE flow_id = ?";
//		System.out.println(sql);

		Student stu = get(Student.class, sql, 5);
		System.out.println(stu);
	}

	/**
	 * ͨ�õĲ�ѯ���������Ը��ݴ���� SQL��Class ���󷵻� SQL ��Ӧ�ļ�¼�Ķ���
	 * @param clazz: �������������
	 * @param sql: SQL ��䡣���ܴ�ռλ��
	 * @param args: ���ռλ���Ŀɱ������
	 * @return
	 */
	public <T> T get(Class<T> clazz, String sql, Object... args) {
		T entity = null;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			//1. �õ� ResultSet ����
			connection = JDBCTools.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			for (int i = 0; i < args.length; i++) {
				preparedStatement.setObject(i + 1, args[i]);
			}
			resultSet = preparedStatement.executeQuery();

			//2. �õ� ResultSetMetaData ����
			ResultSetMetaData rsmd = resultSet.getMetaData();
			
			//3. ����һ�� Map<String, Object> ����, ��: SQL ��ѯ���еı���, 
			//ֵ: �е�ֵ
			Map<String, Object> values = new HashMap<>();
			
			//4. ��������. ���� ResultSetMetaData ��� 3 ��Ӧ�� Map ����
			if(resultSet.next()){
				for(int i = 0; i < rsmd.getColumnCount(); i++){
					String columnLabel = rsmd.getColumnLabel(i + 1);
					Object columnValue = resultSet.getObject(i + 1);
					
					values.put(columnLabel, columnValue);
				}
			}
			
			//5. �� Map ��Ϊ�ռ�, ���÷��䴴�� clazz ��Ӧ�Ķ���
			if(values.size() > 0){
				entity = clazz.newInstance();
				
				//5. ���� Map ����, ���÷���Ϊ Class ����Ķ�Ӧ�����Ը�ֵ. 
				for(Map.Entry<String, Object> entry: values.entrySet()){
					String fieldName = entry.getKey();
					Object value = entry.getValue();
					ReflectionUtils.setFieldValue(entity, fieldName, value);
				}
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.releaseDB(resultSet, preparedStatement, connection);
		}

		return entity;
	}

	public Customer getCustomer(String sql, Object... args) {
		Customer customer = null;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = JDBCTools.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			for (int i = 0; i < args.length; i++) {
				preparedStatement.setObject(i + 1, args[i]);
			}
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				// stu = new Student();
				// stu.setFlowId(resultSet.getInt(1));
				// stu.setType(resultSet.getInt(2));
				// stu.setIdCard(resultSet.getString(3));

				customer = new Customer();
				customer.setId(resultSet.getInt(1));
				customer.setName(resultSet.getString(2));
				customer.setEmail(resultSet.getString(3));
				customer.setBirth(resultSet.getDate(4));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.releaseDB(resultSet, preparedStatement, connection);
		}

		return customer;
	}

	public Student getStudent(String sql, Object... args) {
		Student stu = null;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = JDBCTools.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			for (int i = 0; i < args.length; i++) {
				preparedStatement.setObject(i + 1, args[i]);
			}
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				stu = new Student();
				stu.setFlowId(resultSet.getInt(1));
				stu.setType(resultSet.getInt(2));
				stu.setIdCard(resultSet.getString(3));
				// ...
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.releaseDB(resultSet, preparedStatement, connection);
		}

		return stu;
	}

	/**
	 * ʹ�� PreparedStatement ����Ч�Ľ�� SQL ע������.
	 */
	@Test
	public void testSQLInjection2() {
		String username = "a' OR PASSWORD = ";
		String password = " OR '1'='1";

		String sql = "SELECT * FROM users WHERE username = ? "
				+ "AND password = ?";

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = JDBCTools.getConnection();
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				System.out.println("��¼�ɹ�!");
			} else {
				System.out.println("�û��������벻ƥ����û���������. ");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.releaseDB(resultSet, preparedStatement, connection);
		}
	}

	/**
	 * SQL ע��.
	 */
	@Test
	public void testSQLInjection() {
		String username = "a' OR PASSWORD = ";
		String password = " OR '1'='1";

		String sql = "SELECT * FROM users WHERE username = '" + username
				+ "' AND " + "password = '" + password + "'";

		System.out.println(sql);

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			connection = JDBCTools.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);

			if (resultSet.next()) {
				System.out.println("��¼�ɹ�!");
			} else {
				System.out.println("�û��������벻ƥ����û���������. ");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.releaseDB(resultSet, statement, connection);
		}
	}

	@Test
	public void testPreparedStatement() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = JDBCTools.getConnection();
			String sql = "INSERT INTO customers (name, email, birth) "
					+ "VALUES(?,?,?)";

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, "ATGUIGU");
			preparedStatement.setString(2, "simpleit@163.com");
			preparedStatement.setDate(3,
					new Date(new java.util.Date().getTime()));

			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.releaseDB(null, preparedStatement, connection);
		}
	}

	@Test
	public void testGetStudent() {
		// 1. �õ���ѯ������
		int searchType = getSearchTypeFromConsole();

		// 2. �����ѯѧ����Ϣ
		Student student = searchStudent(searchType);

		// 3. ��ӡѧ����Ϣ
		printStudent(student);
	}

	/**
	 * ��ӡѧ����Ϣ: ��ѧ���������ӡ�������Ϣ. ��������: ��ӡ���޴���.
	 * 
	 * @param student
	 */
	private void printStudent(Student student) {
		if (student != null) {
			System.out.println(student);
		} else {
			System.out.println("���޴���!");
		}
	}

	/**
	 * �����ѯѧ����Ϣ��. ����һ�� Student ����. ��������, �򷵻� null
	 * 
	 * @param searchType
	 *            : 1 �� 2.
	 * @return
	 */
	private Student searchStudent(int searchType) {

		String sql = "SELECT flowid, type, idcard, examcard,"
				+ "studentname, location, grade " + "FROM examstudent "
				+ "WHERE ";

		Scanner scanner = new Scanner(System.in);

		// 1. ��������� searchType, ��ʾ�û�������Ϣ:
		// 1.1 �� searchType Ϊ 1, ��ʾ: ���������֤��. ��Ϊ 2 ��ʾ: ������׼��֤��
		// 2. ���� searchType ȷ�� SQL
		if (searchType == 1) {
			System.out.print("������׼��֤��:");
			String examCard = scanner.next();
			sql = sql + "examcard = '" + examCard + "'";
		} else {
			System.out.print("���������֤��:");
			String examCard = scanner.next();
			sql = sql + "idcard = '" + examCard + "'";
		}

		// 3. ִ�в�ѯ
		Student student = getStudent(sql);

		// 4. �����ڲ�ѯ���, �Ѳ�ѯ�����װΪһ�� Student ����

		return student;
	}

	/**
	 * ���ݴ���� SQL ���� Student ����
	 * 
	 * @param sql
	 * @return
	 */
	private Student getStudent(String sql) {

		Student stu = null;

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			connection = JDBCTools.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);

			if (resultSet.next()) {
				stu = new Student(resultSet.getInt(1), resultSet.getInt(2),
						resultSet.getString(3), resultSet.getString(4),
						resultSet.getString(5), resultSet.getString(6),
						resultSet.getInt(7));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.releaseDB(resultSet, statement, connection);
		}

		return stu;
	}

	/**
	 * �ӿ���̨����һ������, ȷ��Ҫ��ѯ������
	 * 
	 * @return: 1. �����֤��ѯ. 2. ��׼��֤�Ų�ѯ ��������Ч. ����ʾ���û���������.
	 */
	private int getSearchTypeFromConsole() {

		System.out.print("�������ѯ����: 1. �����֤��ѯ. 2. ��׼��֤�Ų�ѯ ");

		Scanner scanner = new Scanner(System.in);
		int type = scanner.nextInt();

		if (type != 1 && type != 2) {
			System.out.println("������������������!");
			throw new RuntimeException();
		}

		return type;
	}

	@Test
	public void testAddNewStudent() {
		Student student = getStudentFromConsole();
		addNewStudent2(student);
	}

	/**
	 * �ӿ���̨����ѧ������Ϣ
	 */
	private Student getStudentFromConsole() {

		Scanner scanner = new Scanner(System.in);

		Student student = new Student();

		System.out.print("FlowId:");
		student.setFlowId(scanner.nextInt());

		System.out.print("Type: ");
		student.setType(scanner.nextInt());

		System.out.print("IdCard:");
		student.setIdCard(scanner.next());

		System.out.print("ExamCard:");
		student.setExamCard(scanner.next());

		System.out.print("StudentName:");
		student.setStudentName(scanner.next());

		System.out.print("Location:");
		student.setLocation(scanner.next());

		System.out.print("Grade:");
		student.setGrade(scanner.nextInt());

		return student;
	}

	public void addNewStudent2(Student student) {
		String sql = "INSERT INTO examstudent(flowid, type, idcard, "
				+ "examcard, studentname, location, grade) "
				+ "VALUES(?,?,?,?,?,?,?)";

		JDBCTools.update(sql, student.getFlowId(), student.getType(),
				student.getIdCard(), student.getExamCard(),
				student.getStudentName(), student.getLocation(),
				student.getGrade());
	}

	public void addNewStudent(Student student) {
		// 1. ׼��һ�� SQL ���:
		String sql = "INSERT INTO examstudent VALUES(" + student.getFlowId()
				+ "," + student.getType() + ",'" + student.getIdCard() + "','"
				+ student.getExamCard() + "','" + student.getStudentName()
				+ "','" + student.getLocation() + "'," + student.getGrade()
				+ ")";

		System.out.println(sql);

		// 2. ���� JDBCTools ��� update(sql) ����ִ�в������.
		JDBCTools.update(sql);
	}

}

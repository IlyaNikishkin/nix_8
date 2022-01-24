package ua.com.alevel.persistence.dao.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.config.JpaConfig;
import ua.com.alevel.persistence.dao.StudentDao;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Group;
import ua.com.alevel.persistence.entity.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class StudentDaoImpl implements StudentDao {

    JpaConfig jpaConfig;

    StudentDaoImpl(JpaConfig jpaConfig) {
        this.jpaConfig = jpaConfig;
    }

    private final static String CREATE_STUDENT_QUERY = "insert into students_table values (default,?,?,?,?)";
    private final static String CREATE_RELATION_GROUP_STUDENT_QUERY = "insert into groups_students_table values (?,?)";
    private final static String UPDATE_STUDENT_QUERY = "update students_table, groups_students_table set updated = ?, name = ?, age = ?, group_id = ? where students_table.id = ? AND groups_students_table.student_id = ?";
    private final static String DELETE_STUDENT_BY_ID_QUERY = "delete from students_table where id = ";
    private final static String DELETE_STUDENT_BY_GROUP_QUERY = "delete from students_table where id in (select student_id from groups_students_table where group_id = ";
    private final static String EXIST_STUDENT_BY_ID_QUERY = "select count(*) from students_table where id = ";
    private final static String FIND_STUDENT_BY_ID_QUERY = "select * from students_table as st join groups_students_table gst on st.id = gst.student_id join groups_table gt on gt.id = gst.group_id where st.id = ";
    private final static String FIND_ALL_STUDENTS_QUERY = "select * from students_table as st join groups_students_table gst on st.id = gst.student_id join groups_table gt on gt.id = gst.group_id order by st.";
    private final static String FIND_ALL_STUDENTS_BY_GROUP_QUERY = "select * from students_table as st join groups_students_table gst on st.id = gst.student_id join groups_table gt on gt.id = gst.group_id where gt.id = ";
    private final static String FIND_ALL_STUDENTS_COUNT = "select count(*) from students_table";
    private final static String FIND_ALL_STUDENTS_BY_GROUP_COUNT = "select count(*) from students_table as st join groups_students_table gst on st.id = gst.student_id join groups_table gt on gt.id = gst.group_id where gt.id = ";

    @Override
    public void create(Student entity) {
        long studentId = 0;
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(CREATE_STUDENT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setTimestamp(1, new Timestamp(entity.getCreated().getTime()));
            preparedStatement.setTimestamp(2, new Timestamp(entity.getUpdated().getTime()));
            preparedStatement.setString(3, entity.getName());
            preparedStatement.setInt(4, entity.getAge());
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    studentId = generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Creating student failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        createRelationGroupStudent(studentId, entity.getGroup().getId());
    }

    @Override
    public void update(Student entity) {
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(UPDATE_STUDENT_QUERY)) {
            preparedStatement.setTimestamp(1, new Timestamp(entity.getUpdated().getTime()));
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setInt(3, entity.getAge());
            preparedStatement.setLong(4, entity.getGroup().getId());
            preparedStatement.setLong(5, entity.getId());
            preparedStatement.setLong(6, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(DELETE_STUDENT_BY_ID_QUERY + id)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean existById(Long id) {
        int count = 0;
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(EXIST_STUDENT_BY_ID_QUERY + id)) {
            if (resultSet.next()) {
                count = resultSet.getInt("count(*)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count == 1;
    }

    @Override
    public Student findById(Long id) {
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(FIND_STUDENT_BY_ID_QUERY + id)) {
            if (resultSet.next()) {
                return convertResultSetToStudent(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public DataTableResponse<Student> findAll(DataTableRequest request) {
        List<Student> students = new ArrayList<>();
        int limit = (request.getCurrentPage() - 1) * request.getPageSize();
        String sqlFindWithParams = FIND_ALL_STUDENTS_QUERY +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                limit + "," +
                request.getPageSize();
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(sqlFindWithParams)) {
            while (resultSet.next()) {
                students.add(convertResultSetToStudent(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("problem: = " + e.getMessage());
        }
        DataTableResponse<Student> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(students);
        return dataTableResponse;
    }

    @Override
    public long count() {
        int count = 0;
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(FIND_ALL_STUDENTS_COUNT)) {
            if (resultSet.next()) {
                count = resultSet.getInt("count(*)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public long countByGroupId(Long groupId) {
        int count = 0;
        String sql = FIND_ALL_STUDENTS_BY_GROUP_COUNT + groupId;
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(sql)) {
            if (resultSet.next()) {
                count = resultSet.getInt("count(*)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public void deleteAllByGroupId(Long groupId) {
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(DELETE_STUDENT_BY_GROUP_QUERY + groupId + ")")) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public DataTableResponse<Student> findAllStudentsByGroup(DataTableRequest request, Long groupId) {
        List<Student> students = new ArrayList<>();
        int limit = (request.getCurrentPage() - 1) * request.getPageSize();
        String sqlFindWithParams = FIND_ALL_STUDENTS_BY_GROUP_QUERY +
                groupId + " order by st." +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                limit + "," +
                request.getPageSize();
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(sqlFindWithParams)) {
            while (resultSet.next()) {
                students.add(convertResultSetToStudent(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("problem: = " + e.getMessage());
        }
        DataTableResponse<Student> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(students);
        return dataTableResponse;
    }

    @Override
    public void createRelationGroupStudent(Long studentId, Long groupId) {
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(CREATE_RELATION_GROUP_STUDENT_QUERY)) {
            preparedStatement.setLong(1, studentId);
            preparedStatement.setLong(2, groupId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Student convertResultSetToStudent(ResultSet resultSet) throws SQLException {
        Long studentId = resultSet.getLong("st.id");
        Timestamp employeeCreated = resultSet.getTimestamp("st.created");
        Timestamp employeeUpdated = resultSet.getTimestamp("st.updated");
        String studentName = resultSet.getString("st.name");
        int age = resultSet.getInt("age");

        Long groupId = resultSet.getLong("gt.id");
        Timestamp groupCreated = resultSet.getTimestamp("gt.created");
        Timestamp groupUpdated = resultSet.getTimestamp("gt.updated");
        String groupName = resultSet.getString("gt.name");

        Group group = new Group();
        group.setId(groupId);
        group.setCreated(new Date(groupCreated.getTime()));
        group.setUpdated(new Date(groupUpdated.getTime()));
        group.setName(groupName);

        Student student = new Student();
        student.setId(studentId);
        student.setCreated(new Date(employeeCreated.getTime()));
        student.setUpdated(new Date(employeeUpdated.getTime()));
        student.setName(studentName);
        student.setAge(age);
        student.setGroup(group);
        return student;
    }
}

package ua.com.alevel.persistence.dao.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.config.JpaConfig;
import ua.com.alevel.persistence.dao.GroupDao;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Group;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GroupDaoImpl implements GroupDao {

    JpaConfig jpaConfig;

    GroupDaoImpl(JpaConfig jpaConfig) {
        this.jpaConfig = jpaConfig;
    }

    private final static String CREATE_GROUP_QUERY = "insert into groups_table values (default,?,?,?,?)";
    private final static String UPDATE_GROUP_QUERY = "update groups_table set updated = ?, name = ?, grade = ? where id = ";
    private final static String DELETE_GROUP_BY_ID_QUERY = "delete from groups_table where id = ";
    private final static String EXIST_GROUP_BY_ID_QUERY = "select count(*) from groups_table where id = ";
    public final static String FIND_GROUP_BY_ID_QUERY = "select * from groups_table where id = ";
    private final static String FIND_ALL_GROUPS_QUERY = "select * from groups_table order by ";
    private final static String FIND_ALL_GROUPS_COUNT = "select count(*) from groups_table";

    @Override
    public void create(Group entity) {
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(CREATE_GROUP_QUERY)) {
            preparedStatement.setTimestamp(1, new Timestamp(entity.getCreated().getTime()));
            preparedStatement.setTimestamp(2, new Timestamp(entity.getUpdated().getTime()));
            preparedStatement.setString(3, entity.getName());
            preparedStatement.setInt(4, entity.getGrade());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Group entity) {
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(UPDATE_GROUP_QUERY + entity.getId())) {
            preparedStatement.setTimestamp(1, new Timestamp(entity.getUpdated().getTime()));
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setInt(3, entity.getGrade());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        try (PreparedStatement preparedStatement = jpaConfig.getConnection().prepareStatement(DELETE_GROUP_BY_ID_QUERY + id)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean existById(Long id) {
        int count = 0;
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(EXIST_GROUP_BY_ID_QUERY + id)) {
            if (resultSet.next()) {
                count = resultSet.getInt("count(*)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count == 1;
    }

    @Override
    public Group findById(Long id) {
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(FIND_GROUP_BY_ID_QUERY + id)) {
            if (resultSet.next()) {
                return convertResultSetToGroup(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public DataTableResponse<Group> findAll(DataTableRequest request) {
        List<Group> groups = new ArrayList<>();
        int limit = (request.getCurrentPage() - 1) * request.getPageSize();
        String sqlFindWithParams = FIND_ALL_GROUPS_QUERY +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                limit + "," +
                request.getPageSize();
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(sqlFindWithParams)) {
            while (resultSet.next()) {
                groups.add(convertResultSetToGroup(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("problem: = " + e.getMessage());
        }
        DataTableResponse<Group> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(groups);
        return dataTableResponse;
    }

    @Override
    public long count() {
        int count = 0;
        try (ResultSet resultSet = jpaConfig.getStatement().executeQuery(FIND_ALL_GROUPS_COUNT)) {
            if (resultSet.next()) {
                count = resultSet.getInt("count(*)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public Group convertResultSetToGroup(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        Timestamp created = resultSet.getTimestamp("created");
        Timestamp updated = resultSet.getTimestamp("updated");
        String name = resultSet.getString("name");
        int grade = resultSet.getInt("grade");
        Group group = new Group();
        group.setId(id);
        group.setCreated(new Date(created.getTime()));
        group.setUpdated(new Date(updated.getTime()));
        group.setName(name);
        group.setGrade(grade);
        return group;
    }
}

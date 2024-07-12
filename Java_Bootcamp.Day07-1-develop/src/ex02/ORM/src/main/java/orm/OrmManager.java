package orm;

import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrmManager {
    private final Connection connection;
    private String tableName;

    public OrmManager(Connection connection) {
        this.connection = connection;
    }
    public void createTable() {
        Reflections reflections = new Reflections("models");
        Set<Class<?>> elements = reflections.getTypesAnnotatedWith(OrmEntity.class);
        List<String> classes = new ArrayList<>();
        for (Class element : elements)
            classes.add(element.getCanonicalName());
        for (String clazz : classes) {
            try {
                Class<?> temp = Class.forName(clazz);
                OrmEntity entity = temp.getAnnotation(OrmEntity.class);
                this.tableName = entity.table();
                Statement stm = this.connection.createStatement();
                String query = String.format("DROP TABLE IF EXISTS %s CASCADE;", this.tableName);
                stm.execute(query);
                System.out.println(query);
                Field[] fields = temp.getDeclaredFields();
                StringBuilder sb = new StringBuilder();
                sb.append(String.format("CREATE TABLE IF NOT EXISTS %s (", this.tableName ));
                for (int i = 0; i < fields.length; i++) {
                    if (fields[i].isAnnotationPresent(OrmColumnId.class))
                        sb.append(String.format("%s SERIAL PRIMARY KEY", fields[i].getName()));
                    if (fields[i].isAnnotationPresent(OrmColumn.class)) {
                        OrmColumn column = fields[i].getAnnotation(OrmColumn.class);
                        sb.append(String.format("%s ", column.name()));
                        switch (fields[i].getType().getSimpleName()) {
                            case "String" -> sb.append(String.format("VARCHAR(%d)", column.length()));
                            case "Integer" -> sb.append("INTEGER");
                            case "Long" -> sb.append("BIGINT");
                            case "Boolean" -> sb.append("BOOLEAN");
                        }
                    }
                    if (i < fields.length - 1)
                        sb.append(",");
                }
                sb.append(");");
                stm.execute(sb.toString());
                System.out.println(sb);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    public void save(Object entity) {
        Class<?> tmp = entity.getClass();
        Field[] fields = tmp.getDeclaredFields();
        if (!tmp.isAnnotationPresent(OrmEntity.class)) {
            System.err.println("Entity is not annotated!");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("INSERT INTO %s (", this.tableName));
        for (int i = 1; i < fields.length; i++) {
            OrmColumn column = fields[i].getAnnotation(OrmColumn.class);
            sb.append(column.name());
            if (i != fields.length - 1)
                sb.append(", ");
        }
        sb.append(") VALUES(");
        for (int i = 1; i < fields.length; i++) {
            fields[i].setAccessible(true);
            try {
                Object obj = fields[i].get(entity);
                if (fields[i].getType().getSimpleName().equals("String")) {
                    sb.append(String.format("'%s'", obj));
                } else {
                    sb.append(String.format("'%s'", obj));
                }
                if (i != fields.length - 1) {
                    sb.append(", ");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            fields[i].setAccessible(false);
        }
        sb.append(");");
        try {
            Statement statement = this.connection.createStatement();
            statement.execute(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(sb);
    }
    public void update(Object entity) {
        Class<?> tmp = entity.getClass();
        Field[] fields = tmp.getDeclaredFields();
        if (!tmp.isAnnotationPresent(OrmEntity.class)) {
            System.err.println("Entity is not annotated!");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("UPDATE %s SET ", this.tableName));
        for (int i = 1; i < fields.length; i++) {
            if (fields[i].isAnnotationPresent(OrmColumn.class)) {
                OrmColumn column = fields[i].getAnnotation(OrmColumn.class);
                sb.append(String.format("%s = ", column.name()));
            }
            fields[i].setAccessible(true);
            try {
                Object obj = fields[i].get(entity);
                if (fields[i].getType().getSimpleName().equals("String")) {
                    sb.append(String.format("'%s'", obj));
                } else {
                    sb.append(String.format("'%s'", obj));
                }
                if (i != fields.length - 1) {
                    sb.append(", ");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            fields[i].setAccessible(false);
        }
        fields[0].setAccessible(true);
        try {
            Object obj = fields[0].get(entity);
            OrmColumnId column = fields[0].getAnnotation(OrmColumnId.class);
            sb.append(String.format(" WHERE %s = %s;", column.name(), obj));
            fields[0].setAccessible(false);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        try {
            Statement statement = this.connection.createStatement();
            statement.execute(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(sb);
    }
    public <T> T findById(Long user_id, Class<T> clazz) {
        if (!clazz.isAnnotationPresent(OrmEntity.class))
            return null;
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("SELECT * FROM %s WHERE user_id = %d", this.tableName, user_id));
        T object = null;
        try {
            object = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sb.toString());
            System.out.println(sb);
            if (!rs.next()) {
                System.err.println("No such entity!");
                return null;
            }
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(OrmColumnId.class)) {
                    field.set(object, rs.getInt(1));
                } else if (field.isAnnotationPresent(OrmColumn.class)) {
                    OrmColumn column = field.getAnnotation(OrmColumn.class);
                    field.set(object, rs.getObject(column.name()));
                }
            }
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return object;
    }
}

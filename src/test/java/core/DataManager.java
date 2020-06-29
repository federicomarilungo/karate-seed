package core;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.FileUtils;

import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DataManager {

    private final JdbcTemplate jdbcTemplate;
    private static DataManager instance = null;
    public static String env = "qa"; // [qa,uat]
    public static String host = "";
    public static String authHost = "";
    public static String canal = "";
    public static String authorization = "";

    public DataManager() {
        env = Optional.ofNullable(System.getProperty("env")).orElse(env);
        System.out.println("Se ejecutar Karate en el ambiente: " + env);

        YmlData ymlData = new YmlData();
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            JsonNode node = mapper.readTree(DataManager.class
                    .getClassLoader().getResourceAsStream("application.yaml"));
            JsonNode ymlNode = node.get(env);
            ymlData = mapper.treeToValue(ymlNode, YmlData.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUser(ymlData.username);
        dataSource.setPassword(ymlData.password);
        dataSource.setUrl(ymlData.jdbcUrl);
        host = ymlData.host;
        authHost = ymlData.authHost;
        canal = ymlData.canal;
        authorization = ymlData.authorization;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public Map<String, Object> executeQueryBy(String queryName, Object... params) {
        Map<String, Object> result = new HashMap<>();
        try {
            result = jdbcTemplate.queryForMap(resolveQueryContent(queryName), params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public int executeUpdateBy(String queryName, Object... params) {
        int result = 0;
        try {
            result = jdbcTemplate.update(resolveQueryContent(queryName), params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private String resolveQueryContent(String queryName) {
        String query = null;
        try {
            query = new String(Files.readAllBytes(FileUtils.findFile(queryName)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return query;
    }

    public String getHost(){
        return host;
    }


    public String getAuthHost(){
        return authHost;
    }


    public String getCanal(){
        return canal;
    }

    public String getAuthorization(){
        return authorization;
    }

    public String getEnv(){
        return env;
    }

    public static class YmlData {
        public String username;
        public String password;
        public String jdbcUrl;
        public String host;
        public String authHost;
        public String canal;
        public String authorization;
    }
}

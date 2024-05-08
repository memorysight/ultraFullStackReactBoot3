package com.smith.ultrafullstackreact.run;


import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RunRepository {

    private final List<Run> runs = new ArrayList<Run>();

    Optional<Run> findById(Integer id){
        return runs.stream()
                .filter(run -> run.id() == id)
                .findFirst();
    }
//
//    void create(Run run){
//        runs.add(run);
//    }
//
//    List<Run> findAll(){
//        return runs;
//    }
//
//
//
//    @PostConstruct
//    private void init(){
//        runs.add(new Run(1, "First Run", LocalDateTime.now(), LocalDateTime.now().plus(1, ChronoUnit.HOURS), 3, Location.OUTDOOR));
//
//        runs.add(new Run(2, "Second Run", LocalDateTime.now(), LocalDateTime.now().plus(1, ChronoUnit.HOURS), 3, Location.OUTDOOR));
//        }
//
//
//    void update(Run run, Integer id){
//        Optional<Run> existingRun = findById(id);
//        if(existingRun.isPresent()){
//            runs.set(runs.indexOf(existingRun.get()), run);
//        }
//    }
//
//    void update(@RequestBody Run run){
//
//    }
//
//
//    public void delete(Integer id) {
//        runs.removeIf(run ->run.id().equals(id));
//
//    }

private static final Logger log =
        LoggerFactory.getLogger(RunRepository.class);

private final JdbcClient jdbcClient;

public RunRepository(JdbcClient jdbcClient) {
    this.jdbcClient =  jdbcClient;
}

public List<Run> findAll(){
    return jdbcClient.sql("select * from run")
            .query(Run.class)
            .list();
}

public void create(Run run) {
        var updated = jdbcClient.sql("INSERT INTO Run(id,title,started_on,completed_on,miles,location) values(?,?,?,?,?,?)")
                .params(List.of(run.id(),run.title(),run.startedOn(),run.completedOn(),run.miles(),run.location().toString()))
                .update();
}



    public void update(Run run, Integer id) {
        var updated = jdbcClient.sql("update run set title = ?, started_on = ?, completed_on = ?, miles = ?, location = ? where id = ?")
                .params(List.of(run.title(),run.startedOn(),run.completedOn(),run.miles(),run.location().toString(), id))
                .update();

//        Assert.state(updated == 1, "Failed to update run " + run.title());
    }

    public void delete(Integer id){
        var updated = jdbcClient.sql("delete from run where id = :id")
                        .params("id")
                                .update();

//        Assert.state(1,"Failed to delete Run" + run.title());

    }

    public int count(){
    return jdbcClient.sql("select * from run").query().listOfRows().size();

    }

    public void saveAll(List<Run> runs){
    runs.stream().forEach(this::create);
    }




    public List<Run> findByLocation(String location) {
        return jdbcClient.sql("select * from run where location = :location")
                .param( location)
                .query(Run.class)
                .list();
    }

}

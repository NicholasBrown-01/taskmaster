package com.nickbrown.taskmaster.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.nickbrown.taskmaster.model.TaskClass;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    public void insertATask(TaskClass tasks);

    @Query("SELECT * FROM TaskClass")
    public List<TaskClass> findAll();

    @Query("SELECT * FROM TaskClass ORDER BY title ASC")
    public List<TaskClass> findAllSortedByName();

    @Query("SELECT * FROM TaskClass WHERE id = :id")
    TaskClass findByAnId(long id);

    @Query("SELECT COUNT(*) FROM TaskClass")
    int getTotalTasksCount();
}

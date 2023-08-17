package com.nickbrown.taskmaster.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.nickbrown.taskmaster.daos.TaskDao;
import com.nickbrown.taskmaster.model.TaskClass;

@Database(entities = {TaskClass.class}, version = 3)
public abstract class TaskmasterDatabase extends RoomDatabase {
    public abstract TaskDao taskdao();



}

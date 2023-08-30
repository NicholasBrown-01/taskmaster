package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.BelongsTo;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.core.model.ModelIdentifier;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the TaskClass type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "TaskClasses", type = Model.Type.USER, version = 1, authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
@Index(name = "byTeam", fields = {"teamID","title"})
public final class TaskClass implements Model {
  public static final QueryField ID = field("TaskClass", "id");
  public static final QueryField TITLE = field("TaskClass", "title");
  public static final QueryField BODY = field("TaskClass", "body");
  public static final QueryField STATE = field("TaskClass", "state");
  public static final QueryField TEAM_TASK = field("TaskClass", "teamID");
  public static final QueryField TASK_IMAGE_S3_KEY = field("TaskClass", "taskImageS3Key");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String title;
  private final @ModelField(targetType="String", isRequired = true) String body;
  private final @ModelField(targetType="TasksEnum") TasksEnum state;
  private final @ModelField(targetType="Team") @BelongsTo(targetName = "teamID", targetNames = {"teamID"}, type = Team.class) Team teamTask;
  private final @ModelField(targetType="String") String taskImageS3Key;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  /** @deprecated This API is internal to Amplify and should not be used. */
  @Deprecated
   public String resolveIdentifier() {
    return id;
  }
  
  public String getId() {
      return id;
  }
  
  public String getTitle() {
      return title;
  }
  
  public String getBody() {
      return body;
  }
  
  public TasksEnum getState() {
      return state;
  }
  
  public Team getTeamTask() {
      return teamTask;
  }
  
  public String getTaskImageS3Key() {
      return taskImageS3Key;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private TaskClass(String id, String title, String body, TasksEnum state, Team teamTask, String taskImageS3Key) {
    this.id = id;
    this.title = title;
    this.body = body;
    this.state = state;
    this.teamTask = teamTask;
    this.taskImageS3Key = taskImageS3Key;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      TaskClass taskClass = (TaskClass) obj;
      return ObjectsCompat.equals(getId(), taskClass.getId()) &&
              ObjectsCompat.equals(getTitle(), taskClass.getTitle()) &&
              ObjectsCompat.equals(getBody(), taskClass.getBody()) &&
              ObjectsCompat.equals(getState(), taskClass.getState()) &&
              ObjectsCompat.equals(getTeamTask(), taskClass.getTeamTask()) &&
              ObjectsCompat.equals(getTaskImageS3Key(), taskClass.getTaskImageS3Key()) &&
              ObjectsCompat.equals(getCreatedAt(), taskClass.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), taskClass.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getTitle())
      .append(getBody())
      .append(getState())
      .append(getTeamTask())
      .append(getTaskImageS3Key())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("TaskClass {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("title=" + String.valueOf(getTitle()) + ", ")
      .append("body=" + String.valueOf(getBody()) + ", ")
      .append("state=" + String.valueOf(getState()) + ", ")
      .append("teamTask=" + String.valueOf(getTeamTask()) + ", ")
      .append("taskImageS3Key=" + String.valueOf(getTaskImageS3Key()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static TitleStep builder() {
      return new Builder();
  }
  
  /**
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static TaskClass justId(String id) {
    return new TaskClass(
      id,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      title,
      body,
      state,
      teamTask,
      taskImageS3Key);
  }
  public interface TitleStep {
    BodyStep title(String title);
  }
  

  public interface BodyStep {
    BuildStep body(String body);
  }
  

  public interface BuildStep {
    TaskClass build();
    BuildStep id(String id);
    BuildStep state(TasksEnum state);
    BuildStep teamTask(Team teamTask);
    BuildStep taskImageS3Key(String taskImageS3Key);
  }
  

  public static class Builder implements TitleStep, BodyStep, BuildStep {
    private String id;
    private String title;
    private String body;
    private TasksEnum state;
    private Team teamTask;
    private String taskImageS3Key;
    @Override
     public TaskClass build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new TaskClass(
          id,
          title,
          body,
          state,
          teamTask,
          taskImageS3Key);
    }
    
    @Override
     public BodyStep title(String title) {
        Objects.requireNonNull(title);
        this.title = title;
        return this;
    }
    
    @Override
     public BuildStep body(String body) {
        Objects.requireNonNull(body);
        this.body = body;
        return this;
    }
    
    @Override
     public BuildStep state(TasksEnum state) {
        this.state = state;
        return this;
    }
    
    @Override
     public BuildStep teamTask(Team teamTask) {
        this.teamTask = teamTask;
        return this;
    }
    
    @Override
     public BuildStep taskImageS3Key(String taskImageS3Key) {
        this.taskImageS3Key = taskImageS3Key;
        return this;
    }
    
    /**
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String title, String body, TasksEnum state, Team teamTask, String taskImageS3Key) {
      super.id(id);
      super.title(title)
        .body(body)
        .state(state)
        .teamTask(teamTask)
        .taskImageS3Key(taskImageS3Key);
    }
    
    @Override
     public CopyOfBuilder title(String title) {
      return (CopyOfBuilder) super.title(title);
    }
    
    @Override
     public CopyOfBuilder body(String body) {
      return (CopyOfBuilder) super.body(body);
    }
    
    @Override
     public CopyOfBuilder state(TasksEnum state) {
      return (CopyOfBuilder) super.state(state);
    }
    
    @Override
     public CopyOfBuilder teamTask(Team teamTask) {
      return (CopyOfBuilder) super.teamTask(teamTask);
    }
    
    @Override
     public CopyOfBuilder taskImageS3Key(String taskImageS3Key) {
      return (CopyOfBuilder) super.taskImageS3Key(taskImageS3Key);
    }
  }
  

  public static class TaskClassIdentifier extends ModelIdentifier<TaskClass> {
    private static final long serialVersionUID = 1L;
    public TaskClassIdentifier(String id) {
      super(id);
    }
  }
  
}

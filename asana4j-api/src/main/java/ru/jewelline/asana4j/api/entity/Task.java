package ru.jewelline.asana4j.api.entity;

import ru.jewelline.asana4j.api.PagedList;
import ru.jewelline.asana4j.api.clients.modifiers.RequestModifier;

import java.util.List;

/**
 * The task is the basic object around which many operations in Asana are centered. In the Asana application,
 * multiple tasks populate the middle pane according to some view parameters, and the set of selected tasks
 * determines the more detailed information presented in the details pane.
 * <p>
 * A section, at its core, is a task whose name ends with the colon character :. Sections are unique in that
 * they will be included in the memberships field of task objects returned in the API when the task is within
 * a section. As explained below they can also be used to manipulate the ordering of a task within a project.
 *
 * @api.link <a href="https://asana.com/developers/api-reference/tasks">Tasks</a>
 * @see HasId
 * @see HasName
 */
public interface Task extends HasId, HasName {

    /**
     * @return User to which this task is assigned, or null if the task is unassigned.
     * @api.field <code>assignee</code>
     * @api.access Read-write
     * @see Task.TaskBuilder#setAssignee(Object)
     */
    User getAssignee();

    /**
     * @return Scheduling status of this task for the user it is assigned to. This field will be set only if
     * {@link #getAssignee()} is not null.
     * @api.field <code>assignee_status</code>
     * @api.access Read-write
     * @see Task.AssigneeStatus
     * @see Task.TaskBuilder#setAssigneeStatus(Task.AssigneeStatus)
     */
    AssigneeStatus getAssigneeStatus();

    /**
     * @return The time at which this task was created.
     * @api.field <code>created_at</code>
     * @api.access Read-only
     */
    String getCreatedAt();

    /**
     * Returns a current completed status.
     *
     * @return <code>true</code> if the task is currently marked complete, <code>false</code> if not.
     * @api.field <code>completed</code>
     * @api.access Read-write
     * @see Task.TaskBuilder#setCompleted(boolean)
     */
    boolean isCompleted();

    /**
     * @return The time at which this task was completed, or null if the task is incomplete.
     * @api.field <code>completed_at</code>
     * @api.access Read-only
     */
    String getCompletedAt();

    /**
     * @return Date on which this task is due, or null if the task has no due date.
     * @api.field <code>due_on</code>
     * @api.access Read-write
     * @see Task.TaskBuilder#setDueOn(String)
     */
    String getDueOn();

    /**
     * @return Date and time on which this task is due, or null if the task has no due time.
     * @api.field <code>due_at</code>
     * @api.access Read-write
     * @see Task.TaskBuilder#setDueAt(long)
     */
    String getDueAt();

    // TODO add external field

    /**
     * @return Array of users following this task.
     * @api.field <code>followers</code>
     * @api.access Read-write
     * @see User
     */
    List<User> getFollowers();

    /**
     * Returns a current hearted status.
     *
     * @return <code>true</code> if the task is hearted by the authorized user, <code>false</code> if not.
     * @api.field <code>hearted</code>
     * @api.access Read-write
     * @see Task.TaskBuilder#setHearted(boolean)
     */
    boolean isHearted();

    /**
     * @return Array of users who have hearted this task.
     * @api.field <code>hearts</code>
     * @api.access Read-only
     */
    List<User> getHeartsAuthors();

    /**
     * @return The time at which this task was last modified. This does not currently reflect any changes in
     * associations such as projects or comments that may have been added or removed from the task.
     * @api.field <code>hearts</code>
     * @api.access Read-only
     */
    String getModifiedAt();

    /**
     * @return More detailed, free-form textual information associated with the task.
     * @api.field <code>notes</code>
     * @api.access Read-write
     * @see Task.TaskBuilder#setNotes(String)
     */
    String getNotes();

    /**
     * @return The number of users who have hearted this task.
     * @api.field <code>num_hearts</code>
     * @api.access Read-only
     */
    int getNumberOfHearts();

    /**
     * @return Array of projects this task is associated with.
     * @api.field <code>projects</code>
     * @api.access Read-write
     * @see Task.TaskCreator#setProjects(long...)
     * @see #addProject(long)
     * @see #removeProject(long)
     */
    List<Project> getProjects();

    /**
     * @return The parent of this task, or null if this is not a subtask.
     * @api.field <code>parent</code>
     * @api.access Read-write
     * @see Task.TaskCreator#setParent(long)
     * @see #setParentTask(Long) (long)
     */
    Task getParent();

    /**
     * @return The workspace this task is associated with. Once created, task cannot be moved to a
     * different workspace. This attribute can only be specified at creation time.
     * @api.field <code>workspace</code>
     * @api.access Create-only
     * @see Task.TaskCreator#setWorkspace(long)
     * @see Workspace
     */
    Workspace getWorkspace();

    /**
     * Returns a section status for the task. (Checks that {@link #getName()} ends with colon character).
     *
     * @return <code>true</code> if the task is a section, <code>false</code> if not.
     * @see Project
     * @see #getName()
     */
    boolean isSection();

    // TODO add membership and tags fields

    /**
     * This method removes the task.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @api.link <a href="https://asana.com/developers/api-reference/tasks#delete">Delete a task</a>
     */
    void delete();

    /**
     * Starts an update process for the task.
     *
     * @return A specific builder which allows to update required fields and save these changes (or discard).
     * @throws IllegalStateException if another update process is in progress
     * @api.link <a href="https://asana.com/developers/api-reference/tasks#update">Update a task</a>
     * @see Task.TaskUpdater
     */
    TaskUpdater startUpdate();

    /**
     * Returns list of subtasks for that task.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @return Returns a compact representation of all of the subtasks of a task.
     * @api.link <a href="https://asana.com/developers/api-reference/tasks#subtasks">Work with subtasks</a>
     * @see Task
     * @see ru.jewelline.asana4j.api.PagedList
     */
    PagedList<Task> getSubTasks();

    /**
     * Starts a new task creation process.
     *
     * @return A builder which allows for user to specify parameters for the new task and complete the task creation process
     * (parent task id is already set, see {@link Task.TaskCreator#setParent(long)} and
     * {@link #getId()}. You shouldn't set the {@link Task.TaskCreator#setWorkspace(long)} or
     * {@link Task.TaskCreator#setProjects(long...)}).
     * @api.link <a href="https://asana.com/developers/api-reference/tasks#subtasks">Work with subtasks</a>
     * @see Task
     * @see Task.TaskCreator
     */
    TaskCreator createSubTask();

    /**
     * Adds or removes parent for that task.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param parentTaskId id of parent task, can be <code>null</code>
     * @api.link <a href="https://asana.com/developers/api-reference/tasks#subtasks">Work with subtasks</a>
     */
    void setParentTask(Long parentTaskId);

    /**
     * Adds the task to the specified project, in the optional location specified. If no location arguments are given,
     * the task will be added to the beginning of the project.
     *
     * @param projectId id of project for which that task should be attached
     * @return A builder which allows to specify the exact task position.
     * @api.link <a href="https://asana.com/developers/api-reference/tasks#projects">Task, project, and section associations</a>
     * @see Task.AddProjectBuilder
     */
    AddProjectBuilder addProject(long projectId);

    /**
     * Removes the task from the specified project. The task will still exist in the system, but it will
     * not be in the project anymore.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @param projectId id of project from which that task should be detached
     * @api.link <a href="https://asana.com/developers/api-reference/tasks#projects">Task, project, and section associations</a>
     */
    void removeProject(long projectId);

    /**
     * Returns list of all stories for that task.
     * <p><i>Triggers HTTP communication with server</i></p>
     *
     * @return Returns the compact records for all stories on the task.
     * @param requestModifiers additional request modifiers such as pagination, requested fields and so on.
     * @api.link <a href="https://asana.com/developers/api-reference/stories#get-all">Get stories on object</a>
     * @see PagedList
     * @see RequestModifier
     */
    PagedList<Story> getStories(RequestModifier... requestModifiers);

    /**
     * Enum which holds all available assignee statuses.
     *
     * @see Task#getAssigneeStatus()
     */
    enum AssigneeStatus {
        INBOX("inbox"),
        LATER("later"),
        TODAY("today"),
        UPCOMING("upcoming"),;

        private String status;

        AssigneeStatus(String status) {
            this.status = status;
        }

        /**
         * @return A string representation of assignee status, for example: <code>inbox</code> for {@link #INBOX}
         * instance.
         */
        public String getStatusCode() {
            return this.status;
        }

        /**
         * Checks if the given value matches the assignee status of the {@link Task.AssigneeStatus}
         * instance.
         *
         * @param statusCode one of assignee statuses or null.
         * @return <code>true</code> if the assignee status matches the one from the instance.
         * @see #getAssigneeStatus()
         */
        public boolean isStatusMatch(String statusCode) {
            return this.status.equalsIgnoreCase(statusCode);
        }

        @Override
        public String toString() {
            return getStatusCode();
        }

        /**
         * Matches the <code>assigneeStatus</code> parameter to one of instances from the {@link Task.AssigneeStatus} enum
         *
         * @param assigneeStatus a string representation of assignee status or null
         * @return A {@link Task.AssigneeStatus} instance
         */
        public static AssigneeStatus getStatusByCode(String assigneeStatus) {
            for (AssigneeStatus status : AssigneeStatus.values()) {
                if (status.isStatusMatch(assigneeStatus)) {
                    return status;
                }
            }
            return AssigneeStatus.UPCOMING;
        }
    }

    /**
     * A base builder class which allows for user to set values for task fields during
     * the edit or create operation.
     */
    interface TaskBuilder<T extends TaskBuilder> {

        /**
         * Sets an assignee for the task.
         *
         * @param assigneeReference id of user or a keyword <code>me</code>, can be <code>null</code>
         * @return The builder.
         * @see Task#getAssignee()
         * @see User
         */
        T setAssignee(Object assigneeReference);

        /**
         * Sets an assignee status for the task. Should be used only if {@link Task#getAssignee()} is specified.
         *
         * @param assigneeStatus new assignee status, can be <code>null</code>
         * @return The builder.
         * @see Task#getAssignee()
         * @see Task#getAssigneeStatus()
         * @see Task.AssigneeStatus
         */
        T setAssigneeStatus(AssigneeStatus assigneeStatus);

        /**
         * Sets a completed status for the task.
         *
         * @param isCompleted <code>true</code>if the task is completed, <code>false</code> if not.
         * @return The builder.
         * @see Task#isCompleted()
         */
        T setCompleted(boolean isCompleted);

        /**
         * Sets a due date for the task.
         *
         * @param dueOn Date on which this task is due, or null if the task has no due date.
         *              This takes a date with YYYY-MM-DD format and should not be used together
         *              with {@link #setDueAt(long)}.
         * @return The builder.
         * @see Task#getDueOn()
         */
        T setDueOn(String dueOn);

        /**
         * Sets a due date for the task.
         *
         * @param dueAt Date and time on which this task is due, or null if the task has no due time.
         *              This takes a UTC timestamp and should not be used together with {@link #setDueOn(String)}.
         * @return The builder.
         * @see Task#getDueAt()
         */
        T setDueAt(long dueAt);

        /**
         * Sets a hearted status for the task.
         *
         * @param isHearted <code>true</code>if the task is hearted by the user, <code>false</code> if not.
         * @return The builder.
         * @see Task#isHearted()
         */
        T setHearted(boolean isHearted);

        /**
         * Sets a name for the task.
         *
         * @param name name for the task.
         * @return The builder.
         * @see Task#getName()
         */
        T setName(String name);

        /**
         * Sets a description for the task.
         *
         * @param notes More detailed, free-form textual information associated with the task.
         * @return The builder.
         * @see Task#getNotes()
         */
        T setNotes(String notes);

    }

    /**
     * A builder class which allows for user to specify task fields. Only touched fields will be
     * saved.
     *
     * @see Workspace#createTask()
     * @see Project#createTask()
     * @see Task#createSubTask()
     */
    interface TaskCreator extends TaskBuilder<TaskCreator> {

        /**
         * Sets a workspace for the task. Once created, task cannot be moved to a different workspace.
         * This attribute can only be specified at creation time.
         *
         * @param workspaceId id of workspace.
         * @return The builder.
         * @see Task#getWorkspace()
         * @see Workspace
         */
        TaskCreator setWorkspace(long workspaceId);

        /**
         * Sets projects which will be associated with the task. At task creation time, this array can be
         * used to add the task to many projects at once.
         *
         * @param projectIds Array of projects this task is associated with.
         * @return The builder.
         * @see Task#getProjects()
         * @see Task#addProject(long)
         * @see Task#removeProject(long)
         * @see Project
         */
        TaskCreator setProjects(long... projectIds);

        /**
         * Sets a parent task for the task.
         *
         * @param parentTaskId The parent of this task, or <code>null</code> if this is not a subtask.
         * @return The builder.
         * @see Task#getParent()
         * @see Task#setParentTask(Long)
         */
        TaskCreator setParent(long parentTaskId);

        /**
         * Creates a new task and adds it to the parent task.
         * <p><i>Triggers HTTP communication with server</i></p>
         *
         * @return The full record for the newly created task.
         * @api.link <a href="https://asana.com/developers/api-reference/tasks#create">Create a task</a>
         */
        Task create();
    }

    /**
     * A builder class which allows for user to set new values for task fields. Only touched fields will be
     * saved.
     *
     * @see Workspace#createTask()
     * @see Project#createTask()
     * @see Task#createSubTask()
     */
    interface TaskUpdater extends TaskBuilder<TaskUpdater> {
        /**
         * Dismisses changes for the task.
         *
         * @return The task with original fields.
         * @see Task
         */
        Task abandon();

        /**
         * Applies changes for the task.
         * <p><i>Triggers HTTP communication with server</i></p>
         *
         * @return The complete updated task record.
         * @api.link <a href="https://asana.com/developers/api-reference/tasks#update">Update a task</a>
         */
        Task update();
    }

    /**
     * A builder class which allows to specify the exact task position in a project or section.
     * @see Task#addProject(long)
     */
    interface AddProjectBuilder {

        /**
         * @param taskId A task id in the project to insert the task after, or <code>null</code> to insert at
         *               the beginning of the list.
         * @return The builder.
         */
        AddProjectBuilder insertAfter(Long taskId);

        /**
         * @param taskId A task id in the project to insert the task before, or <code>null</code> to insert at
         *               the end of the list.
         * @return The builder.
         */
        AddProjectBuilder insertBefore(Long taskId);

        /**
         * @param sectionId A section id in the project to insert the task into. The task will be inserted at
         *                  the top of the section. Can be <code>null</code>.
         * @return The builder.
         */
        AddProjectBuilder section(Long sectionId);

        /**
         * Adds the task to the specified project, in the optional location specified. If no location arguments
         * are given, the task will be added to the beginning of the project.
         * @api.link <a href="https://asana.com/developers/api-reference/tasks#projects">Task, project, and section associations</a>
         */
        void update();
    }
}

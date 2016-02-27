package ru.jewelline.asana4j.examples;

import ru.jewelline.asana4j.Asana;
import ru.jewelline.asana4j.api.PagedList;
import ru.jewelline.asana4j.api.clients.modifiers.Pagination;
import ru.jewelline.asana4j.api.entity.Project;
import ru.jewelline.asana4j.api.entity.ProjectColor;
import ru.jewelline.asana4j.api.entity.ProjectStatus;
import ru.jewelline.asana4j.api.entity.Tag;
import ru.jewelline.asana4j.api.entity.Task;
import ru.jewelline.asana4j.api.entity.Workspace;
import ru.jewelline.asana4j.auth.AuthenticationProperty;
import ru.jewelline.asana4j.auth.AuthenticationType;
import ru.jewelline.asana4j.core.impl.AsanaImpl;
import ru.jewelline.asana4j.utils.Base64;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreationExample {

    public static final SimpleDateFormat DUE_FORMAT = new SimpleDateFormat("YYYY-MM-dd");
    public static final Date TOMORROW = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24));

    public static void main(String[] args) throws Exception {
        Asana asana = new AsanaImpl() {
            @Override
            public Base64 getBase64Tool() {
                return null; // We don't need the base 64 tool
            }
        };
        asana.getAuthenticationService().setAuthenticationType(AuthenticationType.PERSONAL_ACCESS_TOKEN);
        // Change to correct personal token
        asana.getAuthenticationService()
                .setAuthenticationProperty(AuthenticationProperty.ACCESS_TOKEN, "0/e085aec8935735ebfe43a654286f46e5");

        // Find a workspace
        PagedList<Workspace> workspaces = asana.getWorkspacesClient().getWorkspaces(Pagination.FIRST_PAGE);
        if (workspaces.isEmpty()) {
            System.out.println("You have no workspace, it is not possible to continue.");
            System.exit(0);
        }
        Workspace workspace = workspaces.get(0);
        System.out.println("The workspace '" + workspace.getName() + "' will be used.");

        // Create a project
        Project project = workspace.createProject("Asana4J Example project")
                .startUpdate()
                .setNotes("This project was created from Asana4J client")
                .setPublic(true)
                .setDueDate(DUE_FORMAT.format(TOMORROW))
                .setColor(ProjectColor.DARK_GREEN)
                .setStatus(ProjectStatus.Color.GREEN, "Green forecast for the project")
                .update();
        System.out.println("Project '" + project.getName() + "' was created.");

        // Create tasks
        project.createTask()
                .setName("Rate the project")
                .setNotes("Star Asana4J project on GitHub")
                .setDueOn(DUE_FORMAT.format(TOMORROW))
                .setHearted(true)
                .setAssignee("me")
                .create();
        project.createTask()
                .setName("Asana4J:")
                .create();

        project.createTask()
                .setName("Hearted task")
                .setHearted(true)
                .create();
        project.createTask()
                .setName("Task with description")
                .setNotes("Some task notes.")
                .create();
        project.createTask()
                .setName("Task assigned to me")
                .setAssignee("me")
                .create();
        project.createTask()
                .setName("Completed task")
                .setCompleted(true)
                .create();
        project.createTask()
                .setName("Task with due date")
                .setDueOn(DUE_FORMAT.format(TOMORROW))
                .create();
        Tag tag = asana.getTagsClient().createTag(workspace.getId())
                .setColor(ProjectColor.DARK_ORANGE)
                .setNotes("Tag description")
                .setName("Asana4J-TAG")
                .create();
        project.createTask()
                .setName("Task with tag")
                .setTags(tag.getId())
                .create();
        Task parentTask = project.createTask()
                .setName("Task with sub-task")
                .create();
        project.createTask()
                .setParent(parentTask.getId())
                .setName("Child task")
                .create();
        project.createTask()
                .setName("Task with comment")
                .create()
                .addComment("You can comment tasks from Asana4J easily!");
        project.createTask()
                .setName("Task with attachment")
                .create()
                .uploadAttachment("Asana logo.png", CreationExample.class.getResourceAsStream("/logo.png"));
        project.createTask()
                .setName("Functional examples:")
                .create();
    }
}

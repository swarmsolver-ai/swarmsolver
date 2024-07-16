package ai.swarmsolver.backend.app.tool.tools;

import ai.swarmsolver.backend.app.project.Project;
import ai.swarmsolver.backend.app.project.ProjectService;
import ai.swarmsolver.backend.app.tool.annotation.Tool;
import ai.swarmsolver.backend.app.tool.model.ParameterType;
import ai.swarmsolver.backend.app.tool.annotation.Parameter;
import ai.swarmsolver.backend.app.tool.service.ToolBean;
import org.springframework.stereotype.Component;

@Component
public class FileSystemTools implements ToolBean {

    private final ProjectService projectService;

    public FileSystemTools(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Tool("write the content of a java class to a file")
    public String writeContentToFile(@Parameter(name = "projectName") String projectName, @Parameter(name = "content", paramType = ParameterType.SOURCE_CODE)  String content, @Parameter(name = "file path") String filePath) {

        Project project = projectService.getProject(projectName);
        if (project == null) {
            return String.format("UNSUCCESSFUL: no project with name '%s' or project not accessible.", projectName);
        }

        String baseDir = project.getBaseDir();
        return new WriteContentAction().writeContentToFile(baseDir, content, filePath);
    }

    @Tool("get the location of a file in a project")
    public String getFileLocation(@Parameter(name = "projectName") String projectName, @Parameter(name = "file name") String fileName) {
        Project project = projectService.getProject(projectName);
        if (project == null) {
            return String.format("UNSUCCESSFUL: no project with name '%s' or project not accessible.", projectName);
        }

        return new FileLocationAction(project.getBaseDir()).find(fileName);

    }

    @Tool(value = "get content of a file in a project ", returnType = ParameterType.SOURCE_CODE)
    public String getFileContent(@Parameter(name="projectName") String projectName, @Parameter(name="file location") String fileLocation) {
        Project project = projectService.getProject(projectName);
        if (project == null) {
            return String.format("UNSUCCESSFUL: no project with name '%s' or project not accessible.", projectName);
        }
        return new FileContentAction(project.getBaseDir()).readFileContent(fileLocation);
    }


}

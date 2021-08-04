# IntelliJ Servlet Application Setup

## General Setup

Note that if you created the project yourself, you can probably skip this part,
however, if you cloned the project, you will need to:

1. Set the project SDK

    1. File -> Project Structure
    1. For "Project SDK", choose the version of Java you are using

1. Add the `pom.xml` as a Maven project

    1. Right click on the `pom.xml`
    1. Select "Add as Maven Project"

   When IntelliJ recognizes the project as being managed by maven, you'll see
   an "m" icon to the left of the `pom.xml`

1. Mark the `src/main/java` directory as sources root

    1. Right click on the `java` directory
    1. Select "Mark Directory as..."
    1. Select "Sources Root"

## Run Configuration

### Tell IntelliJ about `tomcat`

We need to tell IntelliJ about the local installation of tomcat that we have. We
only need to do this process once, and in the future we can reference the tomcat
installation we have setup. If you get a new computer, or wipe your harddrive,
you would need to re-do this process, otherwise, you only need to do this once.

1. In Intellij, Run -> Edit Configurations...

1. Create a new configuration (click the `+`) -- "Tomcat - local"

1. For the "Name", enter a description of your project, for example, "Adlister
   Server"

1. Next to "Application Server", click configure

1. In the dialog that opens, you will need to provide IntelliJ with the path to
   your local tomcat install.

   If you installed tomcat with brew (if you ran the codeup setup script you
   did), the path to your tomcat install will be:

        /usr/local/Cellar/tomcat/x.x.x/libexec/

   Where `x.x.x` is the version of tomcat.

   To find which version of tomcat you have installed, run:

        ls /usr/local/Cellar/tomcat

   You might see output that looks like

        8.5.23

   So overall the path might be

        /usr/local/Cellar/tomcat/8.5.23/libexec/

   *Note that your might have a different version number.*

**If you've already configured tomcat:**

1. Run -> Edit Configurations...
1. Create a new configuration (click the `+`) -- "Tomcat - local"
1. Choose a name that describes your project
1. For "Application Server", choose the version of tomcat you setup previously

### Configure a `war` to be deployed

Under the tomcat run configuration you just created:

1. At the bottom of the dialog under "Before launch:"
1. Choose "Build Artifacts"
1. Check the name of your project + `:war exploded`
   - **A note: Intellij might not create an artifact definition for you automagically.
     In this case, you will need to define one. We will work through this part together, `:war exploded` not be a presented option**
1. Check under the deployment tab, at the bottom find the input `Application context` the value there should be just `/`

The application will be available at [http://localhost:8080](http://localhost:8080/)


# Urban Workbench

Urban Workbench is a stand-alone application for integrating software for urban modeling, planning and simulation. The application is based on the Eclipse Rich Client Platfrom und available for all major operating systems.

The current proof of concept version comes with the following modules pre-installed:

* Urban Simulation Workflow Editor (also proof of concept for now)
* Parameter catalogs for energy systems
* Read-only UI for Meteorological Data

Once installed, you can add more modules to the Workbench, e.g.:

* Parameter catalogs for building physics (aka materials)
* Parameter catalogs for usage data
* Parameter catalogs for greenery
* [PyDev Python programming environment](https://www.pydev.org)
* [Java GIS Toolkit GeoTools](https://www.geotools.org)

### Installation

Download the application by clicking a link below that suits your operating system (64 bit Intel processor architecture only - AArch64 architecture for macOS and Linux are available on demand):

* [macOS (x86_64)](https://khbrassel.de/urbanworkbench/net.urbanworkbench.uw.product-macosx.cocoa.x86_64.tar.gz)
* [Windows (x86_64)](https://khbrassel.de/urbanworkbench/net.urbanworkbench.uw.product-win32.win32.x86_64.zip)
* [Linux (x86_64)](https://khbrassel.de/urbanworkbench/net.urbanworkbench.uw.product-linux.gtk.x86_64.tar.gz)

For Window/Linux:

* Extract the archive, e.g. to your desktop, and double click on `UW.exe` in the resulting folder.

For macOS:

* Extract the archive, e.g. to your desktop.
* Before starting the resulting `Eclipse.app` for the first time, start the terminal.app and execute `xattr -rd com.apple.quarantine <path to app>` to circumvent the rigid security measures of newer macOS versions like so:

![](switchOffMacSecurity.png)

When startet for the first time, the application will automatically create a directory named `UrbanWorkbenchWorkspace` in the users home directory. If you want to uninstall the app, please delete this directory as well as the extracted application (macOS) or application directory (Windows and Linux).

### First steps with the Urban Workflow Editor PoC

1. Once the workbench has started, find these buttons in the top toolbar: ![qwer](UWbuttons.png)  
1. Click the green magic wand to to provide a name for a new project, e.g. `Test`
1. Click the blue button for creating a new workflow in the selected project with default name `NewWorkflow.urbansimworkflow`
1. In the appearing workflow editor, drag workflow steps from the palette into the editor and connect them with the `Successor` tool (see [user documentation on Sirius diagram editors](https://www.eclipse.org/sirius/doc/user/diagrams/Diagrams.html))
1. Click at a step to edit its properties in the property editor
1. When done click the orange "go" button to compile a python script from the workflow diagram. It will be added to the project (visible in the model explorer) and can be opened by right click on it and choose `Open With -> Text Editor`.

### First steps with Energy Systems Parameter Catalog from git repository

To connect to a git repository, open the Import Projects from git wizard via `File → Import…​ → Git → Projects from Git → Clone URI`. Then:


1. Copy the URI of the git repository into the according input field, e.g.: `https://nextgenerations-cities.encs.concordia.ca/gitea/parameter_catalogs/energy_systems_catalog.git` and provide your credentials in fields User and Password. Tick check box Store in Secure Store and provide a master password if required! If you don’t, be prepared to be prompted for your credentials over and over again
1. Click Next > and select a repository branch to check out, usually main or master
1. Click Next > and choose the directory on your file system where to store the repository, e.g. `<user home>/git/energy-systems`. Here, we adhere to the convention is to have all git repositories stored in `<user home>/git/`
1. After data transfer has completed, the wizard offers to import existing Eclipse projects. Click Next > and select the project with suffix .data, e.g. `ca.concordia.usp.energysystemscatalog.data`.
1. Click Finish.

Now, open the imported modeling project and choose which parameter tables to show initially for inspecting and editing. To insert a new heat pump, eg., right click over table `Heat Pumps` and select command `HeatPump`. Remember to either commit and push any changes to git, or to discard them.
package net.urbanworkbench.uw.ui.wizards;

import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.Logger;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.sirius.ui.tools.api.project.ModelingProjectManager;
import org.eclipse.sirius.viewpoint.provider.SiriusEditPlugin;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;


/**
 * This Class is the wizard that creates a viewpoint modeling project. This will
 * create a project with the viewpoint modeling nature and add a aird file
 * automatically named.
 */
public class NewModelingProjectWizard extends Wizard implements INewWizard {
	@Inject Logger logger;

	/**
	 * Wizard id.
	 */
	public static final String ID = "net.urbanworkbench.uw.ui.wizard"; //$NON-NLS-1$

	/**
	 * This is a new project wizard page.
	 */
	private WizardNewProjectCreationPage newProjectPage;

	/**
	 * Default constructor.
	 */
	public NewModelingProjectWizard() {
		super();
		setNeedsProgressMonitor(true);
	}


	@Override
	public void init(final IWorkbench wkbch, final IStructuredSelection sel) {
		setWindowTitle("New Modeling Project");
//		final URL url = FrameworkUtil.getBundle(getClass()).getEntry("icons/insel9Icon48.png");
//		final ImageDescriptor imageDescriptor = ImageDescriptor.createFromURL(url);
//		setDefaultPageImageDescriptor(imageDescriptor);
	}

	@Override
	public void addPages() {
		newProjectPage = new NewModelingProjectCreationWizardPage("NewModelingProjectCreationWizardPage"); //$NON-NLS-1$
		newProjectPage.setInitialProjectName(""); //$NON-NLS-1$
		newProjectPage.setTitle("Create a Modeling Project");
		newProjectPage.setDescription("Enter a project name");
		addPage(newProjectPage);
		super.addPages();
	}

	/**
	 * Creates the project, all the directories and files and open the .odesign.
	 *
	 * @return true if successful
	 */
	@Override
	public boolean performFinish() {
		boolean finished = true;
		try {
			final String projectName = newProjectPage.getProjectName();
			final IPath locationPath = newProjectPage.getLocationPath();
			getContainer().run(true, false, new IRunnableWithProgress() {
				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					try {
						ModelingProjectManager.INSTANCE.createNewModelingProject(projectName, locationPath, true,
								monitor);
					} catch (final CoreException e) {
						throw new InvocationTargetException(e); 
					}
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			logger.log(new Status(IStatus.ERROR, SiriusEditPlugin.ID, IStatus.ERROR, e.getMessage(), e));
			finished = false;
		}
		return finished;
	}
}

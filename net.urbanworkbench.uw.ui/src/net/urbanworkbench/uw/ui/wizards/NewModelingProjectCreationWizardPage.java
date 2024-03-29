package net.urbanworkbench.uw.ui.wizards;

import java.io.File;
import java.text.MessageFormat;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.internal.resources.Project;
import org.eclipse.core.internal.utils.Messages;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;

/**
 * Override {@link WizardNewProjectCreationPage} to avoid ErrorException when
 * another project having the same project name exists on disk or workspace in a
 * different case.
 *
 */
@SuppressWarnings("restriction")
public class NewModelingProjectCreationWizardPage extends WizardNewProjectCreationPage {

	/**
	 * {@inheritDoc}.
	 * 
	 * @see org.eclipse.ui.dialogs.WizardNewProjectCreationPage.
	 *      WizardNewProjectCreationPage(String).
	 */
	public NewModelingProjectCreationWizardPage(String pageName) {
		super(pageName);
	}

	/*
	 * Prevent the user when another project having the same project name exists on
	 * disk or workspace in a different case and avoid an ErrorException.
	 */
	@Override
	protected boolean validatePage() {
		setErrorMessage(null);
		setMessage(null);
		boolean validProjectName = true;
		if (resourceExistsInWorkspace()) {
			validProjectName = false;
		}
		// check for collision with existing folder of different case on disk
		if (getProjectName() != null && !getProjectName().isBlank() && validProjectName) {
			if (resourceExistsOnDisk()) {
				validProjectName = false;
			}
		}
		return validProjectName;
	}

	/**
	 * Return true if another project having the same project name exists on disk
	 * (in a different case or not)
	 */
	private boolean resourceExistsOnDisk() {
		IFileStore store = ((Project) getProjectHandle()).getStore();
		String name = store.fetchInfo().getName();
		File folder = new File(getLocationPath().toOSString());
		// Return true if the project location have the same project name with
		// same case
		if (folder.getName().equals(getProjectName())) {
			String msg = MessageFormat.format(Messages.localstore_fileExists,
					new Path(folder.toString()).removeLastSegments(1).append(folder.getName()).toOSString());
			// Set a warning message
			setMessage(msg, 2);
		} else {
			// Return true if the project location have the same project name in different
			// case
			if (folder.getName().toUpperCase().equals(getProjectName().toUpperCase())) {
				String msg = MessageFormat.format(Messages.resources_existsLocalDifferentCase,
						new Path(folder.toString()).removeLastSegments(1).append(folder.getName()).toOSString());
				setErrorMessage(msg);
				return true;
			}
		}
		return resourceExistsInFolderLocation(store, name, folder);
	}

	/**
	 * Return true if another project having the same project name exists on the
	 * given folder (in a different case or not)
	 */
	private boolean resourceExistsInFolderLocation(IFileStore store, String name, File folder) {
		boolean projectNameExistsInSameCase = false;
		if (folder.listFiles() != null) {
			for (int i = 0; i < folder.listFiles().length && !projectNameExistsInSameCase; i++) {
				if (name != null && folder.listFiles()[i].getName().equals(getProjectName())) {
					String msg = MessageFormat.format(Messages.localstore_fileExists,
							new Path(folder.toString()).append(folder.listFiles()[i].getName()).toOSString());
					setMessage(msg, 2);
					projectNameExistsInSameCase = true;
				} else {
					if (name != null
							&& folder.listFiles()[i].getName().toUpperCase().equals(getProjectName().toUpperCase())) {
						String msg = MessageFormat.format(Messages.resources_existsLocalDifferentCase,
								new Path(folder.toString()).append(folder.listFiles()[i].getName()).toOSString());
						setErrorMessage(msg);
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Return true if another project having the same project name exists in
	 * workspace
	 */
	private boolean resourceExistsInWorkspace() {
		boolean existsInWorkspace = false;
		if (!getProjectName().isEmpty()) {
			IProject[] projects = IDEWorkbenchPlugin.getPluginWorkspace().getRoot().getProjects();
			for (int i = 0; i < projects.length && !existsInWorkspace; i++) {
				if (getProjectName().toUpperCase().equals(projects[i].getName().toUpperCase())) {
					setErrorMessage(IDEWorkbenchMessages.WizardNewProjectCreationPage_projectExistsMessage);
					existsInWorkspace = true;
				} else {
					if (getProjectName().equals(projects[i].getName())) {
						setErrorMessage(IDEWorkbenchMessages.WizardNewProjectCreationPage_projectExistsMessage);
						return true;
					}
				}
			}
		}
		return existsInWorkspace;
	}

}

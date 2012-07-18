package pl.wppiotrek.wydatki.activities.edit;

import pl.wppiotrek.wydatki.R;
import pl.wppiotrek.wydatki.entities.Project;
import pl.wppiotrek.wydatki.managers.ProjectManager;
import pl.wppiotrek.wydatki.support.AndroidGlobals;
import pl.wppiotrek.wydatki.support.ListSupport;
import pl.wppiotrek.wydatki.units.ResultCodes;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;

public class EditProjectActivity extends EditAbstractActivity<Project> {

	private CheckBox cbx_visibleForAll;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.activity_new_project);

	}

	@Override
	public void linkViews() {
		cbx_visibleForAll = (CheckBox) findViewById(R.id.new_cbx_visibleForAll);
	}

	@Override
	public void prepareToSave() {
		boolean isValid = false;
		if (isUpdate
				|| (etbx_name.getText().length() > 0 && !ListSupport
						.isProjectNameUsed(etbx_name.getText().toString()))) {
			isValid = true;
		}

		if (isValid) {
			Project project = new Project();

			if (isUpdate) {
				int projectId = currentObject.getId();
				project.setId(projectId);
			}

			project.setName(etbx_name.getText().toString());
			project.setIsActive(cbx_isActive.isChecked());
			project.setIsVisibleForAll(cbx_visibleForAll.isChecked());
			this.currentObject = project;
			saveObject();
		}
	}

	@Override
	public void saveObject() {
		super.setProgressDialogCancelable(false);
		super.setIsSendProgressBar(true);
		ProjectManager manager = new ProjectManager(this);
		if (isUpdate)
			manager.editProject(currentObject);
		else
			manager.createNewProject(currentObject);
	}

	@Override
	public void refreshActivity(boolean isForceRefresh) {
		if (isForceRefresh)
			saveObject();
	}

	@Override
	public void leaveActivity(int requestCode) {
		Intent intent = this.getIntent();
		this.setResult(requestCode, intent);
		finish();

	}

	@Override
	public void configureViews() {
		if (!isUpdate)
			currentObject = new Project();
		else
			currentObject = AndroidGlobals.getInstance()
					.getCurrentSelectedProject();

		if (isUpdate) {
			etbx_name.setText(currentObject.getName());
			cbx_isActive.setChecked(currentObject.isActive());
			cbx_visibleForAll.setChecked(currentObject.isVisibleForAll());
		}
	}

	public void onObjectsReceived(Object object, Object object2) {
		super.onObjectsReceived(object, object2);
		if (object instanceof Project) {
			AndroidGlobals globals = AndroidGlobals.getInstance();
			if (isUpdate) {
				globals.updateProjectsList((Project) object);
			} else {
				Project p = (Project) object;
				globals.getProjectsDictionary().put(p.getId(), p);
			}
			leaveActivity(ResultCodes.RESULT_NEED_UPDATE);
		}

	}

}

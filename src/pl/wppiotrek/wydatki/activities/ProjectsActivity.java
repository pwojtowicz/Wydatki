package pl.wppiotrek.wydatki.activities;

import java.util.ArrayList;
import java.util.Collection;

import pl.wppiotrek.wydatki.R;
import pl.wppiotrek.wydatki.activities.edit.EditProjectActivity;
import pl.wppiotrek.wydatki.adapters.ProjectsAdapter;
import pl.wppiotrek.wydatki.adapters.ProjectsAdapter.ProjectAdapterObjectHandler;
import pl.wppiotrek.wydatki.entities.Project;
import pl.wppiotrek.wydatki.managers.DownloadDataManager;
import pl.wppiotrek.wydatki.managers.ProjectManager;
import pl.wppiotrek.wydatki.support.AndroidGlobals;
import pl.wppiotrek.wydatki.support.DialogFactory;
import pl.wppiotrek.wydatki.units.DialogType;
import pl.wppiotrek.wydatki.units.RefreshOptions;
import pl.wppiotrek.wydatki.units.ResultCodes;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class ProjectsActivity extends ProgressActivity implements
		OnItemClickListener {

	private ProjectsAdapter adapter;
	private ListView list;
	private DownloadDataManager ddManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_projects);
		super.setProgressDialogCancelable(true);

		linkViews();
		configureViews();
		refreshActivity(false);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case R.id.menu_refresh:
			refreshActivity(true);
			break;
		}
		return true;
	}

	private void refreshList() {
		AndroidGlobals globals = AndroidGlobals.getInstance();
		Collection<Project> projectsDictionary = globals
				.getProjectsDictionary().values();

		ArrayList<Project> projects = new ArrayList<Project>();
		for (Project project : projectsDictionary) {
			projects.add(project);
		}

		if (projects != null) {
			adapter = new ProjectsAdapter(this, projects);
			list.setAdapter(adapter);
			list.setOnItemClickListener(this);
		}
	}

	@Override
	public void refreshActivity(boolean isForceRefresh) {
		if (ddManager == null) {
			ddManager = new DownloadDataManager(this,
					RefreshOptions.refreshProjects, this);
			refreshList();
		} else {

			if (isForceRefresh)
				ddManager.refresh(isForceRefresh,
						RefreshOptions.refreshProjects);
			else {
				refreshList();
			}
		}
	}

	@Override
	public void leaveActivity(int requestCode) {
		if (ddManager == null)
			ddManager.cancleDownload();
		finish();
	}

	@Override
	public void linkViews() {
		list = (ListView) findViewById(R.id.projects_listview);
		Button btn_addNew = (Button) findViewById(R.id.btn_add_new);
		btn_addNew.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				showEditActivity(false);
			}
		});

	}

	protected void showEditActivity(boolean isUpdate) {
		Intent intent = new Intent(this, EditProjectActivity.class);
		if (isUpdate) {
			Bundle b = new Bundle();
			b.putBoolean("isUpdate", true);
			intent.putExtras(b);
		}
		startActivityForResult(intent, ResultCodes.START_ACTIVITY_EDIT_PROJECT);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == ResultCodes.START_ACTIVITY_EDIT_PROJECT) {
			if (resultCode == ResultCodes.RESULT_NEED_UPDATE) {
				refreshActivity(true);
			}
		}
	}

	@Override
	public void configureViews() {
		// TODO Auto-generated method stub

	}

	public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
		super.vibrate();
		ProjectAdapterObjectHandler oh = (ProjectAdapterObjectHandler) view
				.getTag();

		Bundle b = new Bundle();

		final CharSequence[] items = {
				getText(R.string.edit),
				oh.project.isActive() ? getText(R.string.lock) + " "
						+ getText(R.string.entity_project)
						: getText(R.string.unlock) + " "
								+ getText(R.string.entity_project),
				getText(R.string.delete) };

		b.putCharSequenceArray("options", items);

		AndroidGlobals.getInstance().setCurrentSelectedProject(oh.project);

		AlertDialog dialog = DialogFactory.create(DialogType.OptionsDialog, b,
				this, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						onDialogItemSelected(item);
					}
				});
		if (dialog != null)
			dialog.show();

	}

	protected void onDialogItemSelected(int item) {
		if (item == 0) {
			showEditActivity(true);
		}
		if (item == 1) {
			changeLock();
		}
		if (item == 2) {
			removeObject();
		}
	}

	private void changeLock() {
		super.setProgressDialogCancelable(false);
		super.setIsSendProgressBar(true);
		super.setShowProgressBar(true);
		ProjectManager manager = new ProjectManager(this);
		Project project = AndroidGlobals.getInstance()
				.getCurrentSelectedProject();
		project.setIsActive(!project.isActive());
		manager.editProject(project);
	}

	private void removeObject() {
		super.setProgressDialogCancelable(false);
		super.setIsSendProgressBar(true);
		super.setShowProgressBar(true);
		ProjectManager manager = new ProjectManager(this);
		manager.deleteProject(AndroidGlobals.getInstance()
				.getCurrentSelectedProject());

	}

	@Override
	public void onObjectsReceived(Object object, Object object2) {
		AndroidGlobals globals = AndroidGlobals.getInstance();
		if (object instanceof String && ((String) object).equals("OK")) {
			globals.getProjectsDictionary().remove(
					globals.getCurrentSelectedProject().getId());
			refreshList();
		} else if (object instanceof Project) {
			globals.updateProjectsList((Project) object);
			refreshList();
		} else if (object instanceof Project[]) {
			refreshList();

		}
	}

}

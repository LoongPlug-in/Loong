package loongplugin.uml.action;

import java.util.HashMap;
import java.util.Map;

import loongplugin.uml.LoongUMLPlugin;
import loongplugin.uml.editpart.AbstractUMLEntityEditPart;
import loongplugin.uml.editpart.AttributeEditPart;
import loongplugin.uml.editpart.OperationEditPart;
import loongplugin.uml.editpart.RootEditPart;
import loongplugin.uml.model.AbstractUMLEntityModel;
import loongplugin.uml.model.Visibility;
import loongplugin.uml.model.RootModel;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.IStructuredSelection;

public class ShowPublicAction extends AbstractUMLEditorAction {

	private CommandStack stack;

	private AbstractUMLEntityModel target;
	
	public ShowPublicAction(GraphicalViewer viewer) {
		super(LoongUMLPlugin.getDefault().getResourceString("filter.public"), viewer);
		this.stack = viewer.getEditDomain().getCommandStack();
	}

	public void update(IStructuredSelection sel) {
		Object obj = sel.getFirstElement();
		if (obj != null && obj instanceof AbstractUMLEntityEditPart) {
			target = (AbstractUMLEntityModel) ((AbstractUMLEntityEditPart) obj)
					.getModel();
			setEnabled(true);
		} else if (obj != null && obj instanceof OperationEditPart) {
			target = (AbstractUMLEntityModel) ((OperationEditPart) obj)
					.getParent().getModel();
			setEnabled(true);
		} else if (obj != null && obj instanceof AttributeEditPart) {
			target = (AbstractUMLEntityModel) ((AttributeEditPart) obj)
					.getParent().getModel();
			setEnabled(true);
		} else if (obj != null && obj instanceof RootEditPart) {
			target = (RootModel) ((RootEditPart) obj).getModel();
			setEnabled(true);
		} else {
			setEnabled(false);
			target = null;
		}
	}
	
	public void run() {
		this.stack.execute(new ShowPublicCommand(target));
	}

	private static class ShowPublicCommand extends Command {

		private Map<String, Boolean> oldValue;
		private AbstractUMLEntityModel target;
		
		public ShowPublicCommand(AbstractUMLEntityModel target){
			this.target = target;
		}

		public void execute() {
			oldValue = target.getFilterProperty();
			Map<String, Boolean> newValue = new HashMap<String, Boolean>();
			newValue.put(ToggleAction.ATTRIBUTE + Visibility.PROTECTED, new Boolean(true));
			newValue.put(ToggleAction.ATTRIBUTE + Visibility.PACKAGE, new Boolean(true));
			newValue.put(ToggleAction.ATTRIBUTE + Visibility.PRIVATE, new Boolean(true));
			newValue.put(ToggleAction.OPERATION + Visibility.PROTECTED, new Boolean(true));
			newValue.put(ToggleAction.OPERATION + Visibility.PACKAGE, new Boolean(true));
			newValue.put(ToggleAction.OPERATION + Visibility.PRIVATE, new Boolean(true));
			target.setFilterProperty(newValue);
		}

		public void undo() {
			target.setFilterProperty(oldValue);
		}
	}
}
